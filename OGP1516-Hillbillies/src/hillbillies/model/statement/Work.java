package hillbillies.model.statement;

import java.util.ArrayList;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.*;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Work extends Statement {
	private Expression position;

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
	public Sequence execute(Unit unit, int[] selectedCube){
		int[] targetPos;
		if (getPosition() instanceof IPosition){
			targetPos = (int[]) getPosition().evaluate(unit.getAssignedTask(), selectedCube, unit);
		}
		else if (getPosition() instanceof ReadVariable){
			targetPos = (int[])((IPosition)  getPosition().evaluate(unit.getAssignedTask(), selectedCube, unit)).
					evaluate(unit.getAssignedTask(), selectedCube, unit);
		}
		else {
			throw new RuntimeException();
		}
		
		if (targetPos == null) {
			unit.getAssignedTask().interruptTask();
			return null;
		}
		
		unit.workAt(targetPos);
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
					(getPosition() instanceof ReadVariable
						&& (getPosition().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IPosition)
					)) && getPosition().isWellFormed(task, calledBy,possibleUnit);
	}
}
