package hillbillies.part2.internal.map;

import hillbillies.part2.internal.map.ByteMap3D;

/**
 * For internal use only.
 * 
 * Representation a 3D game map, where every cube has a CubeType.
 * 
 * @author koeny
 *
 */
public class GameMap {

	private ByteMap3D map;

	public GameMap(int nbTilesX, int nbTilesY, int nbTilesZ) {
		this.map = new ByteMap3D(nbTilesX, nbTilesY, nbTilesZ);
	}

	public ByteMap3D getMap() {
		return map;
	}

	public int getNbTilesX() {
		return map.getNbX();
	}

	public int getNbTilesY() {
		return map.getNbY();
	}

	public int getNbTilesZ() {
		return map.getNbZ();
	}

	public CubeType getTypeAt(int x, int y, int z) {
		if (isValidCoordinate(x, y, z)) {
			return CubeType.fromByte(map.getValue(x, y, z));
		}
		return null;
	}

	public boolean isValidCoordinate(int x, int y, int z) {
		return 0 <= x && x < getNbTilesX() && 0 <= y && y < getNbTilesY() && 0 <= z && z < getNbTilesZ();
	}

	public void setTypeAt(int x, int y, int z, CubeType type) {
		if (isValidCoordinate(x, y, z)) {
			if (type != null)
				map.setValue(x, y, z, type.getByteValue());
			else
				map.setValue(x, y, z, (byte) 0);
		}
	}

	public static GameMap fromTypes(CubeType[][][] types) {
		int nbZ = types.length;
		int nbY = types[0].length;
		int nbX = types[0][0].length;

		GameMap result = new GameMap(nbX, nbY, nbZ);
		for (int x = 0; x < nbX; x++) {
			for (int y = 0; y < nbY; y++) {
				for (int z = 0; z < nbZ; z++) {
					result.setTypeAt(x, y, z, types[z][y][x]);
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return String.format("GameMap (%dx%dx%d)", getNbTilesX(), getNbTilesY(), getNbTilesZ());
	}
}
