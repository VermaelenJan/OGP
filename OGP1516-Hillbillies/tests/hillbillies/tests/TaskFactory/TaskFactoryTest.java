package hillbillies.tests.TaskFactory;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Cube;
import hillbillies.model.CubeType;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.TaskFactory;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.programs.TaskParser;

public class TaskFactoryTest {

	private static World world;
	
	@Before
	public void setUp() throws Exception {
		DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
		Cube[][][] cubes = new Cube[3][3][3];
		for (int xIndex = 0; xIndex<cubes.length; xIndex++) {
			for (int yIndex = 0; yIndex<cubes[0].length; yIndex++) {
				for (int zIndex = 0; zIndex<cubes[0][0].length; zIndex++) {
					int[] position = {xIndex, yIndex, zIndex};
					Cube cube = new Cube(position, CubeType.AIR);
					cubes[xIndex][yIndex][zIndex] = cube;
				}	
			}	
		}

		int[] pos1 = {1, 1, 0};
		cubes[1][1][0] = new Cube(pos1, CubeType.ROCK);
		int[] pos2 = {1, 1, 1};
		cubes[1][1][1] = new Cube(pos2, CubeType.ROCK);
		int[] pos3 = {1, 1, 2};
		cubes[1][1][2] = new Cube(pos3, CubeType.WOOD);
		int[] pos4 = {1, 1, 0};
		cubes[1][1][0] = new Cube(pos4, CubeType.WORKSHOP);

		world = new World(cubes, defaultTerrainChangeListener);
	}
	
	@Test
	public void test() { //TODO!!!!!!
		Unit unit = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);
		Faction faction = unit.getFaction();
		Scheduler scheduler = faction.getScheduler();
		int[] loc = new int[] {1, 1, 1};
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 1\nactivities: work selected;", new TaskFactory(),
				Collections.singletonList(loc));


		assertNotNull(tasks);

		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		
		scheduler.addTask(task);
	}

}
