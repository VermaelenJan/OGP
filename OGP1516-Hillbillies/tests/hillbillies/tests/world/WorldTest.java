package hillbillies.tests.world;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import hillbillies.model.Cube;
import hillbillies.model.CubeType;
import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.tests.boulder.BoulderTest;
import hillbillies.tests.log.LogTest;

/**
* @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
* @version 1.0
* based on black-box testing
*/
public class WorldTest {

	private static World smallWorld;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	public World createSmallWorld() {
		DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
		Cube[][][] worldCubes = new Cube[10][11][12];
		for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
			for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
				for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
					int[] position = {xIndex, yIndex, zIndex};
					Cube cube = new Cube(position, CubeType.AIR);
					worldCubes[xIndex][yIndex][zIndex] = cube;
				}	
			}	
		}
		int[] pos1 = {0, 2, 3};
		Cube cube1 = new Cube(pos1, CubeType.ROCK);
		worldCubes[0][2][3] = cube1;
		int[] pos2 = {0, 3, 3};
		Cube cube2 = new Cube(pos2, CubeType.WOOD);
		worldCubes[0][3][3] = cube2;
		int[] pos3 = {0, 1, 3};
		Cube cube3 = new Cube(pos3, CubeType.WORKSHOP);
		worldCubes[0][1][3] = cube3;
		smallWorld = new World(worldCubes, defaultTerrainChangeListener);
		return smallWorld;
	}
	
	@Before
	public void setUp() throws Exception {
		smallWorld = createSmallWorld();
	}

	@Test
	public void constructor() {
		
		assertEquals(10, smallWorld.getNbCubesX());
		assertEquals(11, smallWorld.getNbCubesY());
		assertEquals(12, smallWorld.getNbCubesZ());
		assertEquals(0, smallWorld.getActiveFactions().size());
		assertEquals(0, smallWorld.getAllUnits().size());
		assertTrue(smallWorld.getCubeType(0, 2, 3) == CubeType.ROCK);
		assertTrue(smallWorld.getCubeType(0, 3, 3) == CubeType.WOOD);
		assertTrue(smallWorld.getCubeType(0, 1, 3) == CubeType.WORKSHOP);
		assertTrue(smallWorld.getCubeType(0, 2, 2) == CubeType.AIR);
		
	}
	
	
	@Test
	public void worldUnitFactionCap() {

		Unit unit1 = new Unit(new int[] {0, 0, 0}, "Legal", 35, 35, 35, 35);
		unit1.setWorld(smallWorld);
		assertTrue(smallWorld.getAllUnits().contains(unit1));
		assertEquals(1, smallWorld.getAllUnits().size());
		assertEquals(1, smallWorld.getActiveFactions().size());
		for (int i = 0; i<4; i++) {
			smallWorld.spawnUnit();
		}
		assertEquals(5, smallWorld.getActiveFactions().size());
		assertEquals(5, smallWorld.getAllUnits().size());
		
		boolean adding = true;
		while (adding) {
			int prevAm = smallWorld.getAllUnits().size();
			smallWorld.spawnUnit();
			int newAm = smallWorld.getAllUnits().size();
			
			if (newAm == prevAm) {
				adding = false;
			}
		}
		
		assertEquals(100, smallWorld.getAllUnits().size());
		assertEquals(5, smallWorld.getActiveFactions().size());
		
		Unit newUnit = smallWorld.spawnUnit();
		assertEquals(100, smallWorld.getAllUnits().size());
		assertEquals(5, smallWorld.getActiveFactions().size());
		assertTrue(newUnit.isTerminated());
		
		for (Faction faction : smallWorld.getActiveFactions()) {
			assertTrue(faction.getUnits().size() <= 50);
		}
	}
	
	@Test
	public void setTypeAndcaveIn() {
		smallWorld.setCubeType(9, 9, 9, CubeType.WOOD);
		assertTrue(smallWorld.getCubeType(9, 9, 9) == CubeType.WOOD);
		for (int i = 0; i<=25; i++) {
			smallWorld.advanceTime(0.2);

		}
		assertTrue(smallWorld.getCubeType(9, 9, 9) == CubeType.WOOD);
		assertTrue(smallWorld.isSolidConnectedToBorder(9, 9, 9));
		
		smallWorld.setCubeType(7, 7, 7, CubeType.WOOD);
		assertFalse(smallWorld.isSolidConnectedToBorder(7, 7, 7));
		for (int i = 0; i<=25; i++) {
			smallWorld.advanceTime(0.2);

		}
		assertFalse(smallWorld.getCubeType(7, 7, 7) == CubeType.WOOD);
		assertTrue(smallWorld.getCubeType(7, 7, 7) == CubeType.AIR);
		
		smallWorld.setCubeType(7, 7, 7, CubeType.ROCK);
		for (int i = 0; i<=25; i++) {
			smallWorld.advanceTime(0.2);

		}
		assertFalse(smallWorld.getCubeType(7, 7, 7) == CubeType.ROCK);
		assertTrue(smallWorld.getCubeType(7, 7, 7) == CubeType.AIR);
		
		smallWorld.setCubeType(9, 9, 9, CubeType.AIR);
		assertTrue(smallWorld.getCubeType(9, 9, 9) == CubeType.AIR);
	}
	
	@Test
	public void getBouldersAndLogs() {
		new LogTest().logFall();
		new BoulderTest().boulderFall();
	}
}
