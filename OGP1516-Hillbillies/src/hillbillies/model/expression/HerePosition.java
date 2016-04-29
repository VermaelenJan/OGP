package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class HerePosition extends Expression {

	public HerePosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Here";
	}
	
}
