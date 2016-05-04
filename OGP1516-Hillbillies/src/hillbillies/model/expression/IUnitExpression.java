package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;

public interface IUnitExpression {
	public abstract Unit evaluate(Unit unit, int[] selectedCube, Task task);
}
