package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
/**
 *  *
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalNameException extends RuntimeException {
	
	/**
	 * 
	 * Variable referencing the name of this illegal name exception.
	 */
	private final String name;
	
	/**
	 * 
	 * @param	name
	 * 			The name for this new illegal name exception.
	 * @post	The name of this new illegal name exception is equal to the given
 *         		name.
	 */
	public IllegalNameException(String name){
		this.name = name;
	}
	
	/**
	 * 
	 * Return the name of this new illegal name exception.
	 */
	@Basic @Immutable
	public String getName(){
		return this.name;
	}
	
	
	
	private static final long serialVersionUID = -2008524175821070107L;
	
}
