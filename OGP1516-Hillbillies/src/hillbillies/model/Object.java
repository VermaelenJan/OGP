package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

/** 
 * @invar  Each object can have its weight as weight.
 *       | canHaveAsWeight(this.getWeight())
 *       
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public abstract class Object {
	//Location

	protected World world;
	protected Position positionObj;

	protected Object(World world, double[] location){
		positionObj = new Position(world);
		positionObj.setFreeLocation(location);
		this.weight = (ConstantsUtils.random.nextInt(ConstantsUtils.MAX_OBJECT_WEIGHT + 1)+10);
		this.world = world;
	}
	
	/**
	 * Return the location of this object.
	 */
	@Basic @Raw
	public double[] getLocation() {
		return positionObj.getLocation();
	}
	
	@Basic @Raw
	protected int[] getOccupiedCube(){
		return positionObj.getOccupiedCube();
	}
	
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
	 * @return The weight will be between 10 and 50.
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
	
	protected void advanceTime(double dt){
		if (!positionObj.isValidZPosition()){
			positionObj.fall(dt,positionObj.getCubeBelow());
		}
		
		if (positionObj.isValidZPosition() && !positionObj.isAtMiddleZOfCube()){
			positionObj.fall(dt, positionObj.getOccupiedCube());
		}
	}
	
	private boolean isTerminated;
	
	protected boolean isTerminated() {
		return this.isTerminated;
	}
	
	protected void terminate(){
		this.isTerminated = true;
	}
	
	protected void revive() {
		this.isTerminated = false;
	}
}
