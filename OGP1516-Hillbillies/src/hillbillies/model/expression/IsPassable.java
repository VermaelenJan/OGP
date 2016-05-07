package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsPassable extends Expression implements IBool {

	private Expression position;

	public IsPassable(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		setPosition(position);
	}
	
	private void setPosition(Expression position) {
		this.position = position;
	}
	
	private Expression getPosition() {
		return this.position;
	}
	
	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube) {
		int x = ((int[])getPosition().evaluate(unit, selectedCube))[0];
		int y = ((int[])getPosition().evaluate(unit, selectedCube))[1];
		int z = ((int[])getPosition().evaluate(unit, selectedCube))[2];
		return unit.getWorld().getCubeType(x, y, z).isPassableTerrain();
	}

	@Override
	public String toString() {
		return "Is passable" + getPosition().toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getPosition() instanceof IPosition && getPosition().isWellFormed(task, calledBy);
	}

}
