package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemy extends Expression implements IBool {

	private Expression enemyUnit;

	public IsEnemy(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setEnemyUnit(unit);
	}
	
	private void setEnemyUnit(Expression unit) {
		this.enemyUnit = unit;
	}
	
	private Expression getEnemyUnit() {
		return this.enemyUnit;
	}

	@Override
	public Boolean evaluate(Task task, int[] selectedCube, Unit possibleUnit) {
		Unit enemyUnit;

		if (getEnemyUnit() instanceof ReadVariable) {
			enemyUnit = (Unit) ((Expression) getEnemyUnit().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit);
		}
		else if (getEnemyUnit() instanceof IUnitExpression) {
			enemyUnit = (Unit) getEnemyUnit().evaluate(task, selectedCube,possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		
		if (enemyUnit == null) {
			return false;
		}
		return possibleUnit.getFaction() != enemyUnit.getFaction();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getEnemyUnit() instanceof IUnitExpression ||
				(getEnemyUnit() instanceof ReadVariable
						&& (getEnemyUnit().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IUnitExpression)
						)) && getEnemyUnit().isWellFormed(task, calledBy,possibleUnit);
	}
}
