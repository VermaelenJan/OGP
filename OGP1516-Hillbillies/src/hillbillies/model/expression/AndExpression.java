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
	public String toString() {
		return getLeft().toString() + " and " + getRight().toString();
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return (((Boolean)getLeft().evaluate(unit, selectedCube, task)) && ((Boolean)getRight().evaluate(unit, selectedCube, task)));
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getLeft() instanceof IBool && getLeft().isWellFormed(task, calledBy) &&
				getRight() instanceof IBool && getRight().isWellFormed(task, calledBy);
	}

}
