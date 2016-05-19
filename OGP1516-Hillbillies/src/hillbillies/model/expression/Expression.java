package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
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
		
	public abstract Object evaluate(Task task, Unit possibleUnit);
	
	public abstract Boolean isWellFormed(Task task,  ArrayList<Object> calledBy, Unit possibleUnit);
}
