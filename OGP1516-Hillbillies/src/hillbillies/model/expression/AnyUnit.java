package hillbillies.model.expression;

import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class AnyUnit extends UnitExpression {

	public AnyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Any unit";
	}

	@Override
	public Object evaluate(Unit unit, int[] selectedCube, Task task) {
		Unit anyUnit = null;
		for (Unit currUnit: unit.getWorld().getAllUnits() ){
			if (anyUnit == null || Position.getDistanceBetween(currUnit.getLocation(), unit.getLocation()) <
										Position.getDistanceBetween(anyUnit.getLocation(), unit.getLocation())) {
				anyUnit = currUnit;
			}
		}
		return anyUnit;
		
	}

}
