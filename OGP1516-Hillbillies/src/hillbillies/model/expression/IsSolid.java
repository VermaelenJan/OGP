package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public class IsSolid extends Expression implements IBool {

	private Expression position;

	public IsSolid(Expression position, SourceLocation sourceLocation) {
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
		if (getPosition().evaluate(task, selectedCube) == null){
			task.interruptTask();
			return false;
		}
		int x;
		int y;
		int z;
		if (getPosition() instanceof ReadVariable) {
			x = ((int[]) ((Expression) getPosition().evaluate(task, selectedCube)).evaluate(task, selectedCube))[0];
			y = ((int[]) ((Expression) getPosition().evaluate(task, selectedCube)).evaluate(task, selectedCube))[1];
			z = ((int[]) ((Expression) getPosition().evaluate(task, selectedCube)).evaluate(task, selectedCube))[2];
		}
		else if (getPosition() instanceof IPosition) {
			x = ((int[])getPosition().evaluate(task, selectedCube))[0];
			y = ((int[])getPosition().evaluate(task, selectedCube))[1];
			z = ((int[])getPosition().evaluate(task, selectedCube))[2];
		}
		else {
			throw new RuntimeException();
		}
		return !task.getAssignedUnit().getWorld().getCubeType(x, y, z).isPassableTerrain();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
				(getPosition() instanceof ReadVariable
					&& (getPosition().evaluate(task, task.getSelectedCube()) instanceof IPosition)
				)) && getPosition().isWellFormed(task, calledBy);
	}
}
