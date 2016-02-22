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
	private boolean flag;
	private static int init_min_weight = 25;
	private static int init_max_weight = 100;
	private static int min_weight = 1;
	private static int max_weight = 200;
	private int curr_min_weight;
	private int curr_max_weight;

	public Unit(double x, double y, double z, String name, int weight, int strength, int agility, int toughness, boolean flag) throws IllegalPositionException {
		this.setLocation(x,  y,  z);
//		this.name = name;
		setWeight(weight);
		
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
		List<Double> location = this.getLocation();
		position.add(location.get(0).intValue());
		position.add(location.get(1).intValue());
		position.add(location.get(2).intValue());
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
		if (this.flag = false){
			setFlag(true);
			int curr_min_weight = init_min_weight;
			int curr_max_weight = init_max_weight;
		}
		else {
			int curr_min_weight = min_weight;
			int curr_max_weight = max_weight;
		}
		if (weight >= (this.getStrength()+this.getAgility())/2){
			if (weight < curr_min_weight) 
				this.weight = curr_min_weight;
			else if ((weight >= curr_min_weight) && (weight <= curr_max_weight))
				this.weight = weight;
			else if (weight > curr_max_weight)
				this.weight = curr_max_weight; 
		}
		
		else{
			this.weight = (this.getStrength()+this.getAgility())/2;
			
		}
// T0D0 add getstrength getagility

	}
	
	/**
	 * 
	 * @return
	 */
	public int getWeight(){
		return this.weight;
	}	
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public boolean getFlag(){
		return this.flag;
	}
	
	
	
}
