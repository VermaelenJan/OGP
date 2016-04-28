/**
 * 
 */
package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * @author Maxime
 *
 */
public class True extends Bool {

	public True(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public boolean getValue() {
		return true;
	}

}
