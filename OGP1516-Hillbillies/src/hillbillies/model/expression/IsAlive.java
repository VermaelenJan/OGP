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
	public Boolean evaluate(Task task, int[] selectedCube) {
		return !((Unit)getAliveUnit().evaluate(task, selectedCube)).isTerminated();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getAliveUnit() instanceof IUnitExpression ||
				(getAliveUnit() instanceof ReadVariable
					&& (getAliveUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
				)) && getAliveUnit().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}
}
