package hillbillies.model.expression;

import hillbillies.part3.programs.SourceLocation;

public class LiteralPosition extends Expression {
	
	public int x;
	public int y;
	public int z;
	public SourceLocation sourceLocation;

	public LiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
