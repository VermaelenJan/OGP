package hillbillies.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
// import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.IllegalAdjacentPositionException;
import hillbillies.model.exceptions.IllegalAdvanceTimeException;
import hillbillies.model.exceptions.IllegalAttackPosititonException;
import hillbillies.model.exceptions.IllegalFightFactionException;
import hillbillies.model.exceptions.IllegalNameException;
import hillbillies.model.exceptions.IllegalPositionException;
import hillbillies.model.exceptions.IllegalValueException;
import hillbillies.model.exceptions.IllegalWorkPositionException;

//TODO: fix doc van ALLES en @'s
//TODO: remove //code if doc in orde is
/**
 * @invar  The location of each unit must be a valid location for any
 *         unit.
 *       | isValidLocation(getLocation())
 * @invar  The name of each unit must be a valid name for any
 *         unit.
 *       | isValidName(getName())
 * @invar  The weight of each unit must be greater than or equal to the units strength plus agility, divided by two,
 * 		   and smaller than MAX_VAL.
 *       | getWeight() >= (getAgility()+getStrength)/2 && (getWeight() <= MAX_VAL)
 * @invar  The strength of each unit must be between MIN_VAL and MAX_VAL,inclusively.
 * 		 | (getStrength() >= MIN_VAL) && (getStrength() <= MAX_VAL)
 * @invar  The agility of each unit must be between MIN_VAL and MAX_VAL,inclusively.
 * 		 | (getAgility() >= MIN_VAL) && (getAgility() <= MAX_VAL) 
 * @invar  The toughness of each unit must be between MIN_VAL and MAX_VAL,inclusively.
 * 		 | (getTougness() >= MIN_VAL) && (getToughness() <= MAX_VAL)
 * @invar  The hitpoints of each unit must be a valid hitpoints for the unit.
 *       | isValidHitpoints(getHitpoints())
 * @invar  The stamina of each unit must be a valid stamina for the unit.
 *       | isValidStamina(getStamina())
 * @invar  The orientation of each unit must be between 0 and 2*PI. 
 *       | (getOrientation() >= 0) && (getOrientation < 2*PI)
 *       
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.1
 *
 */
public class Unit {

	private World world;
	private Faction faction;
	public Position positionObj;
	
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
	 * @effect The given weight is set as the weight of this new unit.
	 * 			| setWeight(weight)
	 * @effect The given strength is set as the strength of this new unit.
	 * 			| setStrength(strength)
	 * @effect The given agility is set as the agility of this new unit.
	 * 			| setAgility(agility)
	 * @effect The given toughness is set as the toughness of this new unit.
	 * 			| setToughness(toughness)
	 * @pre The given hitpoints must be a valid hitpoints for this unit.
	 *          | isValidHitpoints(hitpoints)
	 * @post The hitpoints of this new unit is equal to the given hitpoints.
	 *          | new.getHitpoints() == hitpoints
	 * @pre The given stamina must be a valid stamina for this unit.
	 *       	| isValidStamina(stamina)
	 * @post The stamina of this new unit is equal to the given stamina.
	 *       	| new.getStamina() == stamina													
	 * @effect The given orientation is set as the orientation of this new unit.
	 * 			| setOrientation(orientation)
	 * @throws IllegalPositionException
	 * 			The given position is not a valid position for a unit.
	 * 			| ! canHaveAsPosition(position)
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 */
	@Model
	public Unit(int[] CubeLocation, String name, int weight, int strength, int agility, int toughness, World world)
																throws IllegalPositionException, IllegalNameException {
		double[] location = {CubeLocation[0]+ConstantsUtils.CUBE_LENGTH/2, CubeLocation[1]+
				ConstantsUtils.CUBE_LENGTH/2, CubeLocation[2]+ConstantsUtils.CUBE_LENGTH/2};
		setName(name);
		setWeight(weight, true);
		setStrength(strength, true);
		setAgility(agility, true);
		setToughness(toughness, true);
		setHitpoints(getMaxHitpointsStamina());
		setStamina(getMaxHitpointsStamina());
		setOrientation(Math.PI/2);
		setWorld(world);
		setExperience(0);
		
		positionObj = new Position(world);
		positionObj.setLocation(location); //
		}
	
	
		public Unit(int[] CubeLocation, String name, int weight, int strength, int agility, int toughness)
															throws IllegalPositionException, IllegalNameException {
			this(CubeLocation, name, weight, strength, agility, toughness, null);
		}

//	/**
//	 * Initialize this new unit with the given cubeLocation, name, weight, strength,toughness,agility and the default orientation PI/2.
//	 * 
//	 * @param cubeLocation
//	 *			The location for this new unit.
//	 * @param name
//	 * 			The name for this new unit.
//	 * @param weight
//	 * 			The weight for this new unit.
//	 * @param strength
//	 * 			The strength for this new unit.
//	 * @param agility
//	 * 			The agility for this new unit.
//	 * @param toughness
//	 * 			The toughness for this new unit.
//	 * @effect This new unit is initialized with the given cube location as its cube location, the given name as its name, the given 
//	 * 			weight as its weight, the given strength as its strength, the given agility as its agility, the given toughness as 
//	 * 			its toughness, and the default value PI/2 as its orientation.
//	 * @throws IllegalPositionException
//	 * 			The given position is not a valid position for a unit.
//	 * 			| ! canHaveAsPosition(position)
//	 * @throws IllegalNameException 
//	 * 			The given name is not a valid name for a unit.	
//	 * 			| ! isValidName(name)
//	 */
//	public Unit(int[] CubeLocation, String name, int weight, int strength, int agility, int toughness)
//			throws IllegalPositionException, IllegalNameException {
//		this(CubeLocation, name, weight, strength, agility, toughness,Math.PI/2);
//	}
				
	@Basic @Raw
	public World getWorld(){
		return this.world;
	}
	
	@Raw
	public void setWorld(World world){
		this.world = world;
	}
	
	@Basic @Raw
	public Faction getFaction(){
		return this.faction;
		
	}
	
	@Raw
	public void setFaction(Faction faction) throws IllegalValueException{
		if (faction.getNbUnits() >= ConstantsUtils.MAX_UNITS_FACTION){
			throw new IllegalValueException(faction.getNbUnits());
		}
		this.faction = faction;
		
	}
	
	private boolean isTerminated;
	
	protected void terminate(){
		this.isTerminated = true;
		if (this.faction != null) { // ( == if (!(unit didnt get in world yet)))
			this.faction.checkTerminate();
		}
	}
	
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	// LOCATION
	
	/**
	 * Return the location of this unit.
	 */
	@Basic @Raw
	public double[] getLocation() {
		return positionObj.getLocation();
	}
	
//	/**
//	 * Check whether the given location is a valid location for
//	 * any unit.
//	 * 
//	 * @param location
//	 * 			The location to check.
//	 * @return True if and only if the location is in the dimensions of the predefined world. So if the x,y and z value 
//	 * 			are smaller than the WORLD_X, WORLD_Y and WORLD_Z values and the x,y and z value are greater than 0.
//	 * 			| result == (location[x] <= WORLD_X) && (location[y] <= WORLD_Y) && (location[z] <= WORLD_Z) && 
//	 *			| (location[x] >= 0) && (location[y] >= 0) && (location[z] >= 0))
//	 */
//	@Raw @Model
//	private static boolean isValidLocation(double[] location) {
//		return ((location[0] <= WORLD_X) && (location[1] <= WORLD_Y) && (location[2] <= WORLD_Z) && 
//				(location[0] >= 0) && (location[1] >= 0) && (location[2] >= 0));
//	}
	
//	/**
//	 * Set the location of this unit to the given location.
//	 * 
//	 * @param location
//	 *         	The new location for this unit.
//	 * @post The new location of this unit is equal to the given location.
//	 *       	| new.getLocation() == location
//	 * @throws IllegalPositionException
//	 *         	The given location is not a valid location for any unit.
//	 *       	| ! isValidLocation(location)
//	 */
//	@Raw @Model
//	private void setLocation(double[] location) throws IllegalPositionException{
//		positionObj.setLocation(location);
////		if (!isValidLocation(location))
////			throw new IllegalPositionException(location);
////		this.xPos = location[0];
////		this.yPos = location[1];
////		this.zPos = location[2];
//	}
//	
//	/**
//	 * Variables registering the location of this unit.
//	 */
//	private double xPos = 0;
//	private double yPos = 0;
//	private double zPos = 0;
	
	// CUBE
	
	/**
	 * @return The coordinates of the cube the unit is in.
	 */
	@Raw
	public int[] getOccupiedCube() {
		return positionObj.getOccupiedCube();
	}
	
	// NAME 
	
	/**
	 * Return the name of this unit.
	 */
	@Basic @Raw 
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the name of this unit to the given name.
	 * 
	 * @param name
	 *         	The new name for this unit.
	 * @post The new name of this unit is equal to the given name.
	 *         	| new.getName() == Name
	 * @throws IllegalNameException
	 *         	The given name is not a valid name for any unit.
	 *         	| ! isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalNameException{
		if (!isValidName(name))
			throw new IllegalNameException(name);
		this.name = name;
	}
	
	/**
	 * Check whether the given name is a valid name for any unit.
	 *  
	 * @param name
	 *         	The name to check.
	 * @return True if and only if the given name's length is greater than or equal to 2 and the given name's 
	 * 			first character is uppercase and the given name has all valid characters.
	 * 		   	| result == (	(name.length() >= 2) 
	 *							&&	(Character.isUpperCase(name.charAt(0))) 
	 *							&&  (areValidCharacters(name)) )			
	 */
	@Model
	private static boolean isValidName(String name){
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (areValidCharacters(name)));
	}
	
	/**
	 * Check whether all the given name's characters are valid for a name of any unit. 
	 * 
	 * @param name
	 * @return True if and only if the name has letters, spaces, double quotes or single quotes as characters.
	 * 		   	| result == (for char in name
	 *						 ( char.isLetter ||  (char == ' ')  || (char == '"') || (char == ''')))
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
	 * Variable registering the name of this unit.
	 */
	private String name = "";

	
	// WEIGHT
	
	/**
	 * Return the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight(){
		return this.weight;
	}	
	
	/**
	 * Set the weight of this unit to the given weight.
	 * 
	 * @param weight
	 *	     	The new weight for this unit.
	 * @effect The new weight of the unit is set to the given weight, with the restrictions for a not-newly created unit
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
	 * @param weight
	 *          The new weight for this unit.
	 * @param flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *		If the given flag is true, the weight is set for the first time in the constructor,
	 * 		the current minimum value of the weight is the initial minimum value 
	 * 		of the weight of the constructor and the current maximum value of the weight 
	 *      is the initial maximum value of the weight of the constructor.
	 *      | if (flag)
	 *		|	then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    	If the given flag is false,the weight is already set once in the constructor,
	 * 		the current minimum value of the weight is the minimum value 
	 * 		of the weight after the constructor and the current maximum value of the weight 
	 *      is the maximum value of the weight after the constructor.
	 *      | if (! flag)
	 *		|	then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post If the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is smaller than the current minimum value of the weight,
	 * 			the weight of this unit is equal to the current minimum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight < currMinVal)
	 * 			|		then (new.getWeight() == currMinVal) 	
	 * @post If the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the current minimum value and smaller than
	 * 			the current maximum value, the weight of this unit is equal to the given weight.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if ((weight >= currMinVal) && (weight <= currMaxVal))
	 * 			|		then (new.getWeight() == weight)	
	 * @post If the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the current maximum value of the weight,
	 * 			the weight of this unit is equal to the current maximum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight > currMaxVal)
	 * 			|		then (new.getWeight() == currMaxVal) 
	 * @post If the given weight is smaller than the units strength plus agility, divided by 2, 
	 * 			the weight of this unit is equal to the units strength plus agility, divided by 2.
	 * 			| if (weight < (getStrength()+getAgility())/2)
	 * 			|	then (new.getWeight() == (getStrength()+getAgility())/2)
	 */	
	@Raw @Model
	private void setWeight(int weight, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = ConstantsUtils.INIT_MIN_VAL;
			currMaxVal = ConstantsUtils.INIT_MAX_VAL;
		}
		else {
			currMinVal = ConstantsUtils.MIN_VAL;
			currMaxVal = ConstantsUtils.MAX_VAL;
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
	

	
	private void setFreeWeight(int weight){
		
				
		if (weight >= (getStrength()+getAgility())/2){
			if (weight < ConstantsUtils.MIN_VAL) 
				this.weight = ConstantsUtils.MIN_VAL;
			else if (weight >= ConstantsUtils.MIN_VAL){
				this.weight = weight;
			}
		}
		else{
			this.weight = (getStrength()+getAgility())/2;
		} 
	}
	
	/**
	 * Variable registering the weight of this unit.
	 */
	private int weight = 0;
	
	// STRENGTH
	
	/**
	 * Return the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength(){
		return this.strength;
	}
	
	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param strength
	 *	     	The new strength for this unit.
	 * @effect The new strength of this unit is set to the given strength, with the restrictions for a not-newly created unit.
	 * 			| setStrength(strength,false)
	 */
	@Raw
	public void setStrength(int strength) {
		setStrength(strength, false);
	}
	
	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param strength
	 *          The new strength for this unit.
	 * @param flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *		If the given flag is true, the strength is set for the first time in the constructor,
	 * 		the current minimum value of the strength is the initial minimum value 
	 * 		of the strength of the constructor and the current maximum value of the strength 
	 *      is the initial maximum value of the strength of the constructor.
	 *      | if (flag)
	 *		|	then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *   	If the given flag is false,the strength is already set once in the constructor,
	 * 		the current minimum value of the strength is the minimum value 
	 * 		of the strength after the constructor and the current maximum value of the strength 
	 *      is the maximum value of the strength after the constructor.
	 *      | if (! flag)
	 *		|	then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post If the given strength is smaller than the current minimum value of the strength,
	 * 			the strength of this unit is equal to the current minimum value.
	 * 			| if (strength < currMinVal)
	 * 			|		then (new.getStrength() == currMinVal) 	
	 * @post If the given strength is greater than the current minimum value and smaller than
	 * 			the current maximum value, the strength of this unit is equal to the given strength.
	 * 			| if ((strength >= currMinVal) && (strength <= currMaxVal))
	 * 			|		then (new.getStrength() == strength)	
	 * @post If the given strength is greater than the current maximum value of the strength,
	 * 			the strength of this unit is equal to the current maximum value.
	 * 			| if (strength > currMaxVal)
	 * 			|		then (new.getStrength() == currMaxVal) 
	 * @effect The weight is set to the the weight of the unit, depending on the restrictions of the weight according
	 * 			to the strength, due to possible changes of the strength. 
	 */
	@Raw @Model
	private void setStrength(int strength, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = ConstantsUtils.INIT_MIN_VAL;
			currMaxVal = ConstantsUtils.INIT_MAX_VAL;
		}
		else {
			currMinVal = ConstantsUtils.MIN_VAL;
			currMaxVal = ConstantsUtils.MAX_VAL;
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
	private int strength = 0;
	
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
	 * @param agility
	 *	     	The new agility for this unit.
	 * @effect The agility of the unit is set to the given agility, with the restrictions for a not-newly created unit.
	 * 			| setAgility(agility,false)
	 */
	@Raw
	public void setAgility(int agility) {
		setAgility(agility, false);
	}
	
	/**
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param agility
	 *          The new agility for this unit.
	 * @param flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *		If the given flag is true, the agility is set for the first time in the constructor,
	 * 		the current minimum value of the agility is the initial minimum value 
	 * 		of the agility of the constructor and the current maximum value of the agility 
	 *      is the initial maximum value of the agility of the constructor.
	 *      | if (flag)
	 *			then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    	If the given flag is false,the agility is already set once in the constructor,
	 * 		the current minimum value of the agility is the minimum value 
	 * 		of the agility after the constructor and the current maximum value of the agility 
	 *      is the maximum value of the agility after the constructor.
	 *      | if (! flag)
	 *			then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post If the given agility is smaller than the current minimum value of the agility,
	 * 			the agility of this unit is equal to the current minimum value.
	 * 			| if (agility < currMinVal)
	 * 			|		then (new.getAgility() == currMinVal) 	
	 * @post If the given agility is greater than the current minimum value and smaller than
	 * 			the current maximum value, the agility of this unit is equal to the given agility.
	 * 			| if ((agility >= currMinVal) && (agility <= currMaxVal))
	 * 			|		then (new.getAgility() == agility)	
	 * @post If the given agility is greater than the current maximum value of the agility,
	 * 			the agility of this unit is equal to the current maximum value.
	 * 			| if (agility > currMaxVal)
	 * 			|		then (new.getAgility() == currMaxVal) 
	 * @effect The weight is set to the the weight of the unit, depending on the restrictions of the weight according
	 * 			to the agility, due to possible changes of the agility. 
	 */
	@Raw @Model
	private void setAgility(int agility, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = ConstantsUtils.INIT_MIN_VAL;
			currMaxVal = ConstantsUtils.INIT_MAX_VAL;
		}
		else {
			currMinVal = ConstantsUtils.MIN_VAL;
			currMaxVal = ConstantsUtils.MAX_VAL;
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
	private int agility = 0;
	
	
	// TOUGHNESS	

	/**
	 * Return the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness(){
		return this.toughness;
	}
	
	/**
	 * Set the toughness of this unit to the given toughness.
	 * 
	 * @param toughness
	 *	     	The new toughness for this unit.
	 * @effect The new toughness of this unit is set to the given toughness, with the restrictions for a not-newly created unit.
	 * 			| setToughness(toughness,false)
	 */
	@Raw
	public void setToughness(int toughness) {
		setToughness(toughness, false);
	}
	
	/**
	 * Set the toughness of this unit to the given agility.
	 * 
	 * @param toughness
	 *          The new toughness for this unit.
	 * @param flag
	 * 			A flag to mark that the method is used in the constructor.
	 * 
	 *		If the given flag is true, the toughness is set for the first time in the constructor,
	 * 		the current minimum value of the toughness is the initial minimum value 
	 * 		of the toughness of the constructor and the current maximum value of the toughness 
	 *      is the initial maximum value of the toughness of the constructor.
	 *      | if (flag)
	 *			then ((currMinVal == INIT_MIN_VAL) && (currMaxVal = INIT_MAX_VAL))
	 *
	 *    	If the given flag is false,the toughness is already set once in the constructor,
	 * 		the current minimum value of the toughness is the minimum value 
	 * 		of the toughness after the constructor and the current maximum value of the toughness 
	 *      is the maximum value of the toughness after the constructor.
	 *      | if (! flag)
	 *			then ((currMinVal == MIN_VAL) && (currMaxVal = MAX_VAL))
	 *
	 * @post If the given toughness is smaller than the current minimum value of the toughness,
	 * 			the toughness of this unit is equal to the current minimum value.
	 * 			| if (toughness < currMinVal)
	 * 			|		then (new.getToughness() == currMinVal) 	
	 * @post If the given toughness is greater than the current minimum value and smaller than
	 * 			the current maximum value, the toughness of this unit is equal to the given toughness.
	 * 			| if ((toughness >= currMinVal) && (toughness <= currMaxVal))
	 * 			|		then (new.getToughness() == toughness)	
	 * @post If the given toughness is greater than the current maximum value of the toughness,
	 * 			the toughness of this unit is equal to the current maximum value.
	 * 			| if (toughness > currMaxVal)
	 * 			|		then (new.getToughness() == currMaxVal) 
	 */
	@Raw @Model
	private void setToughness(int toughness, boolean flag){
		int currMinVal;
		int currMaxVal;
		if (flag){
			currMinVal = ConstantsUtils.INIT_MIN_VAL;
			currMaxVal = ConstantsUtils.INIT_MAX_VAL;
		}
		else {
			currMinVal = ConstantsUtils.MIN_VAL;
			currMaxVal = ConstantsUtils.MAX_VAL;
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
	private int toughness = 0;

	// HITPOINTS

	/**
	 * Return the maximum hitpoints of this unit.
	 * 
	 * @return The weight times the toughness divided by 50, rounded up to the first
	 * 			larger integer.
	 * 		   | result == ceil((getWeight()*getToughness())/50)
	 */
	@Raw
	public int getMaxHitpointsStamina() {
		if (this.carriedObject == null) {
			return (int) Math.ceil((((double) getWeight())*((double) getToughness()))/50);
		}
		else {
			 return (int) Math.ceil((((double) (getWeight()-this.carriedObject.getWeight()))*((double) getToughness()))/50);
		}
	}
	
	/**
	 * Return the hitpoints of this unit.
	 */
	@Basic @Raw
	public double getHitpoints(){
		return this.hitpoints;
	}
	
	/**
	 * Check whether the given hitpoints is a valid hitpoints for the unit.
	 *  
	 * @param hitpoints
	 *         	The hitpoints to check.
	 * @return True if and only if the given hitpoints are greater than zero and 
	 * 		   	smaller than the maximum hitpoints.
 	 *       	| result == ((hitpoints >= 0) && (hitpoints <= getMaxHitpointsStamina()))
	 */
	@Raw 
	private boolean isValidHitpoints(double hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= getMaxHitpointsStamina()));
	}
	
	/**
	 * Set the hitpoints of this unit to the given hitpoints.
	 * 
	 * @param hitpoints
	 *         	The new hitpoints for this unit.
	 * @pre The given hitpoints must be valid hitpoints for this unit.
	 *       	| isValidHitpoints(hitpoints)
	 * @post The hitpoints of this unit are equal to the given hitpoints.
	 *       	| new.getHitpoints() == hitpoints
	 */
	@Raw
	private void setHitpoints(double hitpoints){
		assert isValidHitpoints(hitpoints);
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Variable registering the hitpoints of this unit.
	 */
	private double hitpoints = 0;
	
	
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
	 * Check whether the given stamina is a valid stamina for this unit.
	 *  
	 * @param stamina
	 *         	The stamina to check.
	 * @return True if and only if the given stamina is greater than zero and 
	 * 		   	smaller than the maximum stamina.
 	 *       	| result == ((stamina >= 0) && (stamina <= getMaxHitpointsStamina())) 
	 */
	@Raw
	private boolean isValidStamina(double stamina){
		return ((stamina >= 0) && (stamina <= getMaxHitpointsStamina()));
	}
	
	/**
	 * Set the stamina of this unit to the given stamina.
	 * 
	 * @param stamina
	 *         The new stamina for this unit.
	 * @pre The given stamina must be a valid stamina for the unit.
	 *         | isValidStamina(stamina)
	 * @post The new stamina of this unit is equal to the given stamina.
	 *         | new.getStamina() == stamina
	 */
	@Raw @Model
	private void setStamina(double stamina){
		assert isValidStamina(stamina);
		this.stamina = stamina;
	}
	
	/**
	 * Variable registering the stamina of this unit.
	 */
	private double stamina = 0;
	
	// ORIENTATION

	/**
	 * Return the orientation of this unit.
	 */
	@Basic @Raw
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Set the orientation of this unit to the given orientation.
	 * 
	 * @param orientation
	 *         The new orientation for this unit.
	 * @post The new orientation of this unit is set to the given orientation modulo 2*PI, made positive.
	 *      	  | new.getOrientation() == orientation%(2*PI)
	 */
	@Raw @Model
	private void setOrientation(double orientation){
		this.orientation = (((orientation%(2*Math.PI))+2*Math.PI)%(2*Math.PI));
	}
	
	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation = 0;
	private boolean interruptRWPermission;
	
	
	// EXPERIENCE POINTS
	
	public int getExperience(){
		return this.experience;
	}
	
	private void setExperience(int experience){
		this.experience = experience;
	}
	
	private void increaseRandomAttributeBy1(){
		switch (ConstantsUtils.random.nextInt(3)) {
		case 0: setStrength(getStrength() + 1);
		case 1: setAgility(getAgility()+ 1); break;						
		case 2: setToughness(getToughness()+ 1); break;}
	}
	
	private void increaseRandomAttributes(int experience){
		int tempExp = getMod10Exp() + experience;
		int nbTimes10Exp = (tempExp) / 10;
		
		setMod10Exp(tempExp % 10);
		
		int count = 0;
		while (count < nbTimes10Exp){
			increaseRandomAttributeBy1();
			count++;
		}
		
	}
	
	private void updateExperience(int experience){
		setExperience(getExperience() + experience);
		increaseRandomAttributes(experience);
	}
	
	// VARIABLES REGISTERING THE EXPERIENCE OF THE UNIT
	private int mod10Exp;
	
	private int getMod10Exp(){
		return this.mod10Exp;
	}
	private void setMod10Exp(int mod10Exp){
		this.mod10Exp = mod10Exp;
	}
	
	private int experience;

	// ADVANCE TIME
	
	/**
	 * Advances the game with duration dt.
	 * @param dt
	 * 		  	The duration which the game time is advanced.
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
	 * @effect If the unit is moving, not arrived and defaultBehaviour is enabled, the unit starts or stops sprinting
	 * 			with a chance of dt/10.
	 * @throws IllegalAdvanceTimeException(dt)
	 * 			The given dt is not a valid advanceTime duration.
	 */
	public void advanceTime(double dt) throws IllegalAdvanceTimeException {
		
		if (!this.isTerminated()) {
			if (this.hitpoints <= 0) {
				setHitpoints(0);
				terminate(); 
				// Hier zeggen we we al terminate, en daarna in dropobject gaan we de weight van de unit nog aanpassen enzo, droppen
				// van object mee in terminate zetten dan of niet?
				if (this.carriedObject != null) {
					dropObject();
				}
			}
			
			if (! isValidAdvanceTime(dt)){
				throw new IllegalAdvanceTimeException(dt);
			}
			
			if (! isResting()) {
				advanceTimeNotResting(dt);
			}
	
			if (isFalling() || !positionObj.isValidUnitPositionDouble(positionObj.getLocation())){
				setFalling(true); 
				stopSprinting();
				advanceTimeFalling(dt);
			}
			
			else if (isAttacking()){
				advanceTimeAttacking(dt);
			}
			else if (isResting() && positionObj.isAtMiddleZOfCube()){ 
				advanceTimeResting(dt);
			}		
			else if (isWorking() && canHaveRecoverdOneHp() && positionObj.isAtMiddleZOfCube()){
				advanceTimeWorking(dt);
			}
			else if (isMoving()){
				advanceTimeMoving(dt);		
			}
			else if (isDefaultBehaviourEnabled()) {
				newDefaultBehaviour();
			}
		}
		
		else {
			this.stopMoving();
			this.stopSprinting();
			this.stopWorking();
			this.stopDefaultBehaviour();
			this.stopResting();
			this.setFalling(false);
			//TODO: nog iets?
		}
	}


	private void advanceTimeMoving(double dt) {
		if (arrived(dt)){
			advanceTimeMovingArrived();
		}
		else{
			advanceTimeMovingNotArrived(dt);
		}
	}
	
	private void advanceTimeFalling(double dt){
		
		int prevZPos = positionObj.getOccupiedCube()[2];

		if (!positionObj.isValidZPosition()){
			positionObj.fall(dt,positionObj.getCubeBelow());
			
			if (positionObj.getOccupiedCube()[2] != prevZPos){ // update hitpoints
				int nbZLvls = prevZPos - positionObj.getOccupiedCube()[2];
				setHitpoints(getHitpoints()-10*nbZLvls);
			}
		}
		
		else {
			if (!positionObj.isAtMiddleZOfCube()){
				positionObj.fall(dt,positionObj.getOccupiedCube());
			}
			else {
				setFalling(false);
				double[] back = {positionObj.getOccupiedCube()[0]+0.5, positionObj.getOccupiedCube()[1]+0.5, positionObj.getOccupiedCube()[2]+0.5,};
				this.target = back;
			}
		}
	}

	private void advanceTimeMovingNotArrived(double dt) {
		double[] newLoc = {positionObj.getLocation()[0]+ this.getCurrentSpeed()[0]*dt,
				positionObj.getLocation()[1]+ this.getCurrentSpeed()[1]*dt,
				positionObj.getLocation()[2]+ this.getCurrentSpeed()[2]*dt};
		try {
			positionObj.setLocation(newLoc);
		} catch (IllegalPositionException e) {
			double[] back = {positionObj.getOccupiedCube()[0]+0.5, positionObj.getOccupiedCube()[1]+0.5, positionObj.getOccupiedCube()[2]+0.5,};
			this.target = back;
		}
		
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
			if (ConstantsUtils.random.nextDouble() <= (float) dt/10) {
				if (isSprinting()) {
					stopSprinting();
				}
				else {
					startSprinting();
				}
			}
		}
		setOrientation(Math.atan2(getCurrentSpeed()[1],getCurrentSpeed()[0]));
	}

	private void advanceTimeMovingArrived() {
		stopMoving();
		try {
			positionObj.setLocation(target);
		} catch (IllegalPositionException e) {} //Exception will never be thrown.
		
		updateExperience(1);

		
		if (!(globalTarget == null) &&
				!((positionObj.getOccupiedCube()[0] == globalTarget[0]) &&
				(positionObj.getOccupiedCube()[1] == globalTarget[1]) &&
				(positionObj.getOccupiedCube()[2] == globalTarget[2]) ) ) {
			try {
				interruptRWPermission = true;
				moveTo(globalTarget);
				interruptRWPermission = false;
				
			} catch (IllegalPositionException e) {} //Exception will never be thrown.
		}
	}

	private void advanceTimeWorking(double dt) {
		setTimeRemainderToWork(getTimeRemainderToWork()-(float)dt);
		if (this.workType == 1) {
			if (!isMoving()) {
				setTimeRemainderToWork(0);
				dropObject();
				updateExperience(10);
				this.workType = 0;
			}
			else {
				setTimeRemainderToWork(Float.MAX_VALUE);
				advanceTimeMoving(dt);
			}
		}
		
		if (this.workType == 2) {
			if (!isMoving()) {
				setTimeRemainderToWork(0);
				Boulder currBoulder = world.getBoulderAtCube(workTarget);
				Log currLog = world.getLogAtCube(workTarget); // could be other boulder/log but doesnt matter?
				if (currBoulder != null && currLog != null) {
					improveEquipment(currBoulder,currLog);
					updateExperience(10);
				}
				this.workType = 0;
			}
			else {
				setTimeRemainderToWork(Float.MAX_VALUE);
				advanceTimeMoving(dt);
			}
		}
		
		if (this.workType == 3) {
			if (!isMoving()) {
				setTimeRemainderToWork(0);
				Boulder currBoulder = world.getBoulderAtCube(workTarget);
				if (currBoulder != null) {
					pickUpObject(currBoulder);
					updateExperience(10);
				}
				this.workType = 0;
			}
			else {
				setTimeRemainderToWork(Float.MAX_VALUE);
				advanceTimeMoving(dt);
			}
		}
		
		if (this.workType == 4) {
			if (!isMoving()) {
				setTimeRemainderToWork(0);
				Log currLog = world.getLogAtCube(workTarget);
				if (currLog != null) {
					pickUpObject(currLog);
					updateExperience(10);
				}
				this.workType = 0;
			}
			else {
				setTimeRemainderToWork(Float.MAX_VALUE);
				advanceTimeMoving(dt);
			}
		}
		
		if (getTimeRemainderToWork() <= 0){
			setTimeRemainderToWork(0);
			stopWorking();

			if (this.workType == 5) {
				world.caveIn(workTarget[0], workTarget[1], workTarget[2]);	
				updateExperience(10);
				this.workType = 0;
			}
			
		}
		
		if (this.workType == 0) {
			stopWorking();
		}
	}

	private void advanceTimeResting(double dt) {
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

	private void advanceTimeAttacking(double dt) {
		stopWorking();
		stopResting();
					
		setAttackTime(getAttackTime() - (float)(dt));
		if (getAttackTime()<= 0){
			setAttackTime(0);
		}
	}

	private void advanceTimeNotResting(double dt) {
		setTimeSinceRest(getTimeSinceRest() + (float)dt);
		if (getTimeSinceRest() > 180){
			startResting();
		}
	}
	
	/**
	 * Checks whether the duration can have the given dt as its value.
	 * @param dt
	 * 		  	The duration which the game time is advanced.
	 * @return True if and only if dt is greater or equal to 0 and smaller or equal to 0.2.
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
	 * @return 1.5 times the strength plus agility of the unit, divided by 200 times the weight ,
	 * 		   divided by 100.
	 * 		   | result == 1.5*(getStrength()+getAgility())/(200*getWeight()/100)
	 */
	@Model
	private double getBaseSpeed(){
		return 1.5*(getStrength()+getAgility())/(2*getWeight());
	}
	
	
	/**
	 * Get the walking speed of this unit.
	 * 
	 * @param targetZ
	 * 		 	The z coordinate of the target to which the unit is moving.
	 * @return 0.5 times the base speed of the unit, if the value of the z coordinate of the current 
	 * 			location of the unit is smaller than the z coordinate of the target location.
	 * 			| if (getLocation()[z_coord]-targetZ < 0)
	 * 			|	then result == 0.5*getBaseSpeed() 
	 * @return 1.2 times the base speed of the unit, if the value of the z coordinate of the current
	 * 			location of the unit is greater than the z coordinate of the target location.
	 * 			| if (getLocation()[z_coord]-targetZ > 0)
	 * 			|	then result == 1.2*getBaseSpeed() 
	 * @return 	The base speed of the unit, if the value of the z coordinate of the current location 
	 * 			is equal to the z coordinate of the target location.
	 * 			| result == getBaseSpeed()			
	 */
	@Model
	private double getWalkingSpeed(double targetZ){
		if (positionObj.getLocation()[2]-targetZ < 0){
			return 0.5*getBaseSpeed();
		}
		else if (positionObj.getLocation()[2]- targetZ > 0){
			return 1.2*getBaseSpeed();
		}
		else{
			return getBaseSpeed();
		}
	}

	/**
	 * Return the current speed of this unit in x, y and z direction.
	 * 
	 * @return If the unit is sprinting, the current speed is equal to the walking speed times 2,
	 * 			multiplied with the difference between the location of the target and location of the unit,
	 * 			divided by the absolute distance between the units location and the target,
	 * 			respectively in x, y and z direction.
	 * 			| result == (velocity*2*(target[x]-getLocation()[x])/distance, 
	 * 			|            velocity*2*(target[y]-getLocation()[y])/distance,
	 * 			|            velocity*2*(target[z]-getLocation()[z])/distance )
	 * @return If the unit is not sprinting, the current speed is equal to the walking speed,
	 * 		   	multiplied with the difference between the location of the target and location of the unit,
	 * 		   	divided by the absolute distance between the units location and the target,
	 * 		   	respectively in x, y and z direction.
	 * 			| result == (velocity*(target[x]-getLocation()[x])/distance, 
	 * 			|            velocity*(target[y]-getLocation()[y])/distance,
	 * 			|            velocity*(target[z]-getLocation()[z])/distance )		
	 */
	@Model
	private double[] getCurrentSpeed() {
		double distance = getDistanceToTarget();

		if (isSprinting()){
			velocity = getWalkingSpeed(target[2])*2;
		}
		
		else{
			velocity = getWalkingSpeed(target[2]);
		}
		
		double[] currentSpeed= {velocity*(target[0]-positionObj.getLocation()[0])/distance, 
								velocity*(target[1]-positionObj.getLocation()[1])/distance, 
								velocity*(target[2]-positionObj.getLocation()[2])/distance};
		return currentSpeed;
	}
	
	/**
	 * variable referencing the velocity of this unit.
	 */
	private double velocity = 0;
	
	/**
	 * Returns the magnitude of the units speed.
	 * 
	 * @return 0, if the unit is not moving.
	 * 			| if (!isMoving())
	 * 			|	then result == 0
	 * @return The 2-norm of the current speed in x,y and z direction.
	 * 			| result == (getCurrentSpeed()[x]^2 + getCurrentSpeed()[y]^2 + getCurrentSpeed()[z]^2)^(1/2)
	 */
	private double getCurrentSpeedMag() {
		if (!isMoving()) {
			return 0;
		}

		return Math.sqrt(Math.pow((getCurrentSpeed()[0] ), 2) +
		Math.pow((getCurrentSpeed()[1] ), 2) + Math.pow((getCurrentSpeed()[2]), 2));
	}
	
	public double getCurrentSpeedMagShow() {
		if (isMoving() && (isWorking() || isResting())) {
			return 0;
		}
		return getCurrentSpeedMag();
	}

	// SPRINTING 
	
	/**
	 * Enable sprinting of this unit.
	 * 
	 * @post	The new state of sprinting of this unit is true.
	 * 			| new.isSprinting() == true
	 * 
	 */
	public void startSprinting(){
		this.sprinting = true;
	}
	
	/**
	 * Disable sprinting of this unit.
	 * 
	 * @post The new state of sprinting of this unit is false.
	 * 			| new.isSprinting == false;		
	 */
	public void stopSprinting(){
		this.sprinting = false;
	}
	
	/**
	 * Checks whether the unit is sprinting.
	 * 
	 * @return The state of sprinting of this unit.
	 * 		   | result == sprinting
	 */
	public boolean isSprinting(){
		return sprinting;
	}
	
	/**
	 * Variable registering sprinting of this unit.
	 */
	private boolean sprinting = false;
	
	// MOVING
	
	/**
	 * Checks whether the unit is moving.
	 * 
	 * @return True if and only if the unit is moving and the unit is not working and 
	 * 			the unit is not resting and the unit is not attacking.
	 * 			| result == (isMoving() && !isWorking() && !isResting() && !isAttacking())
	 */
	public boolean isActualMoving(){
		return (isMoving() && !(positionObj.isAtMiddleZOfCube() && (isWorking() || isResting())));
	}
	
	/**
	 * Enable the movement of this unit.
	 * 
	 * @effect If the unit is resting and has rested long enough to have recovered one hitpoint,
	 * 			the units stops resting.
	 * 			| if ( isResting() && (canHaveRecoverdOneHp()))
	 * 			|	then stopResting()
	 * @effect The unit stops working.
	 * 			| stopWorking()
	 * @effect If the unit is not attacking, the unit starts moving.
	 * 			| if (!isAttacking())
	 * 			|	then new.isMoving == true.
	 */
	@Model 
	private void startMoving(){
		if ( ! interruptRWPermission && isResting() && (canHaveRecoverdOneHp())){
			stopResting();
		}
		if (! interruptRWPermission) {
			stopWorking();

		}
		if (!isAttacking()){
			this.isMoving = true;
		}
	}
	
	/**
	 * Disable the movement of this unit.
	 * 
	 * @post The new state of moving of the unit is false.
	 * 			| new.isMoving == false				
	 */
	@Model 
	private void stopMoving(){
		this.isMoving = false;
	}
	
	/**
	 * Variable registering whether the unit is moving.
	 */
	private boolean isMoving = false;
	
	/**
	 * Checks whether the unit is (globally) moving, possibly temporary interrupted.
	 * 
	 * @return True if and only if the unit is moving.
	 * 			| result == isMoving			
	 */
	@Basic @Model
	private boolean isMoving(){
		return isMoving;
	}

	/**
	 * Returns the distance from the unit's current location to the target.
	 * @return 0 if there is no target.
	 * 			| if (target == null)
	 * 			|	then result == 0
	 * @return The 2-norm of the distance between the target and the current location in x y and z direction.
	 * 			| result == ((target[x]-getLocation()[x])^2+ (target[y] - getLocation()[y])^2 + 
	 * 			| (target[z]-getLocation()[z])^2)^(1/2)			 
	 */
	@Model
	private double getDistanceToTarget() {
		if (target == null) {
			return 0;
		}
		double distance = Math.sqrt(Math.pow((target[0]-positionObj.getLocation()[0]),2)
				+ Math.pow((target[1]-positionObj.getLocation()[1]),2)
				+ Math.pow((target[2]-positionObj.getLocation()[2]),2));
		return distance;
	}
	
	/**
	 * Checks whether the unit is arrived at the target.
	 * 
	 * @param dt
	 * 		  	The duration dt of game time advancement.
	 * @return True if and only if the distance to the target is smaller than the duration dt times the magnitude of
	 * 			the current speed.
	 * 			| result == (getDistanceToTarget() < dt*getCurrentSpeedMag())
	 */
	@Model
	private boolean arrived(double dt){
		return (getDistanceToTarget() <= dt*getCurrentSpeedMag());	
	}

	/**
	 * Move to the adjacent cube.
	 * 
	 * @param dx
	 * 			The movement of the unit in the x direction.
	 * @param dy
	 * 			The movement of the unit in the y direction. 
	 * @param dz
	 * 			The movement of the unit in the z direction.
	 * @effect The unit moves to an adjacent cube with movements dx, dy and dz and the boolean
	 * 			calledByTo on false, which means this function is not called by the function moveTo,
	 * 			but only a single moveToAdjacent request.
	 * 			| moveToAdjacent(dx, dy, dz, false)
	 * @throws IllegalPositionException(currentTarget)
	 * 			The currentTarget (location after the moveToAdjacent) is not a valid location.
	 * 			| ! isValidLocation(currentTarget)
	 * @throws IllegalAdjacentPositionException(dx,dy,dz)
	 * 			The given dx,dy and dz movement is not a valid moveToAdjacent movement.
	 * 			| ! isValidAdjacentMovement(dx,dy,dz)
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalPositionException , IllegalAdjacentPositionException {
		moveToAdjacent(dx, dy, dz, false);
	}
	
	/**
	 * Move to the adjacent cube.
	 * 
	 * @param dx
	 * 			The movement of the unit in the x direction.
	 * @param dy
	 * 			The movement of the unit in the y direction. 
	 * @param dz
	 * 			The movement of the unit in the z direction.
	 * @param calledBy_moveTo
	 * 			Boolean for defining the function called by the function MoveTo.
	 * @effect If the movement in x,y and z direction is not zero, the unit starts moving towards
	 * 			the new current target, equal to the cube position of the current cube plus the dx, 
	 * 			dy and dz movement, respectively in x, y and z direction.
	 * 			|    new.currentTargetCube[x] == this.currentCube[x] + dx
	 * 			| && new.currentTargetCube[y] == this.currentCube[y] + dy
	 * 			| && new.currentTargetCube[z] == this.currentCube[z] + dz
	 * 			| startMoving()				 	
	 * @post If the boolean calledBy_moveTo is false, the new global target cube is equal to 
	 * 			the current target.
	 * 		Note: If the boolean calledBy_moveTo is true, the globalTarget remains unchanged.
	 * 		The global target is the position of the middle of the cube, where the unit would
	 * 		move to after the dx,dy and dz movement.
	 * @throws IllegalPositionException(currentTarget)
	 * 			The currentTarget (location after the moveToAdjacent) is not a valid location.
	 * 			| ! isValidLocation(currentTarget)
	 * @throws IllegalAdjacentPositionException(dx,dy,dz)
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
		
		if (isMoving() && !interruptRWPermission && canHaveRecoverdOneHp()){
			stopWorking();
			stopResting();
		}
		
		if (positionObj.isAtMiddleOfCube() || !isMoving()) {
			int[] currentCube = positionObj.getOccupiedCube();
			double[] currentTarget = {	(double)(currentCube[0]+ dx + ConstantsUtils.CUBE_LENGTH/2), 
										(double)(currentCube[1]+ dy + ConstantsUtils.CUBE_LENGTH/2),
										(double)(currentCube[2]+ dz + ConstantsUtils.CUBE_LENGTH/2)};

			
			if (! positionObj.isValidUnitPositionDouble(currentTarget)){			
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
	}
	
	/**
	 * Checks whether the dx, dy and dz movement is a valid adjacent movement.
	 * 
	 * @param dx
	 * 			The movement of the unit in the x direction.
	 * @param dy
	 * 			The movement of the unit in the y direction. 
	 * @param dz
	 * 			The movement of the unit in the z direction.
	 * @return True if and only if the movement is 0 or 1 or -1,
	 * 			for dx, dy and dz.
	 * 			| result == (((dx == 0) || (dx == 1) || (dx == -1))
	 * 			|         && ((dy == 0) || (dy == 1) || (dy == -1))
	 * 			|         && ((dz == 0) || (dz == 1) || (dz == -1)))
	 */
	private boolean isValidAdjacentMovement(int dx, int dy, int dz){
		return (((dx == 0) || (dx == 1) || (dx == -1)) && 
				((dy == 0) || (dy == 1) || (dy == -1))&& 
				((dz == 0) || (dz == 1) || (dz == -1)));
	}
	
	/**
	 * Move to the given target.
	 * 	
	 * @param	endTarget
	 * 			The target of the unit to move to.
	 * 
	 * The unit paths the shortest way reachable with moveToAdjacent.
	 * | if (currentLocation[x] == targetLocation[x])
	 * | 	then dx = 0
	 * | else if (currentLocation[x] < targetLocation[x])
	 * | 	then dx = 1
	 * | else if (currentLocation[x] > targetLocation[x])
	 * | 	then dx = -1
	 * | 
	 * | if (currentLocation[y] == targetLocation[y])
	 * | 	then dy = 0
	 * | else if (currentLocation[y] < targetLocation[y])
	 * | 	then dy = 1
	 * | else if (currentLocation[y] > targetLocation[y])
	 * | 	then dy = -1
	 * | 
	 * | if (currentLocation[z] == targetLocation[z])
	 * | 	then dz = 0
	 * | else if (currentLocation[z] < targetLocation[z])
	 * | 	then dz = 1
	 * | else if (currentLocation[z] > targetLocation[z])
	 * | 	then dz = -1
	 * 
	 * @effect 	The unit will move to the first relative cube (dx, dy, dz) heading to his target.
	 * 			| moveToAdjacent(dx, dy, dz, true)
	 * @throws	IllegalPositionException
	 * 			The given target is not a valid position for a unit.
	 * 			| ! canHaveAsPosition(target)
	 */
	public void moveToPrev1(int[] endTarget) throws IllegalPositionException { 
										// When IllegalPositionException is thrown in moveToAdjacent(),
										// by the coding rules, the variable "globalTarget" should be reverted to the
										// state as at the beginning of the method. Since the method will always initiate a new
										// globalTarget, the lack of reverting wont cause any problems.
		//globalTarget = endTarget;

//		int xCur = positionObj.getOccupiedCube()[0];
//		int yCur = positionObj.getOccupiedCube()[1];
//		int zCur = positionObj.getOccupiedCube()[2];
//		
//		int xTar = endTarget[0];
//		int yTar = endTarget[1];
//		int zTar = endTarget[2];
//		
//		if (xCur != xTar || yCur != yTar || zCur != zTar){
//			int xRes;
//			int yRes;
//			int zRes;
//			
//			// x
//			if (xCur == xTar){
//				xRes = 0;
//			}
//			else if (xCur < xTar){
//				xRes = 1;
//			}
//			else{
//				xRes = -1;
//			}
//			
//			// y 
//			if (yCur == yTar){
//				yRes = 0;
//			}
//			else if (yCur < yTar){
//				yRes = 1;
//			}
//			else{
//				yRes = -1;
//			}
//			
//			// z
//			if (zCur == zTar){
//				zRes = 0;
//			}
//			else if (zCur < zTar){
//				zRes = 1;
//			}
//			else{
//				zRes = -1;
//			}
//			try {
//				moveToAdjacent(xRes, yRes,zRes, true);
//			} catch (IllegalAdjacentPositionException e) {} //Exception will never be thrown.
//		}
		
		
	}
	
	int currentLvl = 0;
	HashMap<Cube,Integer> queue = new HashMap<Cube, Integer>();
	
	private void search(int[] location, int n_0){
		for (Cube cube : positionObj.getNeighbouringCubes(location)){
			int[] cubeLoc = cube.getCubePosition();
			if (cube.getCubeType().isPassableTerrain() &&
					positionObj.isValidUnitPositionInt(cubeLoc) &&
						!queue.containsKey(cube)){
				queue.put(cube,n_0+1);

			}
		}
	}
	
	public void moveTo(int[] endTarget){ //TODO: exception if unreachable??? of gewoon negeren?

		globalTarget = endTarget;
				
		int[] currentCubeLoc = positionObj.getOccupiedCube();
		Cube currentCube = world.getCube(currentCubeLoc[0], currentCubeLoc[1], currentCubeLoc[2]);
		
		if (!Arrays.equals(endTarget,currentCubeLoc)){
			
			queue.put(world.getCube(endTarget[0], endTarget[1], endTarget[2]),0 );
						
			
			boolean expandingQueue = true;

			while (!queue.containsKey(currentCube) && expandingQueue){
				
				HashMap<Cube,Integer> temp = new HashMap<Cube, Integer>();
				for (HashMap.Entry<Cube, Integer> cube : queue.entrySet()){
					if (cube.getValue() == currentLvl){
						temp.put(cube.getKey(), currentLvl);
					}
				}
				
				int prevQLength = queue.size();
				for (HashMap.Entry<Cube, Integer> tempCube : temp.entrySet()){
					search(tempCube.getKey().getCubePosition(),currentLvl);
				}
				int newQLength = queue.size();
				currentLvl += 1;
				if (prevQLength == newQLength) {
					expandingQueue = false;
				}
			}
			if (expandingQueue) {
				Cube nextCube = null;
				
				for (Cube cube : positionObj.getNeighbouringCubes(currentCubeLoc)){
					if (queue.containsKey(cube) && queue.get(cube) < currentLvl){
						currentLvl = queue.get(cube);
						nextCube = cube;
					}
				}
				
				
				//van hier
				if (nextCube != null) { //TODO: proberen snappen? hoe kan nextCube op deze plaats ooit null zijn? (zonder de if() krijgen we nullpointers
					// als we 100 units elkaar laten forceren)
					int dx = nextCube.getCubePosition()[0]-currentCube.getCubePosition()[0];
					int dy = nextCube.getCubePosition()[1]-currentCube.getCubePosition()[1];
					int dz = nextCube.getCubePosition()[2]-currentCube.getCubePosition()[2];
					moveToAdjacent(dx,dy,dz, true);	
				}
				//tot hier is louche

				queue = new HashMap<Cube, Integer>();
				currentLvl = 0;
			}
		}
	}

	

	/**
	 * Variable registering the target to move to.
	 */
	private double[] target = null;
	
	/**
	 * Variable registering the global target to move to.
	 */
	private int[] globalTarget = null;
	

	
	// FALLING
	
	private boolean isFalling;
	
	private void setFalling(boolean bool){
		this.isFalling = bool;
	}
	
	private boolean isFalling(){
		return this.isFalling;
	}
	private int workType;
	private int[] workTarget;

	// WORKING
	
	public void work(){
		List<Cube> neighbs = (positionObj.getNeighbouringCubesIncludingOwn(positionObj.getOccupiedCube()));
		try {
			workAt(neighbs.get(ConstantsUtils.random.nextInt(neighbs.size())).getCubePosition());
		} catch (IllegalPositionException e) {} //cant work here
	}
	
	/**
	 * Enable the unit's working.
	 * 
	 * @effect The unit starts working.
	 * 			| startWorking()
	 */			
	public void workAt(int[] workTarget) throws IllegalWorkPositionException{

		if (!positionObj.isNeighBouringCube(workTarget)){
			throw new IllegalWorkPositionException(workTarget);
		}
		
		this.workTarget = workTarget;
		
		Cube targetCube = world.getCube(workTarget[0],workTarget[1],workTarget[2]);

		if (this.carriedObject != null){
			moveTo(workTarget);
			this.workType = 1;
			startWorking();
		}
		
		
		else if ( (targetCube.getCubeType() == CubeType.WORKSHOP) && (world.getBoulderAtCube(workTarget) != null)
				&& (world.getLogAtCube(workTarget) != null) ){
			this.workType = 2;
			moveTo(workTarget);
			startWorking();
		}
		
		else if (world.getBoulderAtCube(workTarget) != null){
			this.workType = 3;
			moveTo(workTarget);
			startWorking();
		}
		
		else if (world.getLogAtCube(workTarget) != null){
			this.workType = 4;
			moveTo(workTarget);
			startWorking();
		}
		
		else if (((world.getCubeType(workTarget[0],workTarget[1],workTarget[2]) == CubeType.WOOD)
				  || (world.getCubeType(workTarget[0],workTarget[1],workTarget[2]) == CubeType.ROCK))
					&& this.carriedObject == null){
				this.workType = 5;
				startWorking();
				double[] targetCenter = {workTarget[0]+0.5 ,workTarget[1]+0.5 ,workTarget[2]+0.5};
				setOrientationTo(targetCenter);
			}
	}
	

	private Object carriedObject;
	
	public boolean isCarryingLog() {
		return (this.carriedObject instanceof Log);
	}
	
	public boolean isCarryingBoulder() {
		return (this.carriedObject instanceof Boulder);
	}
	
	protected void improveEquipment(Boulder boulder, Log log){
		setWeight(getWeight() + boulder.getWeight()/2);
		setToughness(getToughness() + log.getWeight()/2); // kheb maar random ding genomen zenne
		
		boulder.terminate();
		log.terminate();
		world.boulders.remove(boulder);
		world.logs.remove(log);
	}
	
	protected void pickUpObject(Object object){
		this.carriedObject = object;
		object.terminate();
		setFreeWeight(getWeight()+object.getWeight());

	}
	
	protected void dropObject(){
		setFreeWeight(getWeight()-this.carriedObject.getWeight());
		this.carriedObject.positionObj.setFreeLocation(this.getLocation());
		this.carriedObject.revive();
		this.carriedObject = null;
	}
	

	/**
	 * The unit starts working.
	 * 
	 * @effect If the unit is resting and the unit has rested for a time at least long enough to recover
	 * 			one hitpoint, the unit stops resting, starts working and its time remainder to work is equal to 
	 * 			500 divided by the strength of the unit.
	 * 			| if ( isResting() && canHaveRecoverdOneHp() )
	 * 			|	then (new.isWorking() == true)
	 * 			|		&& (setTimeRemainderToWork(500/getStrength()))
	 * @effect If the unit is not resting, the units starts working and its time remainder to work
	 * 			is equal to 500 divided by the strength of the unit.
	 * 			| if (! isResting())
	 * 			|	then ((new.isWorking() == true)
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
	 * The unit stops working.
	 * 
	 * @post The unit is not working.
	 * 			| new.isWorking() == false
	 */
	@Model
	private void stopWorking(){
		this.working = false;

	/**
	 * Checks whether the unit is working.
	 * 
	 * @return True if and only if  the unit is working.
	 * 			| result == working
	 */
	}
	@Basic
	private boolean isWorking(){
		return working;
	}
	
	public boolean isWorkingShow(){
		return (isWorking() && !isActualMoving());
	}
	
	/**
	 * Variable registering whether the unit is working.
	 */
	private boolean working = false;
	
	/**
	 * Return the time remaining to work of this unit.
	 */
	@Model 
	private float getTimeRemainderToWork(){
		return timeRemainderToWork;
	}
	
	/**
	 * Set the time remaining to work of this unit to the given time.
	 * 
	 * @param time
	 * 			The new time remaining to work for this unit.
	 * @post The new time remaining to work of this unit is equal to the given time.
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
	private float timeRemainderToWork = 0;
	
	// FIGHTING
	
	/**
	 * Attack an other unit.
	 * 
	 * @param other
	 * 		 	Unit to attack.
	 * @effect If the unit is not equal to the unit to attack and if the unit
	 * 			is resting, the unit stops resting.
	 * 			| if (this != other)
	 * 			|	if (this.isResting())
	 * 			|		then this.stopResting()
	 * @effect If the unit is not equal to the unit to attack, the unit stops working.
	 * 			| if (this != other)
	 * 			|	then this.stopWorking()
	 * @effect If the unit is not equal to the unit to attack,
	 * 			the new orientation is set to the to the orientation in fight.
	 * 			| if (this != other)
	 * 			|	then this.setOrientationInFight(other)
	 * @effect If the unit is not equal to the unit to attack,
	 * 			the new attack time of this unit is equal to 1 second.
	 * 			| if (this != other)
	 * 			|	then this.setAttackTime(1)
	 * @throws IllegalAttackPosititonException(other.getOccupiedCube())
	 * 			The unit cannot attack the given other unit.
	 * 			| ! this.isValidAttackPosition(other.getOccupiedCube())
	 * 			
	 */
	public void attack(Unit other) throws IllegalFightFactionException, IllegalAttackPosititonException {

		if (this != other) {
			if (!this.isValidAttackPosition(other.positionObj.getOccupiedCube())) {
				throw new IllegalAttackPosititonException(other.positionObj.getOccupiedCube());
			}	
			
			if (this.getFaction() == other.getFaction()){
				throw new IllegalFightFactionException(this.getFaction());
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
	 * @param attackCubePosition
	 * 			The position of the cube of the unit to attack.
	 * @return True if and only if the distance between the cube of the unit 
	 * 			and the cube of the unit to attack is 0 or 1 in the x, y and z direction.
	 * 			| result ==((this.getOccupiedCube()[x] - attackCubePosition[x] <= 1)
	 * 					  &&(this.getOccupiedCube()[y] - attackCubePosition[y] <= 1)
	 * 				      &&(this.getOccupiedCube()[z] - attackCubePosition[z] <= 1))
	 */
	@Model
	private boolean isValidAttackPosition(int[] attackCubePosition){
		return((Math.abs(this.positionObj.getOccupiedCube()[0]-attackCubePosition[0]) <=1) && 
				(Math.abs(this.positionObj.getOccupiedCube()[1]-attackCubePosition[1]) <=1) &&
				(Math.abs(this.positionObj.getOccupiedCube()[2]-attackCubePosition[2]) <=1));	
	}
	
	/**
	 * Set the orientation of the units in the fight to each other.
	 * 
	 * @param other
	 * 			The other unit in the fight.
	 * @effect The orientation of this unit is set in the direction of the other unit, 
	 * 			using the arc tangens of the distance difference in y direction of the other unit and
	 * 			this unit, and the distance difference in x direction of the other unit and 
	 * 			this unit.
	 * 			| this.setOrientation(arctangens(other.getLocation()[y]-this.getLocation()[y],
	 * 			|	other.getLocation()[x]-this.getLocation()[x])
	 * @effect The orientation of the other unit is set in the direction of the this unit, 
	 * 			using the arc tangens of the distance difference in y direction of this unit and
	 * 			the other unit, and the distance difference in x direction of this unit and 
	 * 			the other unit.
	 * 			| other.setOrientation(arctangens(this.getLocation()[y]-other.getLocation()[y],
	 * 			|	this.getLocation()[x]-other.getLocation()[x])
	 */
	@Model  
	private void setOrientationInFight(Unit other) {
		this.setOrientationTo(other.positionObj.getLocation());
		other.setOrientationTo(this.positionObj.getLocation());

	}
	
	@Model
	private void setOrientationTo(double[] target) {
		double orientUnitThis = Math.atan2(target[1]-this.positionObj.getLocation()[1],
				target[0]-this.positionObj.getLocation()[0]);
		this.setOrientation(orientUnitThis);
	}
	
	/**
	 * Defend against an other unit.
	 * 
	 * @param other
	 * 			The unit to defend against.
	 * @effect If the unit is not equal to the other unit and the unit is resting, the unit stops resting.
	 * 			| if (this!=other)
	 * 			|	if (this.isResting())
	 * 			|		then this.stopResting()
	 * @effect If the unit is not equal to the other unit, the unit stops working.
	 * 			| if (this!=other)
	 * 			|	then this.stopWorking()
	 *  If the unit is not equal to the other unit, the possibility to dodge is 0.2 times this units agility,
	 * 	divided by the other units agility.
	 * 	| if (this != other)
	 * 	|	then possibilityDodge = (0.2* this.getAgility()/other.getAgility())
	 * @effect If this unit is not equal to the other unit, and the unit dodges successfully, the unit's location is set
	 * 			to a random location and its orientation is set in the direction of the attacking unit.
	 * 			| if (this != other)
	 * 			|	if (getDefendSucces(possibilityDodge))
	 * 			|		then (this.setRandomLocation && this.setOrientationInFight(other))
	 *	If this unit is not equal to the other unit, and the unit is not resting,
	 * 	the possibility to block is equal to 0.25 times the strength plus agility of this unit, 
	 * 	divided by the strength plus agility of the other unit.
	 * 	| possibilityBlock = 0.25*(( this.getStrength() + this.getAgility())/(other.getStrength()+other.getAgility()))
	 * @post If this unit is not equal to the other unit, and if the unit does not 
	 * 			dodge successfully, and if the unit does not block successfully,
	 * 			the new hitpoints are equal to the old hitpoints reduced with the strength
	 * 			of the other unit divided by 10.	
	 * 			| if (this != other)
	 * 			|	if ( ! getDefendSucces(possibilityDodge))
	 * 			|		if (! getDefendSucces(possibilityBlock))
	 * 			|			then (new.getHitpoints() = this.getHitpoints - this.getStrength()/10)
	 * @effect If this unit is not equal to the other unit, and if the unit fails to dodge, and the unit fails
	 * 			to block, and if the hitpoints are less than or equal to 0, then the hitpoints are set to 0.
	 * 			| if (this != other)
	 * 			|	if ( ! getDefendSucces(possibilityDodge))
	 * 			|		if (! getDefendSucces(possibilityBlock))
	 * 			|			if (this.getHitpoints) <=0)
	 * 			|				then this.setHitpoints(0)			
	 */
	public void defend(Unit other){
		
		if (this != other) {

						
			if (this.isResting()) {
			this.stopResting();
			}
			
			stopWorking();
			
			double possibilityDodge = (double)(0.2* (double) this.getAgility()/ (double) other.getAgility());
			if (ConstantsUtils.getPossibilitySucces(possibilityDodge)){
				this.positionObj.setRandomDodgedLocation();
				this.setOrientationInFight(other);
				
				updateExperience(20);
			

			}
			
			else{
				double possibilityBlock = (double)(0.25*(((double) this.getStrength() + (double) this.getAgility())/
						((double) ((double)other.getStrength()+(double) other.getAgility()))));
				
				if (ConstantsUtils.getPossibilitySucces(possibilityBlock)){
					updateExperience(20);
				}
				else {
					double newHitPoints = this.getHitpoints() - (double)((double)other.getStrength()/10);
					this.setHitpoints(newHitPoints);
					other.updateExperience(20);

				}
			}			
		}
	}
	
	private void attackPotentialEnemy(){

		for (Unit other : world.getAllUnits()){
			for (Cube cube : (positionObj.getNeighbouringCubesIncludingOwn(positionObj.getOccupiedCube()))){
				if (Arrays.equals(other.getOccupiedCube(),cube.getCubePosition()) && other != this && !other.isTerminated){
					try {
						this.attack(other);
						other.defend(this);
					} catch(IllegalFightFactionException e){}//do nothing
					return;
				}
			}
		}
	}
	
//	/**
//	 * Set the unit to a random position near the current location.
//	 * 
//	 * @effect The location is a random location.
//	 * 			| setLocation(randomPosition(getLocation()))
//	 */
//	@Model 
//	private void setRandomLocation(){
//		try {
//			positionObj.setLocation(randomPosition(positionObj.getLocation()));
//		} catch (IllegalPositionException e) {
//			setRandomLocation();
//		}
//	}
	
//	/**
//	 * Return a random position in the near area of the current location.
//	 * 
//	 * @param currLoc
//	 * 			The current location of the unit.
//	 * @return The current location plus a random number between -1 and 1, excluding 0,
//	 * 			in the x and y direction.
//	 * 			| result == [currLoc[x]+randomNumber(-1,1), currLoc[y] + randomNumber(-1,1), currLoc[z] ]
//	 * 			
//	 */
//	@Model
//	private double[] randomPosition(double[] currLoc){
//		double[] newLoc = {currLoc[0]+ (random.nextDouble()*2-1), currLoc[1]+ (random.nextDouble()*2-1), currLoc[2]};
//		if (currLoc == newLoc) {
//			return randomPosition(currLoc);
//		}
//		return newLoc;
//	}
	
	/**
	 * Checks whether the unit is attacking.
	 * 
	 * @return True if and only if the attack time is greater than 0.
	 * 			| result == (getAttacktime() > 0)
	 */
	public boolean isAttacking(){
		return (getAttackTime() > 0);
	}
	
	/**
	 * Set the attack time to the given time.
	 * 
	 * @param time
	 * 			The time to set as the unit's attack time.
	 * @post The new attack time of this unit is equal to the given time.
	 * 			| new.getAttackTime() == time			
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
	private float attackTime = 0;
	

	
	// RESTING
	
	/**
	 * The unit starts resting.
	 * 
	 * @effect The unit starts resting.
	 * 			| startResting()
	 */
	public void rest(){
		startResting();
	}
	
	/**
	 * Checks whether the unit is resting.
	 * 
	 * @return True if and only if the unit is resting.
	 * 			| result == resting
	 */
	@Basic 
	public boolean isResting(){
		return this.resting;
	}
	
	/**
	 * The unit starts resting.
	 * 
	 * @effect If the hitpoints of the unit are not equal to the maximum hitpoints or the stamina is not equal
	 * 			to the maximum stamina, the new time resting for the unit is set to 0, the unit stops working,
	 * 			the unit is resting, the new initial hitpoints when starting to rest are set to the current hitpoints
	 * 			and the new initial stamina when starting to rest is set to the current stamina.
	 * 			| if ( (getHitpoints() != getMaxHitpointsStamina()) || 
	 * 			|	 ( (getStamina() != getMaxHitpointsStamina()) ))
	 * 			|	then (setTimeResting(0)
	 * 			|		&& stopWorking()
	 * 			|		&& new.isResting() == true
	 * 			|		&& setStartRestHitpoints(getHitpoints())
	 * 			|		&& setStartRestStamina(getStamina()))
	 * 
	 */
	@Model
	private void startResting(){
		if ( (getHitpoints() != getMaxHitpointsStamina()) || ( (getStamina() != getMaxHitpointsStamina()) )){
			setTimeResting(0);
			stopWorking();

			this.resting = true;
			setStartRestHitpoints(getHitpoints());
			setStartRestStamina(getStamina());
		}
	}
	
	/**
	 * The unit stops resting.
	 * 
	 * @effect If the unit has not rest long enough to have recovered one hitpoint,
	 * 			the new hitpoints are set to the initial hitpoints when the unit 
	 * 			began to rest, and the new stamina is set to the initial stamina 
	 * 			when the unit began to rest.
	 * 			| if (! canHaveRecoverdOneHp())
	 * 			|	then (setHitpoints(getStartRestHitpoints())
	 * @post The unit is not resting
	 * 			| new.isResting() == false
	 * @effect The new time since resting is equal to 0.
	 * 			| setTimeSinceRest(0)
	 * @effect The new time that the unit has rested is equal to a large value.
	 * 			| setTimeResting(MAX_VALUE)
	 */
	@Model
	private void stopResting(){
		if (! canHaveRecoverdOneHp()){
			setHitpoints(getStartRestHitpoints());
			setStamina(getStartRestStamina());
		}
		this.resting = false;
		setTimeSinceRest(0);
		setTimeResting(Float.MAX_VALUE);
	}
	
	/**
	 * Variable registering if the unit is resting.
	 */
	private boolean resting = false;
	
	/**
	 * Set the time since rest to the given time.
	 * 
	 * @param time
	 * 			The time since the unit has stopped resting.
	 * @post The new time since rest is equal to the given time.
	 * 			| new.getTimeSinceRest() == time
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
	private float timeSinceRest = 0;
	
	/**
	 * Set the time resting to the given time.
	 * 
	 * @param time
	 * 			The time that the unit is resting.
	 * @post The new time that the unit is resting is equal to the given time.
	 * 			| new.getTimeResting() == time
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
	 * Variable registering the time the unit is resting. (MAX_VALUE if the unit is not resting.)
	 */
	private float timeResting = Float.MAX_VALUE;
	
	/**
	 * Set the time since the last rest period (0.2sec). 
	 * 
	 * @param time
	 * 			The periodic rest time.
	 * @post The new periodic reset time is equal to the given time.
	 *			| new.getTimePeriodicRest() == time
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
	private float periodicRest = 0;
	
	/**
	 * Checks whether the unit has rested long enough to be able to have recovered one hitpoint.
	 * 
	 * @return True if and only if the time the unit is resting is greater than or
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
	 * @param hitpoints
	 * 			The hitpoints to set.
	 * @post The new initial hitpoints when the unit started resting are equal to the given hitpoints.
	 * 			| new.getStartRestHitpoints() == hitpoints
	 * 			
	 */
	@Model
	private void setStartRestHitpoints(double hitpoints){
		this.startRestHitpoints = hitpoints;
	}
	
	/**
	 * Variable registering the hitpoints on the moment the unit started resting. 
	 */
	private double startRestHitpoints = 0;
	
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
	 * @param stamina
	 * 			The stamina to set.
	 * @post The new initial stamina when the unit started resting are equal to the given stamina.
	 * 			| new.getStartRestStamina() == stamina	
	 */
	@Model
	private void setStartRestStamina(double stamina){
		this.startRestStamina = stamina;
	}
	
	/**
	 * Variable registering the stamina on the moment the unit started resting.
	 */
	private double startRestStamina = 0;

	// DEFAULT BEHAVIOUR
	
	/**
	 * Check whether the default behaviour is enabled.
	 * 
	 * @return True if and only if the default behaviour is enabled.
	 * 			| result == defaultBehaviour
	 */
	@Basic
	public boolean isDefaultBehaviourEnabled(){
		return this.defaultBehaviour;
	}
	
	/**
	 * Enable the default behaviour.
	 * 
	 * @post The new state of default behaviour is set to enabled.
	 * 			| new.defaultBehaviour == true
	 */
	public void startDefaultBehaviour(){
		defaultBehaviour = true;
	}
	
	/**
	 * Stop the default behaviour.
	 * 
	 * @post The new state of default behaviour is set to disabled.
	 * 			| new.defaultBehaviour == false
	 * @effect The unit stops working.
	 * 			| stopWorking()
	 * @effect The unit stops moving.
	 * 			| stopMoving()
	 * @effect The unit stops resting.
	 * 			| stopResting()
	 */
	public void stopDefaultBehaviour(){
		defaultBehaviour = false;
		stopWorking();
		if (isMoving) {
			int[] firstTarget = {(int)target[0], (int)target[1], (int)target[2]};
			moveTo(firstTarget);
		}
		stopResting();
	}
	
	/**
	 * Variable registering if the default behaviour is enabled.
	 */
	private boolean defaultBehaviour = false;
	
	/**
	 * Set a new default behaviour task for the unit to do.
	 * 
	 * Chance 1/3: possibleTask[0] = moveTo(randomPosition),
	 * possibleTask[1] = work, possibleTask[2] = rest
	 * @effect If the possible task is move to random position, unit moves to random position.
	 * 			| if (possibleTask[0])
	 * 			|	then moveTo(getRandomPosition)
	 * @effect If the possible task is work, the unit works.
	 * 			| if (possibleTask[1])
	 * 			|	then work()
	 * @effect	if the possible task is rest, the unit rests.
	 * 			| if (possibleTask[2])
	 * 			|	then rest()
	 */
	@Model
	private void newDefaultBehaviour(){
		switch (ConstantsUtils.random.nextInt(4)) {
			case 0: try {moveTo(positionObj.getRandomPosition());} catch (IllegalPositionException e) {} break;
			case 1: work(); break;						//Exception will never be thrown.
			case 2: rest(); break;
			case 3: attackPotentialEnemy(); break;
		}
	}
	
//	/**
//	 * Return a random cube position in the boundaries of the world.
//	 * 
//	 * @return	Return random cube position smaller than WORLD_X-1 in the x direction, WORLD_Y-1 in the y direction,
//	 * 			WORLD_Z-1 in the z direction, and greater than 0.
//	 * 			| randLoc_x == randomInt(0,WORLD_X-1)
//	 * 			| randLoc_y == randomInt(0,WORLD_Y-1)
//	 * 			| randLoc_z == randomInt(0,WORLD_Z-1)
//	 */
//	@Model
//	private int[] getRandomPosition(){
//		int[] randLoc = {random.nextInt(WORLD_X-1), random.nextInt(WORLD_Y-1), random.nextInt(WORLD_Z-1)};
//		return randLoc;
//	}
}