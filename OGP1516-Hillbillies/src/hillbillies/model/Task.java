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
		this.variables = new HashMap<String, Expression>();//TODO: setter maken (en gebruiken)
		this.originalActivities = activities;//TODO: setter en getter maken (en gebruiken)
		
	}
	
	private Statement originalActivities;
	
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
	
	protected Statement getActivities(){
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
//			System.out.print(el + ": " + activitiesMap.get(el) + "-"); 
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
		setActivities(originalActivities);
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




