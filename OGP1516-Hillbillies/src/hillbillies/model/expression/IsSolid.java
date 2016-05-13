package hillbillies.model.expression;

import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
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
	public Boolean evaluate(Task task, int[] selectedCube, Unit possibleUnit) {
		if (getPosition().evaluate(task, selectedCube, possibleUnit) == null ||
				(getPosition() instanceof ReadVariable && ((Expression) getPosition().evaluate(task, selectedCube, possibleUnit)).
						evaluate(task, selectedCube, possibleUnit) == null)){
			return false;
		}
		int x;
		int y;
		int z;
		if (getPosition() instanceof ReadVariable) {
			x = ((int[]) ((Expression) getPosition().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit))[0];
			y = ((int[]) ((Expression) getPosition().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit))[1];
			z = ((int[]) ((Expression) getPosition().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit))[2];
		}
		else if (getPosition() instanceof IPosition) {
			x = ((int[])getPosition().evaluate(task, selectedCube, possibleUnit))[0];
			y = ((int[])getPosition().evaluate(task, selectedCube, possibleUnit))[1];
			z = ((int[])getPosition().evaluate(task, selectedCube, possibleUnit))[2];
		}
		else {
			throw new RuntimeException();
		}
		return !possibleUnit.getWorld().getCubeType(x, y, z).isPassableTerrain();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
				(getPosition() instanceof ReadVariable
					&& (getPosition().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IPosition)
				)) && getPosition().isWellFormed(task, calledBy, possibleUnit);
	}
}
