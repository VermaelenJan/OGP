package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class ReadVariable extends Expression{
	
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		setVariableName(variableName);
	}
	
	private String getVariableName(){
		return this.variableName;
	}
	
	private void setVariableName(String variableName){
		this.variableName = variableName;
	}

	private String variableName;
	

	@Override
	public Expression evaluate(Task task, int[] selectedCube, Unit possibleUnit) { 
		if (task.readVariable(getVariableName()) instanceof ReadVariable) {
			return (Expression) task.readVariable(getVariableName()).evaluate(task, selectedCube, possibleUnit);
		}
		return task.readVariable(getVariableName());
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return task.getVariables().containsKey(getVariableName());
	}
}