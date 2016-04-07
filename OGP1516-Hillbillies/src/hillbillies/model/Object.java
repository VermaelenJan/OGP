package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

/** 
 * A class of objects that can occur and used by units in a world.
 * 
 * @invar  Each object can have its weight as weight.
 *       | canHaveAsWeight(this.getWeight())
 * @invar  The world of each unit must be effective for every unit.
 * 		 | world != null
 *       
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public abstract class Object {

	/**
	 * Initialize this new ownable with given owner and given value.
	 * 
	 * @param world
	 * 		The world for this new object.
	 * @param location
	 * 		The location for this new object.
	 * @effect The world of this new object is set to the given world.
	 * @effect The weight of this new unit is set to a random weight between 10 and 50.
	 * @effect The location of this new unit is set to the given location.
	 */
	protected Object(World world, double[] location){
		positionObj = new Position(world);
		positionObj.setFreeLocation(location);
		this.weight = (ConstantsUtils.random.nextInt(ConstantsUtils.MAX_OBJECT_WEIGHT + 1)+10);
		this.world = world;
	}
	
	/**
	 * Variable registering the world of this object.
	 */
	protected World world;
	
	/**
	 * Return the location of this object.
	 */
	@Raw
	public double[] getLocation() {
		return positionObj.getLocation();
	}
	
	/**
	 * Return the occupied cube of this object.
	 */
	@Raw
	protected int[] getOccupiedCube(){
		return positionObj.getOccupiedCube();
	}
	
	/**
	 * Variable registering the position of this object.
	 */
	protected Position positionObj;
	
	
	/**
	 * Return the weight of this object.
	 */
	@Basic @Raw @Immutable
	protected int getWeight() {
		return this.weight;
	}
	
	/**
	 * Check whether this object can have the given weight as its weight.
	 *  
	 * @param  weight
	 *         The weight to check.
	 * @return True if and only if the weight is between 10 and 50.
	*/
	@Raw
	protected boolean canHaveAsWeight(int weight) {
		return ((weight >= ConstantsUtils.MIN_OBJECT_WEIGHT) 
				&& (weight <= ConstantsUtils.MAX_OBJECT_WEIGHT));
	}
	
	/**
	 * Variable registering the weight of this object.
	 */
	private final int weight;
	
	/**
	 * Advances the game with duration dt.
	 * 
	 * @param dt
	 * 		The duration which the game time is advanced.
	 * @effect If the position of the object is not a valid Z position, the unit falls
	 * 		with its target the cube below.
	 * @effect If the position of the object is not a valid Z position and the object
	 * 		is not at the middle of a cube on z level, the units falls to the middle
	 * 		of the z level of the cube the object is in.  
	 */
	protected void advanceTime(double dt){
		if (!positionObj.isValidZPosition()){
			positionObj.fall(dt,positionObj.getCubeBelow());
		}
		
		if (positionObj.isValidZPosition() && !positionObj.isAtMiddleZOfCube()){
			positionObj.fall(dt, positionObj.getOccupiedCube());
		}
	}
	
	/**
	 * Variable reflecting whether or not this object is terminated.
	 */
	private boolean isTerminated;
	
	
	/**
	 * Check whether this object is terminated.
	 */
	protected boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this object.
	 * 
	 *@post This object is terminated.
	 */
	protected void terminate(){
		this.isTerminated = true;
	}
	
	/**
	 * Bring the object back into the game world.
	 * 
	 * @post This object is back into the game world.
	 */
	protected void revive() {
		this.isTerminated = false;
	}
}
