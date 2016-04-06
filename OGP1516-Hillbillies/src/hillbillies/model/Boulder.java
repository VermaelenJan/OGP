package hillbillies.model;

/**
 * A class of boulders, a specific kind of objects.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Boulder extends Object{
	
	/**
	 * Initialize this new boulder with given world and given location. 
	 * 
	 * @param world
	 * 		The world for this new boulder.
	 * @param location
	 * 		The location for this new boulder.
	 * @effect This new boulder is initialized as an object with 
	 * 		the given world and given location.
	 */
	public Boulder(World world, double[] location){
		super(world, location);
	}
}
