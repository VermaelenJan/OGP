package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of schedulers which belong to a faction.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Scheduler {
	/**
	 * Initialize this new scheduler with the given faction.
	 * 
	 * @param faction
	 * 		The faction for this new scheduler.
	 * @effect The faction of this new scheduler is set to the given faction
	 * 		| setFaction(faction)
	 * @effect A new list of tasks for this scheduler.
	 * 		| tasks = new ArrayList<Task>()
	 */
	public Scheduler(Faction faction) {
		tasks = new ArrayList<Task>();
		setFaction(faction);
	}
	
	/**
	 * Return the faction of this scheduler.
	 */
	@Basic @Raw 
	protected Faction getFaction(){
		return this.faction;
	}
	
	/**
	 * Set the faction of this scheduler to the given faction.
	 * 
	 * @param faction
	 * 		The faction to set for this scheduler.
	 * @post The new faction of this scheduler is equal to the given faction.
	 * 		| new.getFaction() == faction
	 */
	@Raw
	private void setFaction(Faction faction){
		this.faction = faction;
	}
	
	/**
	 * Variable registering the faction for this scheduler.
	 */
	private Faction faction;
	

	/**
	 * Add the give tasks to the list of tasks of this scheduler.
	 * 
	 * @param task
	 * 		The task to be added.
	 * 
	 * If the scheduler does not contain the task yet:
	 * @post The number of tasks of this scheduler is incremented by 1.
	 * 		| new.getAllTasks.size() == getAllTasks.size() + 1
	 * @post The scheduler is set as one of the schedulers for the given task.
	 * 		| new.task.getSchedulersForTask().contains(this)
	 * @effect Sort the tasks on priority .
	 * 		| sortTasksOnPriority()
	 */
	public void addTask(Task task) {
		if (!getAllTasks().contains(task)) {
			getAllTasks().add(task);
		}
		task.addSchedulerForTask(this);
		sortTasksOnPriority();
	}
	
	/**
	 * Add the given collection of tasks to the tasks of this scheduler.
	 * 
	 * @param Collection<Task> tasks)
	 * 		The collection of tasks to be added.
	 * @effect Add each task in the given collection of tasks to the tasks of this scheduler.
	 * 		| for each task in given tasks:
	 * 		| addTask(task)
	 */
	public void addTasks(Collection<Task> tasks) {
		for (Task task : tasks) {
			addTask(task);
		}
	}
	
	/**
	 * Remove the given task from the list of tasks of this scheduler.
	 * 
	 * @param task
	 * 		The task to be removed.
	 * @pre The given task is effective.
	 * 		| task != null
	 * @effect if the task is assigned to a unit, The unit stops with the task, the task is
	 * 		removed from the units assigned task.
	 * 		| if (task.isAssigned)
	 * 		| 	then task.getAssignedUnit().stopTask()
	 * 		|		 task.getAssignedUnit().removeTask()
	 * 		|        task.setAssignedUnit(null)
	 * @post The number of tasks of this scheduler is decremented by 1.
	 * 		| new.getAllTasks.size() = getAllTasks.size() -1
	 * @post This scheduler no longer has the given task as one of its tasks.
	 * 		| ! new.contains(task)
	 * @post All tasks registered at an index beyond the index at which the 
	 * 		the given task was registered, are shifted one position to the left.
	 * 		| for each I,J in 1..getAllTasks.size():
	 * 		|	if ( (getAllTasks.get(I) == task) and I < J) )
	 * 		|		then new.getAllTasks.get(J-1) == getAllTasks.get(J)
	 * @effect This scheduler is removed as scheduler for the given task.
	 * 		| task.removeSchedulerforTask(this)
	 * 
	 */
	public void removeTask(Task task) {
		assert (task != null);
		if (task.isAssigned()) {
			task.getAssignedUnit().stopTask();
			task.getAssignedUnit().removeTask();
			task.setAssignedUnit(null);

		}
		getAllTasks().remove(task);
		task.removeSchedulerForTask(this);
	}
	
	/**
	 * Remove the given collection of tasks from the list of tasks of this scheduler.
	 * 
	 * @param Collection<Task> tasks)
	 * 		The collection of tasks to be removed.
	 * @effect Remove each task in the given collection of tasks from the tasks of this scheduler.
	 * 		| for each task in given tasks:
	 * 		| removeTask(task)
	 */
	public void removeTasks(Collection<Task> tasks ) {
		for (Task task : tasks) {
			removeTask(task);
		}
	}
	
	/**
	 * Replace the given original task with the given new task.
	 * 
	 * @param original
	 * 		The task to be replaced.
	 * @param replacement
	 * 		The new task to be added.
	 * @effect The original task is removed from the list of tasks 
	 * 		of this scheduler.
	 * 		| removeTask(original)
	 * @effect The replacement task is addad to the list of tasks
	 * 		of this scheduler.
	 * 		| addTask(replacement)
	 */
	public void replace(Task original, Task replacement) {
		removeTask(original);
		addTask(replacement);
	}
	
	/**
	 * Get an iterator on all the tasks of this scheduler.
	 * 
	 * @return an iterator on the tasks of this scheduler.
	 * 		| result == getAllTasks.iterator()
	 */
	public Iterator<Task> getIterator() {
		return getAllTasks().iterator();
	}
	
	/**
	 * Checks whether al the tasks of the given collection of tasks
	 * are in the tasks of this scheduler.
	 * 
	 * @param checkTasks
	 * 		The collection of tasks to check.
	 * @return True if and only if all the tasks of the given collection
	 * 		are in the tasks of this scheduler.
	 * 		| result == getAllTasks().containsAll(checkTasks)
	 */
	public boolean areTasksPartOf(Collection<Task> checkTasks) {
		return getAllTasks().containsAll(checkTasks);
	}
	
	/**
	 * Sort all the tasks on priority from high priority to low priority.
	 * 
	 * @effect Sort the tasks.
	 * 		|Collections.sort(getAllTasks(), new Comparator<Task> ()
	 * @post All the tasks are sorted on decreasing priority
	 */
	private void sortTasksOnPriority() {
		Collections.sort(getAllTasks(), new Comparator<Task> () {
			@Override
			public int compare(Task task1, Task task2) { // anonymous class 
				return Integer.compare(task2.getPriority(), task1.getPriority());
			}
		});
	}
	
	/**
	 * Get the the unassigned task with the highest priority from al the tasks
	 * of this scheduler
	 * 
	 * @return The first unassigned and well formed task in the list of tasks.
	 * 		| for task in getAlltasks() :
	 * 		| 	if (!task.issigned() && task.isWellFormed())
	 * 		|  		then result == task
	 * @return if no task was found , the result equals null.
	 * 		| result == null
	 */
	public Task getHightestUnassignedPriorityTask() {
		for (Task task : getAllTasks()) {
			if (!task.isAssigned() && task.isWellFormed()) {
				return task;
			}
		}
		return null;
	}
	
	/**
	 * A list with all the tasks of this scheduler.
	 */
	private List<Task> getAllTasks() {
		return this.tasks;
	}
	
	/**
	 * Get all the tasks of this scheduler that satisfy a certain condition. 
	 * @note the condition is here implemented as a lambda function.
	 * 
	 * @param lambda
	 * 		The condition that needs to be satisfied.
	 * @return A list with all the tasks that satisfy the given condition.
	 * 		| result == getAllTasks().stream().filter(lambda)
	 */
	public List<Task> getAllTasksWithCond(Predicate<Task> lambda) {
		List<Task> satisfiedTasks = getAllTasks().stream().filter(lambda).collect(Collectors.toList());
		return satisfiedTasks;
	}
	
	/**
	 * @invar Each task registered in the referenced list is effective.
	 * 		| for each tasks in tasks:
	 * 		|   task != null
	 * @invar No other task is registered at several positions
	 * 		in the referenced list.
	 * 		| for each I,J in 0..tasks.size()-1:
	 * 		|   (I == J) || (tasks.get(I) != tasks.get(J))
	 */
	private List<Task> tasks;
}
