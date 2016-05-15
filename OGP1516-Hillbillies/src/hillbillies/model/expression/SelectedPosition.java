package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class SelectedPosition extends Expression implements IPosition {

	public SelectedPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate(Task task, Unit possibleUnit) {
		return task.getSelectedCube();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return task.getSelectedCube() != null;
	}
}
