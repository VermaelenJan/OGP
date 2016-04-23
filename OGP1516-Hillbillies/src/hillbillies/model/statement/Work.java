package hillbillies.model.statement;

import hillbillies.model.expression.Expression;
import hillbillies.model.statement.Statement;
import hillbillies.part3.programs.SourceLocation;

public class Work extends Statement {
	public Expression position;
	public SourceLocation sourceLocation;

	public Work(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
}
