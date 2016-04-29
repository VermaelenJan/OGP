package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class FriendUnit extends UnitExpression {

	public FriendUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "Friend unit";
	}

}
