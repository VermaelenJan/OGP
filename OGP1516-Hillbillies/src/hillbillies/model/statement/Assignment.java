package hillbillies.model.statement;

import java.util.ArrayList;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;
import hillbillies.model.expression.IBool;
import hillbillies.model.expression.IPosition;
import hillbillies.model.expression.IUnitExpression;
import hillbillies.model.expression.ReadVariable;
import hillbillies.part3.programs.SourceLocation;

public class Assignment extends Statement {

	public Assignment(String variableName, Expression value,SourceLocation sourceLocation) {
		super(sourceLocation);
		setVariableName(variableName);
		setValue(value);
		}
	
	private String getVariableName(){
		return this.variableName;
	}
	private void setVariableName(String variableName){
		this.variableName = variableName;
	}
	
	private String variableName;
	
	private Expression getValue(){
		return this.value;
	}
	
	private void setValue(Expression value){
		this.value = value;
	}
	
	private Expression value;

	@Override
	public Sequence execute(Unit unit, int[] selectedCube) {
		unit.getAssignedTask().addVariable(getVariableName(), getValue(), sourceLocation);
		unit.startNewPending();
		return null;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {

		if (!task.getVariables().containsKey(getVariableName())) {
			task.addVariable(getVariableName(), getValue(), getSourceLocation()); 
			return true;
		}
		else if (equalTypes(task.getVariables().get(getVariableName()), getValue(), task, possibleUnit)) {
			task.addVariable(getVariableName(), getValue(), getSourceLocation());
			return true;
		}
		else {
			return false;
		}
	}
	
	private Boolean equalTypes( Expression expr1, Expression expr2, Task task, Unit possibleUnit){
		
		if (expr1 instanceof ReadVariable) {
			expr1 = ((ReadVariable)expr1).evaluate(task, possibleUnit);
		}
		if (expr2 instanceof ReadVariable) {
			expr2 = ((ReadVariable)expr2).evaluate(task, possibleUnit);
		}
		
		if (expr1 instanceof IBool && expr2 instanceof IBool){
			return true;
		}
		else if(expr1 instanceof IPosition && expr2 instanceof IPosition) {
			return true;
		}
		else if(expr1 instanceof IUnitExpression && expr2 instanceof IUnitExpression){
			return true;
		}
		else{
			return false;
		}
	}
}
