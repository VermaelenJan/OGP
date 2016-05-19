package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class BreakStatement extends Statement {

	public BreakStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		throw new RuntimeException();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		for (Object el : calledBy) {
			if (el instanceof While) {
				return true;
			}
		}
		return false;
	}
}
