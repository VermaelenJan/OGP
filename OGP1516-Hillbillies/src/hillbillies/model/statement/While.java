package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IBool;
import hillbillies.model.expression.ReadVariable;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
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

		Boolean tempBool = false;
		if (getCondition() instanceof IBool){
			tempBool = (Boolean) getCondition().evaluate(unit.getAssignedTask(), selectedCube);
		}
		else if (getCondition() instanceof ReadVariable){
			tempBool = (Boolean) ((IBool) getCondition().evaluate(unit.getAssignedTask(), selectedCube)).
					evaluate(unit.getAssignedTask(), selectedCube);
		}		
		if (tempBool){

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
			unit.startNewPending();
			return null;
		}
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		Boolean temp = (getCondition() instanceof IBool ||
				(getCondition() instanceof ReadVariable
					&& (getCondition().evaluate(task, task.getSelectedCube()) instanceof IBool)
					)) && getCondition().isWellFormed(task, calledBy);
		if (getBody() instanceof Sequence) {
			for (Statement statement : ((Sequence) getBody()).getStatements()) {
				if (! statement.isWellFormed(task, calledBy)) {
					return false;
				}
			}
		}
		else if (getBody() instanceof Statement) {
			if (! getBody().isWellFormed(task, calledBy)) {
				return false;
			}
		}
		return temp;
	}
}
