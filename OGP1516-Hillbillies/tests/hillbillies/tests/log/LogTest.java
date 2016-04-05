package hillbillies.tests.log;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Test;
import hillbillies.model.Cube;
import hillbillies.model.CubeType;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

/**
* @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
* @version 1.0
* based on black-box testing
*/
public class LogTest {

	private static World smallWorld;

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

	@Test
	public void logFall() {
		smallWorld = createSmallWorld();
		while (smallWorld.getLogsWorld().size() == 0) {
			smallWorld.setCubeType(7, 7, 7, CubeType.WOOD);
		}
		
		Log logFound = null;
		for (Log log : smallWorld.getLogsWorld()) {
			logFound = log;
			assertTrue(Arrays.equals(log.getLocation(), new double[] {7.5 , 7.5, 7.5}));
		}
		
		while(!(logFound.getLocation()[2] == 0.5)) {
			smallWorld.advanceTime(0.1);
		}
		assertTrue(Arrays.equals(new double[] {7.5, 7.5, 0.5},logFound.getLocation()));
	}
}
