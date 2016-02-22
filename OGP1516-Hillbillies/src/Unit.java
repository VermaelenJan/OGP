import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author
 *
 */
public class Unit { //TODO: put (x,y,z) into a list or...
	
	Unit_Constants constants = new Unit_Constants();
	int World_x = constants.WORLD_X - 1;
	int World_y = constants.WORLD_Y - 1;
	int World_z = constants.WORLD_Z - 1;
	private double x_pos;
	private double y_pos;
	private double z_pos;
//	private String name;
	private int weight;
//	private float strength;
//	private float agility;
//	private float toughness;

	public Unit(double x, double y, double z, String name, int weight, int strength, int agility, int toughness) throws IllegalPositionException {
		this.setLocation(x,  y,  z);
//		this.name = name;
		this.weight = weight;
//		this.strength = strength;
//		this.agility = agility;
//		this.toughness = toughness;
	}
	
	public void setLocation(double x, double y, double z) throws IllegalPositionException{
		if (!isValidPosition(x, y, z))
			throw new IllegalPositionException(x, y, z);
		this.x_pos = x;
		this.y_pos = y;
		this.z_pos = z;
	}
	
	private boolean isValidPosition(double x, double y, double z) {
		return ((x <= World_x) && (y <= World_y) && (z <= World_z) && (x >= 0) && (y >= 0) && (z >= 0));
	}

	public List<Integer> getOccupiedCube() {
		List<Integer> position = new ArrayList<Integer>();
		position.add((int) this.x_pos);
		position.add((int) this.y_pos);
		position.add((int) this.z_pos);
		return(position);
	}
	
	public List<Double> getLocation() {
		List<Double> position = new ArrayList<Double>();
		position.add(this.x_pos);
		position.add(this.y_pos);
		position.add(this.z_pos);
		return(position);
	}
	
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(int weight){
		if (weight < 1)
			this.weight = 1;
		else if ((weight >= 1) && (weight < 200))
			this.weight = weight;
		else if (weight > 200)
			this.weight = 200;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWeight(){
		return this.weight;
	}	
	
	
}
