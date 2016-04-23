package hillbillies.model;

import hillbillies.part3.programs.SourceLocation;

public class Expression {
	
	public class ReadVariable extends Expression{ //TODO: all vars private with get n set
		
		ReadVariable(String variableName, SourceLocation sourceLocation) {
			this.variableName = variableName;
			this.sourceLocation = sourceLocation;
		}

		public String variableName;
		public SourceLocation sourceLocation;
	}
	
	public class LiteralPosition extends Expression {
		
		public int x;
		public int y;
		public int z;
		public SourceLocation sourceLocation;

		LiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.sourceLocation = sourceLocation;
		}
	}

}
