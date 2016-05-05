package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsSolid extends Expression implements IBool {

	private Expression position;

	public IsSolid(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
	
	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		int x = ((int[])this.position.evaluate(unit, selectedCube, task))[0];
		int y = ((int[])this.position.evaluate(unit, selectedCube, task))[1];
		int z = ((int[])this.position.evaluate(unit, selectedCube, task))[2];
		return !unit.getWorld().getCubeType(x, y, z).isPassableTerrain();
	}

	@Override
	public String toString() {
		return "Is solid" + this.position.toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return this.position instanceof IPosition && this.position.isWellFormed(task, calledBy);
	}
}
