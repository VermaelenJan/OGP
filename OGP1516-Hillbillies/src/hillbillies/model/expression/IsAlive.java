package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsAlive extends Expression implements IBool {

	private Expression aliveUnit;

	public IsAlive(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setAliveUnit(unit);;
	}
	
	private void setAliveUnit(Expression unit) {
		this.aliveUnit = unit;
	}
	
	private Expression getAliveUnit() {
		return this.aliveUnit;
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube) {
		return !((Unit)getAliveUnit().evaluate(unit, selectedCube)).isTerminated();
	}

	@Override
	public String toString() {
		return "Is alive " + getAliveUnit().toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getAliveUnit() instanceof IUnitExpression && getAliveUnit().isWellFormed(task, calledBy);
	}
}
