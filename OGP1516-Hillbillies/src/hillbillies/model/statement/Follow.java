package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IUnitExpression;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.part3.programs.SourceLocation;

public class Follow extends Statement {

	private Expression followUnit;

	public Follow(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setFollowUnit(unit);
	}
	
	private void setFollowUnit(Expression unit) {
		this.followUnit = unit;
	}
	
	private Expression getFollowUnit() {
		return this.followUnit;
	}

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		Unit followUnit = (Unit) getFollowUnit().evaluate(unit, selectedCube);
		int followUnitX = followUnit.getOccupiedCube()[0];
		int followUnitY = followUnit.getOccupiedCube()[1];
		int followUnitZ = followUnit.getOccupiedCube()[2];

		if (unit.getPositionObj().getNeighbouringCubesIncludingOwn(unit.getOccupiedCube()).contains
				(unit.getWorld().getCube(followUnitX, followUnitY, followUnitZ))) {
			unit.getAssignedTask().finishedLastActivity();
		}
				
		List<Statement> list = new ArrayList<>();
		list.add(new MoveTo( new LiteralPosition(followUnitX, followUnitY,followUnitZ, sourceLocation), sourceLocation));
		
		list.add(new Follow(getFollowUnit(), sourceLocation));
		return new Sequence(list, sourceLocation); 
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getFollowUnit() instanceof IUnitExpression && getFollowUnit().isWellFormed(task, calledBy));
	}

}
