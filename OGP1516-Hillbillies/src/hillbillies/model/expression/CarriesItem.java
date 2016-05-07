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
	public Boolean evaluate(Unit unit, int[] selectedCube) {
		return ((Unit) getCarryingUnit().evaluate(unit, selectedCube)).isCarryingBoulder() ||
				((Unit) getCarryingUnit().evaluate(unit, selectedCube)).isCarryingLog();
	}

	@Override
	public String toString() {
		return "Is carrying " + this.carryingUnit.toString() ;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getCarryingUnit() instanceof IUnitExpression && this.carryingUnit.isWellFormed(task, calledBy);
	}
}
