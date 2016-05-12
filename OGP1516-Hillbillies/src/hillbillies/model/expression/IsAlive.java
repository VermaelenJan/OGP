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
		Unit unit;
		
		if (getAliveUnit() instanceof ReadVariable) {
			unit = (Unit) ((Expression) getAliveUnit().evaluate(task, selectedCube)).evaluate(task, selectedCube);
		}
		else if (getAliveUnit() instanceof IUnitExpression) {
			unit = (Unit) getAliveUnit().evaluate(task, selectedCube);
		}
		else {
			throw new RuntimeException();
		}
		if (unit == null) {
			return false;
		}
		return !unit.isTerminated();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getAliveUnit() instanceof IUnitExpression ||
				(getAliveUnit() instanceof ReadVariable
					&& (getAliveUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
				)) && getAliveUnit().isWellFormed(task, calledBy);
	}
}
