package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import hillbillies.model.statement.*;

public class Task { //TODO: activities

	protected Task(String name, int priority, Statement activities, int[] selectedCube ){
		setName(name);
		setPriority(priority);
		setSelectedCube(selectedCube);
		setActivities(activities);
		schedulersForTask = new HashSet<Scheduler>();
	}
	
	HashMap<Statement, Boolean> activitiesMap;
	
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
		return this.activitiesReq;
	}
	
	private void setActivities(Statement activities){

		if (! (activities instanceof Sequence)) {
			List<Statement> list = new ArrayList<>();
			list.add(activities);

			this.activitiesReq = (new Sequence(list, activities.sourceLocation));
		}
		else {
			this.activitiesReq = activities;
		}
		
		activitiesMap = new HashMap<Statement, Boolean>();

		for (Statement activity : ((Sequence) activitiesReq).statements) {
			activitiesMap.put(activity, false);
		}
	}
	
	private Statement activitiesReq;

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
		
		for (Scheduler scheduler : getSchedulersForTask()) {
			if (scheduler != assignedUnit.getFaction().getScheduler()) {
				scheduler.removeTask(this);
			}
		}
	}
	
	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}
	
	public boolean isAssigned() {
		return this.assignedUnit != null;
	}
	
	public void executeTask(){

		Sequence sequence = (Sequence) activitiesReq;
		
		for (Statement activity : sequence.statements){
			if (! (activitiesMap.get(activity))) { 
				activity.execute(assignedUnit,selectedCube);
				return;
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

	protected void finishedLastActivity() {
		for (Statement activity : ((Sequence) activitiesReq).statements) {
			if (activitiesMap.get(activity) == false) {
				activitiesMap.put(activity, true);
				break;
			}
		}
		if (activitiesMap.get(((Sequence) activitiesReq).statements.get(activitiesMap.size()-1))==true) {
			finishTask();
		}
	}
	
	private void finishTask() {
		assignedUnit.getFaction().getScheduler().removeTask(this);
		this.assignedUnit.removeTask();
	}
	
	protected void reAssignTaskInSchedulers() {
		for (Scheduler scheduler : getSchedulersForTask()) {
			scheduler.addTask(this);
		}
	}
	
	protected void interruptTask() {
		this.assignedUnit.removeTask();
		this.assignedUnit = null;
		reAssignTaskInSchedulers();
		for (Statement activity : activitiesMap.keySet()) {
			activitiesMap.put(activity, false);
		}
		setPriority(getPriority()-Math.abs(getPriority()/2));
	}
}




