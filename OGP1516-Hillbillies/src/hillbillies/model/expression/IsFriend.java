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
		return task.getAssignedUnit().getFaction() == ((Unit) getFriendUnit().evaluate(task, selectedCube)).getFaction();
	}


	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getFriendUnit() instanceof IUnitExpression ||
				(getFriendUnit() instanceof ReadVariable
						&& (getFriendUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
					)) && getFriendUnit().isWellFormed(task, calledBy);
	}

	@Override
	public String toString(Task task, int[] selectedCube) { // da spel geeft gene boolean terug
		System.out.println( evaluate(task, selectedCube));
		return null;
	}
}
