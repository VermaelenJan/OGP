package hillbillies.model.statement;

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
	public void execute(Unit unit, int[] selectedCube) {
		Bool cond = null;
		if (getCondition() instanceof Bool) {
			cond = (Bool) getCondition();
		}
		else {
			throw new RuntimeException();
		}
		
		if (cond.getValue()) {
			getIfBody().execute(unit, selectedCube);
		}
		else {
			getElseBody().execute(unit, selectedCube);
		}
	}

}
