package hillbillies.model.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class NextToPosition extends Expression implements IPosition{

	private Expression position;

	public NextToPosition(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		setPosition(position);
	}

	private void setPosition(Expression position) {
		this.position = position;
	}
	
	public Expression getPosition() {
		return this.position;
	}

	@Override
	public String toString() {
		return "Next to position";
	}
	
//	public static int[] getPassableNeighbouringPosition(Expression position, Unit unit, int[] selectedCube){
//		if (position instanceof IPosition) {
//			IPosition location = (IPosition) position;
//			int[] target = {location.getX(), location.getY(), location.getZ()};
//			return getNeighbouring(unit, target);
//		}
//		
//		else if (position instanceof SelectedPosition) {
//			if (selectedCube != null){
//				return getNeighbouring(unit, selectedCube);
//			}
//			else throw new RuntimeException();
//		}
//		
//		else if (position instanceof HerePosition) {
//			return getNeighbouring(unit, unit.getOccupiedCube());
//		}
//		
//		else {
//			throw new RuntimeException();
//		}
//	}

	private static int[] getNeighbouring(Unit unit, int[] target) {
		List<hillbillies.model.Cube> cubes = unit.getPositionObj().getNeighbouringCubes(target);
		List<hillbillies.model.Cube> validCubes = new ArrayList<hillbillies.model.Cube>();
		for (hillbillies.model.Cube el : cubes) {
			if (unit.getPositionObj().isValidUnitPositionInt(el.getCubePosition())) {
				validCubes.add(el);
			}
		}
		if (validCubes.size() > 0) {
			Random random = new Random(); //TODO: random van utils
			return validCubes.get(random.nextInt(validCubes.size())).getCubePosition();
		}
		else throw new RuntimeException();
	}

	@Override
	public int[] evaluate(Unit unit, int[] selectedCube, Task task) {
		return getNeighbouring(unit, (int[])position.evaluate(unit, selectedCube, task));
	}

}
