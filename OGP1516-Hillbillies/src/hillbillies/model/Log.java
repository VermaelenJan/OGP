package hillbillies.model;

/**
 * A class of logs, a specific kind of objects.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class Log extends Object{
	
	/**
	 * Initialize this new log with given world and given location. 
	 * 
	 * @param world
	 * 		The world for this new log.
	 * @param location
	 * 		The location for this new log.
	 * @effect This new log is initialized as an object with 
	 * 		the given world and given location.
	 */
	public Log(World world, double[] location){
		super(world, location);
	}
}
