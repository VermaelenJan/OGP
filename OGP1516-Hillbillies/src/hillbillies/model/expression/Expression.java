package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public abstract class Expression {
	
	public SourceLocation sourceLocation;
	
	Expression(SourceLocation sourceLocation){
		setSourceLocation(sourceLocation);
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	protected void setSourceLocation(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
}
