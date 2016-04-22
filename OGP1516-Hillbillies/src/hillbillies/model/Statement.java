package hillbillies.model;

import java.util.List;

import hillbillies.part3.programs.SourceLocation;

public class Statement {
	
	public class Work extends Statement {
		public Expression position;
		public SourceLocation sourceLocation;

		Work(Expression position, SourceLocation sourceLocation) {
			this.position = position;
			this.sourceLocation = sourceLocation;
		}
	}
	
	public class Sequence extends Statement {
		
		public List<Statement> statements;
		public SourceLocation sourceLocation;

		Sequence(List<Statement> statements, SourceLocation sourceLocation) {
			this.statements = statements;
			this.sourceLocation = sourceLocation;
		}
	}
}
