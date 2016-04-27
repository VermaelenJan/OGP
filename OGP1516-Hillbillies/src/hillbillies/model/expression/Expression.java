package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public abstract class Expression {
	
	public SourceLocation sourceLocation;
	
	Expression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
}
