package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;
//TODO: alles van read en assign goed testen
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
	public Expression evaluate(Task task, int[] selectedCube) { // TODO:evaluate in while: w:=x p:=w use p
		return task.readVariable(variableName);
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return task.getVariables().containsKey(variableName);
	}
}