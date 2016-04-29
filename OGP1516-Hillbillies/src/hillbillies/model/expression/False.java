/**
 * 
 */
package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * @author Maxime
 *
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
