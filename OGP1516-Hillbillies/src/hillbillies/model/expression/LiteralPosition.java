package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
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

	private void setX(int x) {
		this.x = x;
		
	}

	private void setY(int y) {
		this.y = y;
		
	}

	private void setZ(int z) {
		this.z = z;
		
	}

	private int getX() {
		return this.x;
	}

	private int getY() {
		return this.y;
	}

	private int getZ() {
		return this.z;
	}

	@Override
	public String toString() {
		return "X: " + getX() + " Y: " + getY() + " Z: " + getZ();
	}

	@Override
	public int[] evaluate(Unit unit, int[] selectedCube) {
		int[] position = {getX(), getY(), getZ()};
		return position;
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}
}
