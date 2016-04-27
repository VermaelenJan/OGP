package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.model.expression.SelectedPosition;
import hillbillies.model.statement.Statement;
import hillbillies.part3.programs.SourceLocation;

public class Work extends Statement {
	public Expression position;
	public SourceLocation sourceLocation;

	public Work(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
	
	public void execute(Unit unit,int[] selectedCube ){
		if (position instanceof LiteralPosition){
			LiteralPosition positionExpression =  (LiteralPosition) position;
			int[] workTarget = {positionExpression.x,positionExpression.y,positionExpression.z};
			unit.workAt(workTarget);
		}
		
		else if (position instanceof SelectedPosition){
			int[] workTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.workAt(workTarget);
		}
		
		else{
			throw new RuntimeException(); //TODO
		}

	}
}
