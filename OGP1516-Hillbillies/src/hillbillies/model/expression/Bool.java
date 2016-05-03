package hillbillies.model.expression;


import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Bool extends Expression{

	public Bool(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public boolean getValue() {
		throw new RuntimeException();
	}

	@Override
	public String toString() {
		return "Boolean";
	}
	
//	public Expression notExpression(Expression expression, SourceLocation sourceLocation) {
//		if (expression instanceof Bool) {
//			if ((Bool)expression instanceof True ) {
//				return new False(sourceLocation);
//			}
//			else {
//				return new True(sourceLocation);
//			}
//		}
//		
//		else {
//			throw new RuntimeException();
//		}
//	}
//	
//	public Expression andExpression (Expression left, Expression right, SourceLocation sourceLocation) {
//		if ((!(left instanceof Bool)) || (!(right instanceof Bool))) {
//			throw new RuntimeException();
//		} 
//		else if ((((Bool) left).getValue() == true ) && (((Bool) right).getValue() == true)) {
//			return new True(sourceLocation);
//		}
//		else {
//			return new False(sourceLocation);
//		}
//	}
//	
//	public Expression orExpression(Expression left, Expression right, SourceLocation sourceLocation){
//		if ((!(left instanceof Bool)) || (!(right instanceof Bool))) {
//			throw new RuntimeException();
//		} 
//		else if ((((Bool) left).getValue() == true ) || (((Bool) right).getValue() == true)) {
//			return new True(sourceLocation);
//		}
//		else {
//			return new False(sourceLocation);
//		}
//	}

	@Override
	public Object evaluate(Unit unit, int[] selectedCube, Task task) {
		throw new RuntimeException();
	}
	
}
