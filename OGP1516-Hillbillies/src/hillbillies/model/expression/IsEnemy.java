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
		return task.getAssignedUnit().getFaction() != ((Unit) getEnemyUnit().evaluate(task, selectedCube)).getFaction();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getEnemyUnit() instanceof IUnitExpression ||
				(getEnemyUnit() instanceof ReadVariable
						&& (getEnemyUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
						)) && getEnemyUnit().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}
}
