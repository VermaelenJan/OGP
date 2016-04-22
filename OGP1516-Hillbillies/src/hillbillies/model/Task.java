package hillbillies.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;

import hillbillies.model.Statement.Sequence;
import hillbillies.part3.programs.SourceLocation;

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
		// ni op letten haha kzat denk ik ergens ander met mijn gedachten:p)
		sequence = new Sequence(getActivities()// weet ik veel gast);
		int i = 0;
		while (i < sequence.statements.size()){
			if (sequence.statements[i] instanceof Statement.Work){
				if (sequence.statements[i+1] instanceof Expression.LiteralPosition) // ja expression
					int[] workTarget = {}
					
					unit.workAt(workTarget);;
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




