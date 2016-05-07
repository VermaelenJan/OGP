package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
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
	public String toString() {
		return getLeft().toString() + " or " + getRight().toString();

	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube) {
		return (((Boolean)getLeft().evaluate(unit, selectedCube)) || ((Boolean)getRight().evaluate(unit, selectedCube)));
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getLeft() instanceof IBool && getLeft().isWellFormed(task, calledBy) &&
				getRight() instanceof IBool && getRight().isWellFormed(task, calledBy);
	}

}
