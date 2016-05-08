package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Boulder;
import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public class BoulderPosition extends Expression implements IPosition {

	public BoulderPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate(Task task, int[] selectedCube) {
		Boulder boulder = null;
		for (Boulder currBoulder: task.getAssignedUnit().getWorld().getBouldersWorld()){
			if (boulder == null || Position.getDistanceBetween(currBoulder.getLocation(), task.getAssignedUnit().getLocation()) <
										Position.getDistanceBetween(boulder.getLocation(), task.getAssignedUnit().getLocation())) {
				boulder = currBoulder;
			}
		}
		return boulder.getOccupiedCube();
	}

	@Override
	public String toString() {
		return "BoulderPosition";
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}
