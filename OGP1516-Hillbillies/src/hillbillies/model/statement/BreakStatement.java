package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class BreakStatement extends Statement {

	public BreakStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		return null;
	}

}
