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
	public String toString() {
		return "Friend unit";
	}

	@Override
	public Unit evaluate(Unit unit, int[] selectedCube, Task task) {
		Unit friendUnit = null;
		for (Unit currUnit: unit.getWorld().getAllUnits()){
			if ((friendUnit == null || Position.getDistanceBetween(currUnit.getLocation(), unit.getLocation()) <
										Position.getDistanceBetween(friendUnit.getLocation(), unit.getLocation()))
					&& currUnit.getFaction() == unit.getFaction() && currUnit != unit) {
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
