package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalWorkPositionException extends RuntimeException{
	
	/**
	 * 
	 * Initialize this new illegal work position exception with the given location.
	 * 
	 * @param	location
	 * 			The location involved in this new illegal work position exception.
	 * 			The location has an x, y and z value stored in a list.
	 * @post	The x value of this new illegal work position exception is equal to 
	 * 			the given x value on index 0 of the list location.
	 * 			| new.getXpos() == location.get(0)
	 * @post	The y value of this new illegal work position exception is equal to 
	 * 			the given y value on index 1 of the list location.
	 * 			| new.getYpos() == location.get(1)
	 * @post	The z value of this new illegal work position exception is equal to 
	 * 			the given z value on index 2 of the list location.
	 * 			| new.getZpos() == location.get(2)
	 */
	public IllegalWorkPositionException(int[] location) {
		this.x = location[0];
		this.y = location[1];
		this.z = location[2];
	}
	
	/**
	 * Return the x value registered for this illegal work position exception.
	 */
	@Basic @Immutable
	public double getXpos() {
		return this.x;
	}
	
	/**
	 * Return the y value registered for this illegal work position exception.
	 */
	@Basic @Immutable
	public double getYpos() {
		return this.y;
	}

	/**
	 * Return the z value registered for this illegal work position exception.
	 */
	@Basic @Immutable
	public double getZpos() {
		return this.z;
	}
	
	/**
	 * Variables registering the x,y and z value of the location involved in this illegal work position exception.
	 */
	private final int x;
	private final int y;
	private final int z;
	

	private static final long serialVersionUID = -7773940502964173992L;
}
