package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.LiteralPosition;
import hillbillies.part3.programs.SourceLocation;

public class Follow extends Statement {

	private Expression followUnit;

	public Follow(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.followUnit = unit;
	}

	@Override
	public Sequence execute(Unit unit, int[] selectedCube, Task task) {
		Unit followUnit = (Unit) this.followUnit.evaluate(unit, selectedCube, task);
		int followUnitX = followUnit.getOccupiedCube()[0];
		int followUnitY = followUnit.getOccupiedCube()[1];
		int followUnitZ = followUnit.getOccupiedCube()[2];

		if (unit.getPositionObj().getNeighbouringCubesIncludingOwn(unit.getOccupiedCube()).contains
				(unit.getWorld().getCube(followUnitX, followUnitY, followUnitZ))) {
			task.finishedLastActivity();
		}
				
		List<Statement> list = new ArrayList<>();
		list.add(new MoveTo( new LiteralPosition(followUnitX, followUnitY,followUnitZ, sourceLocation), sourceLocation));
		
		list.add(new Follow(this.followUnit, sourceLocation));
		return new Sequence(list, sourceLocation); 
	}

}
