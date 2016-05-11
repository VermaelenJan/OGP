package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class IsFriend extends Expression implements IBool {

	private Expression friendUnit;

	public IsFriend(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setFriendUnit(unit);
	}
	
	private void setFriendUnit(Expression unit) {
		this.friendUnit = unit;
	}
	
	private Expression getFriendUnit() {
		return this.friendUnit;
	}

	@Override
	public Boolean evaluate(Task task, int[] selectedCube) {
		Unit friendUnit;

		if (getFriendUnit() instanceof ReadVariable) { 
			friendUnit = (Unit) ((Expression) getFriendUnit().evaluate(task, selectedCube)).evaluate(task, selectedCube);
		}
		else if (getFriendUnit() instanceof IUnitExpression) {
			friendUnit = (Unit) getFriendUnit().evaluate(task, selectedCube);
		}
		else {
			throw new RuntimeException();
		}
		
		if (friendUnit == null) {
			return false;
		}
		return task.getAssignedUnit().getFaction() == friendUnit.getFaction();
	}


	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getFriendUnit() instanceof IUnitExpression ||
				(getFriendUnit() instanceof ReadVariable
						&& (getFriendUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
					)) && getFriendUnit().isWellFormed(task, calledBy);
	}
}
