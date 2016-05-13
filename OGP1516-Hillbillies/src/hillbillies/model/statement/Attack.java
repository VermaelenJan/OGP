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
		Unit tempAttackUnit = null;
		if (getAttackUnit() instanceof IUnitExpression){
			tempAttackUnit = (Unit) getAttackUnit().evaluate(unit.getAssignedTask(), selectedCube, unit);
		}
		else if (getAttackUnit() instanceof ReadVariable){
			tempAttackUnit = (Unit) ((IUnitExpression) getAttackUnit().evaluate(unit.getAssignedTask(), selectedCube, unit)).
					evaluate(unit.getAssignedTask(), selectedCube, unit);
		}
		else{
			throw new RuntimeException();
		}
		
		if (tempAttackUnit == null) {
			unit.getAssignedTask().interruptTask();
			return null;
		}
		
		unit.attack(tempAttackUnit);
		tempAttackUnit.defend(unit);
		
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return (getAttackUnit() instanceof IUnitExpression ||
					(getAttackUnit() instanceof ReadVariable
						&& (getAttackUnit().evaluate(task, task.getSelectedCube(), possibleUnit) instanceof IUnitExpression)
					)) && getAttackUnit().isWellFormed(task, calledBy, possibleUnit);
	}
}
