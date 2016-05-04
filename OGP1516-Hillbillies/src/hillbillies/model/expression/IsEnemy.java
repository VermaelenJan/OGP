package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemy extends Expression implements IBool {

	private Expression enemyUnit;

	public IsEnemy(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.enemyUnit = unit;
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return unit.getFaction() != ((Unit) enemyUnit.evaluate(unit, selectedCube, task)).getFaction();
	}

	@Override
	public String toString() {
		return "Is enemy " + this.enemyUnit.toString();
	}
}
