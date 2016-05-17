package hillbillies.tests.taskFactory;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import hillbillies.model.Task;
import hillbillies.model.TaskFactory;
import hillbillies.model.expression.*;
import hillbillies.model.statement.*;

public class TaskFactoryTest {
	
	static TaskFactory taskFact;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		taskFact = new TaskFactory();
	}
	
	@Test
	public void createSingleTaskNoSelected_sTest() {
		List<Task> tasks = taskFact.createTasks("tasksName", 100, new MoveTo(new HerePosition(null), null), Collections.emptyList());
		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		assertEquals("tasksName", task.getName());
		assertEquals(100, task.getPriority());
		assertNull(task.getSelectedCube());
	}
	
	@Test
	public void createSingleTaskOneSelected_Test() {
		int[] loc = new int[] {1, 1, 1};
		List<Task> tasks = taskFact.createTasks("tasksName", 100, new MoveTo(new HerePosition(null), null), 
				Collections.singletonList(loc));
		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		assertEquals("tasksName", task.getName());
		assertEquals(100, task.getPriority());
		assertArrayEquals(new int[] {1, 1, 1}, task.getSelectedCube()); 
	}
	
	@Test
	public void createTasksMoreSelected_Test() {
		int[] loc1 = new int[] {1, 1, 1};
		int[] loc2 = new int[] {2, 2, 2};
		List<int[]> selected = new ArrayList<>();
		selected.add(loc1); selected.add(loc2);
		List<Task> tasks = taskFact.createTasks("tasksName", 100, new MoveTo(new HerePosition(null), null), selected);
		assertEquals(2, tasks.size());
		Task task1 = tasks.get(0);
		Task task2 = tasks.get(1);
		assertEquals("tasksName", task1.getName());
		assertEquals(100, task1.getPriority());
		assertArrayEquals(loc1, task1.getSelectedCube()); 
		assertEquals("tasksName", task2.getName());
		assertEquals(100, task2.getPriority());
		assertArrayEquals(loc2, task2.getSelectedCube()); 
	}
	
	@Test
	public void createAssignment_Test() {
		Statement assignmentStat = taskFact.createAssignment("varName", new LiteralPosition(1, 2, 3, null), null);
		assertTrue(assignmentStat instanceof Assignment);
	}
	
	@Test
	public void createWhile_Test() {
		Statement whileStat = taskFact.createWhile(new True(null), new MoveTo(new HerePosition(null), null), null);
		assertTrue(whileStat instanceof While);
	}
	
	@Test
	public void createIf_Test() {
		Statement ifStat = taskFact.createIf(new True(null), new MoveTo(new HerePosition(null), null), null, null);
		assertTrue(ifStat instanceof If);
	}
	
	@Test
	public void createBreak_Test() {
		Statement breakStat = taskFact.createBreak(null);
		assertTrue(breakStat instanceof BreakStatement);
	}
	
	public void createPrint_Test() {
		Statement printStat = taskFact.createPrint(new True(null), null);
		assertTrue(printStat instanceof PrintStatement);
	}
	
	public void createSequence_Test() {
		List<Statement> activities = Collections.singletonList(new MoveTo(new HerePosition(null), null));
		Statement seqStat = taskFact.createSequence(activities, null);
		assertTrue(seqStat instanceof Sequence);
	}
	
	@Test
	public void createMoveTo_Test() {
		Statement moveToStat = taskFact.createMoveTo(new HerePosition(null), null);
		assertTrue(moveToStat instanceof MoveTo);
	}
	
	@Test
	public void createWork_Test() {
		Statement workStat = taskFact.createWork(new HerePosition(null), null);
		assertTrue(workStat instanceof Work);
	}
	
	@Test
	public void createFollow_Test() {
		Statement followStat = taskFact.createFollow(new AnyUnit(null), null);
		assertTrue(followStat instanceof Follow);
	}
	
	@Test
	public void createAttack_Test() {
		Statement attackStat = taskFact.createAttack(new AnyUnit(null), null);
		assertTrue(attackStat instanceof Attack);
	}
	
	@Test
	public void createReadVar_Test() {
		Expression readVarExp = taskFact.createReadVariable("name", null);
		assertTrue(readVarExp instanceof ReadVariable);
	}
	
	@Test
	public void createIsSolid_Test() {
		Expression isSolidExp = taskFact.createIsSolid(new HerePosition(null), null);
		assertTrue(isSolidExp instanceof IsSolid);
	}
	
	@Test
	public void createIsPassable_Test() {
		Expression isPassableExp = taskFact.createIsPassable(new HerePosition(null), null);
		assertTrue(isPassableExp instanceof IsPassable);
	}
	
	@Test
	public void createIsFriend_Test() {
		Expression isFriendExp = taskFact.createIsFriend(new AnyUnit(null), null);
		assertTrue(isFriendExp instanceof IsFriend);
	}
	
	@Test
	public void createIsenemy_Test() {
		Expression isEnemyExp = taskFact.createIsEnemy(new AnyUnit(null), null);
		assertTrue(isEnemyExp instanceof IsEnemy);
	}
	
	@Test
	public void createIsAlive_Test() {
		Expression isAliveExp = taskFact.createIsAlive(new AnyUnit(null), null);
		assertTrue(isAliveExp instanceof IsAlive);
	}
	
	@Test
	public void createCarriesItem_Test() {
		Expression carriesItemExp = taskFact.createCarriesItem(new AnyUnit(null), null);
		assertTrue(carriesItemExp instanceof CarriesItem);
	}
	
	@Test
	public void createNot_Test() {
		Expression notExp = taskFact.createNot(new True(null), null);
		assertTrue(notExp instanceof NotExpression);
	}
	
	@Test
	public void createAnd_Test() {
		Expression andExp = taskFact.createAnd(new True(null), new True(null), null);
		assertTrue(andExp instanceof AndExpression);
	}
	
	@Test
	public void createOr_Test() {
		Expression orExp = taskFact.createOr(new True(null), new True(null), null);
		assertTrue(orExp instanceof OrExpression);
	}
	
	@Test
	public void createHere_Test() {
		Expression hereExp = taskFact.createHerePosition(null);
		assertTrue(hereExp instanceof HerePosition);
	}
	
	@Test
	public void createLogPos_Test() {
		Expression logPosExp = taskFact.createLogPosition(null);
		assertTrue(logPosExp instanceof LogPosition);
	}
	
	@Test
	public void createBoulderPos_Test() {
		Expression boulderPosExp = taskFact.createBoulderPosition(null);
		assertTrue(boulderPosExp instanceof BoulderPosition);
	}
	
	@Test
	public void createWorkshopPos_Test() {
		Expression WSPosExp = taskFact.createWorkshopPosition(null);
		assertTrue(WSPosExp instanceof WorkshopPosition);
	}
	
	@Test
	public void createSelPos_Test() {
		Expression selPosExp = taskFact.createSelectedPosition(null);
		assertTrue(selPosExp instanceof SelectedPosition);
	}
	
	@Test
	public void createNTPos_Test() {
		Expression NTPosExp = taskFact.createNextToPosition(new HerePosition(null), null);
		assertTrue(NTPosExp instanceof NextToPosition);
	}
	
	@Test
	public void createLiteralPos_Test() {
		Expression literalPosExp = taskFact.createLiteralPosition(0, 1, 2, null);
		assertTrue(literalPosExp instanceof LiteralPosition);
	}
	
	@Test
	public void createThis_Test() {
		Expression thisExp = taskFact.createThis(null);
		assertTrue(thisExp instanceof ThisUnit);
	}
	
	@Test
	public void createFriend_Test() {
		Expression friendExp = taskFact.createFriend(null);
		assertTrue(friendExp instanceof FriendUnit);
	}
	
	@Test
	public void createEnemy_Test() {
		Expression enemyExp = taskFact.createEnemy(null);
		assertTrue(enemyExp instanceof EnemyUnit);
	}
	
	@Test
	public void createAny_Test() {
		Expression anyExp = taskFact.createAny(null);
		assertTrue(anyExp instanceof AnyUnit);
	}
	
	@Test
	public void createTrue_Test() {
		Expression trueExp = taskFact.createTrue(null);
		assertTrue(trueExp instanceof True);
	}
	
	@Test
	public void createFalse_Test() {
		Expression falseExp = taskFact.createFalse(null);
		assertTrue(falseExp instanceof False);
	}
	
	@Test
	public void createPosOf_Test() {
		Expression posOfExp = taskFact.createPositionOf(new AnyUnit(null), null);
		assertTrue(posOfExp instanceof PositionOfUnit);
	}
}
