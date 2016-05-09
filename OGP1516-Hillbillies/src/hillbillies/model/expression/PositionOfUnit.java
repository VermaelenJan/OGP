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
	public int[] evaluate(Task task, int[] selectedCube) {
		return ((Unit)getExpUnit().evaluate(task, selectedCube)).getOccupiedCube();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getExpUnit() instanceof IUnitExpression ||
				(getExpUnit() instanceof ReadVariable
					&& (getExpUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
				)) && getExpUnit().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}
}
