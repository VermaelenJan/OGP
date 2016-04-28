package hillbillies.model.statement;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.part3.programs.SourceLocation;

public class Sequence extends Statement {
		
		public List<Statement> statements;
		public SourceLocation sourceLocation;

		public Sequence(List<Statement> statements, SourceLocation sourceLocation) {
			super(sourceLocation);
			setStatements(statements);
		}
		
		public List<Statement> getStatements(){
			return this.statements;
		}
		
		private void setStatements(List<Statement> statements){
			this.statements = statements;
		}
		

		@Override
		public void execute(Unit unit, int[] selectedCube) {
			throw new RuntimeException();
			// TODO
		}
}

