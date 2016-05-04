package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsFriend extends Expression implements IBool {

	private Expression friendUnit;

	public IsFriend(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.friendUnit = unit;
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return unit.getFaction() == ((Unit) friendUnit.evaluate(unit, selectedCube, task)).getFaction();
	}

	@Override
	public String toString() {
		return "Is friend " + this.friendUnit.toString();
	}
}
