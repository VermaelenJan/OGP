package hillbillies.model.expression;

import java.util.ArrayList;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class AndExpression extends Expression implements IBool {

	private Expression left;
	private Expression right;

	public AndExpression(Expression left, Expression right, SourceLocation sourceLocation) {
		super(sourceLocation);
		setLeft(left);
		setRight(right);
	}
	
	private Expression getLeft() {
		return this.left;
	}
	
	private Expression getRight() {
		return this.right;
	}
	
	private void setLeft(Expression left) {
		this.left = left;
	}
	
	private void setRight(Expression right) {
		this.right = right;
	}

	@Override
	public Boolean evaluate(Task task, int[] selectedCube, Unit possibleUnit) { // TODO selectedCube verwijderen
		Boolean leftBool;
		if (getLeft() instanceof ReadVariable) {
			leftBool = (Boolean) ((Expression) getLeft().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit);
		}
		else if (getLeft() instanceof IBool) {
			leftBool = (Boolean) getLeft().evaluate(task, selectedCube, possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		
		Boolean rightBool;
		if (getRight() instanceof ReadVariable) {
			rightBool = (Boolean) ((Expression) getRight().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit); 
		}
		else if (getRight() instanceof IBool) {
			rightBool = (Boolean) getRight().evaluate(task, selectedCube, possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		if (leftBool == null || rightBool == null){
			return false;
		}
		
		return (leftBool && rightBool);
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getLeft() instanceof IBool ||
					(getLeft() instanceof ReadVariable
						&& (getLeft().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IBool)
					)) && getLeft().isWellFormed(task, calledBy,possibleUnit) &&
				(getRight() instanceof IBool ||
					(getRight() instanceof ReadVariable
						&& (getRight().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IBool)
					)) && getRight().isWellFormed(task, calledBy, possibleUnit);
	}

}
