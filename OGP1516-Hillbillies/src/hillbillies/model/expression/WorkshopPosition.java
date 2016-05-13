package hillbillies.model.expression;

import java.util.ArrayList;
import hillbillies.model.Cube;
import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class WorkshopPosition extends Expression implements IPosition {

	public WorkshopPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate(Task task, int[] selectedCube, Unit possibleUnit) {
		Unit unit = possibleUnit;
		Cube cube = null;
		double[] cubeMiddle = null;
		for (int x = 0; x<unit.getWorld().getNbCubesX(); x++){
			for (int y = 0; y<unit.getWorld().getNbCubesY(); y++){
				for (int z = 0; z<unit.getWorld().getNbCubesZ(); z++){
					Cube currCube = unit.getWorld().getCube(x, y, z);
					if (currCube.getCubeType().getType().equals("workshop")) {
						double[] currCubeMiddle = {x+0.5, y+0.5, z+0.5};
						if (cube == null || Position.getDistanceBetween(currCubeMiddle , unit.getLocation()) <
								Position.getDistanceBetween(cubeMiddle , unit.getLocation())) {
							cube = currCube;
							if (cube != null) {
								double[] list = {cube.getCubePosition()[0] + 0.5, cube.getCubePosition()[1] + 0.5,
										cube.getCubePosition()[0] + 0.5};
								cubeMiddle = list;
							}
						}
					}
				}
			}
		}
		if (cube == null){
			return null;
		}
		else{
			return cube.getCubePosition(); 

		}
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return true;
	}
}
