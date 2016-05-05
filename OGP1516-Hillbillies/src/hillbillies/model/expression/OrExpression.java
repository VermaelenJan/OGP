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
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " or " + right.toString();

	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return (((Boolean)left.evaluate(unit, selectedCube, task)) || ((Boolean)right.evaluate(unit, selectedCube, task)));
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return left instanceof IBool && left.isWellFormed(task, calledBy) &&
				right instanceof IBool && right.isWellFormed(task, calledBy);
	}

}
