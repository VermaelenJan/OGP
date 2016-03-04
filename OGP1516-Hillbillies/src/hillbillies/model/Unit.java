package hillbillies.model;



import java.util.Random;



import be.kuleuven.cs.som.annotate.Basic;
// import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;




/**
 * @invar  The location of each unit must be a valid location for any
 *         unit.
 *       | isValidLocation(getLocation())
 * @invar  The name of each unit must be a valid name for any
 *         unit.
 *       | isValidName(getName())
 * @invar  The weight of each unit must be greater or equal than the units strength plus agility, divided by two,
 * 		   and smaller than MAX_VAL.
 *       | getWeight() >= (getAgility()+getStrength)/2 && (getWeight() <= MAX_VAL)
 * @invar  The strength of each unit must be between MIN_VAL and MAX_VAL,inclusively.
 * 		 | (getStrength() >= MIN_VAL) && (getStrength() <= MAX_VAL)
 * @invar  The agility of each unit must be between MIN_VAL and MAX_VAL,inclusively.
 * 		 | (getAgility() >= MIN_VAL) && (getAgility() <= MAX_VAL) 
 * @invar  The toughness of each unit must be between MIN_VAL and MAX_VAL,inclusively.
 * 		 | (getTougness() >= MIN_VAL) && (getToughness() <= MAX_VAL)
 * @invar  The hitpoints of each unit must be a valid hitpoints for any
 *         unit.
 *       | isValidHitpoints(getHitpoints())
 * @invar  The stamina of each unit must be a valid stamina for any
 *         unit.
 *       | isValidStamina(getStamina())
  * @invar  The orientation of each unit must be between 0 and 2*PI. 
 *       | (getOrientation() >= 0) && (getOrientation < 2*PI)
 *       
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0.9
 *
 */
public class Unit {
	
	// WORLD DIMENSIONS
	static int WORLD_X = 50;
	static int WORLD_Y = 50;
	static int WORLD_Z = 50;
	private double CUBE_LENGTH = 1;
	
	// MIN,MAX VALUES 
	private static int INIT_MIN_VAL = 25;
	private static int INIT_MAX_VAL = 100;
	private static int MIN_VAL = 1;
	private static int MAX_VAL = 200;
	
	// Random
	Random random = new Random();
		
	/**
	 * Initialize this new unit with the given cubeLocation, name, weight, strength,toughness,agility,orientation.
	 * 
	 * @param cubeLocation
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
	 * @pre    The given hitpoints must be a valid hitpoints for any unit.
	 *          | isValidHitpoints(hitpoints)
	 * @post   The hitpoints of this new unit is equal to the given
	 *         hitpoints.
	 *          | new.getHitpoints() == hitpoints
	 * @pre    The given stamina must be a valid stamina for any unit.
	 *       	| isValidStamina(stamina)
	 * @post   The stamina of this new unit is equal to the given
	 *         stamina.
	 *       	| new.getStamina() == stamina													
	 * @effect the given orientation is set as the orientation of this new unit.
	 * 			| setOrientation(orientation)
	 * @throws IllegalPositionException
	 * 			The given position is not a valid position for a unit.
	 * 			| ! canHaveAsPosition(position)
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 * 
	 */
	@Model
	private Unit(int[] CubeLocation, String name, int weight, int strength, int agility, int toughness, 
		double orientation) throws IllegalPositionException, IllegalNameException {
		double[] location = {CubeLocation[0]+CUBE_LENGTH/2, CubeLocation[1]+CUBE_LENGTH/2, CubeLocation[2]+CUBE_LENGTH/2};
		setLocation(location);
		setName(name);
		setWeight(weight, true);
		setStrength(strength, true);
		setAgility(agility, true);
		setToughness(toughness, true);
		setHitpoints(getMaxHitpointsStamina());
		setStamina(getMaxHitpointsStamina());
		setOrientation(orientation);	
	}
	
	/**
	 * Initialize this new unit with the given cubeLocation, name, weight, strength,toughness,agility and the default orientation PI/2.
	 * 
	 * @param cubeLocation
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
	 * @param orientation
	 * 			The orientation for this new unit.
	 * @effect This new unit is initialized with the given cubeLocation as its cubeLocation, the given name as its name, the given 
	 * 			weight as its weight, the given strength as its strength, the given agility as its agility, the given toughness as 
	 * 			its toughness, and the default value PI/2 as its orientation.
	 * @throws IllegalPositionException
	 * 			The given position is not a valid position for a unit.
	 * 			| ! canHaveAsPosition(position)
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 */
	
	public Unit(int[] CubeLocation, String name, int weight, int strength, int agility, int toughness)
			throws IllegalPositionException, IllegalNameException {
		this(CubeLocation, name, weight, strength, agility, toughness,Math.PI/2);
	}
	
	// LOCATION
	
	/**
	 * 
	 * Return the location of this unit.
	 */
	@Basic @Raw
	public double[] getLocation() {
		double[] position = {this.xPos, this.yPos, this.zPos};
		return(position);
	}
	
	/**
	 * Check whether the given location is a valid location for
	 * any unit.
	 * 
	 * @param location
	 * 			The location to check.
	 * @return True if and only if the location is in the dimensions of the predefined world. So if the x,y and z value 
	 * 			are smaller than the WORLD_X, WORLD_Y and WORLD_Z values and the x,y and z value are greater than 0.
	 * 			| result == (location[0] <= WORLD_X) && (location[1] <= WORLD_Y) && (location[2] <= WORLD_Z) && 
				| (location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0))
	 */
	@Raw @Model
	private static boolean isValidLocation(double[] location) {
		return ((location[0] <= WORLD_X) && (location[1] <= WORLD_Y) && (location[2] <= WORLD_Z) && 
				(location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0));
	}
	
	/**
	 * Set the location of this unit to the given location.
	 * 
	 * @param  location
	 *         The new location for this unit.
	 * @post   The location of this new unit is equal to
	 *         the given location.
	 *       | new.getLocation() == location
	 * @throws IllegalPositionException
	 *         The given location is not a valid location for any
	 *         unit.
	 *       | ! isValidLocation(getLocation())
	 */
	@Raw @Model
	private void setLocation(double[] location) throws IllegalPositionException{
		if (!isValidLocation(location))
			throw new IllegalPositionException(location);
		this.xPos = location[0];
		this.yPos = location[1];
		this.zPos = location[2];
	}
	
	/**
	 * Variables registering the location of this unit.
	 */
	private double xPos;
	private double yPos;
	private double zPos;
	

	// CUBE
	
	/**
	 * 
	 * Returns the coordinates of the cube the unit is in.
	 */
	@Basic @Raw
	public int[] getOccupiedCube() {
		double[] location = this.getLocation();
		int[] position = {(int) location[0], (int) location[1], (int) location[2]};
		return(position);
	}
	
	
	// NAME 
	
	/**
	 * 
	 * Return the name of this unit.
	 */
	@Basic @Raw 
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the name of this unit to the given name.
	 * 
	 * @param  Name
	 *         The new name for this unit.
	 * @post   The name of this new unit is equal to
	 *         the given name.
	 *       | new.getName() == Name
	 * @throws IllegalNameException
	 *         The given name is not a valid name for any
	 *         unit.
	 *       | ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) throws IllegalNameException{
		if (!isValidName(name))
			throw new IllegalNameException(name);
		this.name = name;
	}
	
	/**
	 * Check whether the given name is a valid name for
	 * any unit.
	 *  
	 * @param  name
	 *         The name to check.
	 * @return True if and only if the given name's length is greater or equal to 2 and the given name's first character 
	 * 		   is uppercase and the given name has all valid characters.
	 * 		   | result ==
	 * 				((name.length() >= 2) 
	 *			&&	(Character.isUpperCase(name.charAt(0))) 
	 *			&&  (areValidCharacters(name)))			
	 * 			
	 */
	@Model
	private static boolean isValidName(String name){
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (areValidCharacters(name)));
	}
	
	/**
	 * Check whether the given name(characters) is a valid name for 
	 * any unit. 
	 * 
	 * @param name
	 * @return True if and only if the name has as characters letters,space, double quotes and a single quote,
	 * 		   precursory by a backslash.
	 * 		   | result == for (char c : name.toCharArray()){
							 (( Character.isLetter(c)) || ( (c == ' ') ) && ( (c == '"')) && ( (c == '\''))) }
	 * 			
	 */
	@Model
	private static boolean areValidCharacters(String name){
		for (char c : name.toCharArray()){
			if ( (! Character.isLetter(c)) && (! (c == ' ') ) && (! (c == '"')) && (! (c == '\'')) )
				return false;
		}
		
		return true;	
	}
	
	/**
	 * Variable registering the name of the unit.
	 */
	private String name;

	
	// WEIGHT
	
	/**
	 * 
	 * Return the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight(){
		return this.weight;
	}	
	
	
	/**
	 * Set the weight of this unit to the given weight.
	 * 
	 * @param	weight
	 *	     	The new weight for this unit.
	 * @effect	The weight of the unit is set to the given weight, with the restrictions for a not-newly created unit
	 * 			(with the flag on false).
	 * 			| setWeight(weight,false)
	 */
	@Raw
	public void setWeight(int weight) {
		setWeight(weight, false);
	}
	
	/**
	 * Set the weight of this unit to the given weight
	 * 
	 * @param   weight
	 *          The new weight for this unit.
	 * @param   flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *			If the given flag is true, the weight is set for the first time in the constructor,
	 * 		    the current minimum value of the weight is the initial minimum value 
	 * 		    of the weight of the constructor and the current maximum value of the weight 
	 *          is the initial maximum value of the weight of the constructor.
	 *          | if (flag)
	 *				then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    		If the given flag is false,the weight is already set once in the constructor,
	 * 		    the current minimum value of the weight is the minimum value 
	 * 		    of the weight after the constructor and the current maximum value of the weight 
	 *          is the maximum value of the weight after the constructor.
	 *          | if (! flag)
	 *				then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post    if the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is smaller than the current minimum value of the weight,
	 * 			the weight of this unit is equal to the current minimum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight < currMinVal)
	 * 			|		then (new.weight == currMinVal) 	
	 * @post	if the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the current minimum value and smaller than
	 * 			the current maximum value, the weight of this unit is equal to the given weight.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if ((weight >= currMinVal) && (weight <= currMaxVal))
	 * 			|		then (new.weight == weight)	
	 * @post    if the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the current maximum value of the weight,
	 * 			the weight of this unit is equal to the current maximum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight > currMaxVal)
	 * 			|		then (new.weight == currMaxVal) 
	 * @post    if the given weight is smaller than the units strength plus agility, divided by 2, 
	 * 			the weight of this unit is equal to the units strength plus agility, divided by 2.
	 * 			| if (weight < (getStrength()+getAgility())/2)
	 * 			|	then (new.weight == (getStrength()+getAgility())/2) 			
			
   
	 */	
	@Raw @Model
	private void setWeight(int weight, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = INIT_MIN_VAL;
			currMaxVal = INIT_MAX_VAL;
		}
		else {
			currMinVal = MIN_VAL;
			currMaxVal = MAX_VAL;
		}

		if (weight >= (getStrength()+getAgility())/2){
			if (weight < currMinVal) 
				this.weight = currMinVal;
			else if ((weight >= currMinVal) && (weight <= currMaxVal))
				this.weight = weight;
			else if (weight > currMaxVal)
				this.weight = currMaxVal; 
		}
		
		else{
			this.weight = (getStrength()+getAgility())/2;
		}

	}
	
	/**
	 * Variable registering the weight of this unit.
	 */
	private int weight;
	
	// STRENGTH
	
	/**
	 * 
	 * Return the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength(){
		return this.strength;
	}
	
	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param	strength
	 *	     	The new strength for this unit.
	 * @effect	The strength of the unit is set to the given strength, with the restrictions for a not-newly created unit
	 * 			(with the flag on false).
	 * 			| setStrength(weight,false)
	 */
	@Raw
	public void setStrength(int strength) {
		setStrength(strength, false);
	}
	
	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param   strength
	 *          The new strength for this unit.
	 * @param   flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *			If the given flag is true, the weight is set for the first time in the constructor,
	 * 		    the current minimum value of the strength is the initial minimum value 
	 * 		    of the strength of the constructor and the current maximum value of the strength 
	 *          is the initial maximum value of the strength of the constructor.
	 *          | if (flag)
	 *				then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    		If the given flag is false,the strength is already set once in the constructor,
	 * 		    the current minimum value of the strength is the minimum value 
	 * 		    of the strength after the constructor and the current maximum value of the strength 
	 *          is the maximum value of the strength after the constructor.
	 *          | if (! flag)
	 *				then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post    If the given strength is smaller than the current minimum value of the strength,
	 * 			the strength of this unit is equal to the current minimum value.
	 * 			| if (strength < currMinVal)
	 * 			|		then (new.strength == currMinVal) 	
	 * @post	If the given strength is greater than the current minimum value and smaller than
	 * 			the current maximum value, the strength of this unit is equal to the given strength.
	 * 			| if ((strength >= currMinVal) && (strength <= currMaxVal))
	 * 			|		then (new.strength == strength)	
	 * @post    | if the given strength is greater than the current maximum value of the strength,
	 * 			the strength of this unit is equal to the current maximum value.
	 * 			| if (strength > currMaxVal)
	 * 			|		then (new.strength == currMaxVal) 
	 * @effect	The weight is set to the the weight of the unit, depending on the restrictions of the weight,
	 * 			due to possible changes of the strength. 
	 */
	
	@Raw @Model
	private void setStrength(int strength, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = INIT_MIN_VAL;
			currMaxVal = INIT_MAX_VAL;
		}
		else {
			currMinVal = MIN_VAL;
			currMaxVal = MAX_VAL;
		}

		if ( strength < currMinVal) 
			this.strength = currMinVal;
		else if ((strength >= currMinVal) && (strength <= currMaxVal))
			this.strength = strength;
		else if (strength > currMaxVal)
			this.strength = currMaxVal;
		
		setWeight(getWeight());
	}
	
	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;
	
	
	// AGILITY
		
	/**
	 * 
	 * Return the agility of this unit.
	 */
	@Basic @Raw
	public int getAgility(){
		return this.agility;
	}
	
	/**
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param	agility
	 *	     	The new agility for this unit.
	 * @effect	The agility of the unit is set to the given agility, with the restrictions for a not-newly created unit
	 * 			(with the flag on false).
	 * 			| setAgility(agility,false)
	 */
	@Raw
	public void setAgility(int agility) {
		setAgility(agility, false);
	}
	
	/**
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param   agility
	 *          The new agility for this unit.
	 * @param   flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *			If the given flag is true, the agility is set for the first time in the constructor,
	 * 		    the current minimum value of the agility is the initial minimum value 
	 * 		    of the agility of the constructor and the current maximum value of the agility 
	 *          is the initial maximum value of the agility of the constructor.
	 *          | if (flag)
	 *				then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    		If the given flag is false,the agility is already set once in the constructor,
	 * 		    the current minimum value of the agility is the minimum value 
	 * 		    of the agility after the constructor and the current maximum value of the agility 
	 *          is the maximum value of the agility after the constructor.
	 *          | if (! flag)
	 *				then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post    If the given agility is smaller than the current minimum value of the agility,
	 * 			the agility of this unit is equal to the current minimum value.
	 * 			| if (agility < currMinVal)
	 * 			|		then (new.agility == currMinVal) 	
	 * @post	If the given agility is greater than the current minimum value and smaller than
	 * 			the current maximum value, the agility of this unit is equal to the given agility.
	 * 			| if ((agility >= currMinVal) && (agility <= currMaxVal))
	 * 			|		then (new.agility == agility)	
	 * @post    if the given agility is greater than the current maximum value of the agility,
	 * 			the agility of this unit is equal to the current maximum value.
	 * 			| if (agility > currMaxVal)
	 * 			|		then (new.agility == currMaxVal) 
	 * @effect	The weight is set to the the weight of the unit, depending on the restrictions of the weight,
	 * 			due to possible changes of the strength. 
	 */
	@Raw @Model
	private void setAgility(int agility, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = INIT_MIN_VAL;
			currMaxVal = INIT_MAX_VAL;
		}
		else {
			currMinVal = MIN_VAL;
			currMaxVal = MAX_VAL;
		}

		if ( agility < currMinVal) 
			this.agility = currMinVal;
		else if ((agility >= currMinVal) && (agility <= currMaxVal))
			this.agility = agility;
		else if (agility > currMaxVal)
			this.agility = currMaxVal; 
		
		setWeight(getWeight());
	}
	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility;
	
	
	// TOUGHNESS	

	/**
	 * 
	 * Return the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness(){
		return this.toughness;
	}
	
	/**
	 * Set the toughness of this unit to the given toughness.
	 * 
	 * @param	toughness
	 *	     	The new toughness for this unit.
	 * @effect	The toughness of the unit is set to the given toughness, with the restrictions for a not-newly created unit
	 * 			(with the flag on false).
	 * 			| setToughness(toughness,false)
	 */
	@Raw
	public void setToughness(int toughness) {
		setToughness(toughness, false);
	}
	
	/**
	 * Set the toughness of this unit to the given agility.
	 * 
	 * @param   toughness
	 *          The new toughness for this unit.
	 * @param   flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *			If the given flag is true, the toughness is set for the first time in the constructor,
	 * 		    the current minimum value of the toughness is the initial minimum value 
	 * 		    of the toughness of the constructor and the current maximum value of the toughness 
	 *          is the initial maximum value of the toughness of the constructor.
	 *          | if (flag)
	 *				then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    		If the given flag is false,the toughness is already set once in the constructor,
	 * 		    the current minimum value of the toughness is the minimum value 
	 * 		    of the toughness after the constructor and the current maximum value of the toughness 
	 *          is the maximum value of the toughness after the constructor.
	 *          | if (! flag)
	 *				then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post    If the given toughness is smaller than the current minimum value of the toughness,
	 * 			the toughness of this unit is equal to the current minimum value.
	 * 			| if (toughness < currMinVal)
	 * 			|		then (new.toughness == currMinVal) 	
	 * @post	If the given toughness is greater than the current minimum value and smaller than
	 * 			the current maximum value, the toughness of this unit is equal to the given toughness.
	 * 			| if ((toughness >= currMinVal) && (toughness <= currMaxVal))
	 * 			|		then (new.toughness == toughness)	
	 * @post    | if the given toughness is greater than the current maximum value of the toughness,
	 * 			the toughness of this unit is equal to the current maximum value.
	 * 			| if (toughness > currMaxVal)
	 * 			|		then (new.toughness == currMaxVal) 
	 */
	@Raw @Model
	private void setToughness(int toughness, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = INIT_MIN_VAL;
			currMaxVal = INIT_MAX_VAL;
		}
		else {
			currMinVal = MIN_VAL;
			currMaxVal = MAX_VAL;
		}

		if ( toughness < currMinVal) 
			this.toughness = currMinVal;
		else if ((toughness >= currMinVal) && (toughness <= currMaxVal))
			this.toughness =toughness;
		else if (toughness > currMaxVal)
			this.toughness = currMaxVal; 
	}
	
	
	/**
	 * 	Variable registering the toughness of this unit.
	 */
	private int toughness;

	
	// HITPOINTS

	/**
	 * Return the maximum hitpoints of this unit.
	 * 
	 * @return The weight times the toughness divided by 50, rounded up to the first
	 * 			larger integer.
	 * 		   | result == Math.ceil((getWeight()*getToughness())/50)
	 */
	@Basic @Raw
	public int getMaxHitpointsStamina() {
		 return (int) Math.ceil((((double) getWeight())*((double) getToughness()))/50);
	}
	
	
	/**
	 * 
	 * Return the hitpoints of this unit.
	 */
	@Basic @Raw
	public double getHitpoints(){
		return this.hitpoints;
	}
	
	
	/**
	 * Check whether the given hitpoints is a valid hitpoints for
	 * any unit.
	 *  
	 * @param  hitpoints
	 *         The hitpoints to check.
	 * @return True if and only if the given hitpoints are greater than zero and 
	 * 		   smalller than the maximum hitpoints.
 	 *       | result == ((hitpoints >= 0) && (hitpoints <= getMaxHitpointsStamina()))
	 */
	@Raw 
	private boolean isValidHitpoints(double hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= getMaxHitpointsStamina()));
	}
	
	/**
	 * Set the hitpoints of this unit to the given hitpoints.
	 * 
	 * @param  hitpoints
	 *         The new hitpoints for this unit.
	 * @pre    The given hitpoints must be a valid hitpoints for any
	 *         unit.
	 *       | isValidHitpoints(hitpoints)
	 * @post   The hitpoints of this unit is equal to the given
	 *         hitpoints.
	 *       | new.getHitpoints() == hitpoints
	 */
	@Raw
	private void setHitpoints(double hitpoints){
		assert isValidHitpoints(hitpoints);
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Variable registering the hitpoints of this unit.
	 */
	private double hitpoints;
	
	
	// STAMINA
		
	/**
	 * 
	 * Return the stamina of this unit.
	 */
	@Basic @Raw
	public double getStamina(){
		return this.stamina;
	}

	
	/**
	 * Check whether the given stamina is a valid stamina for
	 * any unit.
	 *  
	 * @param  stamina
	 *         The stamina to check.
	 * @return True if and only if the given stamina is greater than zero and 
	 * 		   smaller than the maximum stamina.
 	 *       | result == ((stamina >= 0) && (stamina <= getMaxHitpointsStamina())) 
	 */
	@Raw
	private boolean isValidStamina(double stamina){
		return ((stamina >= 0) && (stamina <= getMaxHitpointsStamina()));
	}
	
	/**
	 * Set the stamina of this unit to the given stamina.
	 * 
	 * @param  stamina
	 *         The new stamina for this unit.
	 * @pre    The given stamina must be a valid stamina for any
	 *         unit.
	 *       | isValidStamina(stamina)
	 * @post   The stamina of this unit is equal to the given
	 *         stamina.
	 *       | new.getStamina() == stamina
	 */
	@Raw @Model
	private void setStamina(double stamina){
		assert isValidStamina(stamina);
		this.stamina = stamina;
	}
	
	/**
	 * Variable registering the stamina of this unit.
	 */
	private double stamina;
	
	
	// ORIENTATION

	/**
	 * 
	 * Return the orientation of this unit.
	 */
	@Basic @Raw
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Set the orientation of this unit to the given orientation.
	 * 
	* @param  orientation
	*         The new orientation for this unit.
	* @post   The orientation of this new unit is set to the given orientation modulo 2*PI,made positive.
	*      	  | new.getOrientation() == orientation%(2*PI)
	 */
	@Raw @Model
	private void setOrientation(double orientation){
		double modOrientation = orientation%(Math.PI);
		if (modOrientation >= 0){
			this.orientation = modOrientation;
		}
		else{
			this.orientation = modOrientation + 2*Math.PI;
		}
	}
	
	
	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation;
	

	
	/**
	 * Advances the game with duration dt.
	 * @param dt
	 * 		  The duration which the game time is advanced.
	 * @effect The new time since the unit has stopped resting is equal to the previous time
	 * 			the unit has stopped resting, plus dt.
	 * @effect If the time since the unit has last stopped resting is larger than 3 minutes,
	 * 			the unit starts resting.
	 * @effect If the unit is attacking, the unit stops working and resting, and the new attackTime
	 * 			is equal to the previous attackTime minus dt.
	 * @effect If the unit is attacking and the remainder attackTime drops below 0, the attackTime is
	 * 			set to 0.
	 * @effect If the unit is resting, the new hitpoints of the unit are equal to the previous hitpoints
	 * 			plus his toughness divided by 200, for each 0,2 seconds, until the unit reaches its maximum
	 * 			amount of hitpoints.
	 * @effect If the unit is resting and the hitpoints of the unit are equal to its maximum hitpoints,
	 * 			the new stamina of the unit is equal to the previous stamina of the unit, plus his toughness
	 * 			divided by 100, for each 0,2 seconds, until the unit reaches its maximum amount of stamina.
	 * @effect If the unit is resting and the hitpoints of the unit are equal to its maximum hitpoints,
	 * 			and the stamina of the unit is equal its maximum stamina, the unit stops resting.
	 * @effect If the unit is working, the new time remainder to work is equal to the previous time remainder 
	 * 			to work	minus dt.
	 * @effect If the time remainder to work drops below 0, the unit stops working.
	 * @effect If the unit is moving and the unit is arrived, the unit stops moving.
	 * @effect If the unit is arrived at the middle of a cube, and the target is another cube, the unit 
	 * 			starts moving to the target
	 * @effect If the unit is moving and not arrived, the new location of the unit is equal to the previous 
	 * 			location of the unit plus its speed multiplied by dt in x, y and z direction.
	 * @effect If the unit is moving, not arrived and sprinting, the new stamina of the unit is equal to the 
	 * 			previous stamina of the unit minus 10 times dt.
	 * @effect If the unit is moving, not arrived, sprinting and the stamina drops below 0, the new stamina
	 * 			is equal to 0 and the unit stops sprinting.
	 * @effect If the unit is moving and not arrived, its orientation will be updated to the direction the
	 * 			unit is moving in.
	 * @effect If the unit is moving, not arrived and defaultBehaviour is enabled, the unit starts sprinting
	 * 			with a chance of dt/10.
	 * @throws IllegalAdvanceTimeException(dt)
	 * 			The given dt is not a valid advanceTime duration.
	 */
	public void advanceTime(double dt) throws IllegalAdvanceTimeException {
		
		if (! isValidAdvanceTime(dt)){
			throw new IllegalAdvanceTimeException(dt);
		}
		
		setTimeSinceRest(getTimeSinceRest() + (float)dt);
		if (getTimeSinceRest() > 180){
			startResting();
		}
		
		if (isAttacking()){
			
			stopWorking();
			stopResting();
						
			setAttackTime(getAttackTime() - (float)(dt));
			if (getAttackTime()<= 0){
				setAttackTime(0);
			}
		}
		
			
		else if (isResting()){

			setTimePeriodicRest(getTimePeriodicRest() + (float) dt);
			setTimeResting(getTimeResting() + (float) dt);
			
			if (getTimePeriodicRest() >= 0.2){
				
				int nbTimesPeriod = (int)Math.floor((double)(getTimePeriodicRest()/0.2));
				setTimePeriodicRest((float)(getTimePeriodicRest() % 0.2));
				double newHitpoints = getHitpoints() + ((double)getToughness()/200.0)*(double)(nbTimesPeriod);
				double newStamina = getStamina() + ((double) getToughness()/100.0)*(double)(nbTimesPeriod);
				
				
				if (newHitpoints <= getMaxHitpointsStamina()){
					setHitpoints(newHitpoints);
				}
				else if (newStamina <= getMaxHitpointsStamina()){
					setHitpoints(getMaxHitpointsStamina());
					setStamina(newStamina);				
				}
				else{
					setHitpoints(getMaxHitpointsStamina());
					setStamina(getMaxHitpointsStamina());
					stopResting();
				}
			}
		}
				
		else if (isWorking() && canHaveRecoverdOneHp()){
			setTimeRemainderToWork(getTimeRemainderToWork()-(float)dt);	
			if (getTimeRemainderToWork() <= 0){
				stopWorking();
			}
		}

		else if (isMoving()){
			if (Arrived(dt)){
				stopMoving();
				try {
					setLocation(target);
				} catch (IllegalPositionException e) {} //Exception will never be thrown.
				if (!(globalTarget == null) &&
						!((getOccupiedCube()[0] == globalTarget[0]) &&
						(getOccupiedCube()[1] == globalTarget[1]) &&
						(getOccupiedCube()[2] == globalTarget[2]) ) ) {
					try {
						moveTo(globalTarget);
					} catch (IllegalPositionException e) {} //Exception will never be thrown.
				}
			}
			
			else{
				double[] newLoc = {getLocation()[0]+ this.getCurrentSpeed()[0]*dt,
									getLocation()[1]+ this.getCurrentSpeed()[1]*dt,
									getLocation()[2]+ this.getCurrentSpeed()[2]*dt};
				try {
					setLocation(newLoc);
				} catch (IllegalPositionException e) {} //Exception will never be thrown.
				
				if (isSprinting()) {
					if (getStamina() - dt*10 > 0){
						setStamina(getStamina()- dt*10);
					}
					else {
						setStamina(0);
						stopSprinting();
					}
				}
				else if (isDefaultBehaviourEnabled()) {
					if (random.nextDouble() <= (float) dt/10) {
						startSprinting();
					}
				}
				setOrientation(Math.atan2(getCurrentSpeed()[1],getCurrentSpeed()[0]));
			}
						
		}		
		else if (isDefaultBehaviourEnabled()) {
			newDefaultBehaviour();
		}

	}
	/**
	 * Checks whether the duration can have the given dt as its duration.
	 * @param dt
	 * 		  The duration which the game time is advanced.
	 * @return	True if and only if dt is greater or equal to 0 and smaller or equal to 0.2 .
	 * 			result == ((dt >=0)&&(dt <= 0.2))
	 */
	@Model
	private static boolean isValidAdvanceTime(double dt){

		return ((dt >=0)&&(dt <= 0.2));
		
	}
	
	// SPEED
	
	
	/**
	 * Return the base speed of this unit.
	 * 
	 * @return 1.5 times the strength plus agility of the unit, divided by 200/100 ,
	 * 		   divided by the weight of the unit.
	 * 		   | result == 1.5*(getStrength()+getAgility())/(2*getWeight())
	 */
	@Basic @Model
	private double getBaseSpeed(){
		return 1.5*(getStrength()+getAgility())/(2*getWeight());
	}
	
	
	/**
	 * Get the walking speed of the unit.
	 * 
	 * @param 	targetZ
	 * 		 	The z coordinate of the target to which the unit is going.
	 * @return 	0.5 times the base speed of the unit, if the value of the z coordinate of the current 
	 * 			location of the unit is smaller than the z coordinate of the target location.
	 * 			| if (getLocation()[z_coord]-targetZ < 0)
	 * 				then result == 0.5*getBaseSpeed() 
	 * @return 	1.2 times the base speed of the unit, if the value of the z coordinate of the current
	 * 			location of the unit is greater than the z coordinate of the target location.
	 * 			| if (getLocation()[z_coord]-targetZ > 0)
	 * 				then result == 1.2*getBaseSpeed() 
	 * @return 	The base speed of the unit, if the value of the z coordinate of the current location 
	 * 			is equal to the z coordinate of the target location.
	 * 			| result == getBaseSpeed()			
	 */
	@Basic @Model
	private double getWalkingSpeed(double targetZ){
		if (getLocation()[2]-targetZ < 0){
			return 0.5*getBaseSpeed();
		}
		else if (getLocation()[2]- targetZ > 0){
			return 1.2*getBaseSpeed();
		}
		else{
			return getBaseSpeed();
		}
	}

	/**
	 * Return the current speed of the unit in x, y and z direction.
	 * 
	 * @return 	if the unit is sprinting, the current speed is equal to the walking speed times 2,
	 * 			multiplied with the difference between the location of the target and location of the unit,
	 * 			divided by the absolute distance between the units location and the target,
	 * 			respectively in x, y and z direction, 
	 * 			| result ==     (velocity*2*(target[x]-getLocation()[x])/distance, 
	 * 			|                velocity*2*(target[y]-getLocation()[y])/distance,
	 * 			|                velocity*2*(target[z]-getLocation()[z])/distance)
	 * @return	if the unit is not sprinting, the current speed is equal to the walking speed,
	 * 		   	multiplied with the difference between the location of the target and location of the unit,
	 * 		   	divided by the absolute distance between the units location and the target,
	 * 		   	respectively in x, y and z direction, 
	 * 			| result ==     (velocity*(target[x]-getLocation()[x])/distance, 
	 * 			|                velocity*(target[y]-getLocation()[y])/distance,
	 * 			|                velocity*(target[z]-getLocation()[z])/distance)
				 
				
	 */
	@Basic @Model
	private double[] getCurrentSpeed() {
		double distance = getDistanceToTarget();

		if (isSprinting()){
			velocity = getWalkingSpeed(target[2])*2;
		}
		else{
			velocity = getWalkingSpeed(target[2]);
		}
		
		double[] currentSpeed= {velocity*(target[0]-getLocation()[0])/distance, 
								velocity*(target[1]-getLocation()[1])/distance, 
								velocity*(target[2]-getLocation()[2])/distance};
		return currentSpeed;
	}
	
	/**
	 * variable referencing the velocity of this unit.
	 */
	private double velocity;
	
	/**
	 * Returns  the magnitude of the units speed.
	 * 
	 * @return	if the unit is not moving, the speed is equal to 0.
	 * 			| if (!isMoving())
	 * 			|	then result == 0
	 * @return	the 2-norm of the current speed in x,y and z direction.
	 * 			| result == (getCurrentSpeed()[x]^2 + getCurrentSpeed()[y]^2 + getCurrentSpeed()[z]^2)^(1/2)
	 */
	@Basic
	public double getCurrentSpeedMag() {
		if (!isMoving()) {
			return (double) 0;
		}
		return Math.sqrt(Math.pow((getCurrentSpeed()[0] ), 2) +
		Math.pow((getCurrentSpeed()[1] ), 2) + Math.pow((getCurrentSpeed()[2]), 2));
	}
	


	// SPRINTING 
	
	/**
	 * Set sprinting of this unit on true.
	 * 
	 * @post	The new state of sprinting of this unit is true.
	 * 
	 */
	public void startSprinting(){
		this.sprinting = true;
	}
	
	/**
	 * Stop the unit sprinting.
	 * 
	 * @post	The new state of sprinting of this unit is false.
	 * 			sprinting == false;		
	 */
	public void stopSprinting(){
		this.sprinting = false;
	}
	/**
	 * Checks whether the unit is sprinting.
	 * 
	 * @return sprinting.
	 * 		   | result == sprinting
	 */
	public boolean isSprinting(){
		return sprinting;
	}
	
	/**
	 * Variable registering sprinting of this unit.
	 */
	private boolean sprinting;

	
	// MOVING
	
	/**
	 * Checks whether the unit is moving.
	 * 
	 * @return	True if and only if the unit is moving and the unit is not working and 
	 * 			the unit is not resting and the unit is not attacking.
	 * 			| result == (isMoving() && !isWorking() && !isResting() && !isAttacking())
	 */
	@Basic
	public boolean isActualMoving(){
		return (isMoving() && !isWorking() && !isResting() && !isAttacking());
	}
	
	/**
	 * Start moving the unit.
	 * 
	 * @effect 	if the unit is resting and has rested long enough to recover one hitpoint,
	 * 			the units stops resting.
	 * 			| if ( isResting() && (canHaveRecoverdOneHp()))
	 * 			|	then stopResting()
	 * @effect	The unit stops working.
	 * 			| stopResting()
	 * @effect	if the unit is not attacking, the unit is moving.
	 * 			| if (!isAttacking())
	 * 			|	then new.isMoving == true.
	 */
	@Model 
	private void startMoving(){
		if ( isResting() && (canHaveRecoverdOneHp())){
			stopResting();
		}
		stopWorking();
		if (!isAttacking()){
			this.isMoving = true;
		}
	}
	
	/**
	 * stop moving the unit.
	 * 
	 * @post 	the new state of moving of the unit is false.
	 * 			| new.isMoving == false				
	 */
	@Model 
	private void stopMoving(){
		this.isMoving = false;
	}
	
	/**
	 * Variable registering whether the unit is moving.
	 */
	private boolean isMoving ;
	
	/**
	 * Checks whether the unit is moving.
	 * 
	 * @return	True if and only if the unit is moving.
	 * 			| result == isMoving			
	 */
	@Basic @Model
	private boolean isMoving(){
		return isMoving;
	}
	

	/**
	 * Returns the distance from the current location to the target.
	 * @return	if there is no target, the distance is equal to 0.
	 * 			if (target == 0)
	 * 				then result == 0
	 * @return	the 2-norm of the distance between the target and the current location in x y and z direction.
	 * 			| result == ((target[x]-getLocation()[x])^2+ (target[y] - getLocation()[y])^2 + 
	 * 			| (target[z]-getLocation()[z])^2)^(1/2)			 
	 */
	@Model
	private double getDistanceToTarget() {
		if (target == null) {
			return (double) 0;
		}
		double distance = Math.sqrt(Math.pow((target[0]-getLocation()[0]),2)+Math.pow((target[1]-getLocation()[1]),2)
		+ Math.pow((target[2]-getLocation()[2]),2));
		return distance;
	}
	

	
	/**
	 * Checks whether the unit is arrived at the target.
	 * 
	 * @param	dt
	 * 		  	The duration dt of game time.
	 * @return	true if and only if the distance to the target is smaller than the duration dt times the magnitude of
	 * 			the current speed.
	 * 			| result == (getDistanceToTarget() < dt*getCurrentSpeedMag())
	 */
	@Model
	private boolean Arrived(double dt){
		return (getDistanceToTarget() < dt*getCurrentSpeedMag());	
	}
	
	// TODO bij de throws moet niets staan neem ik aan?
	/**
	 * Move to the adjacent cube.
	 * 
	 * @param	dx
	 * 			The movement of the unit in the x direction.
	 * @param	dy
	 * 			the movement of the unit in the y direction. 
	 * @param	dz
	 * 			The movement of the unit in the z direction.
	 * @effect	The unit moves to an adjacent cube with movements dx,dy and dz and the boolean
	 * 			calledByTo on false, which means this function is not called by the function moveTo,
	 * 			but only a single moveToAdjacent request.
	 * @throws 	IllegalPositionException
	 * @throws 	IllegalAdjacentPositionException
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalPositionException , IllegalAdjacentPositionException {
		moveToAdjacent(dx, dy, dz, false);
	}
	
	/**
	 * Move to the adjacent cube.
	 * 
	 * @param	dx
	 * 			The movement of the unit in the x direction.
	 * @param	dy
	 * 			the movement of the unit in the y direction. 
	 * @param	dz
	 * 			The movement of the unit in the z direction.
	 * @param	calledBy_moveTo
	 * 			Boolean for defining the function called by the function MoveTo.
	 * 			The currentTarget is the position of the middle of the cube, where the unit would
	 * 			move to after the dx,dy and dz movement.
	 * @effect	if the movement in x,y and z direction is not zero, the unit starts moving.
	 * 			| startMoving()				 	
	 * @post 	if the boolean calledBy_moveTo is false, the new current target cube is equal to 
	 * 			the cube position of the current cube plus the dx,dy and dz movement,respectively 
	 * 			in x,y and z direction.
	 * 			| currentTargetCube[x] == currentCube[x] + dx
	 * 			|&& currentTargetCube[y] == currentCube[y] + dy
	 * 			|&& currentTargetCube[z] == currentCube[z] + dz
	 * @throws	IllegalPositionException(currentTarget)
	 * 			The currentTarget (location after the moveToAdjacent) is not a valid location.
	 * 			| ! isValidLocation(currentTarget)
	 * @throws	IllegalAdjacentPositionException(dx,dy,dz)
	 * 			The given dx,dy and dz movement is not a valid moveToAdjacent movement.
	 * 			| ! isValidAdjacentMovement(dx,dy,dz)
	 * 			
	 */
	@Model
	private void moveToAdjacent(int dx,int dy,int dz, boolean calledBy_moveTo) throws IllegalPositionException,
																					IllegalAdjacentPositionException{
		
		if (! isValidAdjacentMovement(dx,dy,dz)){
			throw new IllegalAdjacentPositionException(dx,dy,dz);
		}
		
		int[] currentCube = getOccupiedCube();
		double[] currentTarget = {	(double)(currentCube[0]+ dx + CUBE_LENGTH/2), 
									(double)(currentCube[1]+ dy + CUBE_LENGTH/2),
									(double)(currentCube[2]+ dz + CUBE_LENGTH/2)};

		
		if (! isValidLocation(currentTarget)){			
			throw new IllegalPositionException(currentTarget);
		}
		if (! (dx == 0 && dy == 0 && dz ==0)) {
			startMoving();
		}
		
		target = currentTarget;
		
		if (! calledBy_moveTo) {
			int[] currentTargetCube = {currentCube[0] + dx, currentCube[1]+ dy, currentCube[2] + dz};
			globalTarget = currentTargetCube;
		}
	}
	
	/**
	 * Checks whether the dx, dy and dz movement is a valid adjacent movement.
	 * 
	 * @param	dx
	 * 			The movement of the unit in the x direction.
	 * @param	dy
	 * 			the movement of the unit in the y direction. 
	 * @param	dz
	 * 			The movement of the unit in the z direction.
	 * @return	True if and only if the movement is 0 or 1 or -1, respectively 
	 * 			for dx, dy and dz.
	 * 			| result == (((dx == 0) || (dx == 1) || (dx == -1))
	 * 			|        && ((dy == 0) || (dy == 1) || (dy == -1) )
	 * 			|        &&  ((dz == 0) || (dz == 1) || dz == -1))
	 */
	private boolean isValidAdjacentMovement(int dx, int dy, int dz){
		
		return (((dx == 0) || (dx == 1) || (dx == -1)) && 
				((dy == 0) || (dy == 1) || (dy == -1) )&& 
				((dz == 0) || (dz == 1) || dz == -1));
	}
	
	
	// TODO hier ook niets bij de illegalpositionexception schrijven dus?
	/**
	 * Move to the given target.
	 * 	
	 * @param	endTarget
	 * 			The target of the unit to move to.
	 *			if the current		
	 * @throws	IllegalPositionException
	 */
	public void moveTo(int[] endTarget) throws IllegalPositionException { 
										// When IllegalPositionException is thrown in moveToAdjacent(),
										// by the coding rules, the variable "globalTarget" should be reverted to the
										// state as at the beginning of the method. Since the method will always initiate a new
										// globalTarget, the lack of reverting wont cause any problems.
		globalTarget = endTarget;
		
		
		int xCur = getOccupiedCube()[0];
		int yCur = getOccupiedCube()[1];
		int zCur = getOccupiedCube()[2];
		
		int xTar = endTarget[0];
		int yTar = endTarget[1];
		int zTar = endTarget[2];
		
		if (xCur != xTar || yCur != yTar || zCur != zTar){
			int xRes;
			int yRes;
			int zRes;
			
			// x
			if (xCur == xTar){
				xRes = 0;
			}
			else if (xCur < xTar){
				xRes = 1;
			}
			else{
				xRes = -1;
			}
			
			// y 
			if (yCur == yTar){
				yRes = 0;
			}
			else if (yCur < yTar){
				yRes = 1;
			}
			else{
				yRes = -1;
			}
			
			// z
			if (zCur == zTar){
				zRes = 0;
			}
			else if (zCur < zTar){
				zRes = 1;
			}
			else{
				zRes = -1;
			}
			try {
				moveToAdjacent(xRes, yRes,zRes, true);
			} catch (IllegalAdjacentPositionException e) {} //Exception will never be thrown.
		}
	}
	
	/**
	 * Variable registering the target to move to.
	 */
	private double[] target = null;
	
	/**
	 * Variable registering the global target to move to.
	 */
	private int[] globalTarget;
	

	
	// WORKING
	
	
	/**
	 * The unit starts working.
	 * 
	 * @effect	the unit starts working.
	 * 			| startWorking()
	 */			
	public void work(){
		startWorking();
		
	}
	
	/**
	 * The unit starts working.
	 * 
	 * @effect	if the unit is resting and the unit has rested for a time to recover one hitpoint,
	 * 			the unit stops resting ,starts working and its time remainder to work is equal to 
	 * 			500 divided by the strength of the unit.
	 * 			| if ( isResting() && (canHaveRecoverdOneHp()))
	 * 			|	then (stopResting() 
	 * 			|		&& (working == true)
	 * 			|		&& (setTimeRemainderToWork(500/getStrength())))
	 * @effect	if the unit is not resting, the units starts working and its time remainder to work
	 * 			is equal to 500 divided by the strength of the unit.
	 * 			| if (!working)
	 * 			|	then ((working == true)
	 * 			|		&& (setTimeRemainderToWork(500/getStrength())))
	 */
	@Model
	private void startWorking(){
		if ( isResting() && (canHaveRecoverdOneHp())){
			stopResting();
			this.working = true;
			setTimeRemainderToWork((float) 500/getStrength());
		}
		else if (!isResting()){
			this.working = true;
			setTimeRemainderToWork((float) 500/getStrength());			
		}

	}
	
	/**
	 * stop the unit from working.
	 * 
	 * @post	the unit is not working
	 * 			| working == false
	 */
	@Model
	private void stopWorking(){
		this.working = false;

	/**
	 * Checks whether the unit is working.
	 * 
	 * @return	true if and only if  the unit is working.
	 * 			| result == working
	 */
	}
	@Basic
	public boolean isWorking(){
		return working;
	}
	
	/**
	 * Variable registering whether the unit is working.
	 */
	private boolean working;
	
	
	/**
	 * 
	 * Return the time remaining to work of this unit.
	 */
	@Model 
	private float getTimeRemainderToWork(){
		return timeRemainderToWork;
	}
	
	/**
	 * Set the time remaining to work of this unit to the given time.
	 * 
	 * @param	time
	 * 			The new time remaining to work for this unit.
	 * @post	The time remaining to work of this new time is equal to
	 *        	the given time.
	 *       	| new.getTimeRemainderToWork() == time
	 * 			
	 */
	@Model
	private void setTimeRemainderToWork(float time){
		this.timeRemainderToWork = time;
	}
	
	/**
	 * Variable registering the time remaining to work.
	 */
	private float timeRemainderToWork;
	

	
	
	// FIGHTING
	
	/**
	 * Attack an other unit.
	 * 
	 * @param	other
	 * 		 	Unit to attack.
	 * @post	if the unit is not equal to the unit to attack and if the unit
	 * 			is resting, the unit stops resting.
	 * 			| if (this != other)
	 * 			|	if (this.isResting())
	 * 			|		then this.stopResting()
	 * @post 	if the unit is not equal to the unit to attack, 
	 * 			the unit stops working.
	 * 			| if (this != other)
	 * 			|	then this.stopWorking()
	 * @post	if the unit is not equal to the unit to attack,
	 * 			the new orientation in fight is set to the to the unit to attack
	 * 			| if (this != other)
	 * 			|	then this.setOrientationInFight(other)
	 * @post	if the unit is not equal to the unit to attack,
	 * 			the new attack time of this unit is equal to 1.
	 * 			| if (this != other)
	 * 			|	then this.setAttackTime(1)
	 * @throws	IllegalAttackPosititonException(other.getOccupiedCube())
	 * 			The unit cannot attack the given other unit.
	 * 			| ! this.isValidAttackPosition(other.getOccupiedCube())
	 * 			
	 */
	public void attack(Unit other) throws IllegalAttackPosititonException {
		if (this != other) {
			if (!this.isValidAttackPosition(other.getOccupiedCube())) {
				throw new IllegalAttackPosititonException(other.getOccupiedCube());
			}
			
			if (this.isResting()) {
				this.stopResting();	
			}
			
			stopWorking();
			
			this.setOrientationInFight(other);
				
			this.setAttackTime(1);
		}
	}
	
	/**
	 * Checks whether the given position of the unit to attack is a valid position to attack.
	 * 
	 * @param	attackCubePosition
	 * 			The position of the cube of the unit to attack.
	 * @return	True if and only if the distance between the cube of the unit 
	 * 			and the cube of the unit to attack is smaller than 1, as well as 
	 * 			in the x, y and z direction.
	 * 			| result ==((this.getOccupiedCube()[x] - attackCubePostion[x] <= 1)
	 * 					  &&(this.getOccupiedCube()[y] - attackCubePostion[y] <= 1)
	 * 				      &&(this.getOccupiedCube()[z] - attackCubePostion[z] <= 1))
	 */
	@Model
	private boolean isValidAttackPosition(int[] attackCubePosition){
		return((Math.abs(this.getOccupiedCube()[0]-attackCubePosition[0]) <=1) && 
				(Math.abs(this.getOccupiedCube()[1]-attackCubePosition[1]) <=1) &&
				(Math.abs(this.getOccupiedCube()[2]-attackCubePosition[2]) <=1));	
	}
	
	
	/**
	 * Set the orientation of the units in the fight to each other.
	 * 
	 * @param	other
	 * 			The other unit in the fight.
	 * @effect 	The orientation of this unit is set in the direction of the other unit, 
	 * 			using the arc tangens of the distant difference in y direction of the other unit and
	 * 			this unit, and the distant difference in z direciton of the other unit and 
	 * 			this unit.
	 * 			|  setOrientation(arctangens(other.getLocation()[y]-this.getLocation()[y],
	 * 			|	other.getLocation()[x]-this.getLocation()[x])
	 * @effect 	The orientation of the other unit is set in the direction of the this unit, 
	 * 			using the arc tangens of the distant difference in y direction of this unit and
	 * 			the other unit, and the distant difference in z direciton of this unit and 
	 * 			the other unit.
	 * 			|  setOrientation(arctangens(this.getLocation()[y]-other.getLocation()[y],
	 * 			|	this.getLocation()[x]-other.getLocation()[x])
	 * 
	 */
	@Model 
	private void setOrientationInFight(Unit other) {
		double orientUnitThis = Math.atan2(other.getLocation()[1]-this.getLocation()[1],
				other.getLocation()[0]-this.getLocation()[0]);
		double orientUnitOther = Math.atan2(this.getLocation()[1]-other.getLocation()[1],
				this.getLocation()[0]-other.getLocation()[0]);
		
		this.setOrientation(orientUnitThis);
		other.setOrientation(orientUnitOther);
	}
	
	/**
	 * Defend against an other unit.
	 * 
	 * @param	other
	 * 			The unit to defend against.
	 * @effect	if the unit is not equal to the other unit and the unit is resting, than the unit stops resting.
	 * 			| if (this!=other)
	 * 			|	if (this.isResting())
	 * 			|		then this.stopResting()
	 * @effect	if the unit is not equal to the other unit, the unit stops working.
	 * 			| if (this!=other)
	 * 			|	then this.stopWorking()
	 * @effect	if the unit is not equal to the other unit, the possibility to dodge is 0.2 times this units agility,
	 * 			divided by the other units agility.
	 * 			| if (this != other)
	 * 			|	than possibilityDodge = (0.2* this.getAgility()/other.getAgility())
	 * @effect	if this unit is not equal to the other unit, and the unit dodges successfully, the unit is set
	 * 			to a random location and its orientation is in the direction of the other attacking unit.
	 * 			| if (this != other)
	 * 			|	if (getDefendSucces(possibilityDodge))
	 * 			|		then (this.setRandomLocation && this.setOrientationInFight(other))
	 * @effect	if this unit is not equal to the other unit, and the unit is not resting,
	 * 			the possibility to block is equal to 0.25 times the strength plus agility of this unit, 
	 * 			divided by the strength plus agility of the other unit.
	 * 			| possibilityBlock = 0.25*(( this.getStrength() + this.getAgility())/
	 * 				( (other.getStrength()+other.getAgility() ))))
	 * @post	if this unit is not equal to the other unit, and if the unit does not 
	 * 			dodge successfully, and if the unit does not block successfully,
	 * 			the new hitpoints are equal to the old hitpoints reduced with the strength
	 * 			of the other unit divided by 10.	
	 * 			| if (this != other)
	 * 			|	if ( ! getDefendSucces(possibilityDodge))
	 * 			|		if (! getDefendSucces(possibilityBlock))
	 * 			|			then (new.hitpoints = old.hitpoints - getStrength()/10)
	 * @effect 	if this unit is not equal to the other unit, and if the unit fails to dodge, and the unit fails
	 * 			to block, and if the hitpoints are less than 0, than the hitpoints are 0.
	 * 			| if (this != other)
	 * 			|	if ( ! getDefendSucces(possibilityDodge))
	 * 			|		if (! getDefendSucces(possibilityBlock))
	 * 			|			if (this.getHitpoints) <=0)
	 * 			|				than this.setHitpoints(0)			
	 */
	public void defend(Unit other){
		if (this != other) {
			if (this.isResting()) {
			this.stopResting();
			}
			
			stopWorking();
			
			double possibilityDodge = (double)(0.2* (double) this.getAgility()/ (double) other.getAgility());
			if (getDefendSucces(possibilityDodge)){
				this.setRandomLocation();
				this.setOrientationInFight(other);

			}
			
			else{
				double possibilityBlock = (double)(0.25*(((double) this.getStrength() + (double) this.getAgility())/
						((double) ((double)other.getStrength()+(double) other.getAgility()))));
				
				if ( ! getDefendSucces(possibilityBlock)){

					double newHitPoints = this.getHitpoints() - (double)((double)other.getStrength()/10);
					if (newHitPoints <= 0) {
						this.setHitpoints(0);
					}
					else {
						this.setHitpoints(newHitPoints);
					}
				}

				
			}
		}
	}
	
	/**
	 * Set the unit to a random position when dodging.
	 * 
	 * @effect	The location is a random location.
	 * 			| setLocation(randomPosition(getLocation))
	 */
	@Model 
	private void setRandomLocation(){
		try {
			setLocation(randomPosition(getLocation()));
		} catch (IllegalPositionException e) {
			setRandomLocation();
		}
	}
	
	/**
	 * Return a random position in the near area of the current location.
	 * 
	 * @param 	currLoc
	 * 			The current location of the unit.
	 * @return 	The current location plus a random number between -1 and 1, excluding 0,
	 * 			in the x and y direction.
	 * 			| result == (currLoc[x]+randomNumber(-1,1) 
	 * 			| 		&&  (currLoc[y] + randomNumber(-1,1))
	 * 			|		&&  (currLoc[z])
	 * 			
	 */
	@Model
	private double[] randomPosition(double[] currLoc){
		double[] newLoc = {currLoc[0]+ (random.nextDouble()*2-1), currLoc[1]+ (random.nextDouble()*2-1), currLoc[2]};
		if (currLoc == newLoc) {
			return randomPosition(currLoc);
		}
		return newLoc;
	}
	
	/**
	 * Checks whether the unit is attacking.
	 * 
	 * @return	True if and only if the attack time is greater than 0.
	 * 			| result == (getAttacktime() > 0)
	 */
	@Basic
	public boolean isAttacking(){
		return (getAttackTime() > 0);
	}
	
	/**
	 * Set the attack time to the given time.
	 * 
	 * @param	time
	 * 			The time to set to the unit.
	 * @post 	The new attack time of this unit is equal to the given time.				
	 */
	@Model
	private void setAttackTime(float time){
		this.attackTime = time;
	}
	
	/**
	 * Return the attack time of this unit.
	 */
	@Basic @Model
	private float getAttackTime(){
		return this.attackTime;
	}
	
	/**
	 * Variable registering the attack time.
	 */
	private float attackTime;
	
	/**
	 * Checks whether the unit's defend is succesfull.
	 * @param	possibility
	 * 			The possibility to defend succesfull.
	 * @return	True if and only if the the unit has defended succesfull.
	 * 			| result == (random.nextDouble <= possibility)
	 */
	@Basic @Model
	private boolean getDefendSucces(double possibility){
		return (random.nextDouble() <= possibility);
	}
	
	// RESTING
	
	/**
	 * The unit rests.
	 * 
	 * @effect	the unit starts resting.
	 * 			| startResting();
	 */
	public void rest(){
		startResting();
	}
	
	/**
	 * Checks whether the unit is resting.
	 * 
	 * @return	True if and only if the unit is resting.
	 * 			| result == resting
	 */
	@Basic 
	public boolean isResting(){
		return resting;
	}
	
	/**
	 * The unit starts resting.
	 * 
	 * @effect	if the hitpoints of the unit are not equal to the maximum hitpoints or the stamina is not equal
	 * 			to the maximum stamina, the new time to rest for the unit is set to 0, the unit is resting,
	 * 			the unit stops working, the new initial hitpoints when starting to rest are set to the current hitpoints
	 * 			and the new initial stamina when starting to rest is set to the given stamina.
	 * 			| if ( (getHitpoints() != getMaxHitpointsStamina()) || 
	 * 			|	( (getStamina() != getMaxHitpointsStamina()) ))
	 * 			|	then (setTimeResting(0)
	 * 					&& resting = true
	 * 					&& stopWorking()
	 * 					&& setStartRestHitpoints(getHitpoints())
	 * 					&& setStartRestStamina(getStamina()))
	 * 
	 */
	@Model
	private void startResting(){
		if ( (getHitpoints() != getMaxHitpointsStamina()) || ( (getStamina() != getMaxHitpointsStamina()) )){
			setTimeResting(0);
			resting = true;
			stopWorking();
			setStartRestHitpoints(getHitpoints());
			setStartRestStamina(getStamina());
		}
	}
	
	/**
	 * The unit stops resting.
	 * 
	 * @effect  if the unit has not rest long enough to recover one hitpoint,
	 * 			the new hitpoints are set to the initial hitpoints when the unit 
	 * 			began to rest, and the new stamina is set to the initial stamina 
	 * 			when the unit began to rest.
	 * 			| if (! canHaveRecoverdOneHp())
	 * 			|	then (setHitpoints(getStartRestHitpoints())
	 * @post 	the unit is not resting
	 * 			| resting == false
	 * @effect	The new time since resting is equal to 0.
	 * 			| setTimeSinceRest(0)
	 * @effect	The new time that the unit has rested is equal to a large value.
	 * 			| setTimeResting(MAX_VALUE)
	 */
	@Model
	private void stopResting(){
		if (! canHaveRecoverdOneHp()){
			setHitpoints(getStartRestHitpoints());
			setStamina(getStartRestStamina());
		}
		resting = false;
		setTimeSinceRest(0);
		setTimeResting(Float.MAX_VALUE);
	}
	
	/**
	 * Variable registering if the unit is resting.
	 */
	private boolean resting;
	
	/**
	 * Set the time since rest to the given time.
	 * 
	 * @post The new time since resting is equal to the given time.
	 */
	@Model
	private void setTimeSinceRest(float time){
		this.timeSinceRest = time;
	}
	
	/**
	 * Return the time since the ending of the last rest.
	 */
	@Model
	private float getTimeSinceRest(){
		return this.timeSinceRest;
	}
	
	/**
	 * Variable registering the time since the unit last stopped resting.
	 */
	private float timeSinceRest;
	
	/**
	 * Set the time resting to the given time.
	 * 
	 * @param	time
	 * 			The time that the unit is resting.
	 * @post 	The new time that the unit is resting is equal to the given time.
	 */
	@Model
	private void setTimeResting(float time) {
		this.timeResting = time;
	}
	
	/**
	 * Return the time this unit is resting.
	 */
	@Basic @Model
	private float getTimeResting() {
		return this.timeResting;
	}
	
	/**
	 * Variable registering the time the unit is resting.
	 */
	private float timeResting = Float.MAX_VALUE;
	
	/**
	 * Set the time since the last reset period. 
	 * 
	 * @param	time
	 * 			The periodic rest time.
	 * @post	The new periodic reset time is equal to the given time.
	 */
	@Model
	private void setTimePeriodicRest(float time){
		this.periodicRest = time;
	}
	
	/**
	 * Return the periodic rest of this unit.
	 */
	@Basic @Model
	private float getTimePeriodicRest(){
		return this.periodicRest;
	}
	
	/**
	 * Variable registering the periodic rest.
	 */
	private float periodicRest;
	
	/**
	 * Checks whether the unit has rested enough to recover one hitpoint.
	 * 
	 * @return	True if and only if the time the unit is resting is greater than or
	 * 			equal the time for the unit to recover one hitpoint.
	 * 			| result == (getTimeResting >= ( 1/((getToughness()/200.0)/0.2)))
	 */
	@Model
	private boolean canHaveRecoverdOneHp(){
		return (getTimeResting() >= ((double) 1/(((double) getToughness()/200.0)/0.2)));

	}
	
	
	/**
	 * Return the initial hitpoints on the moment the unit started resting.
	 */
	@Basic @Model
	private double getStartRestHitpoints(){
		return this.startRestHitpoints;
	}
	
	/**
	 * Set the initial hitpoints when the unit started resting to the given hitpoints.
	 * 
	 * @param	hitpoints
	 * 			The hitpoints to set.
	 * @post	The new initial hitpoints when the unit started resting are equal to the given hitpoints.
	 * 			
	 */
	@Model
	private void setStartRestHitpoints(double hitpoints){
		this.startRestHitpoints = hitpoints;
	}
	
	/**
	 * Variable registering the hitpoints on the moment the unit started resting. 
	 */
	private double startRestHitpoints;
	
	/**
	 * Return the initial stamina on the moment the unit started resting.
	 */
	@Basic @Model
	private double getStartRestStamina(){
		return this.startRestStamina;
	}
	
	/**
	 * Set the initial stamina when the unit started resting to the given stamina.
	 * 
	 * @param	stamina
	 * 			The stamina to set.
	 * @post	
	 */
	@Model
	private void setStartRestStamina(double stamina){
		this.startRestStamina = stamina;
	}
	
	/**
	 * Variable registering the stamina on the moment the unit started resting.
	 */
	private double startRestStamina;
	

	// DEFAULT BEHAVIOUR
	
	/**
	 * Check whether the default behaviour is enabled.
	 * 
	 * @return	true if and only if the default behaviour is enabled.
	 * 			| result == defaultBehaviour
	 */
	@Basic
	public boolean isDefaultBehaviourEnabled(){
		return defaultBehaviour;
	}
	
	/**
	 * Start the default behaviour.
	 * 
	 * @post	the new state of default behaviour is set to enabled.
	 * 			| new.defaultBehaviour == true
	 */
	public void startDefaultBehaviour(){
		defaultBehaviour = true;
	}
	
	/**
	 * Stop the default behaviour.
	 * 
	 * @post	the new state of default behaviour is set to disabled.
	 * 			| new.defaultBehaviour == false
	 * @effect	The unit stops working.
	 * 			| stopWorking()
	 * @effect	The unit stops moving.
	 * 			| stopMoving()
	 * @effect	The unit stops resting.
	 * 			| stopResting()
	 */
	public void stopDefaultBehaviour(){
		defaultBehaviour = false;
		stopWorking();
		stopMoving();
		stopResting();
	}
	
	/**
	 * Variable registering if the defaultbehaviour is enabled.
	 */
	private boolean defaultBehaviour;
	
	/**
	 * Set a new default behaviour for the unit to do.
	 * 
	 * possibleTask[0] = moveTo(randomPosition),possibleTask[1] = work, possibleTask[2] = rest
	 * @effect	if the possible task is move to random position, unit moves to random position.
	 * 			| if (possibleTask[0])
	 * 			|	than moveTo(getRandomPosition)
	 * @effect	if the possible task is work, the unit works.
	 * 			| if (possibleTask[1])
	 * 			|	than work()
	 * @effect	if the possible task is rest, the unit rests.
	 * 			| if (possibleTask[2])
	 * 			|	than rest()
	 */
	@Model
	private void newDefaultBehaviour(){
		int possibleTask = random.nextInt(3);
		if (possibleTask == 0){
			try {
				moveTo(getRandomPosition());
			} catch (IllegalPositionException e) {} //Exception will never be thrown.
		}
		else if (possibleTask == 1){
			work();
		}
		
		else{
			rest();
		}
	}
	
	/**
	 * Return a random position in the boundaries of the playing field.
	 * 
	 * @return	Return random position smaller than WORLD_X-1 in the x direction, WORLD_Y -1 in the y direction,
	 * 			WORLD_Z - 1 in the z direction.
	 * 			| randLoc_x == randomInt(0,WORLD_X-1)
	 * 			| randLoc_y == randomInt(0,WORLD_Y-1)
	 * 			| randLoc_z == randomInt(0,WORLD_Z-1)
	 */
	@Model
	private int[] getRandomPosition(){
		int[] randLoc = {random.nextInt(WORLD_X-1), random.nextInt(WORLD_Y-1), random.nextInt(WORLD_Z-1)};
		return randLoc;
	}
}
