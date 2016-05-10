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
public class FriendUnit extends Expression implements IUnitExpression {

	public FriendUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Task task, int[] selectedCube) {
		Unit friendUnit = null;
		for (Unit currUnit: task.getAssignedUnit().getWorld().getAllUnits()){
			if ((friendUnit == null || Position.getDistanceBetween(currUnit.getLocation(), task.getAssignedUnit().getLocation()) <
										Position.getDistanceBetween(friendUnit.getLocation(), task.getAssignedUnit().getLocation()))
					&& currUnit.getFaction() == task.getAssignedUnit().getFaction() && currUnit != task.getAssignedUnit()) {
				friendUnit = currUnit;
			}
		}
		return friendUnit;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}
