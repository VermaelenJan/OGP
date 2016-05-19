package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class EnemyUnit extends Expression implements IUnitExpression {

	public EnemyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}


	@Override
	public Unit evaluate(Task task, Unit possibleUnit) {
		Unit enemyUnit = null;
		for (Unit currUnit: possibleUnit.getWorld().getAllUnits()){
			if ((enemyUnit == null || Position.getDistanceBetween(currUnit.getLocation(), possibleUnit.getLocation()) <
										Position.getDistanceBetween(enemyUnit.getLocation(), possibleUnit.getLocation()))
					&& currUnit.getFaction() != possibleUnit.getFaction()) {
				enemyUnit = currUnit;
			}
		}
		return enemyUnit;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return true;
	}
}
