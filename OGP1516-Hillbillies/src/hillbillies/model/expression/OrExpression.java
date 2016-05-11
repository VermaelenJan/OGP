package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public class OrExpression extends Expression implements IBool {

	private Expression left;
	private Expression right;

	public OrExpression(Expression left, Expression right,SourceLocation sourceLocation) {
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
	public Boolean evaluate(Task task, int[] selectedCube) {
		if (getLeft().evaluate(task, selectedCube) == null || getRight().evaluate(task, selectedCube) == null) {
			return false;
		}
		Boolean leftBool;
		if (getLeft() instanceof ReadVariable) {
			leftBool = (Boolean) ((Expression) getLeft().evaluate(task, selectedCube)).evaluate(task, selectedCube);
		}
		else if (getLeft() instanceof IBool) {
			leftBool = (Boolean) getLeft().evaluate(task, selectedCube);
		}
		else {
			throw new RuntimeException();
		}
		
		Boolean rightBool;
		if (getRight() instanceof ReadVariable) {
			rightBool = (Boolean) ((Expression) getRight().evaluate(task, selectedCube)).evaluate(task, selectedCube); 
		}
		else if (getRight() instanceof IBool) {
			rightBool = (Boolean) getRight().evaluate(task, selectedCube);
		}
		else {
			throw new RuntimeException();
		}
		return leftBool || rightBool;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getLeft() instanceof IBool ||
					(getLeft() instanceof ReadVariable
						&& (getLeft().evaluate(task, task.getSelectedCube()) instanceof IBool)
					)) && getLeft().isWellFormed(task, calledBy) &&
				(getRight() instanceof IBool ||
					(getRight() instanceof ReadVariable
						&& (getRight().evaluate(task, task.getSelectedCube()) instanceof IBool)
					)) && getRight().isWellFormed(task, calledBy);
	}
}
