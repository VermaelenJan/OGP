package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class True extends Bool {

	public True(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public boolean getValue() {
		return true;
	}

	@Override
	public String toString() {
		return "True";
	}

}
