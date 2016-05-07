package hillbillies.model.expression;

import hillbillies.model.Unit;

public interface IBool {
	public abstract Boolean evaluate(Unit unit, int[] selectedCube);
}
