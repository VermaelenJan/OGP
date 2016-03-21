package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class IllegalValueException extends RuntimeException {

	private final double value;
	
	public IllegalValueException(double value){
		this.value = value;
	}
	
	
	@Basic @Immutable
	public double getValue(){
		return this.value;
	}
	
	private static final long serialVersionUID = 5271679868895387308L;
	
}
