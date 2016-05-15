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
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of scheduler which belong to a faction.
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
	 * @post The number of tasks of this scheduler is incremented by 1.
	 * 		| new.getAllTasks.size() == getAllTasks.size() + 1
	 * @post This scheduler has the given task as its very last task.
	 * 		| new.tasks.get(getAllTasks().size()+1) == task
	 * @post The scheduler is set as one of the scheduler for the given task.
	 * 		| new.task.getSchedulersForTask().contains(this)
	 * @effect Sort the tasks on priority .
	 * 		| sortTasksOnPriority()
	 */
	public void addTask(Task task) {
		if (!tasks.contains(task)) {
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
	@Model
	private void addTasks(Collection<Task> tasks) {
		for (Task task : tasks) {
			addTask(task);
		}
	}
	
	/**
	 * Remove the given task from the list of tasks of this scheduler.
	 * 
	 * @param task
	 * 		The task to be removed.
	 * 
	 */
	public void removeTask(Task task) {
		if (task.isAssigned()) {
			task.getAssignedUnit().stopTask();
			task.getAssignedUnit().removeTask();
			task.setAssignedUnit(null);

		}
		getAllTasks().remove(task);
		task.removeSchedulerForTask(this);
	}
	
	@Model
	private void removeTasks(Collection<Task> tasks ) {
		for (Task task : tasks) {
			removeTask(task);
		}
	}
	
	public void replace(Task original, Task replacement) {
		removeTask(original);
		addTask(replacement);
	}
	
	public Iterator<Task> getIterator() {
		return getAllTasks().iterator();
	}
	
	public boolean areTasksPartOf(Collection<Task> checkTasks) {
		return getAllTasks().containsAll(checkTasks);
	}
	
	private void sortTasksOnPriority() {
		Collections.sort(getAllTasks(), new Comparator<Task> () {
			@Override
			public int compare(Task task1, Task task2) { // anonymous class 
				return Integer.compare(task2.getPriority(), task1.getPriority());
			}
		});
	}
	
	public Task getHightestUnassignedPriorityTask() {
		for (Task task : getAllTasks()) {
			if (!task.isAssigned() && task.isWellFormed()) {
				return task;
			}
		}
		return null;
	}
	
	private List<Task> getAllTasks() {
		return this.tasks;
	}
	
	public List<Task> getAllTasksWithCond(Predicate<Task> lambda) {
		List<Task> satisfiedTasks = (List<Task>) getAllTasks().stream().filter(lambda).collect(Collectors.toList());
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
