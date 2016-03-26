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

	public World world;
	public Position positionObj;

	public Object(World world, double[] location){
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
	
	/**
	 * Return the weight of this object.
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
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
	public boolean canHaveAsWeight(int weight) {
		return ((weight >= ConstantsUtils.MIN_OBJECT_WEIGHT) 
				&& (weight <= ConstantsUtils.MAX_OBJECT_WEIGHT));
	}
	
	
	/**
	 * Variable registering the weight of this object.
	 */
	private final int weight;
	
	public void advanceTime(double dt){
		if (!positionObj.isValidZPosition()){
			positionObj.fall(dt,positionObj.getCubeBelow());
		}
		
		if (positionObj.isValidZPosition() && !positionObj.isAtMiddleZOfCube()){
			fall(dt);
		}
	}
	
	private void fall(double dt) {
		positionObj.fall(dt, positionObj.getOccupiedCube());
		
	}
	

}
