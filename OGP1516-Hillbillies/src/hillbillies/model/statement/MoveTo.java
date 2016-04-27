package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.model.expression.SelectedPosition;
import hillbillies.part3.programs.SourceLocation;

public class MoveTo extends Statement {

	public Expression position;

	public MoveTo(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
	
	public void execute(Unit unit,int[] selectedCube){
		if (position instanceof LiteralPosition) {
			LiteralPosition positionExpression = (LiteralPosition) position;
			int[] moveToTarget = {positionExpression.x,positionExpression.y,positionExpression.z};
			unit.moveTo(moveToTarget);
		}
		
		else if (position instanceof SelectedPosition) {
			int[] moveToTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.moveTo(moveToTarget);
		}
		
		else{
			throw new RuntimeException(); //TODO
		}
	}
}
