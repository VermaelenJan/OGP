package hillbillies.model.statement;

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
public class BreakStatement extends Statement {

	public BreakStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		for (Object el : calledBy) {
			if (el instanceof While) {
				return true;
			}
		}
		return false;
	}
}
