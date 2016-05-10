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
		if (getFriendUnit().evaluate(task, selectedCube) == null) {
			return null;
		}
		if (getFriendUnit() instanceof ReadVariable) {
			return task.getAssignedUnit().getFaction() == 
					((Unit) ((Expression) getFriendUnit().evaluate(task, selectedCube)).evaluate(task, selectedCube)).getFaction();
		}
		else if (getFriendUnit() instanceof IUnitExpression) {
			return task.getAssignedUnit().getFaction() == ((Unit) getFriendUnit().evaluate(task, selectedCube)).getFaction();
		}
		else {
			throw new RuntimeException();
		}
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
