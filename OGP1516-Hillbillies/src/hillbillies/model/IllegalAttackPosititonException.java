package hillbillies.model;

import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalAttackPosititonException extends RuntimeException {
	
	/**
	 * Variables registering the x,y and z value of the unit to attack.
	 */
	private final double x;
	private final double y;
	private final double z;

	/**
	 * 
	 * @param	location
	 * 			The location involved in this new illegal attack position exception.
	 * 			The location has an x, y and z value stored in a list.
	 * @post	The x value of this new illegal attack position exception is equal to 
	 * 			the given x value on index 0 of the list location.
	 * 			| new.getXpos() == location.get(0)
	 * @post	The y value of this new illegal attack position exception is equal to 
	 * 			the given y value on index 1 of the list location.
	 * 			| new.getYpos() == location.get(1)
	 * @post	The z value of this new illegal attack position exception is equal to 
	 * 			the given z value on index 2 of the list location.
	 * 			| new.getZpos() == location.get(2)
	 */
	public IllegalAttackPosititonException(List<Integer> location) {
		this.x = location.get(0);
		this.y = location.get(1);
		this.z = location.get(2);
	}
	
	/**
	 * 
	 * Return the x value of the unit to attack involved in this illegal attack position exception.
	 */
	@Basic @Immutable
	public double getXpos() {
		return this.x;
	}
	
	/**
	 * 
	 * Return the y value of the unit to attack involved in this illegal attack position exception.
	 */
	@Basic @Immutable
	public double getYpos() {
		return this.y;
	}

	/**
	 * 
	 * Return the z value of the unit to attack involved in this illegal attack position exception.
	 */
	@Basic @Immutable
	public double getZpos() {
		return this.z;
	}
	
	private static final long serialVersionUID = 5531748912930885221L;
}
