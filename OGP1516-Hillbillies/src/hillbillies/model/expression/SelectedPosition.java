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
public class SelectedPosition extends Expression {

	public SelectedPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Selected cube";
	}

	@Override
	public int[] evaluate(Unit unit, int[] selectedCube, Task task) {
		return selectedCube;
	}
}
