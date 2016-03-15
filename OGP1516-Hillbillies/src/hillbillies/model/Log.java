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

	public Log(double[] location){
		positionObj.setLocation(location);
		this.weight = (ConstantsUtils.random.nextInt(41)+10);
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
		return ((weight >= ConstantsUtils.MIN_LOG_WEIGHT) && (weight <= ConstantsUtils.MAX_LOG_WEIGHT));
	}
	
	/**
	 * Variable registering the weight of this log.
	 */
	private final int weight;
	
	
	
	
}
