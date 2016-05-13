package hillbillies.model.expression;

import java.util.ArrayList;


import hillbillies.model.Task;
import hillbillies.model.Unit;
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
	public Boolean evaluate(Task task, int[] selectedCube, Unit possibleUnit) {

		Boolean tempBool;
		if (getExpression() instanceof IBool){
			tempBool = (Boolean) getExpression().evaluate(task, selectedCube, possibleUnit);
		}
		else if (getExpression() instanceof ReadVariable){
			tempBool = (Boolean) ((IBool) getExpression().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube,possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		if (tempBool == null){
			return false;
		}
		
		return !tempBool;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getExpression() instanceof IBool ||
				(getExpression() instanceof ReadVariable
					&& (getExpression().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IBool)
				)) && getExpression().isWellFormed(task, calledBy, possibleUnit);
	}
}
