package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;

public interface IBool {
	public abstract Boolean evaluate(Task task, int[] selectedCube, Unit possibleUnit);
}
