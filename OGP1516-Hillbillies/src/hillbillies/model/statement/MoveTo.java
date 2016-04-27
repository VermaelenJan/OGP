package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IPosition;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.model.expression.SelectedPosition;
import hillbillies.part3.programs.SourceLocation;

public class MoveTo extends Statement {

	public Expression position;

	public MoveTo(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
	
	@Override
	public void execute(Unit unit,int[] selectedCube){
		
		if (position instanceof SelectedPosition){
			int[] endTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.moveTo(endTarget);
		}
		
		else if (position instanceof IPosition){
			int[] endTarget = {((IPosition) position).getX(),((IPosition) position).getY(),
					((IPosition) position).getZ()};
			unit.moveTo(endTarget);
		}
		
		else{
			throw new RuntimeException(); //TODO
		}
	}
}
