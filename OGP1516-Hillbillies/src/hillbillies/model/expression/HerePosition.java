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
public class HerePosition extends Expression implements IPosition {

	public HerePosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Here";
	}

	@Override
	public int[] evaluate(Unit unit, int[] selectedCube) {
		return unit.getOccupiedCube();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}
