package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.som.annotate.Model;
import hillbillies.model.expression.Expression;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Scheduler {
	public Scheduler() {
		tasks = new ArrayList<Task>();
	}
	
	List<Task> tasks;
	
	public void addTask(Task task) {
		if (!tasks.contains(task)) {
			tasks.add(task);
		}
		task.addSchedulerForTask(this);
		sortTasksOnPriority();
	}
	
	@Model
	private void addTasks(Collection<Task> tasks) {
		for (Task task : tasks) {
			addTask(task);
		}
	}
	
	public void removeTask(Task task) {
		if (task.isAssigned()) {
			task.getAssignedUnit().stopTask();
			task.getAssignedUnit().removeTask();
		}
		tasks.remove(task);
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
		return tasks.iterator();
	}
	
	public boolean areTasksPartOf(Collection<Task> checkTasks) {
		return tasks.containsAll(checkTasks);
	}
	
	private void sortTasksOnPriority() {
		Collections.sort(tasks, new Comparator<Task> () {
			@Override
			public int compare(Task task1, Task task2) {
				return Integer.compare(task2.getPriority(), task1.getPriority());
			}
		});
	}
	
	protected Task getHightestUnassignedPriorityTask() {
		for (Task task : tasks) {
			if (!task.isAssigned() && task.isWellFormed()) { //TODO: hoe wellformed gebruiken hier?
				return task;
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("unused")
	private List<Task> getAllTasks() {
		return this.tasks;
	}
	
	@SuppressWarnings("unused")
	private List<Task> getAllTasksWithCond(Expression cond) {
		List<Task> result = new ArrayList<>();
		for (Task task: tasks) {
			if (true) {
				//TODO
			}
		}
		return result;
	}
}
