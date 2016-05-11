package hillbillies.model.statement;

import java.util.ArrayList;
import java.util.Arrays;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

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
		if (getValue().evaluate(unit.getAssignedTask(), selectedCube) instanceof Boolean) {
			System.out.println(getValue().evaluate(unit.getAssignedTask(), selectedCube)); 
		}
		else if (getValue().evaluate(unit.getAssignedTask(), selectedCube) instanceof int[]) {
			System.out.println(Arrays.toString((int[]) getValue().evaluate(unit.getAssignedTask(), selectedCube)));
		}
		else if (getValue().evaluate(unit.getAssignedTask(), selectedCube) instanceof Unit) {
			System.out.println(((Unit) getValue().evaluate(unit.getAssignedTask(), selectedCube)).getName());
		}
		else if (getValue().evaluate(unit.getAssignedTask(), selectedCube) == null){
			System.out.println("null");
		}
		else {
			throw new RuntimeException();
		}
		unit.startNewPending();
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		calledBy.add(this);
		return getValue().isWellFormed(task, calledBy);
	}
}
