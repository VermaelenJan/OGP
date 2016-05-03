package hillbillies.model.statement;

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
public class Assignment extends Statement {

	public Assignment(String variableName, Expression value,SourceLocation sourceLocation) { //TODO: new assignment, type,...
		super(sourceLocation);
		setVariableName(variableName);
		setValue(value);
		}
	
	public String getVariableName(){
		return this.variableName;
	}
	private void setVariableName(String variableName){
		this.variableName = variableName;
	}
	
	private String variableName;
	
	public Expression getValue(){
		return this.value;
	}
	
	private void setValue(Expression value){
		this.value = value;
	}
	
	private Expression value;

	@Override
	public Sequence execute(Unit unit, int[] selectedCube, Task task) {
		task.addVariable(variableName, value, sourceLocation);
		return null;
	}
}
