package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hillbillies.model.expression.Expression;
import hillbillies.model.statement.*;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Task {

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

			setActivitiesReq((new Sequence(list, activities.sourceLocation)));
		}
		else {
			setActivitiesReq((Sequence) activities);
		}
		
		setActivitiesReq(removeNestedSeq(getActivitiesReq()));
		
		activitiesMap = new HashMap<Statement, Boolean>();

		for (Statement activity : ((Sequence) getActivitiesReq()).getStatements()) {
			activitiesMap.put(activity, false);
		}
	}
	
	private Sequence removeNestedSeq(Sequence activitiesSequence) { //TODO: verwijderen indien niet nodig
		if (activitiesSequence instanceof Sequence) {
			int i = 0;
			List<Statement> result = ((Sequence) activitiesSequence).getStatements();
			while (i < result.size()) {
				if (result.get(i) instanceof Sequence) {
					Sequence toAdd = (Sequence) result.remove(i);
					result.addAll(i, (removeNestedSeq(toAdd)).getStatements());
				} //TODO: nakijken
			i++;
			}
			return new Sequence(result, activitiesSequence.getSourceLocation());
		}
		else {
			return activitiesSequence;
		}
	}
	
	private Sequence activitiesReq;
	
	private void setActivitiesReq(Sequence activitiesReq){
		this.activitiesReq = activitiesReq;
	}
	
	private Sequence getActivitiesReq(){
		return this.activitiesReq;
	}

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
		setAssignedUnit(unit);
		
		for (Scheduler scheduler : getSchedulersForTask()) {
			if (scheduler != assignedUnit.getFaction().getScheduler()) {
				scheduler.removeTask(this);
			}
		}
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
		

		
/*		for (Statement el : getActivitiesReq().getStatements()) {
			System.out.print(el + ": " + activitiesMap.get(el) + "-"); 
		}
		System.out.println();*/
		
		Sequence sequence = (Sequence) getActivitiesReq();
		
		for (Statement activity : sequence.getStatements()){ //TODO: goed nakijken
			if (! (activitiesMap.get(activity))) {
				if (activity instanceof BreakStatement) {
					breakWhile();
					return;
				}
				Sequence result = activity.execute(assignedUnit,selectedCube, this);
				if (result != null) {
					int i = getActivitiesReq().getStatements().indexOf(activity);
					getActivitiesReq().getStatements().remove(i);
					// FOR "WHILE": TODO: verbeteren
					int j = 0;
					int k = 0;
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
		// TODO: prevent break outside while ( ook in isWellFormed!!)
		for (Statement activity : ((Sequence) getActivitiesReq()).getStatements()) {
			if (activity instanceof While) {
				finishedLastActivity();
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

	public void finishedLastActivity() { //TODO: MAG NIET PUBLIC, MAAR MOET REACHABLE ZIJN IN IF.JAVA
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
		setAssignedUnit(null);
		reAssignTaskInSchedulers();
		for (Statement activity : activitiesMap.keySet()) {
			activitiesMap.put(activity, false);
		}
		setPriority(getPriority()-Math.abs(getPriority()/2));
	}
	
	public void addVariable(String variableName, Expression value, SourceLocation sourceLocation) {
		//TODO over overwrite types!!!!!
		variables.put(variableName, value); // boeit sourceLoc?
	}
	
	public Expression readVariable(String variableName) {
		return variables.get(variableName);
		//TODO waar we read nodig hebben!!?!!? (overal dus?)
	}
	
	HashMap<String, Expression> variables;
}




