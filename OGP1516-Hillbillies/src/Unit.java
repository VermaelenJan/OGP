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
	private String name;
	private int weight;
	private int strength;
	private int agility;
	private int toughness;
	private int hitpoints;
	private int stamina;
	private float orientation;
	private boolean flag;
	private static int init_min_val = 25;
	private static int init_max_val = 100;
	private static int min_val = 1;
	private static int max_val = 200;
	private int curr_min_val;
	private int curr_max_val;
	// afronden naar bovenste integer, dus + 1??
	private int max_hitpoints_stamina = (this.getWeight()*this.getToughness())/50;;

	//TODO: think about int vs double
	public Unit(List<Double> location, String name, int weight, int strength, int agility, int toughness, int hitpoints, int stamina, float orientation, boolean flag) throws IllegalPositionException {
		this.setLocation(location);
//		setName(name);
		setWeight(weight);
		setStrength(strength);
		setAgility(agility);
		setToughness(toughness);
		setHitpoints(hitpoints);
		setStamina(stamina);
		setOrientation(orientation);
	}
	
	public Unit(List<Double> location, String name, int weight, int strength, int agility, int toughness, int hitpoints, int stamina, float orientation) throws IllegalPositionException {
		this(location, name, weight, strength, agility, toughness, hitpoints, stamina, orientation, true);
	}
	
	public void setLocation(List<Double> location) throws IllegalPositionException{
		if (!isValidPosition(location))
			throw new IllegalPositionException(location);
		this.x_pos = location.get(0);
		this.y_pos = location.get(1);
		this.z_pos = location.get(2);
	}
	
	private boolean isValidPosition(List<Double> location) {
		return ((location.get(0) <= World_x) && (location.get(1) <= World_y) && (location.get(2) <= World_z) && 
				(location.get(0) >= 0) && (location.get(1) >= 0) && (location.get(2) >= 0));
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
			curr_min_val = init_min_val;
			curr_max_val = init_max_val;
		}
		else {
			curr_min_val = min_val;
			curr_max_val = max_val;
		}
		if (weight >= (this.getStrength()+this.getAgility())/2){
			if (weight < curr_min_val) 
				this.weight = curr_min_val;
			else if ((weight >= curr_min_val) && (weight <= curr_max_val))
				this.weight = weight;
			else if (weight > curr_max_val)
				this.weight = curr_max_val; 
		}
		
		else{
			this.weight = (this.getStrength()+this.getAgility())/2;
			
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public int getWeight(){
		return this.weight;
	}	
	
	/**
	 * 
	 * @return
	 */
	public int getStrength(){
		return this.strength;
	}
	
	/**
	 * 
	 * @param strength
	 */
	public void setStrength(int strength){
		if (this.flag = false){
			setFlag(true);
			curr_min_val = init_min_val;
			curr_max_val = init_max_val;
		}
		else {
			curr_min_val = min_val;
			curr_max_val = max_val;
		}

		if ( strength < curr_min_val) 
			this.strength = curr_min_val;
		else if ((strength >= curr_min_val) && (strength <= curr_max_val))
			this.strength = strength;
		else if (strength > curr_max_val)
			this.strength = curr_max_val; 
	}
	
	/**
	 * 
	 * @param agility
	 */
	public void setAgility(int agility){
		if (this.flag = false){
			setFlag(true);
			curr_min_val = init_min_val;
			curr_max_val = init_max_val;
		}
		else {
			curr_min_val = min_val;
			curr_max_val = max_val;
		}

		if ( agility < curr_min_val) 
			this.agility = curr_min_val;
		else if ((agility >= curr_min_val) && (agility <= curr_max_val))
			this.agility = agility;
		else if (agility > curr_max_val)
			this.agility = curr_max_val; 
	}
	/**
	 * 
	 * @return
	 */
	public int getAgility(){
		return this.agility;
	}
	
	/**
	 * 
	 * @param toughness
	 */
	public void setToughness(int toughness){
		if (this.flag = false){
			setFlag(true);
			curr_min_val = init_min_val;
			curr_max_val = init_max_val;
		}
		else {
			curr_min_val = min_val;
			curr_max_val = max_val;
		}

		if ( toughness < curr_min_val) 
			this.toughness = curr_min_val;
		else if ((toughness >= curr_min_val) && (toughness <= curr_max_val))
			this.toughness =toughness;
		else if (toughness > curr_max_val)
			this.toughness = curr_max_val; 
	}
	/**
	 * 
	 * @return
	 */
	public int getToughness(){
		return this.toughness;
	}
	/**
	 * 
	 * @param flag
	 */
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getFlag(){
		return this.flag;
	}
	
	public boolean isValidHitpoints(int hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= max_hitpoints_stamina));
	}
	
	public boolean isValidStamina(int stamina){
		return ((stamina >= 0) && (stamina <= max_hitpoints_stamina));
	}
	
	/**
	 * 
	 * @param hitpoints
	 * 
	 * @pre
	 */
	public void setHitpoints(int hitpoints){
		assert isValidHitpoints(hitpoints);
		this.hitpoints = hitpoints;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getHitpoints(){
		return this.hitpoints;
	}
	
	/**
	 * 
	 * @param stamina
	 * 
	 * @pre
	 */
	public void setStamina(int stamina){
		assert isValidStamina(stamina);
		this.stamina = stamina;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getStamina(){
		return this.stamina;
	}
	
	/**
	 * 
	 * @param orientation
	 */
	public void setOrientation(float orientation){
		this.orientation = orientation;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getOrientation(){
		return this.orientation;
	}

	
	
	
	
	
	
}
