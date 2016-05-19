package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class AnyUnit extends Expression implements IUnitExpression {

	public AnyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Task task, Unit possibleUnit) {
		Unit anyUnit = null;
		for (Unit currUnit: possibleUnit.getWorld().getAllUnits()){
			if ((anyUnit == null || Position.getDistanceBetween(currUnit.getLocation(), possibleUnit.getLocation()) <
										Position.getDistanceBetween(anyUnit.getLocation(), possibleUnit.getLocation()))
				&& currUnit != possibleUnit) {
				anyUnit = currUnit;
			}
		}
		return anyUnit;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return true;
	}
}
