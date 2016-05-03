/**
 * 
 */
package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class False extends Bool{

	public False(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public boolean getValue() {
		return false;
	}

	@Override
	public String toString() {
		return "False";
		
	}

}
