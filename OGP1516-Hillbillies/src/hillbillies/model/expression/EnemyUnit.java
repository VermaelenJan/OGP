package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class EnemyUnit extends UnitExpression {

	public EnemyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Enemy unit";
	}

}
