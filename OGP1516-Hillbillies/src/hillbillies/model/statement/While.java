package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.expression.Bool;
import hillbillies.model.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public class While extends Statement {

	public While(Expression condition, Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		setCondition(condition);
		setBody(body);
	}
	
	private Expression getCondition(){
		return this.condition;
	}
	
	private void setCondition(Expression condition){
		this.condition = condition;
	}
	
	private Expression condition;
	
	
	private Statement getBody(){
		return this.body;
	}
	
	private void setBody(Statement body){
		this.body = body;
	}
	
	private Statement body;
	

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		Bool cond = null;
		if (getCondition() instanceof Bool) {
			cond = (Bool) getCondition();
		}
		else {
			throw new RuntimeException();
		}
		
		if (cond.getValue()){
			if (getBody() instanceof Sequence) {
				List<Statement> list = new ArrayList<>();
				list.addAll(((Sequence) getBody()).getStatements());
				list.add(new While(getCondition(), getBody(), getSourceLocation()));
				return new Sequence(list, sourceLocation);
			}
			else {
				List<Statement> list = new ArrayList<>();
				list.add(getBody());
				list.add(new While(getCondition(), getBody(), getSourceLocation()));
				return new Sequence(list, sourceLocation);
			} 
		}
		else {
			unit.getAssignedTask().finishedLastActivity();
			return null;
		}
		
	}

}
