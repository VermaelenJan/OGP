package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IPosition;
import hillbillies.model.expression.SelectedPosition;
import hillbillies.part3.programs.SourceLocation;

public class MoveTo extends Statement {



	public MoveTo(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		setPosition(position);
	}
	
	private void setPosition(Expression position){
		this.position = position;
	}
	
	private Expression getPosition(){
		return this.position;
	}
	
	private Expression position;
	
	@Override
	public Sequence execute(Unit unit,int[] selectedCube){
		
		if (position instanceof SelectedPosition){
			int[] endTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.moveTo(endTarget);
		}
		
		else if (position instanceof IPosition){
			int[] endTarget = {((IPosition) getPosition()).getX(),((IPosition) getPosition()).getY(),
					((IPosition) getPosition()).getZ()};
			unit.moveTo(endTarget);
		}
		
		else{
			throw new RuntimeException(); //TODO
		}
		return null;
	}
}
