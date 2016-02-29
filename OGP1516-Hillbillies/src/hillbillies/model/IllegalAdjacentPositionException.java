package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalAdjacentPositionException extends Exception{
	

	/**
	 * 
	 * Variables registering the dx,dy and dz movement.
	 */
	private final int dx;
	private final int dy;
	private final int dz;
	
	/**
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
	 * Return the dx value involved in this illegal adjacent position exception.
	 */
	
	@Basic @Immutable
	public double getDxMov() {
		return this.dx;
	}
	
	/**
	 * Return the dy value involved in this illegal adjacent position exception.
	 */
	@Basic @Immutable
	public double getDyMov() {
		return this.dy;
	}

	/**
	 * Return the dz value involved in this illegal adjacent position exception.
	 */
	@Basic @Immutable
	public double getDzMov() {
		return this.dz;
	}
	
	
	
	private static final long serialVersionUID = 5388709404169284579L;
}
