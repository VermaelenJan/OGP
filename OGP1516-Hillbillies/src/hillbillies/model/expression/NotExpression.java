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
		Boolean tempBool;
		if (getExpression() instanceof IBool){
			tempBool = (Boolean) getExpression().evaluate(task, selectedCube);
		}
		else if (getExpression() instanceof ReadVariable){
			tempBool = (Boolean) ((IBool) getExpression().evaluate(task, selectedCube)).evaluate(task, selectedCube);
		}
		else {
			throw new RuntimeException();
		}
		return !tempBool;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getExpression() instanceof IBool ||
				(getExpression() instanceof ReadVariable
					&& (getExpression().evaluate(task, task.getSelectedCube()) instanceof IBool)
				)) && getExpression().isWellFormed(task, calledBy);
	}
}
