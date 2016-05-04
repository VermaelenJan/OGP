package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class ReadVariable extends Expression{ //TODO: all vars private with get n set
	
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		setVariableName(variableName);
	}
	
	protected String getVariableName(){
		return this.variableName;
	}
	
	private void setVariableName(String variableName){
		this.variableName = variableName;
	}

	private String variableName;
	
	@Override
	public String toString() {
		return getVariableName(); 
	}

	@Override
	public Expression evaluate(Unit unit, int[] selectedCube, Task task) {
		return task.readVariable(variableName);
	}
}