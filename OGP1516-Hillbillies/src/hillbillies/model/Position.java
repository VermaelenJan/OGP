package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;


//TODO: indexes from 1 to 4
/**
 * A class of positions.
 * @value
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0.0
 */
class Position {
	
	@Raw @Model
	private void setLocation(double[] location) throws IllegalPositionException {
		if (!isValidLocation(location))
			throw new IllegalPositionException(location);	
	this.xPos = location[0];
	this.yPos = location[1];
	this.zPos = location[2];
	}
	
	private double xPos = 0;
	private double yPos = 0;
	private double zPos = 0;
	
	@Raw @Model
	private static boolean isValidLocation(double[] location) {
		return ((location[0] <= Unit.WORLD_X) && (location[1] <= Unit.WORLD_Y) && (location[2] <= Unit.WORLD_Z) && 
				(location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0));
	}
	
	@Basic @Raw
	public double[] getLocation() {
		double[] position = {this.xPos, this.yPos, this.zPos};
		return(position);
	}
	
	@Raw
	public int[] getOccupiedCube() {
		double[] location = this.getLocation();
		int[] position = {(int) location[0], (int) location[1], (int) location[2]};
		return(position);
	}
}
