package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class ReadVariable extends Expression{ //TODO: all vars private with get n set
	
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		setVariableName(variableName);
	}
	
	
	protected void setVariableName(String variableName){
		this.variableName = variableName;
	}

	public String variableName;
	public SourceLocation sourceLocation;
}
