package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class AnyUnit extends UnitExpression {

	public AnyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Any unit";
	}

}
