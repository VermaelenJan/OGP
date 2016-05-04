package hillbillies.model.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;

/**
 * A ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public interface IPosition {
	public abstract int[] evaluate(Unit unit, int[] selectedCube, Task task);
}
