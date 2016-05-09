package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class CarriesItem extends Expression implements IBool {

	private Expression carryingUnit;

	public CarriesItem(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setCarryingUnit(unit);
	}
	
	private void setCarryingUnit(Expression unit) {
		this.carryingUnit = unit;
	}
	
	private Expression getCarryingUnit() {
		return this.carryingUnit;
	}

	@Override
	public Boolean evaluate(Task task, int[] selectedCube) {
		return ((Unit) getCarryingUnit().evaluate(task, selectedCube)).isCarryingBoulder() ||
				((Unit) getCarryingUnit().evaluate(task, selectedCube)).isCarryingLog();
	}


	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getCarryingUnit() instanceof IUnitExpression ||
				(getCarryingUnit() instanceof ReadVariable
					&& (getCarryingUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
				)) && getCarryingUnit().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}
}
