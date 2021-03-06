package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.*;
import hillbillies.part3.programs.SourceLocation;

public class If extends Statement {

	public If(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		super(sourceLocation);
		setCondition(condition);
		setIfBody(ifBody);
		setElseBody(elseBody);
		
	}
	
	private Expression getCondition(){
		return this.condition;
	}
	
	private void setCondition(Expression condition){
		this.condition = condition;
	}
	
	private Expression condition;
	
	private Statement getIfBody(){
		return this.ifBody;
	}
	
	private void setIfBody(Statement ifBody){
		this.ifBody = ifBody;
	}
	
	private Statement ifBody;
	
	private Statement getElseBody(){
		return this.elseBody;
	}
	
	private void setElseBody(Statement elseBody){
		this.elseBody = elseBody;
	}
	
	private Statement elseBody;
	
	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		Boolean tempBool;
		if (getCondition() instanceof IBool){
			tempBool = (Boolean) getCondition().evaluate(unit.getAssignedTask(), unit);
		}
		else if (getCondition() instanceof ReadVariable){
			tempBool = (Boolean) ((IBool) getCondition().evaluate(unit.getAssignedTask(), unit)).
					evaluate(unit.getAssignedTask(), unit);
		}
		else {
			throw new RuntimeException();
		}
		if (tempBool) {
			if (getIfBody() instanceof Sequence) {
				return (Sequence) getIfBody();
			}
			else {
				List<Statement> list = new ArrayList<>();
				list.add(getIfBody());
				return new Sequence(list, sourceLocation);
				}
		}
		else {
			if (getElseBody() != null) {
				if (getElseBody() instanceof Sequence) {
					return (Sequence) getElseBody();
				}
				else {
					List<Statement> list = new ArrayList<>();
					list.add(getElseBody());
					return new Sequence(list, sourceLocation);
				}
			}
			else {
				unit.startNewPending(); 
				return new Sequence(Collections.emptyList(), sourceLocation);
			}
		}
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		if (! (getCondition() instanceof IBool || ((getCondition() instanceof ReadVariable)
				&& (getCondition().evaluate(task, possibleUnit) instanceof IBool))
				&& getCondition().isWellFormed(task, calledBy, possibleUnit))) {
			return false;
		}
		Boolean tempBool;
		if (getCondition() instanceof IBool) {
			tempBool = (boolean) getCondition().evaluate(task, possibleUnit);
		}
		else if (getCondition() instanceof ReadVariable) {
			tempBool = (boolean) ((Expression) getCondition().evaluate(task, possibleUnit)).
					evaluate(task, possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		if (tempBool){
			return (getIfBody() instanceof Sequence || getIfBody() instanceof Statement) &&
			getIfBody().isWellFormed(task, calledBy, possibleUnit);
		}
		else{
			if (getElseBody() == null) {
				return true;
			}
			else {
				return (getElseBody() instanceof Sequence || getElseBody() instanceof Statement) &&
				getElseBody().isWellFormed(task, calledBy, possibleUnit);
			}
		}
	}
}
