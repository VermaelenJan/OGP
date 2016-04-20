package hillbillies.model;

import hillbillies.part3.programs.SourceLocation;

public class Expression {
	
	public class ReadVariable extends Expression{
		
		ReadVariable(String variableName, SourceLocation sourceLocation) {
			this.variableName = variableName;
			this.sourceLocation = sourceLocation;
		}

		public String variableName;
		public SourceLocation sourceLocation;
	}
	
}
