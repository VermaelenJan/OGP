package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;

public interface IPosition {
	public abstract int[] evaluate(Task task, Unit possibleUnit);
}
