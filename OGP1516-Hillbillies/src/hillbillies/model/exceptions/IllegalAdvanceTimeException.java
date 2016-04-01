package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalAdvanceTimeException extends RuntimeException {
	
	/**
	 * 
	 * Initialize this new illegal advanceTime exception with the given dt.
	 * 
	 * @param dt
	 * 		The time step dt involved in this illegal advanceTime exception.
	 * @post	The time step dt of this new illegal advanceTime exception is equal
	 * 			to the given dt.
	 * 			|new.getDt() == dt;
	 */
	public IllegalAdvanceTimeException(double dt){
		this.dt = dt;
	}
	
	/**
	 * 
	 * Return the time step registered for this illegal advanceTime exception.
	 */
	@Basic @Immutable
	public double getDt(){
		return this.dt;
	}
	
	/**
	 * Variable registering the time step involved in this illegal advanceTime exception.
	 */
	private final double dt;
	
	
	private static final long serialVersionUID = -3612270809203081170L;
	
}
