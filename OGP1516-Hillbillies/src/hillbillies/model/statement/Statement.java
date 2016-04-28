package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class Statement {
	public SourceLocation sourceLocation;
	public Statement(SourceLocation sourceLocation){
		setSourceLocation(sourceLocation);
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	protected void setSourceLocation(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public abstract void execute(Unit unit,int[] selectedCube );

}