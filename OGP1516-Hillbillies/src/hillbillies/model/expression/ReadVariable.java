package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
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
	public Expression evaluate(Task task, int[] selectedCube) { 
		if (task.readVariable(variableName) instanceof ReadVariable) {
			return (Expression) task.readVariable(variableName).evaluate(task, selectedCube);
		}
		return task.readVariable(variableName);
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return task.getVariables().containsKey(variableName);
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		return getVariableName();
	}
}