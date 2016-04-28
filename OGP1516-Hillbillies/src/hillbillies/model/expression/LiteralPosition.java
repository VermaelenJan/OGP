package hillbillies.model.expression;


import hillbillies.part3.programs.SourceLocation;

public class LiteralPosition extends Expression implements IPosition{
	
	private int x;
	private int y;
	private int z;

	public LiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		super(sourceLocation);
		setX(x);
		setY(y);
		setZ(z);
	}

	@Override
	public void setX(int x) {
		this.x = x;
		
	}

	@Override
	public void setY(int y) {
		this.y = y;
		
	}

	@Override
	public void setZ(int z) {
		this.z = z;
		
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getZ() {
		return this.z;
	}
}
