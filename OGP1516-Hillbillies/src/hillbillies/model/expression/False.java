/**
 * 
 */
package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class False extends Expression implements IBool {

	public False(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "False";
		
	}

	@Override
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return false;
	}

}
