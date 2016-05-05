package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsAlive extends Expression implements IBool {

	private Expression aliveUnit;

	public IsAlive(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.aliveUnit = unit;
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return !((Unit)aliveUnit.evaluate(unit, selectedCube, task)).isTerminated();
	}

	@Override
	public String toString() {
		return "Is alive " + this.aliveUnit.toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return this.aliveUnit instanceof IUnitExpression && this.aliveUnit.isWellFormed(task, calledBy);
	}
}
