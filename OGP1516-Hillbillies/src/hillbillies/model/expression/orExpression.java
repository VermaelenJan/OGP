package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class orExpression extends Expression {

	private Expression left;
	private Expression right;

	public orExpression(Expression left, Expression right,SourceLocation sourceLocation) {
		super(sourceLocation);
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " or " + right.toString();

	}

	@Override
	public Object evaluate(Unit unit, int[] selectedCube, Task task) {
		return (((Boolean)left.evaluate(unit, selectedCube, task)) || ((Boolean)right.evaluate(unit, selectedCube, task)));
	}

}
