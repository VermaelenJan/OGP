package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IPosition;
import hillbillies.model.expression.ReadVariable;
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
	public Sequence execute(Unit unit,int[] selectedCube){
		IPosition pos = (IPosition) getPosition().evaluate(unit, selectedCube);
		unit.moveTo((int[]) pos.evaluate(unit, selectedCube)); 
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getPosition() instanceof IPosition ||
					(getPosition() instanceof ReadVariable 
						&& (getPosition().evaluate(task.getAssignedUnit(), task.getSelectedCube()) instanceof IPosition)
					)) && getPosition().isWellFormed(task, calledBy);
	}
}
