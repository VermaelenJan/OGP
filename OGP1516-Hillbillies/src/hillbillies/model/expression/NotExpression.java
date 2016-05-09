package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public class NotExpression extends Expression implements IBool {

	private Expression expression;

	public NotExpression(Expression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		setExpression(expression);
	}
	
	private void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	private Expression getExpression() {
		return this.expression;
	}


	@Override
	public Boolean evaluate(Task task, int[] selectedCube) {
		return !(Boolean)getExpression().evaluate(task, selectedCube);
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getExpression() instanceof IBool ||
				(getExpression() instanceof ReadVariable
					&& (getExpression().evaluate(task, task.getSelectedCube()) instanceof IBool)
				)) && getExpression().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}

}
