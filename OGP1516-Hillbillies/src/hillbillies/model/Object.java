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
	public Position positionObj = new Position(world);

	public Object(World world, double[] location){
		positionObj.setLocation(location);
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
			fall(dt,getCubeBelow());
		}
		
		if (positionObj.isValidZPosition() && !positionObj.isAtMiddleZOfCube()){
			fall(dt);
		}
	}
	
	private int[] getCubeBelow(){
		int [] occupiedCube = positionObj.getOccupiedCube();
		int [] cubeBelow = {occupiedCube[0],occupiedCube[1],(occupiedCube[2]-1)};
		return cubeBelow;
	}
	
	
	private void fall(double dt) {
		fall(dt, positionObj.getOccupiedCube());
		
	}
	
	private void fall(double dt, int[] position){
		double[] currPos = positionObj.getLocation();
		double[] nextPos = {currPos[0],currPos[1],currPos[2]-dt*ConstantsUtils.FALLING_SPEED};
		
		if (nextPos[2] >= (position[2]+0.5)){
			positionObj.setLocation(nextPos);
		}
		else{
			double [] currMiddleCube = {currPos[0],currPos[1],position[2]+0.5};
			positionObj.setLocation(currMiddleCube);
		}
	}
	
	
	
	
	
	
	
}
