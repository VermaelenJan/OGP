package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.*;
import hillbillies.part3.programs.SourceLocation;

public class Work extends Statement {
	public Expression position;
	public SourceLocation sourceLocation;

	public Work(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.position = position;
	}
	
	@Override
	public void execute(Unit unit,int[] selectedCube ){
		
		if (position instanceof SelectedPosition){
			int[] workTarget = {selectedCube[0], selectedCube[1], selectedCube[2]};
			unit.workAt(workTarget);
		}
		
		else if (position instanceof IPosition){
			int[] workTarget = {((IPosition) position).getX(),((IPosition) position).getY(),
					((IPosition) position).getZ()};
			unit.workAt(workTarget);
		}
		
		else{
			throw new RuntimeException(); //TODO
		}

	}
}
