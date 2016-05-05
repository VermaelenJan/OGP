package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.statement.Statement;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
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
		public Sequence execute(Unit unit, int[] selectedCube, Task task) {
			throw new RuntimeException();
			// TODO
		}

		@Override
		public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
			calledBy.add(this);
			for (Statement statement : getStatements()) {
				if (! statement.isWellFormed(task, calledBy)) {
					return false;
				}
			}
			return true;
		}
}

