package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import hillbillies.model.expression.*;
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
		}//TODO: put back in all schedulers if not successfully executed
	}
	
	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}
	
	public boolean isAssigned() {
		return this.assignedUnit != null;
	}
	
	public void executeTask(){

		Sequence sequence = (Sequence) activitiesReq;
		int i = 0;
		while (i < sequence.statements.size()){
			if (! (activitiesMap.get(sequence.statements.get(i)))) {
				if (sequence.statements.get(i) instanceof Work){
					Work workStatement = (Work) sequence.statements.get(i);
					if (workStatement.position instanceof LiteralPosition){
						LiteralPosition positionExpression =  (LiteralPosition) workStatement.position;
						int[] workTarget = {positionExpression.x,positionExpression.y,positionExpression.z};
						assignedUnit.workAt(workTarget);
						i++;
					}
					
					else if (workStatement.position instanceof SelectedPosition){
						int[] workTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
						assignedUnit.workAt(workTarget);
						i++;
					}
					
					else{
						throw new RuntimeException(); //TODO
					}
				}
				
				else if (sequence.statements.get(i) instanceof MoveTo) {
					MoveTo moveToStatement = (MoveTo) sequence.statements.get(i);
					if (moveToStatement.position instanceof LiteralPosition) {
						LiteralPosition positionExpression = (LiteralPosition) moveToStatement.position;
						int[] moveToTarget = {positionExpression.x,positionExpression.y,positionExpression.z};
						assignedUnit.moveTo(moveToTarget);
						i++;
					}
					
					else if (moveToStatement.position instanceof SelectedPosition) {
						int[] moveToTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
						assignedUnit.moveTo(moveToTarget);
						i++;
					}
					
					else{
						throw new RuntimeException(); //TODO
					}
				}
				
				else {
					//TODO: fix
					throw new RuntimeException();
				}
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
	}
}




