package hillbillies.model;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author
 *
 */
public class IllegalPositionException extends Exception {

	private final double x;
	private final double y;
	private final double z;
	
	/**
	 * 
	 * @param location
	 */
	public IllegalPositionException(List<Double> location) {
		this.x = location.get(0);
		this.y = location.get(1);
		this.z = location.get(2);
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Immutable
	public double getXpos() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Immutable
	public double getYpos() {
		return this.y;
	}

	/**
	 * 
	 * @return
	 */
	@Basic @Immutable
	public double getZpos() {
		return this.z;
	}

	private static final long serialVersionUID = -3414498906993155864L;
}
