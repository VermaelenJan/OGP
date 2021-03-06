package hillbillies.model.expression;

import java.util.ArrayList;
import java.util.List;
import hillbillies.model.ConstantsUtils;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class NextToPosition extends Expression implements IPosition {

	private Expression position;

	public NextToPosition(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		setPosition(position);
	}

	private void setPosition(Expression position) {
		this.position = position;
	}
	
	private Expression getPosition() {
		return this.position;
	}


	private static int[] getNeighbouring(Unit unit, int[] target) {
		List<hillbillies.model.Cube> cubes = unit.getPositionObj().getNeighbouringCubes(target);
		List<hillbillies.model.Cube> validCubes = new ArrayList<hillbillies.model.Cube>();
		for (hillbillies.model.Cube el : cubes) {
			if (unit.getPositionObj().isValidUnitPositionInt(el.getCubePosition())) {
				validCubes.add(el);
			}
		}
		if (validCubes.size() > 0) {
			return validCubes.get(ConstantsUtils.random.nextInt(validCubes.size())).getCubePosition();
		}
		else {
			return null;
		}
	}

	@Override
	public int[] evaluate(Task task, Unit possibleUnit) {
		if (getPosition().evaluate(task, possibleUnit) == null){
			return null;
		}
		if (getPosition() instanceof IPosition) {
			return getNeighbouring(possibleUnit, (int[])getPosition().evaluate(task, possibleUnit));
		}
		else if (getPosition() instanceof ReadVariable) {
			return getNeighbouring(possibleUnit, (int[]) ((Expression) getPosition().evaluate(task, possibleUnit)).
					evaluate(task, possibleUnit));
		}
		else {
			throw new RuntimeException();
		}
	}
	

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
					(getPosition() instanceof ReadVariable 
						&& (getPosition().evaluate(task, possibleUnit) instanceof IPosition)
					)) && getPosition().isWellFormed(task, calledBy, possibleUnit);
	}
}
