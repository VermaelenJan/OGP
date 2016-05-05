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
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return unit.getFaction() != ((Unit) getEnemyUnit().evaluate(unit, selectedCube, task)).getFaction();
	}

	@Override
	public String toString() {
		return "Is enemy " + getEnemyUnit().toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getEnemyUnit() instanceof IUnitExpression && getEnemyUnit().isWellFormed(task, calledBy);
	}
}
