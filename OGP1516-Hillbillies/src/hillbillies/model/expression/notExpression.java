package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class notExpression extends Expression {

	private Expression expression;

	public notExpression(Expression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression; //TODO
	}

	@Override
	public String toString() {
		return "Not " + expression.toString();
	}

	@Override
	public Object evaluate(Unit unit, int[] selectedCube, Task task) {
		return !(Boolean)expression.evaluate(unit, selectedCube, task);
	}

}
