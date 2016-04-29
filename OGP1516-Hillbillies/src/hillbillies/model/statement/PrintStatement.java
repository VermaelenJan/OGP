package hillbillies.model.statement;


import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

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
		System.out.println(getValue().toString());
		unit.getAssignedTask().finishedLastActivity();
		return null;
	}

}
