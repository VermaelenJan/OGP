package hillbillies.model.statement;

import hillbillies.model.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public class MoveTo extends Statement {

	public Expression position;

	public MoveTo(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
}
