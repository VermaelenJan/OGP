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
	public String toString() {
		return getVariableName(); 
	}

	@Override
	public Expression evaluate(Unit unit, int[] selectedCube) {
		return unit.getAssignedTask().readVariable(variableName);
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}