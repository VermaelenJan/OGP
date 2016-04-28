/**
 * 
 */
package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * @author Maxime
 *
 */
public abstract class Bool extends Expression{

	Bool(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public abstract boolean getValue();
}
