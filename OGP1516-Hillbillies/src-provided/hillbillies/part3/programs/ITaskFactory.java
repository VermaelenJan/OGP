package hillbillies.part3.programs;

import java.util.List;

import hillbillies.tests.facade.Part3TestPartial;

/**
 * A task factory is used by the parser ({@link TaskParser}) to construct an
 * in-memory representation of your program. For example, when reading the task
 * description
 * 
 * <pre>
 * name: "test"
 * priority: 1
 * activities: moveTo here;
 * </pre>
 * 
 * the parser will create a Task object by (conceptually) executing the
 * following code:
 * 
 * <pre>
 * factory.createTask("test", 1, factory.createMoveTo(factory.createHerePosition()))
 * </pre>
 * 
 * on the returned factory object.
 * 
 * <p>
 * For testing, you may use the methods from {@link TaskParser} yourself, as
 * demonstrated in the partial test file {@link Part3TestPartial}.
 * 
 * <p>
 * You should declare your class as follows:<code><pre>
 * public class TaskFactory implements ITaskFactory&lt;MyExpression, MyStatement, Task&gt;
 * </pre></code> where MyExpression and MyStatement are your classes for
 * representing expressions and statements, respectively.
 * 
 * <p>
 * The SourceLocation object in the methods defined by this factory refers to
 * the location (line and column) in the text file where the statement or
 * expression begins.
 * 
 * @param <E>
 *            Your class for representing an expression.
 * @param <S>
 *            Your class for representing a statement.
 * @param <T>
 *            Your class for representing a task (should be Task).
 * 
 * 
 */
public interface ITaskFactory<E, S, T> {

	/* TASKS */

	/**
	 * Create a list of tasks from the given arguments.
	 * 
	 * @param name
	 *            The name of the task
	 * @param priority
	 *            The initial priority of the task
	 * @param activity
	 *            The activity of the task. Most likely this is a sequence
	 *            statement.
	 * @param selectedCubes
	 *            A list of cube coordinates (each represented as an array {x,
	 *            y, z}) that were selected by the player in the GUI.
	 * @return A list of new task instances. One task instance should be created
	 *         for each selectedCube coordinate. If selectedCubes is empty and
	 *         the 'selected' expression does not occur in the activity, a list
	 *         with exactly one Task instance should be returned.
	 */
	public List<T> createTasks(String name, int priority, S activity, List<int[]> selectedCubes);

	/* STATEMENTS */

	/**
	 * Create a statement that represents the assignment of a variable.
	 * 
	 * @param variableName
	 *            The name of the assigned variable
	 * @param value
	 *            An expression that evaluates to the assigned value
	 */
	public S createAssignment(String variableName, E value, SourceLocation sourceLocation);

	/**
	 * Create a statement that represents a while loop.
	 * 
	 * @param condition
	 *            The condition of the while loop
	 * @param body
	 *            The body of the loop (most likely this is a sequence
	 *            statement).
	 */
	public S createWhile(E condition, S body, SourceLocation sourceLocation);

	/**
	 * Create an if-then-else statement.
	 * 
	 * @param condition
	 *            The condition of the if statement
	 * @param ifBody
	 *            * The body of the if-part, which must be executed when the
	 *            condition evaluates to true
	 * @param elseBody
	 *            The body of the else-part, which must be executed when the
	 *            condition evaluates to false. Can be null if no else clause is
	 *            specified.
	 */
	public S createIf(E condition, S ifBody, S elseBody, SourceLocation sourceLocation);

	/**
	 * Create a break statement that immediately terminates the enclosing loop.
	 * 
	 * @param sourceLocation
	 * 
	 * @note Students working alone may return null.
	 */
	public S createBreak(SourceLocation sourceLocation);

	/**
	 * Create a print statement that prints the value obtained by evaluating the
	 * given expression.
	 * 
	 * @param value
	 *            The expression to evaluate and print
	 */
	public S createPrint(E value, SourceLocation sourceLocation);

	/**
	 * Create a sequence of statements.
	 * 
	 * @param statements
	 *            The statements that must be executed in the given order.
	 */
	public S createSequence(List<S> statements, SourceLocation sourceLocation);

	/**
	 * Create a moveTo statement
	 * 
	 * @param position
	 *            The position to which to move
	 */
	public S createMoveTo(E position, SourceLocation sourceLocation);

	/**
	 * Create a work statement
	 * 
	 * @param position
	 *            The position on which to work
	 */
	public S createWork(E position, SourceLocation sourceLocation);

	/**
	 * Create a follow statement
	 * 
	 * @param unit
	 *            The unit to follow
	 */
	public S createFollow(E unit, SourceLocation sourceLocation);

	/**
	 * Create an attack statement
	 * 
	 * @param unit
	 *            The unit to attack
	 */
	public S createAttack(E unit, SourceLocation sourceLocation);

	/* EXPRESSIONS */

	/**
	 * Create an expression that evaluates to the current value of the given
	 * variable.
	 * 
	 * @param variableName
	 *            The name of the variable to read.
	 */
	public E createReadVariable(String variableName, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given position
	 * evaluates to a solid position; false otherwise.
	 * 
	 * @param position
	 *            The position expression
	 */
	public E createIsSolid(E position, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given position
	 * evaluates to a passable position; false otherwise.
	 * 
	 * @param position
	 *            The position expression
	 */
	public E createIsPassable(E position, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit of the same faction; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	public E createIsFriend(E unit, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit of another faction; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	public E createIsEnemy(E unit, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit that is alive; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	public E createIsAlive(E unit, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit that carries an item; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	public E createCarriesItem(E unit, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when the given expression
	 * evaluates to false, and vice versa.
	 * 
	 * @param expression
	 */
	public E createNot(E expression, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true when both the left and right
	 * expression evaluate to true; false otherwise.
	 * 
	 * @note short-circuit: the right expression does not need to be evaluated
	 *       when the left expression evaluates to false.
	 */
	public E createAnd(E left, E right, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to false only when the left and right
	 * expression evaluate to false; true otherwise.
	 * 
	 * @note short-circuit: the right expression does not need to be evaluated
	 *       when the left expression evaluates to true.
	 */
	public E createOr(E left, E right, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the current position of the unit
	 * that is executing the task.
	 */
	public E createHerePosition(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the position of a log.
	 * 
	 * @note for groups of two students, this needs to be the log closest to the
	 *       unit that is executing the task.
	 */
	public E createLogPosition(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the position of a boulder.
	 * 
	 * @note for groups of two students, this needs to be the boulder closest to
	 *       the unit that is executing the task.
	 */
	public E createBoulderPosition(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the position of a workshop.
	 * 
	 * @note for groups of two students, this needs to be the workshop closest
	 *       to the unit that is executing the task.
	 */
	public E createWorkshopPosition(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the position selected by the user
	 * in the GUI.
	 * 
	 * @note Students working alone may return null.
	 */
	public E createSelectedPosition(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to a position next to the given
	 * position.
	 * 
	 * @param position
	 * 
	 */
	public E createNextToPosition(E position, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the position of the given unit.
	 * 
	 * @param unit
	 */
	public E createPositionOf(E unit, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to a static position with a given
	 * coordinate.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public E createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to the unit that is currently
	 * executing the task.
	 */
	public E createThis(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to a unit that is part of the same
	 * faction as the unit currently executing the task.
	 */
	public E createFriend(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to a unit that is not part of the
	 * same faction as the unit currently executing the task.
	 */
	public E createEnemy(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to any unit (other than this).
	 */
	public E createAny(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to true.
	 */
	public E createTrue(SourceLocation sourceLocation);

	/**
	 * Create an expression that evaluates to false.
	 */
	public E createFalse(SourceLocation sourceLocation);

}
