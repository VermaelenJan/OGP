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
	public Boolean evaluate(Task task, int[] selectedCube) {
		Unit enemyUnit;

		if (getEnemyUnit() instanceof ReadVariable) { //TODO: deze stijl van == null overal gebruiken!!!
			enemyUnit = (Unit) ((Expression) getEnemyUnit().evaluate(task, selectedCube)).evaluate(task, selectedCube);
		}
		else if (getEnemyUnit() instanceof IUnitExpression) {
			enemyUnit = (Unit) getEnemyUnit().evaluate(task, selectedCube);
		}
		else {
			throw new RuntimeException();
		}
		
		if (enemyUnit == null) {
			return false;
		}
		return task.getAssignedUnit().getFaction() == enemyUnit.getFaction();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getEnemyUnit() instanceof IUnitExpression ||
				(getEnemyUnit() instanceof ReadVariable
						&& (getEnemyUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
						)) && getEnemyUnit().isWellFormed(task, calledBy);
	}
}
