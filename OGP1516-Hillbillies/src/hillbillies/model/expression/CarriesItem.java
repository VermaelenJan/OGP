package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class CarriesItem extends Expression implements IBool {

	private Expression carryingUnit;

	public CarriesItem(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.carryingUnit = unit;
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return ((Unit) carryingUnit.evaluate(unit, selectedCube, task)).isCarryingBoulder() ||
				((Unit) carryingUnit.evaluate(unit, selectedCube, task)).isCarryingLog();
	}

	@Override
	public String toString() {
		return "Is carrying " + this.carryingUnit.toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return this.carryingUnit instanceof IUnitExpression && this.carryingUnit.isWellFormed(task, calledBy);
	}
}
