package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfUnit extends Expression implements IPosition {

	private Expression expUnit;

	public PositionOfUnit(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expUnit = unit;
	}
	
	@Override
	public int[] evaluate(Unit unit, int[] selectedCube, Task task) {
		return ((Unit)expUnit.evaluate(unit, selectedCube, task)).getOccupiedCube();
	}

	@Override
	public String toString() {
		return "Position of " + expUnit.toString();
	}
}
