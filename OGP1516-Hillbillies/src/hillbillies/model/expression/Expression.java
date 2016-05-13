package hillbillies.model.expression;

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
		
	public abstract Object evaluate(Task task, int[] selectedCube, Unit possibleUnit);
	
	public abstract Boolean isWellFormed(Task task,  ArrayList<Object> calledBy, Unit possibleUnit);
}
