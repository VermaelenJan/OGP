package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.exceptions.IllegalPositionException;


//TODO: indexes from 1 to 4
//TODO: doc
/**
 * A class of positions.
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
@Value
class Position {
	
	private World world;

	protected Position(World world){
		this.world = world;	
	}
	
	@Raw @Model 
	protected void setLocation(double[] location) throws IllegalPositionException {
		if (!isValidLocationInWorld(location))
			throw new IllegalPositionException(location);	
	this.xPos = location[0];
	this.yPos = location[1];
	this.zPos = location[2];
	}
	
	private double xPos = 0;
	private double yPos = 0;
	private double zPos = 0;
	
	@Raw @Model
	protected boolean isValidLocationInWorld(double[] location) {
		return ( isInBoundaries(location) && isValidZPosition());
	}
	
	protected boolean isInBoundaries(double[] location){
		return ((location[0] <= world.getNbCubesX()) && (location[1] <= world.getNbCubesY()) && (location[2] <= world.getNbCubesZ()) && 
				(location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0));
	}
	
	@Raw @Model
	protected boolean isValidUnitPosition(double[] location){
		if (isInBoundaries(location)){
			int cube[] = {(int)location[0],(int)location[1],(int)location[2]};
			int [] xList = {cube[0]-1,cube[0],cube[0]+ 1};
			int [] yList = {cube[1]-1,cube[1],cube[1]+ 1};
			int [] zList = {cube[2]-1,cube[2],cube[2]+ 1};
			for (int x : xList){
				for (int y : yList){
					for (int z : zList){
						if (!world.getCubeType(x, y, z).isPassableTerrain()){
							return true;
						}
					}
				}
			}
			return false;
		}
		else{
			return false;
		}
	}
	@Basic @Raw
	protected double[] getLocation() {
		double[] position = {this.xPos, this.yPos, this.zPos};
		return(position);
	}
	
	@Raw
	protected int[] getOccupiedCube() {
		double[] location = this.getLocation();
		int[] position = {(int) location[0], (int) location[1], (int) location[2]};
		return(position);
	}
	
	protected boolean isAtMiddleOfCube() {
		int[] occCube = this.getOccupiedCube();
		double[] position = {occCube[0] + 0.5, occCube[1] + 0.5, occCube[2] + 0.5};
		return(this.getLocation()[0] == position[0] && this.getLocation()[1] == position[1] && this.getLocation()[2] == position[2]);
	}
	
	protected boolean isAtMiddleZOfCube() {
		int[] occCube = this.getOccupiedCube();
		double Zposition = occCube[2] + 0.5;
		return(this.getLocation()[2] == Zposition);
	}
	
	public boolean isValidZPosition() {
		return (isValidZCube(this.getOccupiedCube()));
	}
	
	public boolean isValidZCube(int[] cube){
		int [] cubeBelow = {cube[0],cube[1],(cube[2]-1)};
		return (( cube[2] == 0) || (!world.getCubeType(cubeBelow[0], cubeBelow[1], cubeBelow[2]).isPassableTerrain()));
	}
	
	@Model 
	protected void setRandomLocation(){
		try {
			setLocation(randomPosition(getLocation()));
		} catch (IllegalPositionException e) {
			setRandomLocation();
		}
	}
	
	@Model
	protected double[] randomPosition(double[] currLoc){
		double[] newLoc = {currLoc[0]+ (ConstantsUtils.random.nextDouble()*2-1), currLoc[1] + 
				(ConstantsUtils.random.nextDouble()*2-1), currLoc[2]};
		if (currLoc == newLoc) {
			return randomPosition(currLoc);
		}
		return newLoc;
	}
	
	@Model
	protected int[] getRandomPosition(){
		int[] randLoc = {ConstantsUtils.random.nextInt(world.getNbCubesX()-1), 
				ConstantsUtils.random.nextInt(world.getNbCubesY()-1), 
				ConstantsUtils.random.nextInt(world.getNbCubesZ()-1)};
		if (isValidZCube(randLoc)){
			return randLoc;			
		}
		else{
			return getRandomPosition();
		}
	}
}