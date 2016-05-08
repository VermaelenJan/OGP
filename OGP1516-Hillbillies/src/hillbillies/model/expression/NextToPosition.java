package hillbillies.model.expression;

import java.util.ArrayList;
import java.util.List;


import hillbillies.model.ConstantsUtils;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;


/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
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

	@Override
	public String toString() {
		return "Next to position";
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
		else throw new RuntimeException();
	}

	@Override
	public int[] evaluate(Unit unit, int[] selectedCube) {
		return getNeighbouring(unit, (int[])getPosition().evaluate(unit, selectedCube));
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
					(getPosition() instanceof ReadVariable 
						&& (getPosition().evaluate(task.getAssignedUnit(), task.getSelectedCube()) instanceof IPosition)
					)) && getPosition().isWellFormed(task, calledBy);
	}

}
