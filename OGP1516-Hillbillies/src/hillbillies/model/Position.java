package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.exceptions.IllegalPositionException;


/**
 * A class of position in a world.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
@Value
class Position {
	
	/**
	 * Initialize this new position with the given world.
	 * 
	 * @param world
	 * 		The world for this new position.
	 */
	protected Position(World world){
		this.world = world;	
	}
	
	@Raw @Model 
	protected void setLocation(double[] location) throws IllegalPositionException {
		if (!isValidUnitPositionDouble(location))
			throw new IllegalPositionException(location);	
	this.xPos = location[0];
	this.yPos = location[1];
	this.zPos = location[2];
	}
	
	@Raw @Model 
	protected void setFreeLocation(double[] location) throws IllegalPositionException {
		if (!(isInBoundariesDouble(location) && 
				world.getCubeType((int) location[0], (int) location[1], (int) location[2]).isPassableTerrain()))
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
		return ( isInBoundariesDouble(location) && isValidZPosition());
	}
	
	protected boolean isInBoundariesDouble(double[] location){
		return ((location[0] < world.getNbCubesX()) && (location[1] < world.getNbCubesY()) &&
				(location[2] < world.getNbCubesZ()) && (location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0));
	}
	
	protected boolean isInBoundariesInt(int[] location) {
		double[] loc = {(double) location[0], (double) location[1], (double) location[2]};
		return isInBoundariesDouble(loc);
	}
	
	@Raw @Model
	protected boolean isValidUnitPositionDouble(double[] location){
		if (isInBoundariesDouble(location) && 
				world.getCubeType((int) location[0], (int) location[1], (int) location[2]).isPassableTerrain()){
			int cube[] = {(int)location[0],(int)location[1],(int)location[2]};
			if (isValidZCube(cube)) {
				return true;
			}
			
			for (Cube neighCube : getNeighbouringCubes(cube)) {
				if (!neighCube.getCubeType().isPassableTerrain()) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean isValidUnitPositionInt(int[] location) {
		double[] pos = {(double) location[0], (double) location[1], (double) location[2]};
		return isValidUnitPositionDouble(pos);
	}
	
	protected List<Cube> getNeighbouringCubes(int[] cube) {
		List<Cube> result = new ArrayList<Cube>();
		int [] xList = {cube[0]-1,cube[0],cube[0]+ 1};
		int [] yList = {cube[1]-1,cube[1],cube[1]+ 1};
		int [] zList = {cube[2]-1,cube[2],cube[2]+ 1};
		for (int x : xList){
			for (int y : yList){
				for (int z : zList){
					int[] locCube = {x, y, z};
					if (isInBoundariesInt(locCube) && !(Arrays.equals(cube, locCube))){
						result.add(world.getCube(x, y, z));
					}
				}
			}
		}
		return result;
	}
	
	protected List<Cube> getNeighbouringCubesIncludingOwn(int[] cube) {
		List<Cube> result = new ArrayList<Cube>();
		result.addAll(getNeighbouringCubes(cube));
		result.add(world.getCube(cube[0], cube[1], cube[2]));
		return result;
	}
	
	protected boolean isNeighBouringCube(int[] cubePos){
		
		return (getNeighbouringCubesIncludingOwn(getOccupiedCube()).contains(world.getCube(cubePos[0], cubePos[1], cubePos[2])));
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
		return Arrays.equals(getLocation(), position);
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
	protected void setRandomDodgedLocation(){
		try {
			setFreeLocation(getRandomDodgePosition(getLocation()));
		} catch (IllegalPositionException e) {
			setRandomDodgedLocation();
		}
	}
	
	@Model
	protected double[] getRandomDodgePosition(double[] currLoc){
		double[] newLoc = {currLoc[0]+ (ConstantsUtils.random.nextDouble()*2-1), currLoc[1] + 
				(ConstantsUtils.random.nextDouble()*2-1), currLoc[2]};
		if (currLoc == newLoc) {
			return getRandomDodgePosition(currLoc);
		}
		
		return newLoc;
	}
	
	@Model
	protected int[] getRandomPosition(){
		
		int[] randLoc = {ConstantsUtils.random.nextInt(world.getNbCubesX()), 
				ConstantsUtils.random.nextInt(world.getNbCubesY()), 
				ConstantsUtils.random.nextInt(world.getNbCubesZ())};
		if (isValidZCube(randLoc) && isValidUnitPositionInt(randLoc)){
			return randLoc;			
		}
		else{
			return getRandomPosition();
		}
	}
	
	@Model
	protected int[] getCubeBelow(){
		int [] occupiedCube = getOccupiedCube();
		int [] cubeBelow = {occupiedCube[0],occupiedCube[1],(occupiedCube[2]-1)};
		return cubeBelow;
	}
	
	protected void fall(double dt, int[] position){
		double[] currPos = getLocation();
		double[] nextPos = {currPos[0],currPos[1],currPos[2]-dt*ConstantsUtils.FALLING_SPEED};
		
		if (nextPos[2] >= (position[2]+0.5)){
			setFreeLocation(nextPos);
		}
		else{
			double [] currMiddleCube = {currPos[0],currPos[1],position[2]+0.5};
			setFreeLocation(currMiddleCube);
		}
	}
	
	/**
	 * Variable registering the world of this position.
	 */
	private World world;
}