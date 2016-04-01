package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalValueException extends RuntimeException {


	/**
	 * 
	 * Initialize this new illegal value exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegal value exception.
	 * @post   The value of this new illegal value exception is equal
	 *         to the given value.
	 *       | new.getValue() == value
	 */
	public IllegalValueException(double value){
		this.value = value;
	}
	
	
	/**
	 * Return the value registered for this illegal denominator exception.
	 */
	@Basic @Immutable
	public double getValue(){
		return this.value;
	}
	
	/**
	 * Variable registering the value involved in this illegal denominator
	 * exception.
	 */
	private final double value;
	
	private static final long serialVersionUID = 5271679868895387308L;
	
}
