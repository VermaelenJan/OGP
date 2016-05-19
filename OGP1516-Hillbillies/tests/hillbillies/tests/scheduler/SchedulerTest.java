package hillbillies.tests.scheduler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.programs.TaskParser;

public class SchedulerTest {
	
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
	public void testSchedulerWithTask() {
		
		Unit unit = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);
		unit.startDefaultBehaviour();
		Faction faction = unit.getFaction();
		Scheduler scheduler = faction.getScheduler();
		int[] loc = new int[] {1, 1, 1};
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 1\nactivities: work selected;", new TaskFactory(),
				Collections.singletonList(loc));


		assertNotNull(tasks);

		assertEquals(1, tasks.size());
		Task task = tasks.get(0);

		assertEquals("work task", task.getName());

		assertEquals(1, task.getPriority());
		
		assertTrue(Arrays.equals(loc, task.getSelectedCube()));
		
		assertEquals(null, task.getAssignedUnit());
		
		assertEquals(0, task.getVariables().size());
		Expression exp = new LiteralPosition(0, 0, 0, null);
		task.addVariable("name", exp, null);
		assertEquals(1, task.getVariables().size());
		assertEquals(exp, task.readVariable("name"));
		
		scheduler.addTask(task);
		world.advanceTime(0);
		
		assertEquals(unit, task.getAssignedUnit());

		advanceTimeFor(world, 100, 0.02);

		assertEquals(CubeType.AIR, world.getCubeType(1, 1, 1));

		assertFalse(scheduler.areTasksPartOf(Collections.singleton(task)));
	}
	
	@Test
	public void testAddAndRemoveTask() {
		
		Unit unit = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);
		Faction faction = unit.getFaction();
		Scheduler scheduler = faction.getScheduler();
		int[] loc1 = new int[] {1, 1, 1};
		List<Task> tasks1 = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: -2\nactivities: work selected;", new TaskFactory(),
				Collections.singletonList(loc1));
		
		int[] loc2 = new int[] {0, 1, 0};
		List<Task> tasks2 = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 12\nactivities: moveTo selected;", new TaskFactory(),
				Collections.singletonList(loc2));
		
		scheduler.addTask(tasks1.get(0));
		assertTrue(scheduler.areTasksPartOf(Collections.singletonList(tasks1.get(0))));
		scheduler.removeTask(tasks1.get(0));
		assertFalse(scheduler.areTasksPartOf(Collections.singletonList(tasks1.get(0))));
		scheduler.addTask(tasks1.get(0));
		assertTrue(scheduler.areTasksPartOf(Collections.singletonList(tasks1.get(0))));
		unit.startDefaultBehaviour();
		world.advanceTime(0);
		world.advanceTime(0.1);
		assertTrue(unit.isWorkingShow());
		scheduler.replace(tasks1.get(0), tasks2.get(0));
		assertFalse(unit.isWorkingShow());
		assertFalse(scheduler.areTasksPartOf(Collections.singletonList(tasks1.get(0))));
		assertTrue(scheduler.areTasksPartOf(Collections.singletonList(tasks2.get(0))));
		scheduler.addTask(tasks1.get(0));
		ArrayList<Task> tempArray = new ArrayList<Task>();
		tempArray.add(tasks1.get(0)); tempArray.add(tasks2.get(0));
		assertTrue(scheduler.areTasksPartOf(tempArray));
		scheduler.removeTasks(tempArray);
		assertFalse(scheduler.areTasksPartOf(tempArray));
		scheduler.addTasks(tempArray);
		assertTrue(scheduler.areTasksPartOf(tempArray));
		
		assertEquals(scheduler.getHightestUnassignedPriorityTask(), tasks2.get(0));
		Iterator<Task> iter = scheduler.getIterator();
		assertEquals(iter.next(), tasks2.get(0));
		assertEquals(iter.next(), tasks1.get(0));
		
		unit.advanceTime(0);
		unit.advanceTime(0.01);
		assertEquals(scheduler.getHightestUnassignedPriorityTask(), tasks1.get(0));
		
	}
	
	@Test
	public void testMultipleSchedulersOneTask() {
		
		Unit unit1 = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);
		Unit unit2 = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);

		Faction faction1 = unit1.getFaction();
		Faction faction2 = unit2.getFaction();

		Scheduler scheduler1 = faction1.getScheduler();
		Scheduler scheduler2 = faction2.getScheduler();

		int[] loc = new int[] {0, 1, 0};
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: -5\nactivities: moveTo selected;", new TaskFactory(),
				Collections.singletonList(loc));

		Task task = tasks.get(0);

		scheduler1.addTask(task);
		scheduler2.addTask(task);
		world.advanceTime(0);
		assertEquals(2, task.getSchedulersForTask().size());
		
		unit1.startDefaultBehaviour();
		world.advanceTime(0.1);
		assertEquals(1, task.getSchedulersForTask().size());
		assertEquals(scheduler1, task.getSchedulersForTask().iterator().next());
		assertEquals(unit1, task.getAssignedUnit());
	}

	@Test
	public void testgetTaskWithCondition() {

		Unit unit = new Unit(new int[] { 0, 0, 0 }, "Test", 50, 50, 50, 50, world);
		
		Faction faction = unit.getFaction();
		Scheduler scheduler = faction.getScheduler();
		int[] loc = new int[] {1, 1, 1};
		List<Task> tasks1 = TaskParser.parseTasksFromString(
				"name: \"move task\"\npriority: -1\nactivities: moveTo selected;", new TaskFactory(),
				Collections.singletonList(loc));

		List<Task> tasks2 = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 100\nactivities: work here;", new TaskFactory(),
				Collections.singletonList(loc));


		Task task1 = tasks1.get(0);
		Task task2 = tasks2.get(0);
		
		scheduler.addTask(task1);
		scheduler.addTask(task2);
		
		assertEquals(2, scheduler.getAllTasksWithCond((Task p) -> p.getPriority() > -2).size());
		assertEquals(1, scheduler.getAllTasksWithCond((Task p) -> p.getPriority() > 10).size());
		assertEquals(task2, scheduler.getAllTasksWithCond((Task p) -> p.getPriority() > 10).get(0));
		assertEquals(0, scheduler.getAllTasksWithCond((Task p) -> p.isAssigned()).size());
	}

	private static void advanceTimeFor(World world, double time, double step) {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			world.advanceTime(step);
		world.advanceTime(time - n * step);
	}

}
