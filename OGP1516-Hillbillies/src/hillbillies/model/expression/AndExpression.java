package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class AndExpression extends Expression implements IBool {

	private Expression left;
	private Expression right;

	public AndExpression(Expression left, Expression right,SourceLocation sourceLocation) {
		super(sourceLocation);
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " and " + right.toString();
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return (((Boolean)left.evaluate(unit, selectedCube, task)) && ((Boolean)right.evaluate(unit, selectedCube, task)));
	}

}
