package hillbillies.tests.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import hillbillies.model.Cube;
import hillbillies.model.CubeType;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.TaskFactory;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.programs.TaskParser;
import ogp.framework.util.ModelException;

public class TaskTest {

	@Test
	public void testTask() throws ModelException {
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
		cubes[2][2][2] = new Cube(pos4, CubeType.WORKSHOP);

		World world = new World(cubes, defaultTerrainChangeListener);
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
		
		assertTrue(task.isWellFormed());

		assertEquals("work task", task.getName());

		assertEquals(1, task.getPriority());
		
		assertTrue(Arrays.equals(loc, task.getSelectedCube()));
		
		assertEquals(null, task.getAssignedUnit());
		assertFalse(task.isAssigned());
		
		assertEquals(0, task.getVariables().size());
		Expression exp = new LiteralPosition(0, 0, 0, null);
		task.addVariable("name", exp, null);
		assertEquals(1, task.getVariables().size());
		assertEquals(exp, task.readVariable("name"));
		
		scheduler.addTask(task);
		unit.startDefaultBehaviour();
		world.advanceTime(0);
		unit.stopDefaultBehaviour();
		
		assertTrue(task.isAssigned());
		assertEquals(unit, task.getAssignedUnit());
		assertEquals(task, unit.getAssignedTask());
		task.interruptTask();
		world.advanceTime(0);
		assertFalse(task.isAssigned());
		assertEquals(null, task.getAssignedUnit());
		
		unit.startDefaultBehaviour();
		world.advanceTime(0);
		unit.stopDefaultBehaviour();
		
		assertFalse(unit.isWorkingShow());
		
		task.executeTask();
		world.advanceTime(0);
		assertTrue(unit.isWorkingShow());
		
		task.finishedLastActivity();
		world.advanceTime(0);
		assertFalse(unit.isWorkingShow());

	}
	
	@Test
	public void testTaskNotWellFormed() throws ModelException {
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
		cubes[2][2][2] = new Cube(pos4, CubeType.WORKSHOP);

		World world = new World(cubes, defaultTerrainChangeListener);
		Unit unit = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);

		Faction faction = unit.getFaction();
		Scheduler scheduler = faction.getScheduler();
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 10\nactivities: work this;", new TaskFactory(),
				Collections.emptyList());
		
		scheduler.addTask(tasks.get(0));
		
		assertFalse(tasks.get(0).isWellFormed());
		
	}
	
}
