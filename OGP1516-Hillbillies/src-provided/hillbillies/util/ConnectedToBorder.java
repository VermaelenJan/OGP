package hillbillies.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for a (somewhat efficient) algorithm that determines whether a
 * solid cube is connected to a border of the world through other directly
 * adjacent solid cubes.
 * 
 * The algorithm assumes two things: (1) initially, the entire world is solid;
 * (2) a cube can only transition from solid to passable.
 * 
 * USAGE: Create ONE instance of {@link ConnectedToBorder} for a world,
 * initialized with the dimensions of the world. Initially, the world is assumed
 * to be completely composed of SOLID tiles. Update the algorithm state using
 * the {@link #changeSolidToPassable(int, int, int)} method. At any point, use
 * the {@link #isSolidConnectedToBorder(int, int, int)} method to find out if a
 * cube is connected to the world.
 * 
 * @author Koen Yskout
 * 
 * @note The problem to solve is an instance of the 'decremental dynamic
 *       connectivity' graph problem; better algorithms probably exist.
 */

public class ConnectedToBorder {

	/**
	 * Create a new instance of the algorithm, initialized for a world of the
	 * given dimensions where all cubes are solid.
	 * 
	 * @param nbX
	 * @param nbY
	 * @param nbZ
	 */
	public ConnectedToBorder(int nbX, int nbY, int nbZ) {
		this.nbX = nbX;
		this.nbY = nbY;
		this.nbZ = nbZ;
		this.passable = new boolean[nbX * nbY * nbZ];
		this.notConnected = new boolean[nbX * nbY * nbZ];
	}

	/**
	 * Returns whether the cube at the given position is a solid cube that is
	 * connected to a border of the world through other directly adjacent solid
	 * cubes.
	 * 
	 * @note The result is pre-computed, so this query returns immediately.
	 * 
	 * @param x
	 *            The x-coordinate of the cube to test
	 * @param y
	 *            The y-coordinate of the cube to test
	 * @param z
	 *            The z-coordinate of the cube to test
	 * @return true if the cube is connected; false otherwise
	 */
	public boolean isSolidConnectedToBorder(int x, int y, int z) {
		int index = getIndex(x, y, z);
		return isSolid(index) && !notConnected[index];
	}

	/**
	 * Make the cube at the given position solid instead of passable, and return
	 * the list of coordinates that became connected to a border of the world
	 * due to this change.
	 * 
	 * @note This operation possibly iterates over the whole world, so it could
	 *       take some time.
	 * 
	 * @param x
	 *            The x-coordinate of the cube to make solid
	 * @param y
	 *            The y-coordinate of the cube to make solid
	 * @param z
	 *            The z-coordinate of the cube to make solid
	 * 
	 * @return The list of cube coordinates (where each coordinate is an array
	 *         {x, y, z}) that have become connected to the border by performing
	 *         this change. If non-empty, this always includes the provided
	 *         coordinate itself.
	 */
	public List<int[]> changePassableToSolid(int x, int y, int z) {
		int index = getIndex(x, y, z);
		if (isSolid(index)) {
			return Collections.emptyList();
		}
		passable[index] = false;
		List<Integer> coord = Arrays.asList(x, y, z);
		// if the coordinate is at the border, or one of its solid neighbours is known to be connected, the coordinate becomes connected as well
		// any adjacent non-connected neighbours now also become connected, and this repeats recursively
		if (isBorder(coord) || getDirectlyAdjacentSolids(coord).stream().anyMatch(c -> isSolidConnectedToBorder(c.get(0), c.get(1), c.get(2)))) {
			List<int[]> result = new ArrayList<>();
			Set<List<Integer>> alreadyMadeConnected = new HashSet<>();
			Queue<List<Integer>> stillToMakeConnected = new LinkedList<>();
			stillToMakeConnected.add(coord);
			while (!stillToMakeConnected.isEmpty()) {
				List<Integer> coordToMakeConnected = stillToMakeConnected.poll();
				if (alreadyMadeConnected.contains(coordToMakeConnected)) {
					continue;
				}
				notConnected[getIndex(coordToMakeConnected)] = false;
				alreadyMadeConnected.add(coordToMakeConnected);
				result.add(new int[] { coordToMakeConnected.get(0), coordToMakeConnected.get(1), coordToMakeConnected.get(2) });
				for (List<Integer> neighbour : getDirectlyAdjacentSolids(coordToMakeConnected)) {
					if (!alreadyMadeConnected.contains(neighbour) && !isSolidConnectedToBorder(neighbour.get(0), neighbour.get(1), neighbour.get(2))) {
						stillToMakeConnected.add(neighbour);
					}
				}
			}
			return result;
		}
		return Collections.emptyList();
	}

	
	/**
	 * Make the cube at the given position passable instead of solid, and return
	 * the list of coordinates that are no longer connected to a border of the
	 * world due to this change.
	 * 
	 * @note This operation possibly iterates over the whole world, so it could
	 *       take some time.
	 * 
	 * @param x
	 *            The x-coordinate of the cube to make passable
	 * @param y
	 *            The y-coordinate of the cube to make passable
	 * @param z
	 *            The z-coordinate of the cube to make passable
	 * 
	 * @return The list of cube coordinates (where each coordinate is an array
	 *         {x, y, z}) that have become disconnected from the border by
	 *         performing this change.
	 */
	public List<int[]> changeSolidToPassable(int x, int y, int z) {
		int index = getIndex(x, y, z);
		if (passable[index])
			return Collections.emptyList();

		passable[index] = true;
		notConnected[index] = true;

		Set<List<Integer>> knownConnectedToBorder = new HashSet<>();
		Set<List<Integer>> knownNotConnectedToBorder = new HashSet<>();
		List<int[]> changed = new ArrayList<>();
		// get all solid neighbours
		for (List<Integer> neighbour : getDirectlyAdjacentSolids(Arrays.asList(x, y, z))) {
			if (knownConnectedToBorder.contains(neighbour) || knownNotConnectedToBorder.contains(neighbour)) {
				// if we already know the state of this neighbour, go to the
				// next one
				continue;
			} else {
				// if we don't already know the state of this neighbour, try to
				// find a path to the border
				// We want to keep track of all cubes tested while trying to
				// find a path.
				Set<List<Integer>> testedWhenFindingPath = new HashSet<>();
				if (!existsPathToBorder(neighbour, knownConnectedToBorder, knownNotConnectedToBorder,
						testedWhenFindingPath)) {
					// no path is found, so all tested cubes are definitely NOT
					// connected to the border
					for (List<Integer> testedCoord : testedWhenFindingPath) {
						knownNotConnectedToBorder.add(testedCoord);
						notConnected[getIndex(testedCoord)] = true;
						changed.add(new int[] { testedCoord.get(0), testedCoord.get(1), testedCoord.get(2) });
					}
				} else {
					// neighbour is still connected via some path; don't
					// change anything (but now we also know that all tested
					// cubes are also definitely connected)
					knownConnectedToBorder.addAll(testedWhenFindingPath);
				}
			}
		}
		return changed;
	}

	/*
	 * THE FIELDS AND OPERATIONS BELOW ARE PRIVATE AND MAY NOT BE USED BY YOUR
	 * IMPLEMENTATION
	 */

	private final int nbX;
	private final int nbY;
	private final int nbZ;

	private final boolean[] passable;
	private final boolean[] notConnected;

	private int getIndex(List<Integer> coord) {
		return getIndex(coord.get(0), coord.get(1), coord.get(2));
	}

	private int getIndex(int x, int y, int z) {
		return x + y * nbX + z * (nbX * nbY);
	}

	private boolean isSolid(int index) {
		return !passable[index];
	}

	private boolean isBorder(List<Integer> coord) {
		int x = coord.get(0);
		int y = coord.get(1);
		int z = coord.get(2);
		return x == 0 || x == nbX - 1 || y == 0 || y == nbY - 1 || z == 0 || z == nbZ - 1;
	}

	private final int[][] directAdjacentOffsets = new int[][] { { -1, 0, 0 }, { +1, 0, 0 }, { 0, -1, 0 }, { 0, +1, 0 },
			{ 0, 0, -1 }, { 0, 0, +1 } };

	private boolean existsPathToBorder(List<Integer> origin, Set<List<Integer>> knownAdjacent,
			Set<List<Integer>> knownNonAdjacent, Set<List<Integer>> visited) {
		// visited == part of solid blob connected to origin that has been
		// visited (but not necessarily tested)
		Deque<List<Integer>> cubesToVisit = new LinkedList<>();
		cubesToVisit.add(origin);
		visited.add(origin);
		while (!cubesToVisit.isEmpty()) {
			// this might take a long time - check if the impatient user has
			// stopped us yet
			if (Thread.currentThread().isInterrupted()) {
				throw new IllegalStateException();
			}
			List<Integer> cube = cubesToVisit.pollFirst();
			if (knownAdjacent.contains(cube)) {
				return true;
			}
			if (knownNonAdjacent.contains(cube)) {
				return false;
			}
			if (isBorder(cube)) {
				knownAdjacent.add(cube);
				return true;
			}
			for (List<Integer> neighbour : getDirectlyAdjacentSolids(cube)) {
				if (!visited.contains(neighbour)) {
					visited.add(neighbour);
					cubesToVisit.addFirst(neighbour);
				}
			}
		}
		return false;
	}

	private List<List<Integer>> getDirectlyAdjacentSolids(List<Integer> coord) {
		return getDirectlyAdjacentCoordinates(coord).filter(c -> {
			int x = c.get(0);
			int y = c.get(1);
			int z = c.get(2);
			return x >= 0 && x < nbX && y >= 0 && y < nbY && z >= 0 && z < nbZ && isSolid(getIndex(c));
		}).collect(Collectors.toList());
	}

	private Stream<List<Integer>> getDirectlyAdjacentCoordinates(List<Integer> coord) {
		List<int[]> shuffledOffsets = new ArrayList<>(Arrays.asList(directAdjacentOffsets));
		Collections.shuffle(shuffledOffsets);
		return shuffledOffsets.stream().map(
				offset -> Arrays.asList(coord.get(0) + offset[0], coord.get(1) + offset[1], coord.get(2) + offset[2]));
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int z = 0; z < nbZ; z++) {
			for (int y = 0; y < nbY; y++) {
				for (int x = 0; x < nbX; x++) {
					int index = getIndex(x, y, z);
					result.append((isSolidConnectedToBorder(x, y, z) ? "@@" : (isSolid(index) ? "oo" : "..")));
				}
				result.append("\n");
			}
			result.append("\n");
		}
		return result.toString();
	}
}
