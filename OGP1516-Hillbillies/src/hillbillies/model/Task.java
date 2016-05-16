package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import hillbillies.model.expression.*;
import hillbillies.model.statement.*;
import hillbillies.part3.programs.SourceLocation;


/**
 * A class of tasks that belong to one or more schedulers.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Task {

	/**
	 * Initialize this new task with the given name, priority, activities and eventual selected cubes.
	 * 
	 * @param name
	 * 		The name for this new task.
	 * @param priority
	 * 		The priority for this new task.
	 * @param activities
	 * 		The activities for this new task.
	 * @param selectedCube
	 * 		The selected cube for this new task.
	 * @effect The name of this new task is set to the given name.
	 * 		| setName(name)
	 * @effect The priority of this new task is set to the given priority.
	 * 		| setPriority(priority)
	 * @effect The selected cube for this new task is set to the given selected cube.
	 * 		| setSelectedCube(selectedCube)
	 * @effect The activities for this new task are set to the given activities.
	 * 		| setActivities(activities)
	 * @effect A new set of schedulers for this task.
	 * 		| schedulersForTask = new HashSet<Scheduler>()
	 * @effect A new empty hashmap with as key a variable name and as value an expression is
	 * 		set as the new variables map of this task.
	 * 		| setVariables(new HashMap<String, Expression>())
	 * @effect The original activities for this new task are set to the given activities.
	 * 		| setOriginalActivities(activities)  		
	 * 
	 */
	protected Task(String name, int priority, Statement activities, int[] selectedCube ){
		setName(name);
		setPriority(priority);
		setSelectedCube(selectedCube);
		setActivities(activities);
		schedulersForTask = new HashSet<Scheduler>();
		setVariables(new HashMap<String, Expression>());
		setOriginalActivities(activities);
	}
	
	// ORIGINAL ACTIVITIES
	
	/**
	 * Get the original activities of this task.
	 */
	private Statement getOriginalActivities() {
		return this.originalActivities;
	}
	
	/**
	 * Set the original activities of this task to the given activities.
	 * 
	 * @param activities
	 * 		The activities to set.
	 * 
	 * @post The new original activities for this task are equal to the given activities.
	 * 	| new.getOriginalActivities() == activities
	 */
	private void setOriginalActivities(Statement activities) {
		this.originalActivities = activities;
	}

	/**
	 * Variable registering the original activities of this task.
	 */
	private Statement originalActivities;
	

	
	// NAME  
	/**
	 * Get the name of this task.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the name of this task to the given name.
	 * 
	 * @param name
	 * 		the name to set.
	 * @post The new name of this task is equal to the given name.
	 * 		| new.getName() == name
	 */
	private void setName(String name){
		this.name = name;
	}
	
	/**
	 * Variable registering the name of this task.
	 */
	private String name;

	
	// PRIORITY
	
	/**
	 * Get the priority of this task.
	 */
	public int getPriority(){
		return this.priority;
	}
	
	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param priority
	 * 		The priority to set.
	 * @post The new priority of this task is equal to the given priority.
	 * 		| new.getPriority() == priority
	 */
	protected void setPriority(int priority){
		this.priority = priority;
	}
	
	/**
	 * Variable registering the priority of this task.
	 */
	private int priority;

	
	// ACTIVITY
	
	/**
	 * Get the active activities of this task.
	 * @note return type is a statement.
	 */
	protected Statement getActivities(){
		return this.activitiesReq;
	}
	
	/**
	 * Get the active activities of this task.
	 * @note return type is a sequence.
	 */
	private Sequence getActivitiesReq(){
		return this.activitiesReq;
	}
	
	/**
	 * Set the active activities of this task to the given activities.
	 * @note param activities is a sequence.
	 * 
	 * @param activitiesReq
	 * 		The activities to set.
	 * 
	 * The new active activities for this task are equal to the given activities.
	 * 		new.getActivitiesReq() == activitiesReq
	 */
	private void setActivitiesReq(Sequence activitiesReq){
		this.activitiesReq = activitiesReq;
	}
	
	/**
	 * Set the active activities of this task with the given activities.
	 * @note added as a sequence to activitiesReq
	 * 
	 * @param activities
	 * 		The activities to set.
	 * @effect a new sequence with a list of the given activities and the sourcelocation of the given
	 * 		activities is set to the active activities of this task.
	 * 		| setActivitiesReq((new Sequence(list, activities.sourceLocation)));
	 * @effect Remove the nested sequences of activities from the active activities of this task.
	 * 		| setActivitiesReq(removeNestedSeq(getActivitiesReq()));
	 * @effect a new map with every activity as key and a boolean on false as value.
	 * 		| for each activity in getActivitiesREq().getStatements
	 * 		|	activitiesMap.put(activity, false)
	 */
	private void setActivities(Statement activities){

		List<Statement> list = new ArrayList<>();
		list.add(activities);

		setActivitiesReq((new Sequence(list, activities.sourceLocation)));
		
		setActivitiesReq(removeNestedSeq(getActivitiesReq()));
		
		activitiesMap = new HashMap<Statement, Boolean>();

		for (Statement activity : ((Sequence) getActivitiesReq()).getStatements()) {
			activitiesMap.put(activity, false);
		}
	}
	
	/**
	 * 
	 * @param activitiesSequence
	 * @return
	 */
	private Sequence removeNestedSeq(Sequence activitiesSequence) {
		if (activitiesSequence instanceof Sequence) {
			int i = 0;
			List<Statement> result = ((Sequence) activitiesSequence).getStatements();
			while (i < result.size()) {
				if (result.get(i) instanceof Sequence) {
					Sequence toAdd = (Sequence) result.remove(i);
					result.addAll(i, (removeNestedSeq(toAdd)).getStatements());
				}
			i++;
			}
			return new Sequence(result, activitiesSequence.getSourceLocation());
		}
		else {
			return activitiesSequence;
		}
	}
	
	private Sequence activitiesReq;

	


	// SELECTED CUBE
	
	public int[] getSelectedCube(){
		return this.selectedCube;
	}
	
	private void setSelectedCube(int[] selectedCube){
		this.selectedCube = selectedCube;
	}
	
	private int[] selectedCube;	
	
	private Unit assignedUnit;
	
	protected void assignTo(Unit unit) throws RuntimeException {

		List<Scheduler> temp = new ArrayList<>();
		for (Scheduler scheduler : getSchedulersForTask()) {
			if (scheduler != unit.getFaction().getScheduler()) {
				temp.add(scheduler);
			}
		}
		for (Scheduler scheduler : temp) {
			scheduler.removeTask(this);
		}
		
		unit.assignTask(this);
		setAssignedUnit(unit);
	}
	
	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}
	
	protected void setAssignedUnit(Unit unit){
		this.assignedUnit = unit;
	}
	
	public boolean isAssigned() {
		return this.assignedUnit != null;
	}
	
	public void executeTask(){
		
		
//		for (Statement el : getActivitiesReq().getStatements()) {
//			System.out.print(el + ": " + activitiesMap.get(el) + " - "); 
//		}
//		System.out.println();
//		
//		for (Statement el : activitiesMap.keySet()) {
//			System.out.print(el.toString() + "  " + activitiesMap.get(el).toString() + " - ");
//		}
//		System.out.println();

		Sequence sequence = (Sequence) getActivitiesReq();
		for (Statement activity : sequence.getStatements()){
			if (! (activitiesMap.get(activity))) {
				if (activity instanceof BreakStatement) {
					breakWhile();
					return;
				}
				Sequence result = activity.execute(getAssignedUnit(),getSelectedCube());
				if (result != null) {
					int i = getActivitiesReq().getStatements().indexOf(activity); //index of current activity
					getActivitiesReq().getStatements().remove(i);
					int j = 0; //counter for size of statements to add
					int k = 0; //counter for amount of added statements
					while (j < result.getStatements().size()) {
						   if (!getActivitiesReq().getStatements().contains(result.getStatements().get(j))) {
							   getActivitiesReq().getStatements().add(i+k, result.getStatements().get(j));
							   k++;
						   }
						   j++;
					}
					
					activitiesMap.remove(activity);
					for (Statement el : result.getStatements()) {
						activitiesMap.put(el, false);
					}
				}
				return;
			}
		}
	}
	
	
	private void breakWhile() {
		for (Statement activity : ((Sequence) getActivitiesReq()).getStatements()) {
			if (activity instanceof While) {
				getAssignedUnit().startNewPending();
				return;
			}
			activitiesMap.put(activity, true);
		}
	}

	private Set<Scheduler> schedulersForTask;
	
	public Set<Scheduler> getSchedulersForTask() {
		return schedulersForTask;
	}
	
	protected void addSchedulerForTask(Scheduler scheduler) {
		schedulersForTask.add(scheduler);
	}
	
	protected void removeSchedulerForTask(Scheduler scheduler) {
		schedulersForTask.remove(scheduler);
	}

	public void finishedLastActivity() {
		for (Statement activity : ((Sequence) activitiesReq).getStatements()) {
			if (activitiesMap.get(activity) == false) {
				activitiesMap.put(activity, true);
				break;
			}
		}
		if (activitiesMap.get(getActivitiesReq().getStatements().get(getActivitiesReq().getStatements().size()-1))) {
			finishTask();
		}
	}
	
	
	HashMap<Statement, Boolean> activitiesMap;
	
	private void finishTask() {
		assignedUnit.removeTask();
		assignedUnit.getFaction().getScheduler().removeTask(this);
	}
	
	protected void reAssignTaskInSchedulers() {
		for (Scheduler scheduler : getSchedulersForTask()) {
			scheduler.addTask(this);
		}
	}
	
	public void interruptTask() {
		this.assignedUnit.removeTask();
		setAssignedUnit(null);
		reAssignTaskInSchedulers();
		setActivities(getOriginalActivities());
		if (getPriority() == 0) {
			setPriority(-1);
		}
		else {
			setPriority(getPriority() - 1);
		}
	}
	
	public void addVariable(String variableName, Expression value, SourceLocation sourceLocation) {
		getVariables().put(variableName, value);
	}
	
	public Expression readVariable(String variableName) {
		return getVariables().get(variableName);
	}
	
	HashMap<String, Expression> variables;
	
	private void setVariables(HashMap<String, Expression> variables) {
		this.variables = variables;
	}
	
	public HashMap<String, Expression> getVariables(){
		return this.variables;
	}
	
	public Boolean isWellFormed() {
		for (Scheduler tempScheduler : (getSchedulersForTask())){
			for (Statement activity : ((Sequence) getActivitiesReq()).getStatements()) {
				ArrayList<java.lang.Object> calledBy = new ArrayList<java.lang.Object>();
				calledBy.add(this);
				if (!activity.isWellFormed(this, calledBy, tempScheduler.getFaction().getUnits().iterator().next())) {
					System.out.println("iswellformed fout: " + activity);
					getVariables().clear();
					return false;
				}
			}
			getVariables().clear();
		}
		System.out.println("iswellformed juist");
		return true;
	}
}




