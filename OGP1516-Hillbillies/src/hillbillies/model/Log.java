package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

/** 
 * @invar  Each log can have its weight as weight.
 *       | canHaveAsWeight(this.getWeight())
 *       
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class Log {
	//Location
	public Position positionObj = new Position();
	public World world;

	public Log(World world, double[] location){
		positionObj.setLocation(location);
		this.weight = (ConstantsUtils.random.nextInt(MAX_LOG_WEIGHT + 1)+10);
		this.world = world;
	}
	
	/**
	 * Return the weight of this log.
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Check whether this log can have the given weight as its weight.
	 *  
	 * @param  weight
	 *         The weight to check.
	 * @return The weight will be between 10 and 50.
	*/
	@Raw
	public boolean canHaveAsWeight(int weight) {
		return ((weight >= ConstantsUtils.MIN_LOG_WEIGHT) 
				&& (weight <= ConstantsUtils.MAX_LOG_WEIGHT));
	}
	
	
	/**
	 * Variable registering the weight of this log.
	 */
	private final int weight;
	
	

	public boolean isValidPosition(Position position) {
		int [] occupiedCube = position.getOccupiedCube();
		int [] cubeBelow = {occupiedCube[0],occupiedCube[1],(occupiedCube[2]-1)};
		return ((world.getCubeType(cubeBelow[0], cubeBelow[1], cubeBelow[2]).isPassableTerrain())
				|| ( position.getOccupiedCube()[2] == 0));
	}
	
	public void advanceTime(double dt){
		if (!isValidPosition(positionObj)){
			fall(dt,getCubeBelow());
		}
		
		if (isValidPosition(positionObj) && !positionObj.isAtMiddleOfCube()){ //TODO: nadenken over "zelfde Z, maar niet midden"
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
		//TODO
	}
	
	
	
	
	
}
