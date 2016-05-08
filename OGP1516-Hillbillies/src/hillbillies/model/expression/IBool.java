package hillbillies.model.expression;

import hillbillies.model.Task;

public interface IBool {
	public abstract Boolean evaluate(Task task, int[] selectedCube);
}
