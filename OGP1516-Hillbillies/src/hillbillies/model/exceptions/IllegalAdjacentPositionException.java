package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalAdjacentPositionException extends RuntimeException{
	
	/**
	 * 
	 * Initialize this new illegal adjacent position exception with the given operands.
	 * 
	 * @param	dx
	 * 			The dx movement involved in this new illegal adjacent position exception.
	 * @param	dy
	 * 			The dy movement involved in this new illegal adjacent position exception.
	 * @param	dz
	 * 			The dz movement involved in this new illegal adjacent position exception.
	 * @post	The dx value of this new illegal adjacent position exception is equal to 
	 * 			the given dx value.
	 * 			| new.getDxMov() = dx;
	 * @post	The dy value of this new illegal adjacent position exception is equal to 
	 * 			the given dy value.
	 * 			| new.getDyMov() = dy;
	 * @post	The dz value of this new illegal adjacent position exception is equal to 
	 * 			the given dz value.
	 * 			| new.getDzMov() = dz;
	 */
	public IllegalAdjacentPositionException(int dx, int dy, int dz){
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	/**
	 * 
	 * Return the dx value registered for this illegal adjacent position exception.
	 */
	
	@Basic @Immutable
	public double getDxMov() {
		return this.dx;
	}
	
	/**
	 * Return the dy value registered for this illegal adjacent position exception.
	 */
	@Basic @Immutable
	public double getDyMov() {
		return this.dy;
	}

	/**
	 * Return the dz value registered for this illegal adjacent position exception.
	 */
	@Basic @Immutable
	public double getDzMov() {
		return this.dz;
	}
	
	/**
	 * 
	 * Variables registering the dx,dy and dz movement involved in this illegal adjacent position exception.
	 */
	private final int dx;
	private final int dy;
	private final int dz;
	
	
	
	private static final long serialVersionUID = 5388709404169284579L;
}
