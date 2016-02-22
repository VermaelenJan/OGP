package hillbillies.model;
import java.util.ArrayList;
import java.util.List;

//TODO: write tests for everything

/**
 * 
 * @author
 *
 */
public class Unit {
	
	int World_x = 50 - 1;
	int World_y = 50 - 1;
	int World_z = 50 - 1;
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

	//TODO: think about int vs double
	/**
	 * 
	 * @param location
	 * @param name
	 * @param weight
	 * @param strength
	 * @param agility
	 * @param toughness
	 * @param hitpoints
	 * @param stamina
	 * @param orientation
	 * @param flag
	 * @throws IllegalPositionException
	 */
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
	
	/**
	 * 
	 * @param location
	 * @param name
	 * @param weight
	 * @param strength
	 * @param agility
	 * @param toughness
	 * @param hitpoints
	 * @param stamina
	 * @param orientation
	 * @throws IllegalPositionException
	 */
	public Unit(List<Double> location, String name, int weight, int strength, int agility, int toughness, int hitpoints, int stamina, float orientation) throws IllegalPositionException {
		this(location, name, weight, strength, agility, toughness, hitpoints, stamina, orientation, true);
	}
	
	/**
	 * 
	 * @param location
	 * @throws IllegalPositionException
	 */
	public void setLocation(List<Double> location) throws IllegalPositionException{
		if (!isValidPosition(location))
			throw new IllegalPositionException(location);
		this.x_pos = location.get(0);
		this.y_pos = location.get(1);
		this.z_pos = location.get(2);
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	private boolean isValidPosition(List<Double> location) {
		return ((location.get(0) <= World_x) && (location.get(1) <= World_y) && (location.get(2) <= World_z) && 
				(location.get(0) >= 0) && (location.get(1) >= 0) && (location.get(2) >= 0));
	}

	/**
	 * 
	 * @return
	 */
	public List<Integer> getOccupiedCube() {
		List<Integer> position = new ArrayList<Integer>();
		List<Double> location = this.getLocation();
		position.add(location.get(0).intValue());
		position.add(location.get(1).intValue());
		position.add(location.get(2).intValue());
		return(position);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Double> getLocation() {
		List<Double> position = new ArrayList<Double>();
		position.add(this.x_pos);
		position.add(this.y_pos);
		position.add(this.z_pos);
		return(position);
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) throws IllegalNameException{
		if (!isValidName(name))
			throw new IllegalNameException(name);
		this.name = name;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private boolean isValidName(String name){
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (hasValidCharacters(name)));
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private boolean hasValidCharacters(String name){
		for (char c : name.toCharArray()){
			if ( (! Character.isLetter(c)) || (! (c == ' ') ) || (! (c == '"')) || (! (c == '\'')) )
				return false;
		}
		
		return true;
				
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName(){
		return this.name;
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
	

	/**
	 * 
	 * @return
	 */
	public int getMaxHitpointsStamina() {
		 return (int) Math.ceil((((double) this.getWeight())*((double) this.getToughness()))/50);
	}
	
	/**
	 * 
	 * @param hitpoints
	 * @return
	 */
	public boolean hasValidHitpoints(int hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= this.getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param stamina
	 * @return
	 */
	public boolean hasValidStamina(int stamina){
		return ((stamina >= 0) && (stamina <= this.getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param hitpoints
	 * 
	 * @pre
	 */
	public void setHitpoints(int hitpoints){
		assert this.hasValidHitpoints(hitpoints);
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
		assert this.hasValidStamina(stamina);
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
	
	public void advanceTime(float duration){
		
	}
	
	
}
