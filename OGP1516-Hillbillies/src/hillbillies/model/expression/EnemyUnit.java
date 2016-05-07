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
public class EnemyUnit extends Expression implements IUnitExpression {

	public EnemyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Enemy unit";
	}

	@Override
	public Unit evaluate(Unit unit, int[] selectedCube) {
		Unit enemyUnit = null;
		for (Unit currUnit: unit.getWorld().getAllUnits()){
			if ((enemyUnit == null || Position.getDistanceBetween(currUnit.getLocation(), unit.getLocation()) <
										Position.getDistanceBetween(enemyUnit.getLocation(), unit.getLocation()))
					&& currUnit.getFaction() != unit.getFaction()) {
				enemyUnit = currUnit;
			}
		}
		return enemyUnit;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}

}
