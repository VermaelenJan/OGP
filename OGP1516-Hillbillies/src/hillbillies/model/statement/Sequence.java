package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.part3.programs.SourceLocation;

public class Sequence extends Statement {
		
		private List<Statement> statements;
		private SourceLocation sourceLocation;

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
		
		protected SourceLocation getSourcelocation() {
			return this.sourceLocation;
		}		

		@Override
		public Sequence execute(Unit unit, int[] selectedCube) {
			throw new RuntimeException();
		}

		@Override
		public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
			calledBy.add(this);
			for (Statement statement : getStatements()) {
				if (! statement.isWellFormed(task, calledBy,possibleUnit)) {
					return false;
				}
			}
			return true;
		}
}

