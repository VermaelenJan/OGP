package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.*;
import hillbillies.part3.programs.SourceLocation;

public class Work extends Statement {
	public Expression position;
	public SourceLocation sourceLocation;

	public Work(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		setPosition(position);
	}
	
	private Expression getPosition(){
		return this.position;
	}
	
	private void setPosition(Expression position){
		this.position = position;
	}
	
	@Override
	public Sequence execute(Unit unit,int[] selectedCube ){
		
		if (getPosition() instanceof SelectedPosition){
			int[] workTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.workAt(workTarget);
		}
		
		else if (getPosition() instanceof IPosition){
			int[] workTarget = {((IPosition) getPosition()).getX(),((IPosition) getPosition()).getY(),
					((IPosition) getPosition()).getZ()};
			unit.workAt(workTarget);
		}
		
		else if (getPosition() instanceof HerePosition){
			unit.workAt(unit.getOccupiedCube());
		}
		
		else if (getPosition() instanceof NextToPosition){
			
			unit.workAt(NextToPosition.getPassableNeighbouringPosition(((NextToPosition) getPosition()).getPosition(), unit, selectedCube));
		}
		
		else{
			throw new RuntimeException(); //TODO
		}
		return null;
	}
}
