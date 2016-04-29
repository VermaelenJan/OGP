package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class ThisUnit extends UnitExpression {

	public ThisUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "This unit"; 
	}

}
