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
	public Boolean evaluate(Task task, int[] selectedCube, Unit possibleUnit) {
		Unit friendUnit;

		if (getFriendUnit() instanceof ReadVariable) { 
			friendUnit = (Unit) ((Expression) getFriendUnit().evaluate(task, selectedCube, possibleUnit)).evaluate(task, selectedCube, possibleUnit);
		}
		else if (getFriendUnit() instanceof IUnitExpression) {
			friendUnit = (Unit) getFriendUnit().evaluate(task, selectedCube, possibleUnit);
		}
		else {
			throw new RuntimeException();
		}
		
		if (friendUnit == null) {
			return false;
		}

		return possibleUnit.getFaction() == friendUnit.getFaction();
	}


	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getFriendUnit() instanceof IUnitExpression ||
				(getFriendUnit() instanceof ReadVariable
						&& (getFriendUnit().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IUnitExpression)
					)) && getFriendUnit().isWellFormed(task, calledBy, possibleUnit);
	}
}
