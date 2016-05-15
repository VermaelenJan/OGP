package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Boulder;
import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class BoulderPosition extends Expression implements IPosition {

	public BoulderPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate(Task task, Unit possibleUnit) {
		Boulder boulder = null;
		for (Boulder currBoulder: possibleUnit.getWorld().getBouldersWorld()){
			if (boulder == null || Position.getDistanceBetween(currBoulder.getLocation(), possibleUnit.getLocation()) <
										Position.getDistanceBetween(boulder.getLocation(), possibleUnit.getLocation())) {
				boulder = currBoulder;
			}
		}
		if (boulder == null){
			return null;
		}
		else{
			return boulder.getOccupiedCube();
		}
	}



	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return true;
	}
}
