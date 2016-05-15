package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.Arrays;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class PrintStatement extends Statement {

	public PrintStatement(Expression value, SourceLocation sourceLocation) {
		super(sourceLocation);
		setValue(value);
	}
	
	private Expression getValue(){
		return this.value;
	}
	
	private void setValue(Expression value){
		this.value = value;
	}
	
	private Expression value;

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		if (getValue().evaluate(unit.getAssignedTask(), unit) instanceof Boolean) {
			System.out.println(getValue().evaluate(unit.getAssignedTask(), unit)); 
		}
		else if (getValue().evaluate(unit.getAssignedTask(), unit) instanceof int[]) {
			System.out.println(Arrays.toString((int[]) getValue().evaluate(unit.getAssignedTask(), unit)));
		}
		else if (getValue().evaluate(unit.getAssignedTask(), unit) instanceof Unit) {
			System.out.println(((Unit) getValue().evaluate(unit.getAssignedTask(), unit)).getName());
		}
		else if (getValue().evaluate(unit.getAssignedTask(), unit) == null){
			System.out.println("null");
		}
		else {
			throw new RuntimeException();
		}
		unit.startNewPending();
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		calledBy.add(this);
		return getValue().isWellFormed(task, calledBy, possibleUnit);
	}
}
