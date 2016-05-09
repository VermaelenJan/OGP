package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
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
	public Boolean evaluate(Task task, int[] selectedCube) {
		int x = ((int[])getPosition().evaluate(task, selectedCube))[0];
		int y = ((int[])getPosition().evaluate(task, selectedCube))[1];
		int z = ((int[])getPosition().evaluate(task, selectedCube))[2];
		return task.getAssignedUnit().getWorld().getCubeType(x, y, z).isPassableTerrain();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
				(getPosition() instanceof ReadVariable
					&& (getPosition().evaluate(task, task.getSelectedCube()) instanceof IPosition)
					)) && getPosition().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}

}
