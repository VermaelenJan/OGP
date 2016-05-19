package hillbillies.model.statement;

import java.util.ArrayList;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IPosition;
import hillbillies.model.expression.ReadVariable;
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
	public Sequence execute(Unit unit, int[] selectedCube){ 
		int[] targetPos;
		if (getPosition() instanceof IPosition){
			targetPos = (int[]) getPosition().evaluate(unit.getAssignedTask(), unit);
		}
		else if (getPosition() instanceof ReadVariable){
			targetPos = (int[])((IPosition)  getPosition().evaluate(unit.getAssignedTask(), unit)).
					evaluate(unit.getAssignedTask(), unit);
		}
		else {
			throw new RuntimeException();
		}
		
		if (targetPos == null) {
			unit.getAssignedTask().interruptTask();
			return null;
		}
		
		unit.moveTo(targetPos);
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
					(getPosition() instanceof ReadVariable 
						&& (getPosition().evaluate(task, possibleUnit) instanceof IPosition)
					)) && getPosition().isWellFormed(task, calledBy, possibleUnit);
	}
}
