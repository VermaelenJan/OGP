package hillbillies.model.statement;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public class Attack extends Statement {

	private Expression attackUnit;

	public Attack(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.attackUnit = unit;
	}

	@Override
	public Sequence execute(Unit unit, int[] selectedCube, Task task) {
		unit.attack((Unit)this.attackUnit.evaluate(unit, selectedCube, task));
		((Unit) this.attackUnit.evaluate(unit, selectedCube, task)).defend(unit);
		return null;
	}

}
