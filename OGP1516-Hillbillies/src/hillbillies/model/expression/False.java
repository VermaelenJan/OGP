/**
 * 
 */
package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
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
	public Boolean evaluate(Task task, int[] selectedCube) {
		return false;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}
