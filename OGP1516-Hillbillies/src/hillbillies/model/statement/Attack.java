package hillbillies.model.statement;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IUnitExpression;
import hillbillies.model.expression.ReadVariable;
import hillbillies.part3.programs.SourceLocation;

public class Attack extends Statement {

	private Expression attackUnit;

	public Attack(Expression unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		setAttackUnit(unit);
	}
	
	private void setAttackUnit(Expression unit) {
		this.attackUnit = unit;
	}
	
	private Expression getAttackUnit() {
		return this.attackUnit;
	}
	
	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		unit.attack((Unit)getAttackUnit().evaluate(unit.getAssignedTask(), selectedCube));
		((Unit)getAttackUnit().evaluate(unit.getAssignedTask(), selectedCube)).defend(unit);
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return (getAttackUnit() instanceof IUnitExpression ||
					(getAttackUnit() instanceof ReadVariable
						&& (getAttackUnit().evaluate(task, task.getSelectedCube()) instanceof IUnitExpression)
					)) && getAttackUnit().isWellFormed(task, calledBy);
	}
}
