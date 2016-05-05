package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class NotExpression extends Expression implements IBool {

	private Expression expression;

	public NotExpression(Expression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression; //TODO
	}

	@Override
	public String toString() {
		return "Not " + expression.toString();
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return !(Boolean)expression.evaluate(unit, selectedCube, task);
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return this.expression instanceof IBool && this.isWellFormed(task, calledBy);
	}

}
