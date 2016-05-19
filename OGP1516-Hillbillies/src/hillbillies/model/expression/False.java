/**
 * 
 */
package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class False extends Expression implements IBool {

	public False(SourceLocation sourceLocation) {
		super(sourceLocation);
	}


	@Override
	public Boolean evaluate(Task task, Unit possibleUnit) {
		return false;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return true;
	}
}
