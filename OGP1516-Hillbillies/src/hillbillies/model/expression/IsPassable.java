package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsPassable extends Expression implements IBool {

	private Expression position;

	public IsPassable(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
	
	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		int x = ((int[])this.position.evaluate(unit, selectedCube, task))[0];
		int y = ((int[])this.position.evaluate(unit, selectedCube, task))[1];
		int z = ((int[])this.position.evaluate(unit, selectedCube, task))[2];
		return unit.getWorld().getCubeType(x, y, z).isPassableTerrain();
	}

	@Override
	public String toString() {
		return "Is passable" + this.position.toString();
	}

}
