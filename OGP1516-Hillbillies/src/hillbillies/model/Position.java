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
 * A class of positions related to units and objects in a world.
 * 
 * @invar Each positionobject must have an effective world.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 2.0
 */
public class Position {
	
	/**
	 * Initialize this new positionobject with the given world.
	 * 
	 * @param world
	 * 		The world for this new positionobject.
	 * @post The new world of this positionobject is equal to the given world.
	 */
	protected Position(World world){
		setWorld(world);	
	}
	
	/**
	 * Return the world of this positionobject.
	 */
	@Basic @Raw
	protected World getWorld(){
		return this.world;
	}
	
	/**
	 * Set the world of this positionobject to the given world.
	 * 
	 * @param world
	 * 		The world to set.
	 * @post The new world of this positionobject is equal to the given world.
	 */
	@Raw
	private final void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * Variable registering the world of this position.
	 */
	private World world;
	
	/**
	 * Return the location of this positionobject in its world.
	 */
	@Raw
	protected double[] getLocation() {
		double[] position = {this.xPos, this.yPos, this.zPos};
		return(position);
	}
	
	/**
	 * Return the occupied cube location of this positionobject in its world.
	 */
	@Raw
	protected int[] getOccupiedCube() {
		double[] location = this.getLocation();
		int[] position = {(int) location[0], (int) location[1], (int) location[2]};
		return(position);
	}
	
	/**
	 * Return the location of the cube below the occupying cube of this positionobject in the world.
	 */
	protected int[] getCubeBelow(){
		int [] occupiedCube = getOccupiedCube();
		int [] cubeBelow = {occupiedCube[0],occupiedCube[1],(occupiedCube[2]-1)};
		return cubeBelow;
	}
	
	/**
	 * Set the location of this positionobject to the given location near solid terrain.
	 * 
	 * @param location
	 * 		The location for this positionobject.
	 * @post The new location of this positionobject is equal to the given location.
	 * @throws IllegalPositionException
	 * 		The given location is not a valid unit location for this positionobject.
	 */
	@Raw
	protected void setLocation(double[] location) throws IllegalPositionException {
		if (!isValidUnitPositionDouble(location))
			throw new IllegalPositionException(location);	
	this.xPos = location[0];
	this.yPos = location[1];
	this.zPos = location[2];
	}
	
	/**
	 * Set the location of this positionobject to the given location in its world.
	 * 
	 * @param location
	 * 		The location for this positionobject.
	 * @throws IllegalPositionException
	 * 		The given location is not a valid location for this positionobject 
	 * 			(i.e. solid terrain or located outside of the world).
	 */
	@Raw
	protected void setFreeLocation(double[] location) throws IllegalPositionException {
		if (!(isInBoundariesDouble(location) && 
				world.getCubeType((int) location[0], (int) location[1], (int) location[2]).isPassableTerrain()))
			throw new IllegalPositionException(location);	
	this.xPos = location[0];
	this.yPos = location[1];
	this.zPos = location[2];
	}
	
	/**
	 * Variable referencing the x location of this positionobject.
	 */
	private double xPos = 0;
	/**
	 * Variable referencing the y location of this positionobject.
	 */
	private double yPos = 0;
	/**
	 * Variable referencing the z location of this positionobject.
	 */
	private double zPos = 0;
	
	
	/**
	 * Check whether the given location is a valid location for a positionobject in its world.
	 * @param location
	 * 		The location to check.
	 * @return True if and only if the given location is in the boundaries of the world and there is 
	 * 		a solid terrain cube (or the bottom of the world) below the given location.
	 */
	@Raw
	protected boolean isValidLocationInWorld(double[] location) {
		return ( isInBoundariesDouble(location) && isValidZPosition());
	}
	
	/**
	 * Check whether the double precision location is in the boundaries of the world.
	 * 
	 * @param location
	 * 		The location to check.
	 * @return True if and only if the double precision x,y,z coordinates of the given location are 
	 * 		between 0 and respectively the maximum x,y,z coordinates of the world.
	 */
	@Raw
	protected boolean isInBoundariesDouble(double[] location){
		return ((location[0] < world.getNbCubesX()) && (location[1] < world.getNbCubesY()) &&
				(location[2] < world.getNbCubesZ()) && (location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0));
	}
	
	/**
	 * Check whether the integer location is in the boundaries of the world.
	 * 
	 * @param location
	 * 		The location to check
	 * @return True if and only if the integer x,y,z coordinates of the given location are 
	 * 		between 0 and respectively the maximum x,y,z coordinates of the world.
	 */
	@Raw
	protected boolean isInBoundariesInt(int[] location) {
		double[] loc = {(double) location[0], (double) location[1], (double) location[2]};
		return isInBoundariesDouble(loc);
	}
	
	/**
	 * Check whether the given double precision location is a valid location for a positionobject in its world.
	 * @param location
	 * 		The location to check.
	 * @return True if and only if the location is in the boundaries of the world, and the cube type at the 
	 * 		given location is passable terrain, and a neighbouring cube of the cube at the given location is 
	 * 		of solid terrain.
	 */
	@Raw
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
	
	/**
	 * Check whether the given integer location is a valid location for a positionobject in its world.
	 * @param location
	 * 		The location to check.
	 * @return True if and only if the location is in the boundaries of the world, and the cube type at the 
	 * 		given location is passable terrain, and a neighbouring cube of the cube at the given location is 
	 * 		of solid terrain.
	 */
	public boolean isValidUnitPositionInt(int[] location) {
		double[] pos = {(double) location[0], (double) location[1], (double) location[2]};
		return isValidUnitPositionDouble(pos);
	}
	
	/**
	 * Check whether the location of this positionobject is at the centre of the cube it 
	 * 	is occupying in its world.
	 * 
	 * @return True if and only of the location of this positionobject is equal to the centre
	 * 		of the cube the positionobject is occupying.
	 */
	protected boolean isAtMiddleOfCube() {
		int[] occCube = this.getOccupiedCube();
		double[] position = {occCube[0] + 0.5, occCube[1] + 0.5, occCube[2] + 0.5};
		return Arrays.equals(getLocation(), position);
	}
	
	/**
	 * Check whether the positionobject is at middle of of a cube in z direction.
	 * 
	 * @return True if and only if the location of this positionobject is at the 
	 * middle in z direction of the cube it is currently in. 
	 */
	protected boolean isAtMiddleZOfCube() {
		int[] occCube = this.getOccupiedCube();
		double Zposition = occCube[2] + 0.5;
		return(this.getLocation()[2] == Zposition);
	}
	
	/**
	 * Check whether the occupied cube of this positionobject is above
	 * a solid terrain or at the bottom of the world.
	 * 
	 * @return True if and only if the there is a solid terrain cube below
	 * 		the occupied cube of this positionobject or is at the bottom
	 * 		of the world.
	 */
	protected boolean isValidZPosition() {
		return (isValidZCube(this.getOccupiedCube()));
	}
	
	/**
	 * Check whether the given cube location is above
	 * a solid terrain or at the bottom of the world.
	 * 
	 * @param cube
	 * 		The cube location to check.
	 * @return True if and only if the there is a solid terrain cube below
	 * 		the occupied cube of cub location or is at the bottom of the world.
	 */
	protected boolean isValidZCube(int[] cube){
		int [] cubeBelow = {cube[0],cube[1],(cube[2]-1)};
		return (( cube[2] == 0) || (!world.getCubeType(cubeBelow[0], cubeBelow[1], cubeBelow[2]).isPassableTerrain()));
	}
	
	/**
	 * Return a list of the neighbouring cubes of the given cube location.
	 * 
	 * @param cube
	 * 		The cube location to get the neighbouring cubes of.
	 * @return A list of the neighbouring cubes which are in the boundaries of the world and its location 
	 * 		is not equal to the given cube location.
	 */
	public List<Cube> getNeighbouringCubes(int[] cube) {
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
	
	/**
	 * Return a list of the neighbouring cubes, including the own cube of the given cube location.
	 * 
	 * @param cube
	 * 		The cube location to get the neighbouring cubes of.
	 * @return A list of the neighbouring cubes, including the cube of the given location,
	 * 		which are in the boundaries of the world.
	 */
	public List<Cube> getNeighbouringCubesIncludingOwn(int[] cube) {
		List<Cube> result = new ArrayList<Cube>();
		result.addAll(getNeighbouringCubes(cube));
		result.add(world.getCube(cube[0], cube[1], cube[2]));
		return result;
	}
	
	/**
	 * Check whether the given location is a neighbouring cube of this positionobject.
	 * 
	 * @param cubePos
	 * 		The cube location to check.
	 * @return	True if and only if the given cube location is a neighbouring cube of
	 * 		this positionobject.
	 */
	protected boolean isNeighBouringCube(int[] cubePos){
		return (getNeighbouringCubesIncludingOwn(getOccupiedCube()).contains(world.getCube(cubePos[0], cubePos[1], cubePos[2])));
	}
	
	
	/**
	 * Set the positionobject to a random valid dodge position in its world.
	 * 
	 * @effect the location of the positionobject is set to a random location with
	 * 		the restrictions of the valid dodging places.
	 */
	protected void setRandomDodgedLocation(){
		try {
			setFreeLocation(getRandomDodgePosition(getLocation()));
		} catch (IllegalPositionException e) {
			setRandomDodgedLocation();
		}
	}
	
	/**
	 * Return a random dodge location for the given location in its world.
	 * 
	 * @param currLoc
	 * 		The location to get a random dodge location for.
	 * @return A random location with the x and y coordinate between +- 1 of the 
	 * 		x and y coordinate of the given location, and the same z coordinate as
	 * 		the z coordinate of the given location.
	 */
	protected double[] getRandomDodgePosition(double[] currLoc){
		double[] newLoc = {currLoc[0]+ (ConstantsUtils.random.nextDouble()*2-1), currLoc[1] + 
				(ConstantsUtils.random.nextDouble()*2-1), currLoc[2]};
		if (currLoc == newLoc) {
			return getRandomDodgePosition(currLoc);
		}
		
		return newLoc;
	}
	
	/**
	 * Return a random location for this positionobject in its world.
	 * 
	 * @return A random location in the boundaries of the world of this positionobject,
	 * 		if the location is a valid position.
	 */
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
	
	/**
	 * The positionobject falls for a given time.
	 *  
	 * @param dt
	 * 		The time step dt for this action fall.
	 * @param location.
	 * 		The location for this position to fall to.
	 * The x and y coordinates of the next location are equal to the x and y coordinates
	 * 	of the location of this positionobject, the z location of the next location is equal to the z coordinate
	 * 	of the location of this positionobject minus the time step dt times the falling speed.
	 *@post If the z coordinate of the next location is greater than the middle of the occupying cube in 
	 *		z direction of the given location, the location of this positionobject is set to the next location.
	 *@post If the z coordinate of the next location is smaller than the middle of the occupying cube in 
	 *		z direction of the given location, the location of this positionobject is set to the x and y coordinate of 
	 *		the next location, and the z coordinate to the middle of the given location.
	 */
	protected void fall(double dt, int[] location){
		double[] currPos = getLocation();
		double[] nextPos = {currPos[0],currPos[1],currPos[2]-dt*ConstantsUtils.FALLING_SPEED};
		
		if (nextPos[2] >= (location[2]+0.5)){
			setFreeLocation(nextPos);
		}
		else{
			double [] currMiddleCube = {currPos[0],currPos[1],location[2]+0.5};
			setFreeLocation(currMiddleCube);
		}
	}

	/**
	 * Return the direct distance between two locations.
	 * 
	 * @param location1
	 * 		The first location of the distance to check between two locations.
	 * @param location2
	 * 		The second location of the distance to check between two locations.
	 * @return The direct absolute distance between the two given locations.
	 */
	public static double getDistanceBetween(double[] location1, double[] location2){
		double[] difference = {location1[0] - location2[0], location1[1] - location2[1], location1[2]- location2[2]};
		return Math.sqrt(Math.pow(difference[0], 2) + Math.pow(difference[1], 2) + Math.pow(difference[2], 2));
	}

}