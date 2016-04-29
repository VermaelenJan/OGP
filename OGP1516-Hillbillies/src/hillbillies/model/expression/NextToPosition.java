package hillbillies.model.expression;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class NextToPosition extends Expression {

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
	
	public static int[] getPassableNeighbouringPosition(Expression position, Unit unit){
		if (position instanceof IPosition) {
			IPosition location = (IPosition) position;
			int[] target = {location.getX(), location.getY(), location.getZ()};
			List<hillbillies.model.Cube> cubes = unit.getPositionObj().getNeighbouringCubes(target);
			for (hillbillies.model.Cube el : cubes) {
				if (el.getCubeType().isPassableTerrain()) {
					return el.getCubePosition();
				}
			}
			throw new RuntimeException();
		}
		
		else if (position instanceof SelectedPosition || position instanceof HerePosition) {
			//TODO:
			throw new IllegalAccessError();
			//TODO:
		}
		
		else {
			throw new RuntimeException();
		}
	}

}
