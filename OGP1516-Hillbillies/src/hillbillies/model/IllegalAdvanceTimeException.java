package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalAdvanceTimeException extends RuntimeException {

	private final double dt;
	
	public IllegalAdvanceTimeException(double dt){
		this.dt = dt;
	}
	
	
	@Basic @Immutable
	public double getDt(){
		return this.dt;
	}
	
	private static final long serialVersionUID = -3612270809203081170L;
	
}
