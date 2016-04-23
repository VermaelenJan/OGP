package hillbillies.model;

import java.util.HashSet;
import java.util.Set;
import hillbillies.model.Expression.LiteralPosition;
import hillbillies.model.Statement.Sequence;
import hillbillies.model.Statement.Work;

public class Task { //TODO: activities

	protected Task(String name, int priority, Statement activities, int[] selectedCube ){
		setName(name);
		setPriority(priority);
		setActivities(activities);
		setSelectedCube(selectedCube);
		schedulersForTask = new HashSet<Scheduler>();
	}
	
	// NAME  
	public String getName(){
		return this.name;
	}
	
	private void setName(String name){
		this.name = name;
	}
	
	private String name;

	
	// PRIORITY
	public int getPriority(){
		return this.priority;
	}

	protected void setPriority(int priority){
		this.priority = priority;
	}
	
	private int priority;

	
	// ACTIVITY
	
	public Statement getActivities(){
		return this.activities;
	}
	
	private void setActivities(Statement activities){
		this.activities = activities;
	}
	
	private Statement activities;

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
		unit.assignTask(this);
		this.assignedUnit = unit;
	}
	
	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}
	
	public boolean isAssigned() {
		return this.assignedUnit != null;
	}
	
	public void executeTask(Unit unit){
		Sequence sequence = (Statement.Sequence) activities;
		int i = 0;
		while (i < sequence.statements.size()){
			if (sequence.statements.get(i) instanceof Statement.Work){
				Statement.Work workStatement = (Work) sequence.statements.get(i);
				if (workStatement.position instanceof Expression.LiteralPosition){
					Expression.LiteralPosition positionExpression =  (LiteralPosition) workStatement.position;
					int[] workTarget = {positionExpression.x,positionExpression.y,positionExpression.z};
					unit.workAt(workTarget);
					i++;
				}
			}
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
}



