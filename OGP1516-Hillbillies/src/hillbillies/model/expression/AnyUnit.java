package hillbillies.model.expression;

import java.util.ArrayList;

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
public class AnyUnit extends Expression implements IUnitExpression {

	public AnyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Task task, int[] selectedCube) {
		Unit anyUnit = null;
		for (Unit currUnit: task.getAssignedUnit().getWorld().getAllUnits()){
			if ((anyUnit == null || Position.getDistanceBetween(currUnit.getLocation(), task.getAssignedUnit().getLocation()) <
										Position.getDistanceBetween(anyUnit.getLocation(), task.getAssignedUnit().getLocation()))
				&& currUnit != task.getAssignedUnit()) {
				anyUnit = currUnit;
			}
		}
		return anyUnit;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}
