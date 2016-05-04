package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.*;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
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

//	@Override
//	public Sequence execute(Unit unit, int[] selectedCube, Task task) {
//		Bool cond = null;
//		if (getCondition() instanceof Bool) {
//			cond = (Bool) getCondition();
//		}
//		else {
//			throw new RuntimeException();
//		}
//		
//		if (cond.getValue()) {
//			if (getIfBody() instanceof Sequence) {
//				return (Sequence) getIfBody();
//			}
//			else {
//				List<Statement> list = new ArrayList<>();
//				list.add(getIfBody());
//				return new Sequence(list, sourceLocation);
//			}
//		}
//		else {
//			if (getElseBody() != null) {
//				if (getElseBody() instanceof Sequence) {
//					return (Sequence) getElseBody();
//				}
//				else {
//					List<Statement> list = new ArrayList<>();
//					list.add(getElseBody());
//					return new Sequence(list, sourceLocation);
//				}
//			}
//			else {
//				unit.getAssignedTask().finishedLastActivity();
//			}
//			return null;
//		}
//	}
	
	@Override
	public Sequence execute(Unit unit, int[] selectedCube, Task task) {
		if ((Boolean) getCondition().evaluate(unit, selectedCube, task)) { //TODO: in isWellFormed checken of het wel boolean is
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
				return null;
			}
		}
	}

}
