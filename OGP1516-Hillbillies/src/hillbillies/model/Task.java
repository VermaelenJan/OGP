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
	 * Remove the nested sequences in the given sequence and return a sequence with all 
	 * the activities.
	 * 
	 * @param activitiesSequence
	 * 		The sequence to remove the nested sequences in.
	 * @effect if an activity of a sequence is also a sequence, the sequence is 
	 * 		removed from the newly created list with activities and al the activities
	 * 		of the removed sequence are placed in the list.
	 * 		| if newlistofactivities.get(activity) instanceof Sequence
	 * 		|  then newlistofactivities.remove(activity)
	 * 		| 		newlistofactivities.addAll(activity, (removeNestedSeq(newlistofactivities.remove(activity).getStatements())))
	 * 		| return new Sequence(newlistofactivities, newlistofactivities.getSourceLocation())
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
	
	/**
	 * Variable registering the active activities of this task.
	 */
	private Sequence activitiesReq;

	
	// SELECTED CUBE
	
	/**
	 * Get the selected cube of this task.
	 */
	public int[] getSelectedCube(){
		return this.selectedCube;
	}
	
	/**
	 * Set the selected cube of this task to the given selected cube.
	 * 
	 * @param selectedCube
	 * 		The selected cube to set for this task.
	 * @post The new selected cube for this task is equal to the given selected cube.
	 * 		| new.getSelectedCube() == selectedCube
	 */
	private void setSelectedCube(int[] selectedCube){
		this.selectedCube = selectedCube;
	}
	
	/**
	 * Variable registering the selectedCube for this task.
	 */
	private int[] selectedCube;	
	
	// ASSIGNED UNIT

	/**
	 * Get the assigned unit for this task.
	 */
	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}
	
	/**
	 * Assign this task to the given unit.
	 * 
	 * @param unit
	 * 		The unit to assign the task to.
	 * @effect If the faction of the unit, its scheduler is not in the 
	 * 		the set of schedulers of this task, this task is removed from 
	 * 		those schedulers.
	 * 		| for each scheduler in getSchedulersForTask():
	 * 		|	if (scheduler != unit.getFaction.getScheduler()
	 * 		|		scheduler.removeTask(this)
	 * @effect This is assigned to the given unit.
	 * 		| unit.assigneTask(this)
	 * @effect The assigned unit of this task is set to the given unit.
	 * 		| setAssignedUnit(unit)
	 * @throws RuntimeException // TODO runtime omschrijven?
	 */
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
	
	/**
	 * Set the assigned unit of this task to the given unit.
	 * 
	 * @param unit
	 * 		The unit to set for the assigned unit of this task.
	 * @post The new assigned unit of this task is equal to the given unit.
	 * 		| new.getAssignedUnit() == unit
	 */
	protected void setAssignedUnit(Unit unit){
		this.assignedUnit = unit;
	}
	
	/**
	 * Checks whether or not there is an assigned unit for this task.
	 * 
	 * @return True if and only if the assigned unit is effective.
	 * 		| result == (getAssignedUnit() != null)
	 */
	public boolean isAssigned() {
		return getAssignedUnit() != null;
	}
	
	/**
	 * Variable registering the assigned unit for this task.
	 */
	private Unit assignedUnit;
	
	
	/**
	 * Execute this task.
	 * 
	 * @effect For all the activities in this task, if the activity is not yet executed, and if 
	 * 		the activity is a break statement, break the current while loop.
	 * 		| for each activity in getActivitiesReq().getStatements()
	 * 		|	if (!activitiesMap.get(activity))
	 * 		|		if (activity instanceof BreakStatement)
	 * 		|			then breakWhile()
	 * 
	 * @effect For all the activities in this task, if the activity is not yet executed, and if 
	 * 		the executed activity is not null
	 */
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
	
	/**
	 * Break the while statement.
	 * 
	 * @effect For all the activities in this task, if an activity is a while statement,
	 * 		the assigned unit of this task starts a new pending. The value of the activity
	 * 		in the activitiesmap is set on true, which means that the activities are marked 
	 * 		as done. TODO klopt toch he?
	 * 		| for each activity in getActivitiesReq().getStatements():
	 * 		|	if (activity instanceof While)
	 * 		|		then getAssignedUnit().startNewPending()
	 * 		| activitiesMap.put(activity,true)
	 */
	private void breakWhile() {
		for (Statement activity : ((Sequence) getActivitiesReq()).getStatements()) {
			if (activity instanceof While) {
				getAssignedUnit().startNewPending();
				return;
			}
			activitiesMap.put(activity, true);
		}
	}

	/**
	 * Get a set with al the schedulers for this task.
	 */
	public Set<Scheduler> getSchedulersForTask() {
		return schedulersForTask;
	}
	
	/**
	 * Add the given scheduler to the set of scheduler for this task.
	 * 
	 * @param scheduler
	 * 		The scheduler to add to the schedulers of this task.
	 * @pre The given scheduler is effective.
	 * 		| scheduler != null
	 * @post this set of schedulers has the given scheduler as one of its schedulers.
	 * 		| new.getSchedulersForTask().contains(scheduler)
	 */
	protected void addSchedulerForTask(Scheduler scheduler) {
		assert (scheduler != null);
		schedulersForTask.add(scheduler);
	}
	
	/**
	 * Remove the given scheduler from the set of scheduler from this task.
	 * 
	 * @param scheduler
	 * 		The scheduler to be removed.
	 * @post The set of schedulers for this task no longer has the given 
	 * 		scheduler as one of its schedulers.
	 * 		| ! new.schedulersForTask.contains(scheduler)
	 */
	protected void removeSchedulerForTask(Scheduler scheduler) {
		schedulersForTask.remove(scheduler);
	}
	
	/**
	 * Variable registering a set of schedulers for this task.
	 */
	private Set<Scheduler> schedulersForTask;

	/**
	 * Finish the last activity that the unit was executing in this task.
	 * 
	 * @post The last activity that was being executed is set on finished. 
	 * 		(i.e in the activitiesMap which keeps booleans for finished activities,
	 * 		the boolean of that activity is set on false)
	 * 		| for the first activity in in activities map which value is false:
	 * 		|  	activitiesMap.put(activity, true)
	 * @effect If the last activity of the task is done, the task is finished.
	 * 		| if(activitiesMap.get(lastActivity) == true (so activity is executed already)
	 * 		|	then finishTask()
	 */
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
	
	/**
	 * Variable registering a map with statements as key en booleans as values to keep 
	 * track of the activities in the task are already executed.
	 */
	HashMap<Statement, Boolean> activitiesMap;
	
	/**
	 * Finish this task.
	 * 
	 * @effect Remove this task as assigned task from the assigned unit of this task.
	 * 		| assignedUnit.removeTask()
	 * @effect Remove the task from the scheduler of the faction of the assigned unit of this task.
	 * 		| assignedUnit.getFaction().getScheduler().removeTask(this)
	 */
	private void finishTask() {
		assignedUnit.removeTask();
		assignedUnit.getFaction().getScheduler().removeTask(this);
	}
	
	/**
	 * Re-assign this task to al the schedulers for this task.
	 * 		| for each scheduler in getSchedulersForTask()
	 * 		|	scheduler.addTask(this)
	 */
	protected void reAssignTaskInSchedulers() {
		for (Scheduler scheduler : getSchedulersForTask()) {
			scheduler.addTask(this);
		}
	}
	
	/**
	 * Interrupt this task from executing.
	 * 
	 * @effect The task is removed from the assigned unit of this task.
	 * 		| getAssignedUnit().removeTask()
	 * @effect Remove the assigned unit of this task.
	 * 		| setAssignedUnit(null)
	 * @effect Re assign this task to all the schedulers for this task.
	 * 		| reAssignTaskInSchedulers()
	 * @effect Set the activities of this task to the original activities 
	 * 		of this task with al the activities not executed.
	 * 		| setActivities(getOriginalActivities())
	 * @effect Decrease the priority with 1.
	 * 		| setPriority(getPriority() -1)
	 */
	public void interruptTask() {
		getAssignedUnit().removeTask();
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
	
	/**
	 * Get the map of variables of this task.
	 */
	public HashMap<String, Expression> getVariables(){
		return this.variables;
	}
	
	/**
	 * Set the variables of this task to the given map of variables.
	 * 
	 * @param variables
	 * 		The map of variables to set.
	 * 
	 * @post the new variables of this task are equal to the given empty map of variables.
	 * 		| new.getVariables() == variables
	 */
	private void setVariables(HashMap<String, Expression> variables) {
		this.variables = variables;
	}
	
	/**
	 * Add a variable to the map of variables of this task.
	 * 
	 * @param variableName
	 * 		The name of the variable to add.
	 * @param value
	 * 		The expression of this variable.
	 * @param sourceLocation
	 * 		The sourcelocation of this varaible.
	 * @effect The variable is put in the map of variables of this task with key the variable name
	 * 		and value the expression of this variable.
	 * 		| getVariables().put(variableName, value)
	 */
	public void addVariable(String variableName, Expression value, SourceLocation sourceLocation) {
		getVariables().put(variableName, value);
	}
	
	/**
	 * Get the expression of the variable with the given variable name.
	 * 
	 * @param variableName
	 * 		The variable name to get the expression of.
	 * @return	The expression of the variable with the given variable name.
	 * 		| result == getVariables().get(variableName)
	 */
	public Expression readVariable(String variableName) {
		return getVariables().get(variableName);
	}
	
	/**
	 * Map registering the variables of this task with as key a string for the name of the variable
	 * 	and as value an expression.
	 */
	HashMap<String, Expression> variables;
	

	/**
	 * Checks whether or not this task is well formed.
	 * 
	 * @return For each scheduler in all the schedulers of this task, for each activity in this task,
	 * 		if the activity is not well formed, then false and all the variables are removed. 
	 * 		| for each scheduler in getSchedulersForTask():
	 * 		|	for each activities in getActivitiesReq().getStatements()
	 * 		|		if (! acitivy.isWellFormed()
	 * 		|			result == false
	 * 		|			getVariables().clear()
	 * @return For each scheduler in all the schedulers of this task, for each activity in this task,
	 * 		if all the activities are well formed, then true and all the variables are removed.
	 * 		| for each scheduler in getSchedulersForTask():
	 * 		|	for each activities in getActivitiesReq().getStatements()
	 * 		|		if every acitivy.isWellFormed()
	 * 		|			result == true
	 * 		|			getVariables().clear()
	 */
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




