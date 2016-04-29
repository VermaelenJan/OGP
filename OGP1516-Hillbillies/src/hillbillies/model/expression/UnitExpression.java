package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public abstract class UnitExpression extends Expression{

	UnitExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
}
