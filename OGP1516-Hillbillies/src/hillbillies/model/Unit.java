package hillbillies.model;
import java.util.ArrayList;
import java.util.List;

//TODO: write tests for everything
//TODO: CONSTANTS, randomMethods,... 
//TODO: fix flag
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
	private double orientation;
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
	 *			The location for this new unit.
	 * @param name
	 * 			The name for this new unit.
	 * @param weight
	 * 			The weight for this new unit.
	 * @param strength
	 * 			The strength for this new unit.
	 * @param agility
	 * 			The agility for this new unit.
	 * @param toughness
	 * 			The toughness for this new unit.
	 * @param hitpoints
	 * 			The hitpoints for this new unit.
	 * @param stamina
	 * 			The stamina for this new unit.
	 * @param orientation
	 * 			The orientation for this new unit.
	 * @post The location of this new unit is equal to the given location.
	 * 			| new.getLocation() == location
	 * @post The name of this new unit is equal to the given name.
	 * 			| new.getName() == name
	 * @effect the given weight is set as the weight of this new unit.
	 * 			| setWeight(weight)
	 * @effect the given strength is set as the strength of this new unit.
	 * 			| setStrength(strength)
	 * @effect the given agility is set as the agility of this new unit.
	 * 			| setAgility(agility)
	 * @effect the given toughness is set as the toughness of this new unit.
	 * 			| setToughness(toughness)
	 * @effect the given hitpoints are set as the hitpoints of this new unit.
	 * 			| setHitpoints(hitpoints)
	 * @effect the given stamina is set as the stamina of this new unit.
	 * 			| setStamina(stamina)
	 * @effect the given orientation is set as the orientation of this new unit.
	 * 			| setOrientation(orientation)
	 * @throws IllegalPositionException
	 * 			The given position is not a valid position for a unit.
	 * 			| ! canHaveAsPosition(position) //TODO: add this.?
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 * 
	 */
	public Unit(List<Double> location, String name, int weight, int strength, int agility, int toughness, 
			int hitpoints, int stamina, double orientation) throws IllegalPositionException, IllegalNameException {
		setLocation(location);
		setName(name);
		setWeight(weight, true);
		setStrength(strength, true);
		setAgility(agility, true);
		setToughness(toughness, true);
		setHitpoints(hitpoints);
		setStamina(stamina);
		setOrientation(orientation);
	}
	
	public Unit(List<Double> location, String name, int weight, int strength, int agility, int toughness, 
			int hitpoints, int stamina) throws IllegalPositionException, IllegalNameException{
		this(location, name, weight, strength, agility, toughness, hitpoints, stamina, Math.PI/2);
	}
	
	/**
	 * 
	 * @param location
	 * 			The location to set.
	 * @throws IllegalPositionException
	 * 			the location is not a valid location for a unit.
	 * 			| !canHaveAsPosition(location)
	 */
	public void setLocation(List<Double> location) throws IllegalPositionException{
		if (!canHaveAsPosition(location))
			throw new IllegalPositionException(location);
		this.x_pos = location.get(0);
		this.y_pos = location.get(1);
		this.z_pos = location.get(2);
	}
	
	/**
	 * 
	 * @param location
	 * 			The location to check.
	 * @return True if and only if the location is in the dimensions of the predefined world. So if the x,y and z value 
	 * 			are smaller than the world_x, world_y and world_z values and the x,y and z value are greater than 0.
	 * 			| result == (location.get(0) <= World_x) && (location.get(1) <= World_y) && (location.get(2) <= World_z) && 
				| (location.get(0) >= 0) && (location.get(1) >= 0) && (location.get(2) >= 0))
	 */
	private  boolean canHaveAsPosition(List<Double> location) {
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
	private static boolean isValidName(String name){
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (hasAllValidCharacters(name)));
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private static boolean hasAllValidCharacters(String name){
		for (char c : name.toCharArray()){
			if ( (! Character.isLetter(c)) && (! (c == ' ') ) && (! (c == '"')) && (! (c == '\'')) )
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
	private void setWeight(int weight, boolean flag){
		if (flag){
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
	 * @param weight
	 */
	public void setWeight(int weight) {
		setWeight(weight, false);
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
	private void setStrength(int strength, boolean flag){
		if (flag){
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
	 * @param strength
	 */
	public void setStrength(int strength) {
		setStrength(strength, false);
	}
	
	/**
	 * 
	 * @param agility
	 */
	private void setAgility(int agility, boolean flag){
		if (flag){
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
	 * @param agility
	 */
	public void setAgility(int agility) {
		setAgility(agility, false);
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
	private void setToughness(int toughness, boolean flag){
		if (flag){
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
	 * @param toughness
	 */
	public void setToughness(int toughness) {
		setToughness(toughness, false);
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
	 * @return
	 */
	private int getMaxHitpointsStamina() {
		 return (int) Math.ceil((((double) this.getWeight())*((double) this.getToughness()))/50);
	}
	
	/**
	 * 
	 * @param hitpoints
	 * @return
	 */
	private boolean hasValidHitpoints(int hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= this.getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param stamina
	 * @return
	 */
	private boolean hasValidStamina(int stamina){
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
	public void setOrientation(double orientation){
		this.orientation = orientation;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * 
	 * @param duration
	 */
	public void advanceTime(float duration){
		
	}
}
