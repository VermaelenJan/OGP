package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfUnit extends Expression implements IPosition {

	private Expression expUnit;

	public PositionOfUnit(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setExpUnit(unit);
	}
	
	private void setExpUnit(Expression unit) {
		this.expUnit = unit;
	}
	
	private Expression getExpUnit() {
		return this.expUnit;
	}
	
	@Override
	public int[] evaluate(Task task, Unit possibleUnit) {
		Unit unit;
		
		if (getExpUnit() instanceof ReadVariable) {
			unit = (Unit) ((Expression) getExpUnit().evaluate(task, possibleUnit)).evaluate(task, possibleUnit);
		}
		else if (getExpUnit() instanceof IUnitExpression) {
			unit = (Unit) getExpUnit().evaluate(task, possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		if (unit == null) {
			return null;
		}
		return unit.getOccupiedCube();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getExpUnit() instanceof IUnitExpression ||
				(getExpUnit() instanceof ReadVariable
					&& (getExpUnit().evaluate(task, possibleUnit) instanceof IUnitExpression)
				)) && getExpUnit().isWellFormed(task, calledBy, possibleUnit);
	}
}
