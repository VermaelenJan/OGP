package hillbillies.part3.facade;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.TaskParser;
import hillbillies.tests.facade.Part3TestPartial;
import ogp.framework.util.ModelException;

/**
 * Implement this interface to connect your code to the graphical user interface
 * (GUI).
 * 
 * <ul>
 * <li>For separating the code that you wrote from the code that was provided to
 * you, put <b>ALL your code in the <code>src</code> folder.</b> The code that
 * is provided to you stays in the <code>src-provided</code> folder. Only if you
 * modify the provided code, it's advised to move the modified code to the
 * <code>src</code> folder, so that your changes cannot be accidentally
 * overwritten by an updated version of the provided code.</li>
 * 
 * <li>You should at least <b>create the following classes</b> in the package
 * <code>hillbillies.model</code>:
 * <ul>
 * <li>a class {@link Unit} for representing a Hillbilly Unit</li>
 * <li>a class {@link World} for representing a Hillbilly World</li>
 * <li>a class {@link Faction} for representing a unit faction</li>
 * <li>a class {@link Boulder} for representing a boulder object</li>
 * <li>a class {@link Log} for representing a log object</li>
 * <li>a class {@link Task} for representing a task program</li>
 * </ul>
 * You may, of course, add additional classes as you see fit.
 * 
 * <li>You should <b>create a class</b> {@link Facade} that implements this
 * interface. This class should be placed <b>in the package
 * <code>hillbillies.part3.facade</code></b> in your <code>src</code> folder.
 * </li>
 * 
 * <li>The <b>header of that {@link Facade} class</b> should look as follows:
 * <br>
 * <code>public class Facade implements IFacade { ... }</code><br>
 * Consult the <a href=
 * "http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">
 * Java tutorial</a> for more information on interfaces, if necessary.</li>
 *
 * <li>Your <code>Facade</code> class should offer a <b>default constructor</b>.
 * </li>
 * 
 * <li><b>Each non-deprecated method</b> defined in this {@link IFacade}
 * interface and the interfaces from part 1 (@link
 * hillbillies.part1.facade.IFacade) and part 2 (
 * {@link hillbillies.part2.facade.IFacade}) must be implemented by your @{link
 * Facade} class. For example, the implementation of
 * {@link #getCubeCoordinate(Unit)} should call one or more methods on the given
 * <code>unit</code> to retrieve its current cube coordinate.</li>
 * 
 * <li>The methods {@link #advanceTime(Unit, double)} and {@link #work(Unit)}
 * from part 1 are <b>deprecated</b> and no longer need to be implemented.</li>
 * 
 * <li>Methods in this interface are <b>only allowed to throw exceptions of
 * type</b> {@link ModelException} (this class is provided). No other exception
 * types are allowed. This exception can only be thrown if (1) calling a method
 * of your <code>Unit</code> class with the given parameters would violate a
 * precondition, or (2) if the method of your <code>Unit</code> class throws an
 * exception (if so, wrap the exception in the {@link ModelException}).</li>
 * 
 * <li>{@link ModelException} should not be used anywhere outside of your
 * {@link Facade} implementation.</b></li>
 * 
 * <li>Your {@link Facade} implementation should <b>only contain trivial
 * code</b> (for example, calling a method, combining multiple return values
 * into an array, creating @Value instances, catching exceptions and wrapping it
 * in a ModelException). All non-trivial code should be placed in the other
 * classes that you create.</li>
 * 
 * <li>The rules described above and the documentation described below for each
 * method apply only to your {@link Facade} class. <b>Your other classes (e.g.,
 * {@link Unit}, {@link World}) should follow the rules described in the
 * assignment.</b></li>
 * 
 * <li><b>Do not modify the signatures</b> of the methods defined in this
 * interface.</li>
 * 
 * </ul>
 *
 */
public interface IFacade extends hillbillies.part2.facade.IFacade {

	/**
	 * Create a new instance of a Task factory.
	 * 
	 * <p>
	 * This factory is used by the parser ({@link TaskParser}) to construct an
	 * in-memory representation of your program. For example, when reading the
	 * task description
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
	 * @return An instance of ITaskFactory. See the documentation of that
	 *         interface for an explanation of its parameters.
	 */
	public ITaskFactory<?, ?, Task> createTaskFactory();

	/**
	 * Returns whether the given task is well-formed.
	 * 
	 * A task is well-formed if
	 * <ul>
	 * <li>it is type-safe</li>
	 * <li>there are no break statements outside loops</li>
	 * <li>variables assigned before they are first used</li>
	 * </ul>
	 * See the assignment text for more details.
	 * 
	 * @param task
	 *            The task to check for well-formedness
	 * 
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 * @note Single-student groups may always return true for this method.
	 */
	public boolean isWellFormed(Task task) throws ModelException;

	/**
	 * Returns the scheduler associated to the given faction.
	 * 
	 * @param faction
	 *            The faction of which to return the scheduler.
	 * 
	 * @return The scheduler associated to the given faction.
	 * 
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Scheduler getScheduler(Faction faction) throws ModelException;

	/**
	 * Schedule the given task for execution on the given scheduler.
	 * 
	 * @param scheduler
	 *            The scheduler on which the task should be scheduled.
	 * @param task
	 *            The task to schedule.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public void schedule(Scheduler scheduler, Task task) throws ModelException;

	/**
	 * Replace the given task by another task in the given scheduler.
	 * 
	 * @param scheduler
	 *            The scheduler in which a task should be replaced
	 * @param original
	 *            The task that needs to be replaced.
	 * @param replacement
	 *            The task that will replace the original task.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */

	public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException;

	/**
	 * Returns whether the given tasks are all part of the given scheduler.
	 * 
	 * @param scheduler
	 *            The scheduler on which to check
	 * @param tasks
	 *            The tasks to check
	 * @return true if all given tasks are part of the scheduler; false
	 *         otherwise.
	 * 
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.s
	 */
	public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException;

	/**
	 * Returns an iterator for all tasks currently managed by the given
	 * scheduler.
	 * 
	 * @param scheduler
	 *            The scheduler for which to return an iterator.
	 * 
	 * @return An iterator that yields all scheduled tasks managed by the given
	 *         scheduler, independent of whether they're currently assigned to a
	 *         Unit or not. Completed tasks should not be part of the result.
	 *         The tasks should be delivered by decreasing priority (highest
	 *         priority first).
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException;

	/**
	 * Return all schedulers in which the given task is present.
	 * 
	 * @param task
	 *            The task for which to retrieve the schedulers
	 * @return A set of all schedulers that contain the given task.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException;

	/**
	 * Return the unit currently assigned to the given task, if any.
	 * 
	 * @param task
	 *            The task for which to retrieve the unit
	 * @return The unit that is currently assigned to the given task, or null if
	 *         the task is not assigned to a unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Unit getAssignedUnit(Task task) throws ModelException;

	/**
	 * Return the task currently assigned to the given unit, if any.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the assigned task
	 * @return The task that is currently assigned to the given unit, or null if
	 *         the unit does not have an assigned task.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Task getAssignedTask(Unit unit) throws ModelException;

	/**
	 * Return the name of the given task.
	 * 
	 * @param task
	 *            The task of which to retrieve the name
	 * @return The name of the given task.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public String getName(Task task) throws ModelException;

	/**
	 * Return the priority of the given task.
	 * 
	 * @param task
	 *            The task of which to retrieve the priority
	 * @return The priority of the given task.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public int getPriority(Task task) throws ModelException;

}
