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
  * @invar  The orientation of each unit must be between -PI 
 *       | 
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
	private int currMinVal;
	private int currMaxVal;
		
	private boolean sprinting;
	private double velocity;

	// Target
	private double[] target = null;
	
	// Working
	private boolean working;
	private float timeRemainderToWork;
	
	// Attack
	private float attackTime;
	
	// Rest
	private boolean resting;
	private float timeSinceRest;
	private float timeResting = Float.MAX_VALUE;
	private double startRestHitpoints;
	private double startRestStamina;
	private float periodicRest;
	
	// Default Behaviour
	private boolean defaultBehaviour;
	
	// Moving
	private boolean isMoving ;
	private int[] globalTarget;
	
	
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
	 * 			| ! canHaveAsPosition(position)w
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
	 * @post    | if the given agility is greater than the current maximum value of the agility,
	 * 			the agility of this unit is equal to the current maximum value.
	 * 			| if (agility > currMaxVal)
	 * 			|		then (new.agility == currMaxVal) 
	 * @effect	The weight is set to the the weight of the unit, depending on the restrictions of the weight,
	 * 			due to possible changes of the strength. 
	 */
	@Raw @Model
	private void setAgility(int agility, boolean flag){
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
	* @post   The orientation of this new unit is set to the given orientation modulo 2*PI.
	*      	  | new.getOrientation() == orientation%(2*PI)
	 */
	@Raw @Model
	private void setOrientation(double orientation){
		double modOrientation = orientation%(2*Math.PI);
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
	 * 
	 * @param duration
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
			if (getTimeRemainderToWork() < 0){
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
					if (random.nextDouble() <= 0.01) {
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
	
	@Model
	private static boolean isValidAdvanceTime(double dt){

		return ((dt >=0)&&(dt <= 0.2));
		
	}
	
	@Model
	private void setTimePeriodicRest(float time){
		this.periodicRest = time;
	}
	
	@Basic @Model
	private float getTimePeriodicRest(){
		return this.periodicRest;
	}
	
	@Model
	private boolean canHaveRecoverdOneHp(){
		return (getTimeResting() > ((double) 1/(((double) getToughness()/200.0)/0.2)));

	}
		
	@Basic @Model
	private double getBaseSpeed(){
		return 1.5*(getStrength()+getAgility())/(2*getWeight());
	}
	
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
	
	@Basic
	public double[] getCurrentSpeed() {
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
	 * 
	 */
	public void startSprinting(){
		this.sprinting = true;
	}
	
	public void stopSprinting(){
		this.sprinting = false;
	}
	
	public boolean isSprinting(){
		return sprinting;
	}
	
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
	
	@Model 
	private void stopMoving(){
		this.isMoving = false;

	}
	
	@Basic @Model
	private boolean isMoving(){
		return isMoving;
	}
	
	@Basic
	public boolean isActualMoving(){
		return (isMoving() && !isWorking() && !isResting() && !isAttacking());
	}
	
	@Model
	private double getDistanceToTarget() {
		if (target == null) {
			return (double) 0;
		}
		double distance = Math.sqrt(Math.pow((target[0]-getLocation()[0]),2)+Math.pow((target[1]-getLocation()[1]),2)
		+ Math.pow((target[2]-getLocation()[2]),2));
		return distance;
	}
	
	@Model
	private boolean Arrived(double dt){
		return (getDistanceToTarget() < dt*getCurrentSpeedMag());	
	}
	
	@Basic
	public double getCurrentSpeedMag() {
		if (!isMoving()) {
			return (double) 0;
		}
		return Math.sqrt(Math.pow((getCurrentSpeed()[0] ), 2) +
		Math.pow((getCurrentSpeed()[1] ), 2) + Math.pow((getCurrentSpeed()[2]), 2));
	}
	
	
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalPositionException , IllegalAdjacentPositionException {
		moveToAdjacent(dx, dy, dz, false);
	}
	
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
	
	private boolean isValidAdjacentMovement(int dx, int dy, int dz){
		
		return (((dx == 0) || (dx == 1) || (dx == -1)) && 
				((dy == 0) || (dy == 1) || (dy == -1) )&& 
				((dz == 0) || (dz == 1) || dz == -1));
	}
	
	public void work(){
		startWorking();
		
	}
	
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
	
	@Model
	private void stopWorking(){
		this.working = false;

	}
	@Basic
	public boolean isWorking(){
		return working;
	}
	
	@Model
	private void setTimeRemainderToWork(float time){
		this.timeRemainderToWork = time;
	}
	
	@Model 
	private float getTimeRemainderToWork(){
		return timeRemainderToWork;
	}
	
	
	public void attack(Unit other) throws IllegalAttackPosititonException {
		if (this != other) {
			if (!this.canHaveAsAttackPosition(other.getOccupiedCube())) {
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
	
	
	@Model 
	private void setOrientationInFight(Unit other) {
		double orientUnitThis = Math.atan2(other.getLocation()[1]-this.getLocation()[1],
				other.getLocation()[0]-this.getLocation()[0]);
		double orientUnitOther = Math.atan2(this.getLocation()[1]-other.getLocation()[1],
				this.getLocation()[0]-other.getLocation()[0]);
		
		this.setOrientation(orientUnitThis);
		other.setOrientation(orientUnitOther);
	}
	
	
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
	
	@Model 
	private void setRandomLocation(){
		try {
			setLocation(randomPosition(getLocation()));
		} catch (IllegalPositionException e) {
			setRandomLocation();
		}
	}
	
	@Model
	private double[] randomPosition(double[] currLoc){
		double[] newLoc = {currLoc[0]+ (random.nextDouble()*2-1), currLoc[1]+ (random.nextDouble()*2-1), currLoc[2]};
		if (currLoc == newLoc) {
			return randomPosition(currLoc);
		}
		return newLoc;
	}
	
	@Model
	private boolean canHaveAsAttackPosition(int[] attackCubePosition){
		return((Math.abs(this.getOccupiedCube()[0]-attackCubePosition[0]) <=1) && 
				(Math.abs(this.getOccupiedCube()[1]-attackCubePosition[1]) <=1) &&
				(Math.abs(this.getOccupiedCube()[2]-attackCubePosition[2]) <=1));	
	}
	
	@Basic
	public boolean isAttacking(){
		return (getAttackTime() > 0);
	}
	
	@Model
	private void setAttackTime(float time){
		this.attackTime = time;
	}
	
	@Basic @Model
	private float getAttackTime(){
		return this.attackTime;
	}
	
	@Basic @Model
	private boolean getDefendSucces(double x){
		return (random.nextDouble() <= x);
	}
	
	
	public void rest(){
		startResting();
	}
	
	@Basic 
	public boolean isResting(){
		return resting;
	}
	
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
	
	@Model
	private void setTimeSinceRest(float time){
		this.timeSinceRest = time;
	}
	
	@Model
	private float getTimeSinceRest(){
		return this.timeSinceRest;
	}
	
	@Model
	private void setTimeResting(float time) {
		this.timeResting = time;
	}
	
	@Basic @Model
	private float getTimeResting() {
		return this.timeResting;
	}
	
	@Model
	private void setStartRestHitpoints(double hitpoints){
		this.startRestHitpoints = hitpoints;
	}
	
	@Basic @Model
	private double getStartRestHitpoints(){
		return this.startRestHitpoints;
	}
	
	@Model
	private void setStartRestStamina(double stamina){
		this.startRestStamina = stamina;
	}
	
	@Basic @Model
	private double getStartRestStamina(){
		return this.startRestStamina;
	}
	
	@Basic
	public boolean isDefaultBehaviourEnabled(){
		return defaultBehaviour;
	}
	
	
	public void startDefaultBehaviour(){
		defaultBehaviour = true;
	}
	
	public void stopDefaultBehaviour(){
		defaultBehaviour = false;
		stopWorking();
		stopMoving();
		stopResting();
	}
	
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
	
	@Model
	private int[] getRandomPosition(){
		int[] randLoc = {random.nextInt(WORLD_X-1), random.nextInt(WORLD_Y-1), random.nextInt(WORLD_Z-1)};
		return randLoc;
	}
}
