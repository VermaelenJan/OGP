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
	public Boolean evaluate(Unit unit, int[] selectedCube, Task task) {
		return unit.getFaction() == ((Unit) getFriendUnit().evaluate(unit, selectedCube, task)).getFaction();
	}

	@Override
	public String toString() {
		return "Is friend " + getFriendUnit().toString();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getFriendUnit() instanceof IUnitExpression && getFriendUnit().isWellFormed(task, calledBy);
	}
}
