package hillbillies.model.statement;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.HerePosition;
import hillbillies.model.expression.IPosition;
import hillbillies.model.expression.NextToPosition;
import hillbillies.model.expression.SelectedPosition;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
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
	public Sequence execute(Unit unit,int[] selectedCube, Task task){
		
		if (getPosition() instanceof SelectedPosition){
			int[] endTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.moveTo(endTarget);
		}
		
		else if (getPosition() instanceof IPosition){
			int[] endTarget = {((IPosition) getPosition()).getX(),((IPosition) getPosition()).getY(),
					((IPosition) getPosition()).getZ()};
			unit.moveTo(endTarget);
		}
		
		else if (getPosition() instanceof HerePosition){
			unit.moveTo(unit.getOccupiedCube());
		}
		
		else if (getPosition() instanceof NextToPosition){
			
			unit.moveTo(NextToPosition.getPassableNeighbouringPosition(((NextToPosition) getPosition()).getPosition(), unit, selectedCube));
		}
		
		else{
			throw new RuntimeException(); //TODO
		}
		return null;
	}
}
