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
	public Unit evaluate(Task task, int[] selectedCube) {
		Unit enemyUnit = null;
		for (Unit currUnit: task.getAssignedUnit().getWorld().getAllUnits()){
			if ((enemyUnit == null || Position.getDistanceBetween(currUnit.getLocation(), task.getAssignedUnit().getLocation()) <
										Position.getDistanceBetween(enemyUnit.getLocation(), task.getAssignedUnit().getLocation()))
					&& currUnit.getFaction() != task.getAssignedUnit().getFaction()) {
				enemyUnit = currUnit;
			}
		}
		return enemyUnit;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}

}
