import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalPositionException extends Exception {

	private final double x;
	private final double y;
	private final double z;

	
	public IllegalPositionException(double x, double y, double z) {
	
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	@Basic @Immutable
	public double getXpos() {
		return this.x;
	}
	
	@Basic @Immutable
	public double getYpos() {
		return this.y;
	}

	
	@Basic @Immutable
	public double getZpos() {
		return this.z;
	}


	private static final long serialVersionUID = -3414498906993155864L;
}
