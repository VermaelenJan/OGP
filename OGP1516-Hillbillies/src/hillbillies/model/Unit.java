package hillbillies.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.IllegalAdjacentPositionException;
import hillbillies.model.exceptions.IllegalAdvanceTimeException;
import hillbillies.model.exceptions.IllegalAttackPosititonException;
import hillbillies.model.exceptions.IllegalFightFactionException;
import hillbillies.model.exceptions.IllegalNameException;
import hillbillies.model.exceptions.IllegalPositionException;
import hillbillies.model.exceptions.IllegalValueException;
import hillbillies.model.exceptions.IllegalWorkPositionException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

/**
 * A class of units which conduct several activities in a world.
 * 
 * @invar  The location of each unit must be a valid location for any
 *         unit.
 *       | positionObj.isValidUnitPositionDouble(getLocation())
 * @invar  The name of each unit must be a valid name for any unit.
 *       | isValidName(getName())
 * @invar  The weight of each unit must be greater than or equal to the units strength plus agility,
 * 		   divided by two, and smaller than MAX_VAL for any unit.
 *       | getWeight() >= (getAgility()+getStrength)/2 && (getWeight() <= MAX_VAL)
 * @invar  The strength of each unit must be between MIN_VAL and MAX_VAL,inclusively, for any unit.
 * 		 | (getStrength() >= MIN_VAL) && (getStrength() <= MAX_VAL)
 * @invar  The agility of each unit must be between MIN_VAL and MAX_VAL,inclusively, for any unit.
 * 		 | (getAgility() >= MIN_VAL) && (getAgility() <= MAX_VAL) 
 * @invar  The toughness of each unit must be between MIN_VAL and MAX_VAL,inclusively, for any unit.
 * 		 | (getTougness() >= MIN_VAL) && (getToughness() <= MAX_VAL)
 * @invar  The hitpoints of each unit must be a valid hitpoints for any unit.
 *       | isValidHitpoints(getHitpoints())
 * @invar  The stamina of each unit must be a valid stamina for any unit.
 *       | isValidStamina(getStamina())
 * @invar  The orientation of each unit must be between 0 and 2*PI, for any unit. 
 *       | (getOrientation() >= 0) && (getOrientation < 2*PI)
 * @invar  The world of each unit must be effective for any unit.
 * 		 | world != null
 * @invar  The experience of each unit must be greater or equal to 0 for any unit.
 * 		 | experience >= 0
 * @invar  The faction of each unit must be effective for any unit.
 * 		 | faction != null
 *       
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 2.1
 *
 */
public class Unit {

	// CONSTRUCTORS
	
	/**
	 * Initialize this new unit with the given cubeLocation, name, weight, strength,toughness,agility,world, a default orientation of PI/2,
	 * and experience 0.
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
	 * @param world.
	 * 			The world for this new unit.
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
	 * @effect The orientation of this new unit is set to PI/2.
	 * 			| setOrientation(PI/2)
	 * @effect The given world is set as the world of this new unit.
	 * 			| setWorld(world)
	 * @effect The experience of this new unit is equal to 0.
	 * 			| setExperience(0)
	 * @throws IllegalPositionException
	 * 			The given position is not a valid position for a unit.
	 * 			| ! positionObj.IsValidUnitPositionDouble(position)
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 */
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
		getPositionObj().setLocation(location); 
		}
	
	
	/**
	 * Initialize this new unit with the given cubeLocation, name, weight, strength,toughness,agility, an empty default world, 
	 * a default orientation of PI/2 and experience 0.
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
	 * @effect The orientation of this new unit is set to PI/2.
	 * 			| setOrientation(PI/2)
	 * @effect The world for this new unit is set to a default empty world.
	 * 			| setWorld(createVoidWorld())
	 * @effect The experience of this new unit is equal to 0.
	 * 			| setExperience(0)
	 * @throws IllegalPositionException
	 * 			The given position is not a valid position for a unit.
	 * 			| ! canHaveAsPosition(position)
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 */
	public Unit(int[] CubeLocation, String name, int weight, int strength, int agility, int toughness)
															throws IllegalPositionException, IllegalNameException {
			this(CubeLocation, name, weight, strength, agility, toughness, createVoidWorld());
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
	@Raw
	private static boolean isValidName(String name){
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (areValidCharacters(name)));
	}
	
	/**
	 * Check whether all the given name's characters are valid for a name of any unit. 
	 * 
	 * @param name
	 * 		The name to check.
	 * @return True if and only if the name has letters, spaces, double quotes or single quotes as characters.
	 * 		   	| result == (for char in name
	 *						 ( char.isLetter ||  (char == ' ')  || (char == ' " ') || (char == ' ' ')))
	 */
	@Raw
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
	 * Set the weight of this unit to the given weight.
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
	@Raw
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
	
	/**
	 * Set the weight of this unit to the given weight, without maximum restrictions on the weight, when
	 * picking up an object in the world to add its weight to the units weight.
	 * 
	 * @param weight
	 * 		The new weight for this unit.
	 * @post If the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is smaller than the minimum value of the weight,
	 * 			the weight of this unit is equal to the minimum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight < MinVal)
	 * 			|		then (new.getWeight() == MinVal) 
	 * @post If the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the minimum value of the weight,
	 * 			the weight of this unit is equal to the given weight.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight > currMaxVal)
	 * 			|		then (new.getWeight() == MaxVal)
	 * @post If the given weight is smaller than the units strength plus agility, divided by 2, 
	 * 			the weight of this unit is equal to the units strength plus agility, divided by 2.
	 * 			| if (weight < (getStrength()+getAgility())/2)
	 * 			|	then (new.getWeight() == (getStrength()+getAgility())/2) 
	 */
	@Raw
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
	 * @effect If the unit does not carry an object, 
	 * 			The weight is set to the the weight of the unit, depending on the restrictions of the weight according
	 * 			to the strength, due to possible changes of the strength. 
	 * 			| if (this.carriedObject == null)
	 * 			|		then setWeight(getWeight())
	 * @effect If the unit carries an object, the weight is set to the weight of the unit plus the weight of the carried
	 * 			object, depending on the restrictions of the weight according to the strength, 
	 * 			due to possible changes of the strength. 
	 * 			| if (this.carriedObject != null)
	 *				 then setWeight(getWeight()-carriedObject.getWeight())
	 *					  setFreeWeight(getWeight()+carriedObject.getWeight())
	 */
	@Raw
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
		
		if (this.carriedObject == null) {
			setWeight(getWeight());
		}
		else {
			setWeight(getWeight()-carriedObject.getWeight());
			setFreeWeight(getWeight()+carriedObject.getWeight());
		}
	}
	
	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength = 0;
	
	// AGILITY
		
	/**
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
	 * @effect If the unit does not carry an object, 
	 * 			The weight is set to the the weight of the unit, depending on the restrictions of the weight according
	 * 			to the agility, due to possible changes of the agility. 
	 * 			| if (this.carriedObject == null)
	 * 			|		then setWeight(getWeight())
	 * @effect If the unit carries an object, the weight is set to the weight of the unit plus the weight of the carried
	 * 			object, depending on the restrictions of the weight according to the agility,
	 * 			due to possible changes of the agility. 
	 * 			| if (this.carriedObject != null)
	 *				 then setWeight(getWeight()-carriedObject.getWeight())
	 *					  setFreeWeight(getWeight()+carriedObject.getWeight())
	 */
	@Raw
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
		
		if (this.carriedObject == null) {
			setWeight(getWeight());
		}
		else {
			setWeight(getWeight()-carriedObject.getWeight());
			setFreeWeight(getWeight()+carriedObject.getWeight());
		}
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
	 * Set the toughness of this unit to the given toughness.
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
	@Raw 
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
	 * 			larger integer, if the weight does not carries an object.
	 * 		   | result == ceil((getWeight()*getToughness())/50)
	 * 		   Otherwise, the weight minus the weight of the carried object, times the toughness
	 * 		   divided by 50, rounded up to the first larger integer, if the weight carries an object.
	 * 		   | result == ceil((((getWeight()- carriedObject.getWeight())*getToughness()))/50)
	 */
	@Raw
	public int getMaxHitpointsStamina() {
		if (this.carriedObject == null) {
			return (int) (Math.ceil((double) (((double) getWeight())*((double) getToughness()))/50));
		}
		else {
			return (int) (Math.ceil((double) (((double) ((double) getWeight()- (double) carriedObject.getWeight()))*
					 																	((double) getToughness()))/50));
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
	 * Check whether the given hitpoints are valid hitpoints for the unit.
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
	@Raw
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
	@Raw
	private void setOrientation(double orientation){
		this.orientation = (((orientation%(2*Math.PI))+2*Math.PI)%(2*Math.PI));
	}
	
	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation = 0;

	
	// EXPERIENCE POINTS
	
	/**
	 * Return the experience of this unit.
	 */
	@Basic @Raw
	public int getExperience(){
		return this.experience;
	}
	
	/**
	 * Set the experience of the of this unit to the given experience.
	 * 
	 * @post If the given experience is smaller than 0, the new experience is equal to 0.
	 * 		| new.getExperience() == 0
	 * @post If the given experience is greater than 0, the new experience is equal to 
	 * 		the given experience.
	 * 		| new.getExperience() == experience
	 */
	@Raw
	private void setExperience(int experience){
		if (experience < 0){
			this.experience = 0;
		}
		else{
			this.experience = experience;
		}
	}
	
	/**
	 * Increase at random the strength or agility or toughness by 1.
	 * 
	 * @effect if situation 0 is chosen at random, the strength of this unit is increased by 1.
	 * 			| if (situation 0)
	 * 			| 	then setStrength(getStrength()+1)
	 * @effect if situation 1 is chosen at random, the agility of this unit is increased by 1.
	 * 			| if (situation 1)
	 * 			| 	then setAgility(getAgility()+1)
	 * @effect if situation 2 is chosen at random, the toughness of this unit is increased by 1.
	 * 			| if (situation 2)
	 * 			| 	then setToughness(getToughness()+1)
	 */
	private void increaseRandomAttributeBy1(){
		switch (ConstantsUtils.random.nextInt(3)) {
		case 0: setStrength(getStrength() + 1);break;
		case 1: setAgility(getAgility()+ 1); break;						
		case 2: setToughness(getToughness()+ 1); break;
		}
	}
	
	/**
	 * Increase a random attribute for every 10 experience points a unit gains.
	 * 
	 * @param experience
	 * 		The experience the unit has gain.
	 *
	 * @effect For every 10 experience of the given experience points, a random attribute 
	 *		(i.e strength,agility or toughness) will be increased by 1.
	 *			| for (nbTimes10Exp)
	 *			|	then increaseRandomAttributeBy1()
	 */
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
	
	/**
	 * Update the experience points of the unit.
	 * 
	 * @param experience
	 * 		The extra experience points for this unit.
	 * @effect The experience of this unit is incremented with the given experience.
	 * 		| setExperience(getExperience() + experience);
	 * @effect Increase a random attribute for every 10 experience points a unit gains.
	 * 		| increaseRandomAttributes(experience)
	 */
	private void updateExperience(int experience){
		setExperience(getExperience() + experience);
		increaseRandomAttributes(experience);
	}
	
	/**
	 * Return the modulo 10 experience of the units experience.
	 */
	private int getMod10Exp(){
		return this.mod10Exp;
	}
	
	/**
	 * Set the modulo 10 experience to the given experience.
	 * 
	 * @param mod10Exp
	 * 		The new modulo 10 experience for this unit.
	 * @post The new modulo 10 experience is equal to the given experience.
	 * 		| new.getMod10Exp() == mod10Exp
	 */
	private void setMod10Exp(int mod10Exp){
		this.mod10Exp = mod10Exp;
	}

	/**
	 * Variable registering the modulo 10 experience of this unit.
	 */
	private int mod10Exp;
	
	/**
	 * Variable registering the experience of this unit.
	 */
	private int experience;
	
	
	// WORLD

	/**
	 * Return the world of this unit.
	 */
	@Basic @Raw
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Check whether this unit can have the given world as its world.
	 * 
	 * @return True if and only if the given world is effective.
	 * 		| result = this.world != null
	 */
	@Raw
	protected boolean hasWorld(){
		return (this.world!=null);
	}
	
	/**
	 * Set the world of this unit to the given world.
	 * 
	 * @param world
	 * 		The new world for this unit.
	 * @post If the unit has a world, the new world for this unit is the given world, the given 
	 * 		world has this unit as one of its units and the position of the unit in the old world 
	 * 		is set as the new position in the new world if possible, otherwise random position is chosen.
	 * 		| if (hasworld())
	 * 		|	then new.getWorld() == world
	 * 		|		 world.hasUnit(this)
	 * 		|		if (isValidLocation(previousLocation)
	 * 		|				then setLocation(previousLocation)
	 * 		|		if (!isValidLocation(priviousLocation)
	 * 		|				then setLocation(getRandomlocation)
	 * @post If the unit has not a world, the new world for this unit is the given world,
	 * 		and the given world has this unit as one of its units.
	 * 		| if (!hasworld())
	 * 		|	then new.getWorld() == world
	 * 				 world.hasUnit(this)
	 */
	@Raw
	public void setWorld(World world){
		if (hasWorld()) {
			double[] prevPos = getPositionObj().getLocation();
			this.world = world;
			world.addUnit(this);
			positionObj = new Position(world);
			try {
				getPositionObj().setLocation(prevPos);
			} catch (IllegalPositionException e) {
				int[] randomCube = getPositionObj().getRandomPosition();
				double[] randomPos = {randomCube[0]+0.5, randomCube[1]+0.5, randomCube[2]+0.5};
				getPositionObj().setLocation(randomPos);
			}
		}
		else {
			this.world = world;
			world.addUnit(this);
		}
	}
	
	/**
	 * Create an empty world for this unit.
	 * 
	 * @return A new 5 x 5 x 5 world with all the cubes of cube type air.
	 * 		| result == new world with for each cube in worldcubes:
	 * 		|	(cube.getCubeType() == type.AIR)
	 */
	private static World createVoidWorld() {
		DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
		int size = 5;
		Cube[][][] worldCubes = new Cube[size][size][size];
		for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
			for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
				for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
					int[] position = {xIndex, yIndex, zIndex};
					Cube cube = new Cube(position, CubeType.AIR);
					worldCubes[xIndex][yIndex][zIndex] = cube;
				}	
			}	
		}
	
			World voidWorld = new World(worldCubes, defaultTerrainChangeListener);
			return voidWorld;
		}

	/**
	 * Variable registering the world for this unit.
	 */
	private World world;

		
	// FACTION
	
	/**
	 * Return the faction of this unit.
	 */
	@Basic @Raw
	public Faction getFaction(){
		return this.faction;	
	}
	
	/**
	 * Check whether this unit can have the given faction as its faction.
	 * 
	 * @param faction
	 * 		The faction to check.
	 * @return True if and only if the faction is effective or not terminated.
	 * 		| ((faction == null) || (!faction.isTerminated()))
	 */
	@Raw
	protected boolean canHaveAsFaction(Faction faction){
		return ((faction == null) || (!faction.isTerminated()));
	}
	
	/**
	 * Set the faction of this unit to the given faction.
	 * 
	 * @param faction
	 * 		The new faction for this unit.
	 * @throws IllegalValueException
	 * 		The given number of units in the faction are greater than or equal 
	 * 		to the maximum number of units allowed in a faction.
	 * 		| (faction.getNbUnits() >= MAX_UNITS_FACTION)
	 */
	@Raw
	protected void setFaction(Faction faction) throws IllegalValueException{
		if (faction.getNbUnits() >= ConstantsUtils.MAX_UNITS_FACTION){
			throw new IllegalValueException(faction.getNbUnits());
		}
		this.faction = faction;
	}
	
	/**
	 * Variable registering the faction of this world.
	 */
	private Faction faction;
	
	
	// TERMINATE
	
	/**
	 * Check whether this unit is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}

	/**
	 * Terminate this unit.
	 * 
	 * @effect If the unit has an assigned task, the task is interrupted.
	 * 		| if (getAssignedTask() != null) 
	 * 		| 	then assignedTask.interruptTask()
	 * @effect If the unit carries an object, the unit drops the object.
	 * 		| if (this.carriedObject != null)
	 * 		|   then dropObject()
	 * @post The unit is terminated.
	 * 		| this.terminated == true
	 * @effect If this unit has a faction, the unit is removed from 
	 * 		its faction and check whether the faction
	 * 		is still effective after removal of the unit.
	 * 		| if (faction != null)
	 * 		|	then faction.removeUnit(this)
	 * 		|        faction.checkTerminate()
	 * 

	 */
	protected void terminate() {
		if (getAssignedTask() != null) {
			assignedTask.interruptTask();
		}
		
		if (this.carriedObject != null) {
			dropObject();
		}
		this.isTerminated = true;
		if (this.faction != null) { // ( == if (!(unit didnt get in world yet)))
			this.faction.removeUnit(this);
			this.faction.checkTerminate();
		}
	}
	
	/**
	 * Variable reflecting whether or not this unit is terminated.
	 */
	private boolean isTerminated;
	
	
	// LOCATION
	
	/**
	 * Return the location of this unit.
	 * 
	 * @return The location of the positionobject of this unit.
	 * 		| result == positionObj.getLocation()
	 */
	@Raw
	public double[] getLocation() {
		return getPositionObj().getLocation();
	}
	
	// CUBE
	
	/**
	 * Return the coordinates of the occupying cube of this unit.
	 * 
	 * @return The occupied cube location of the positionobject of this unit.
	 * 		| result == positionObj.getOccupiedCube()
	 */
	@Raw
	public int[] getOccupiedCube() {
		return getPositionObj().getOccupiedCube();
	}
	
	/**
	 * Return the positionobject of this unit.
	 */
	@Basic @Raw 
	public Position getPositionObj(){
		return this.positionObj;
	}

	/**
	 * Variable registering the positionobject of this unit.
	 */
	private Position positionObj;
	

	
	
	// ADVANCE TIME
	
	/**
	 * Advance the units game time with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect If the unit is not terminated and if the hitpoints are equal to or below 0, the hitpoints
	 * 		are set 0 and the unit terminates.
	 * @effect If the unit is not terminated, the time pending is set to the given dt.
	 * @effect If the unit is not terminated and not resting, advance time not resting.
	 * @effect If the unit is not terminated and is falling or the location of the positionobject of this unit is not a valid unit
	 * 		location, the unit falls and is not sprinting and advance time falling.
	 * @effect If the unit is not terminated and is falling or the location of the positionobject of this unit is not a valid unit
	 * 		location, and if the unit has an assigned task, the task is interrupted. 
	 * @effect If the unit is not terminated and if the unit is attacking, advance time attacking.
	 * @effect If the unit is not terminated and resting and its location of its positionobject is at the middle in the z direction
	 * 			of a cube, advance time resting.
	 * @effect If the unit is not terminated and working and rested enough to recover one hitpoint and at the middle in the z direction
	 * 			of a cube, advance time working.
	 * @effect If the unit is not terminated and moving, advance time moving.
	 * @effect If the unit its default behaviour is enabled, a new default behaviour is started.
	 * @effect If the unit is terminated, the unit stops moving,sprinting,working,defaultbehaviour,resting and falling.
	 * @throws IllegalAdvanceTimeException(dt)
	 * 			The given dt is not a valid advanceTime duration.
	 */
	public void advanceTime(double dt) throws IllegalAdvanceTimeException, IllegalWorkPositionException {
		if (!this.isTerminated()) {
			if (this.hitpoints <= 0) {
				setHitpoints(0);
				terminate();
			}
			
			if (! isValidAdvanceTime(dt)){
				throw new IllegalAdvanceTimeException(dt);
			}
			
			setTimePending(dt);
			
			if (! isResting()) {
				advanceTimeNotResting(dt);
			}
	
			if (isFalling() || !getPositionObj().isValidUnitPositionDouble(getPositionObj().getLocation())){
				setFalling(true); 
				if (getAssignedTask() != null) {
					getAssignedTask().interruptTask();
				}
				stopSprinting();
				advanceTimeFalling(dt);
			}
			
			else if (isAttacking()){
				advanceTimeAttacking(dt);
			}
			else if (isResting() && getPositionObj().isAtMiddleZOfCube()){ 
				advanceTimeResting(dt);
			}		

			else if (isWorking() && canHaveRecoverdOneHp()){
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

		}
	}
	
	/**
	 * Advance the time not resting with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect Increment the since the end of the last time resting with the given dt.
	 * @effect if the time since the last time resting is greater than the automatic rest time, the unit starts resting.
	 */
	private void advanceTimeNotResting(double dt) {
		setTimeSinceRest(getTimeSinceRest() + (float)dt);
		if (getTimeSinceRest() > ConstantsUtils.AUTO_REST_TIME){
			startResting();
		}
	}

	/**
	 * Advance the time falling with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 *	start cube is equal to the occupying cube.
	 * @effect If the unit is not above a cube of solid terrain, the units falls 
	 * 		to the cube below.
	 * @effect If the unit is not above a cube of solid terrain and if the cube the 
	 * 		the unit is occupying is not the same as the start cube, the hitpoints decrement
	 * 		with 10 points for every z level the unit falls.
	 * @effect If the unit is above a cube of solid terrain, and if the unit is not at the middle 
	 * 		of the cube in z direction, the unit falls to the current
	 * 		occupying cube, to the middle in z direction.
	 * @effect If the unit is above a cube of solid terrain, and if the unit is at the middle of
	 * 		the cube in z direction, the unit stops falling.
	 * @effect If the unit is above a cube of solid terrain, and if the unit is at the middle of 
	 * 		the cube in z direction, and if the unit is not at the middle of the cube, the target
	 * 		of the unit is set to the middle of the cube.
	 *
	 */
	private void advanceTimeFalling(double dt){
		
		int prevZPos = getPositionObj().getOccupiedCube()[2];

		if (!getPositionObj().isValidZPosition()){
			getPositionObj().fall(dt,getPositionObj().getCubeBelow());
			
			if (getPositionObj().getOccupiedCube()[2] != prevZPos){ 
				int nbZLvls = prevZPos - getPositionObj().getOccupiedCube()[2];
				double newHitpoints = getHitpoints()-10*nbZLvls;
				if (newHitpoints > 0 && newHitpoints < getMaxHitpointsStamina()) {
					setHitpoints(newHitpoints);
				}
				else if (newHitpoints <= 0){
					setHitpoints(0);
				}
				else {
					setHitpoints(getMaxHitpointsStamina());
				}
			}
		}
		
		else {
			if (!getPositionObj().isAtMiddleZOfCube()){
				getPositionObj().fall(dt,getPositionObj().getOccupiedCube());
			}
			else {
				setFalling(false);
				if (!getPositionObj().isAtMiddleOfCube()){
					double[] back = {getPositionObj().getOccupiedCube()[0]+0.5, getPositionObj().getOccupiedCube()[1]+0.5, getPositionObj().getOccupiedCube()[2]+0.5,};
					if (!Arrays.equals(getPositionObj().getLocation(), back)) {
						this.target = back;
					}
				}
			}
		}
	}
	
	/**
	 * Advance the time attacking with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect The unit stops working.
	 * @effect The unit stops resting.
	 * @effect If the time attacking is smaller or equal to 0,
	 * 		the attack is set to 0.
	 * @effect If the time attacking is smaller or equal to 0,
	 * 		and if the unit has an assigned task, the current activity of 
	 * 		the task is finished.
	 */
	private void advanceTimeAttacking(double dt) {
		stopWorking();
		stopResting();
					
		setAttackTime(getAttackTime() - (float)(dt));
		if (getAttackTime()<= 0){
			setAttackTime(0);
			if (getAssignedTask() != null) {
				getAssignedTask().finishedLastActivity();
			}
		}
	}
	
	/**
	 * Advance the time attacking with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect The periodic time rested increments with the given time step dt.
	 * @effect The time rested increments with the given time step dt.
	 * @effect The new hitpoints of the unit are equal to the previous hitpoints.
	 * 			plus his toughness divided by 200, for each 0,2 seconds, until the unit reaches its maximum
	 * 			amount of hitpoints.
	 * @effect If the hitpoints of the unit are equal to its maximum hitpoints,
	 * 			the new stamina of the unit is equal to the previous stamina of the unit, plus his toughness
	 * 			divided by 100, for each 0,2 seconds, until the unit reaches its maximum amount of stamina.
	 * @effect If the hitpoints of the unit are equal to its maximum hitpoints,
	 * 			and the stamina of the unit is equal its maximum stamina, the unit stops resting.
	 */
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
	 
	/**
	 * Advance the time working with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect The time time remainder to work is decremented with the given time step dt.
	 * 
	 * When the time remainder reaches zero:
	 * 
	 * @effect If the unit is carrying an object and the worktarget is passable terrain, 
	 * 		the unit drops the object at the worktarget, the experience is updated with 10.
	 * @effect If the unit is carrying an object and the worktarget is passable terrain, and 
	 * 		if the unit has an assigned task, the current activity of the task is finished.
	 * @effect If the worktarget is a workshop and if there is a boulder at the worktarget and 
	 * 		a log at the worktarget, the unit improves its equipment with the boulder and the log
	 * 		and the experience is updated with 10.
	 * @effect If the worktarget is a workshop and if there is a boulder at the worktarget and 
	 * 		a log at the worktarget, and if the unit has an assigned task, the current activity of 
	 * 		the task is finished.
	 * @effect If there is a boulder at the worktarget, the unit picks up the boulder and updates
	 * 		its experience with 10.
	 * @effect If there is a boulder at the worktarget and if the unit has an assigned task, the 
	 * 		last activity of the task is finished.
	 * @effect If there is a log at the worktarget, the unit picks up the log and updates
	 * 		its experience with 10.
	 * @effect If there is a log at the worktarget and if the unit has an assigned task, the 
	 * 		last activity of the task is finished.
	 * @effect If the worktarget is of wood or rock , and if the unit is not carrying an object, 
	 * 		the world caves in at the worktarget and the experience of the unit updates with 10.
	 * @effect If the worktarget is of wood or rock , and if the unit is not carrying an object, 
	 * 		and if the unit has an assigned task, the current activity of the task is finished.
	 * 
	 * @effect If the unit has an assigned task, and none of the above were effective, the task is interrupted.
	 * 
	 * @effect The time remaining to work is set to 0.
	 * @effect The unit stops working.
	 */
	private void advanceTimeWorking(double dt) throws IllegalWorkPositionException {
		setTimeRemainderToWork(getTimeRemainderToWork()-(float)dt);
		
		if (getTimeRemainderToWork() > 0) {
			return;
		}
		
		else if (this.carriedObject != null && workCube.getCubeType().isPassableTerrain()) {
			double [] currentWorkTarget = {workTarget[0]+0.5,workTarget[1]+0.5,workTarget[2]+0.5};
			dropObject(currentWorkTarget);
			updateExperience(10);
			if (getAssignedTask() != null) {
				getAssignedTask().finishedLastActivity();
			}
		}
		
		else if ( (workCube.getCubeType() == CubeType.WORKSHOP) && (world.getBoulderAtCube(workTarget) != null)
				&& (world.getLogAtCube(workTarget) != null) ) {

			Boulder currBoulder = world.getBoulderAtCube(workTarget);
			Log currLog = world.getLogAtCube(workTarget);
			if (currBoulder != null && currLog != null) {
				improveEquipment(currBoulder,currLog);
				updateExperience(10);
				if (getAssignedTask() != null) {
					getAssignedTask().finishedLastActivity();
				}
			}

		}
		
		else if (world.getBoulderAtCube(workTarget) != null) {

			Boulder currBoulder = world.getBoulderAtCube(workTarget);
			if (currBoulder != null) {
				pickUpObject(currBoulder);
				updateExperience(10);
				if (getAssignedTask() != null) {
					getAssignedTask().finishedLastActivity();
				}
			}

		}
		
		else if (world.getLogAtCube(workTarget) != null) {

			Log currLog = world.getLogAtCube(workTarget);
			if (currLog != null) {
				pickUpObject(currLog);
				updateExperience(10);
				if (getAssignedTask() != null) {
					this.assignedTask.finishedLastActivity();
				}
			}

		}

		else if (((world.getCubeType(workTarget[0],workTarget[1],workTarget[2]) == CubeType.WOOD)
				  || (world.getCubeType(workTarget[0],workTarget[1],workTarget[2]) == CubeType.ROCK))
					&& this.carriedObject == null) {
			world.caveIn(workTarget[0], workTarget[1], workTarget[2]);	
			updateExperience(10);
			if (getAssignedTask() != null) {
				this.assignedTask.finishedLastActivity();
			}

		}
		
		else {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
		}
		
		setTimeRemainderToWork(0);
		stopWorking();
		
	}
	
	/**
	 * Advance the time moving with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect If the unit is arrived at the target, advance time moving arrived.
	 * @effect If the unit is not arrived at the target, advance time moving not arrived
	 * 		with the given time step dt.
	 */
	private void advanceTimeMoving(double dt) {
		if (arrived(dt)){
			advanceTimeMovingArrived();
		}
		else{
			advanceTimeMovingNotArrived(dt);
		}
	}
	
	/**
	 * The unit arrives at its target.
	 * 
	 * @effect The unit stops moving.
	 * @effect The unit updates its experience with 1 points.
	 * @effect If the unit has a global target to move to, the unit moves
	 * 		to its global target.
	 * @effect If the unit has reached its global target, and the unit has an 
	 * 		assigned task, the current activity of the task is finished.
	 */
	private void advanceTimeMovingArrived() {
		stopMoving();
		try {
			getPositionObj().setLocation(target);
		} catch (IllegalPositionException e) {} //Exception will never be thrown.
		
		updateExperience(1);
		
		if (!(globalTarget == null) &&
				!((getPositionObj().getOccupiedCube()[0] == globalTarget[0]) &&
				(getPositionObj().getOccupiedCube()[1] == globalTarget[1]) &&
				(getPositionObj().getOccupiedCube()[2] == globalTarget[2]) ) ) {
			try {
				interruptRWPermission = true;
				moveTo(globalTarget);
				interruptRWPermission = false;
				
			} catch (IllegalPositionException e) {} //Exception will never be thrown.
		}
		
		if (Arrays.equals(globalTarget, getOccupiedCube()) && getAssignedTask() != null) {
			this.assignedTask.finishedLastActivity();
		}
	}
	
	/**
	 * Advance the time moving with the given time step dt.
	 * 
	 * @param dt
	 *		The time step which the game time is advanced.
	 * @effect The new location of the unit is equal to the previous 
	 * 		location of the unit plus its speed multiplied by dt in x, y and z direction.
	 * @effect If the unit is sprinting, the new stamina of the unit is equal to the 
	 * 		previous stamina of the unit minus 10 times dt.
	 * @effect If the unit is sprinting and the stamina drops below 0, the new stamina
	 * 		is equal to 0 and the unit stops sprinting.
	 * @effect If the unit its defaultBehaviour is enabled, the unit starts or stops sprinting
	 * 			with a chance of dt/10.
	 * @effect The unit its orientation will be updated to the direction the
	 * 			unit is moving in.
	 */
	private void advanceTimeMovingNotArrived(double dt) {
		double[] newLoc = {getPositionObj().getLocation()[0]+ this.getCurrentSpeed()[0]*dt,
				getPositionObj().getLocation()[1]+ this.getCurrentSpeed()[1]*dt,
				getPositionObj().getLocation()[2]+ this.getCurrentSpeed()[2]*dt};
		try {
			getPositionObj().setLocation(newLoc);
		} catch (IllegalPositionException e) {
			if (!getPositionObj().isAtMiddleOfCube()){
				double[] back = {getPositionObj().getOccupiedCube()[0]+0.5, getPositionObj().getOccupiedCube()[1]+0.5, getPositionObj().getOccupiedCube()[2]+0.5,};
				if (!Arrays.equals(getPositionObj().getLocation(), back)) {
					this.target = back;
				}
			}

		}
		
		if (isSprinting()) {
			double newStamina = getStamina() - dt*10;
			if (0 < newStamina && newStamina < getMaxHitpointsStamina()){
				setStamina(newStamina);
			}
			else if (newStamina <= 0) {
				setStamina(0);
				stopSprinting();
			}
			else {
				setStamina(getMaxHitpointsStamina());
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
	
	/**
	 * Checks whether the duration can have the given dt as its value.
	 * 
	 * @param dt
	 * 		The duration which the game time is advanced.
	 * @return True if and only if dt is greater or equal to 0 and smaller or equal to 0.2.
	 * 		| result == ((dt >=0)&&(dt <= 0.2))
	 */
	private static boolean isValidAdvanceTime(double dt){
		return ((dt >=0)&&(dt <= 0.2));
	}
	
	
	/**
	 * Set the time pending to the given time.
	 * 
	 * @param time
	 * 		The time to set for the pending time.
	 * @post The new pending time is equal to the given time.
	 * 		| new.getTimePending() == time
	 */
	private void setTimePending(double time) {
		this.timeToPend = time;
	}

	/**
	 * Return the time pending.
	 * 
	 * @return the time pending.
	 * 		| result == timeToPend
	 */
	@Basic
	private double getTimePending() {
		return this.timeToPend;
	}
	
	/**
	 * Variable registering the time to pend.
	 */
	private double timeToPend;
	
	/**
	 * Start a new pending time.
	 * 
	 * @effect If the time pending is greater than zero, the new time pending is set to the 
	 * 		old time pending minus the the pend_time (0.001) and the current activity of the
	 * 		assigned task is finished.
	 * 		| if (getTimePending() > 0)
	 * 		| 	then setTimePending(getTimePending()-ConstantsUtils.PEND_TIME)
	 * 		|		 getAssignedTask().finishedLastActivity()
	 * @effect If the time pending is greater than zero and if this unit has an assigned task,
	 * 		the next activity of the assigned task is executed.
	 * 		| if (getTimePending() > 0)
	 * 		|   if (getAssignedTask() != null)
	 * 		|		then getAssignedTask.executeTask()
	 */
	public void startNewPending() {

		if (getTimePending() > 0){
			setTimePending(getTimePending()-ConstantsUtils.PEND_TIME);
			getAssignedTask().finishedLastActivity();
			if (getAssignedTask() != null) {
				getAssignedTask().executeTask();
			}
		}
	}
	
	
	// SPEED
	
	/**
	 * Return the base speed of this unit.
	 * 
	 * @return 1.5 times the strength plus agility of the unit, divided by 200 times the weight ,
	 * 		   divided by 100.
	 * 		   | result == 1.5*(getStrength()+getAgility())/(200*getWeight()/100)
	 */
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
	private double getWalkingSpeed(double targetZ){
		if (getPositionObj().getLocation()[2]-targetZ < 0){
			return 0.5*getBaseSpeed();
		}
		else if (getPositionObj().getLocation()[2]- targetZ > 0){
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
	private double[] getCurrentSpeed() {
		double distance = getDistanceToTarget();

		if (isSprinting()){
			velocity = getWalkingSpeed(target[2])*2;
		}
		
		else{
			velocity = getWalkingSpeed(target[2]);
		}
		
		double[] currentSpeed= {velocity*(target[0]-getPositionObj().getLocation()[0])/distance, 
								velocity*(target[1]-getPositionObj().getLocation()[1])/distance, 
								velocity*(target[2]-getPositionObj().getLocation()[2])/distance};
		return currentSpeed;
	}
	
	/**
	 * variable referencing the velocity of this unit.
	 */
	private double velocity = 0;
	
	/**
	 * Return the magnitude of the units speed.
	 * 
	 * @return The falling speed, if the unit is falling.
	 * 		| if (isFalling())
	 * 		|	then result == FALLING_SPEED
	 * @return 0, if the unit is moving and working or if the unit is 
	 * 		moving and resting.
	 * 		| if (isMoving() && (isWorking() || isResting()))
	 * 		|	then result == 0
	 * @return the current magnitude of the speed while moving towards a target.
	 * 		| result == getCurrentSpeedMag()
	 */
	public double getCurrentSpeedMagShow() {
		if (isFalling()) {
			return ConstantsUtils.FALLING_SPEED;
		}
		if (isMoving() && (isWorking() || isResting())) {
			return 0;
		}
		return getCurrentSpeedMag();
	}
	
	/**
	 * Returns the magnitude of the units speed while moving towards a target.
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

	
	// SPRINTING 
	
	/**
	 * Enable sprinting of this unit.
	 * 
	 * @post	The new state of sprinting of this unit is true.
	 * 			| new.isSprinting() == true
	 */
	public void startSprinting(){
		if (! isFalling()) {
			this.sprinting = true;
		}
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
	@Basic
	public boolean isSprinting(){
		return sprinting;
	}
	
	/**
	 * Variable registering whether or not this unit is sprinting.
	 */
	private boolean sprinting = false;
	

	// MOVING
	
	/**
	 * Checks whether the unit is moving.
	 * 
	 * @return True if and only if the unit is moving and the unit is not, at the middle of a cube
	 * 		and working, and the unit is not, at the middle of a cube in z direction and resting,
	 * 		or the unit is falling.
	 * 		| result == (((isMoving() && !(positionObj.isAtMiddleOfCube() && isWorking())) &&
	 * 		| !(positionObj.isAtMiddleZOfCube() && isResting())) || isFalling())
	 * 
	 */
	public boolean isActualMoving(){
		return (((isMoving() && !(getPositionObj().isAtMiddleOfCube() && isWorking())) && 
				!(getPositionObj().isAtMiddleZOfCube() && isResting())) || isFalling());
	}
	
	/**
	 * Enable the movement of this unit.
	 * 
	 * @effect If the unit is resting and has rested long enough to have recovered one hitpoint
	 * 		and the unit is not interupted by resting or working during a movement to a global target,
	 * 		the units stops resting.
	 * 		| if ( !interruptRWPermission && isResting() && (canHaveRecoverdOneHp()))
	 * 		|	then stopResting()
	 * @effect The unit stops working.
	 * 		| stopWorking()
	 * @effect If the unit is not attacking, the unit starts moving.
	 * 		| if (!isAttacking())
	 * 		|	then new.isMoving == true.
	 */
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
	 * 		| new.isMoving == false				
	 */
	private void stopMoving(){
		this.isMoving = false;
	}
	
	
	/**
	 * Checks whether the unit is (globally) moving, possibly temporary interrupted.
	 * 
	 * @return True if and only if the unit is moving.
	 * 		| result == isMoving			
	 */
	@Basic
	private boolean isMoving(){
		return isMoving;
	}
	
	/**
	 * Variable registering whether the unit is moving.
	 */
	private boolean isMoving = false;
	

	/**
	 * Returns the distance from the unit's current location to the target.
	 * @return 0 if there is no target.
	 * 		| if (target == null)
	 * 		|	then result == 0
	 * @return The 2-norm of the distance between the target and the current location in x y and z direction.
	 * 		| result == ((target[x]-getLocation()[x])^2+ (target[y] - getLocation()[y])^2 + 
	 * 		| (target[z]-getLocation()[z])^2)^(1/2)			 
	 */
	private double getDistanceToTarget() {
		if (target == null) {
			return 0;
		}
		double distance = Math.sqrt(Math.pow((target[0]-getPositionObj().getLocation()[0]),2)
				+ Math.pow((target[1]-getPositionObj().getLocation()[1]),2)
				+ Math.pow((target[2]-getPositionObj().getLocation()[2]),2));
		return distance;
	}
	
	/**
	 * Checks whether the unit is arrived at the target.
	 * 
	 * @param dt
	 * 	 	The time step dt which the game time is advanced.
	 * @return True if and only if the distance to the target is smaller than the duration dt times the magnitude of
	 * 		the current speed.
	 * 		| result == (getDistanceToTarget() < dt*getCurrentSpeedMag())
	 */
	private boolean arrived(double dt){
		return (getDistanceToTarget() <= dt*getCurrentSpeedMag());	
	}

	/**
	 * Move to the adjacent cube.
	 * 
	 * @param dx
	 * 		The movement of the unit in the x direction.
	 * @param dy
	 * 		The movement of the unit in the y direction. 
	 * @param dz
	 * 		The movement of the unit in the z direction.
	 * @effect The unit moves to an adjacent cube with movements dx, dy and dz and the boolean
	 * 		calledByTo on false, which means this function is not called by the function moveTo,
	 * 		but only a single moveToAdjacent request.
	 * 		| moveToAdjacent(dx, dy, dz, false)
	 * @throws IllegalPositionException(currentTarget)
	 * 		The currentTarget (location after the moveToAdjacent) is not a valid location.
	 * 		| ! positionObj.isValidUnitPosition(currentTarget)
	 * @throws IllegalAdjacentPositionException(dx,dy,dz)
	 * 		The given dx,dy and dz movement is not a valid moveToAdjacent movement.
	 * 		| ! isValidAdjacentMovement(dx,dy,dz)
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalPositionException , IllegalAdjacentPositionException {
		moveToAdjacent(dx, dy, dz, false);
	}

	/**
	 * Move to the adjacent cube.
	 * 
	 * @param dx
	 * 		The movement of the unit in the x direction.
	 * @param dy
	 * 		The movement of the unit in the y direction. 
	 * @param dz
	 * 		The movement of the unit in the z direction.
	 * @param calledBy_moveTo
	 * 		Boolean for defining the function called by the function MoveTo.
	 * @effect If the given dx, dy and dz movement is not a valid move to adjacent movement, 
	 * 		and if unit has an assigned task, the assigned task is interrupted.
	 * 		|  if (! isValidAdjacentMovement(dx,dy,dz))
	 * 		|    if (assignedTask != null)
	 * 		|      then  assignedTask.interruptTask()
	 * @effect If the unit is moving and the unit has rested long enough to have recovered one hitpoint
	 * 		and the unit is not interupted by resting or working during a movement to a global target,
	 * 		the units stops resting and stops working.
	 * 		| if (isMoving() && !interruptRWPermission && canHaveRecoverdOneHp())
	 * 		|	then stopWorking()
	 * 		|	     stopResting()
	 * @effect If the unit is at the middle of a cube and the unit is not moving and the new 
	 * 		target with dx, dy and dz is an illegal position, and if the unit has an assigned
	 * 		task, the task is interrupted.
	 * 		|  if (positionObj.isAtMiddleOfCube() || !isMoving())
	 * 		| 		if (! positionObj.isValidUnitPositionDouble(currentTarget))
	 * 		|			if (assignedTask != null)
	 * 		|				then assignedTask.interruptTask()
	 * @effect If the movement in x,y and z direction is not zero,and the unit is at the middle
	 * 		of a cube and the unit is not moving, the unit starts moving towards
	 * 		the new current target, equal to the cube position of the current cube plus the dx, 
	 * 		dy and dz movement, respectively in x, y and z direction.
	 * 		|    new.currentTargetCube[x] == this.currentCube[x] + dx
	 * 		| && new.currentTargetCube[y] == this.currentCube[y] + dy
	 * 		| && new.currentTargetCube[z] == this.currentCube[z] + dz
	 * 		| startMoving()				 	
	 * @post If the boolean calledBy_moveTo is false, the new global target cube is equal to 
	 * 		the current target.
	 * 		| globalTarget == {currentCube[0] + dx, currentCube[1]+ dy, currentCube[2] + dz}
	 * 		Note: If the boolean calledBy_moveTo is true, the globalTarget remains unchanged.
	 * 		The global target is the position of the middle of the cube, where the unit would
	 * 		move to after the dx,dy and dz movement.
	 * @throws IllegalPositionException(currentTarget)
	 * 		The currentTarget (location after the moveToAdjacent) is not a valid location.
	 * 		| ! positionObj.isValidUnitPosition(currentTarget)
	 * @throws IllegalAdjacentPositionException(dx,dy,dz)
	 * 		The given dx,dy and dz movement is not a valid moveToAdjacent movement.
	 * 		| ! isValidAdjacentMovement(dx,dy,dz)
	 * 			
	 */
	private void moveToAdjacent(int dx,int dy,int dz, boolean calledBy_moveTo) throws IllegalPositionException,
																					IllegalAdjacentPositionException{
		if (! isValidAdjacentMovement(dx,dy,dz)){
			if (assignedTask != null) {
				assignedTask.interruptTask();
			}
			throw new IllegalAdjacentPositionException(dx,dy,dz);
		}
		
		if (isMoving() && !interruptRWPermission && canHaveRecoverdOneHp()){
			stopWorking();
			stopResting();
		}
		
		if (getPositionObj().isAtMiddleOfCube() || !isMoving()) {
			int[] currentCube = getPositionObj().getOccupiedCube();
			double[] currentTarget = {	(double)(currentCube[0]+ dx + ConstantsUtils.CUBE_LENGTH/2), 
										(double)(currentCube[1]+ dy + ConstantsUtils.CUBE_LENGTH/2),
										(double)(currentCube[2]+ dz + ConstantsUtils.CUBE_LENGTH/2)};

			
			if (! getPositionObj().isValidUnitPositionDouble(currentTarget)){	
				if (getAssignedTask() != null) {
					getAssignedTask().interruptTask();
				}
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
	 * 		The movement of the unit in the x direction.
	 * @param dy
	 * 		The movement of the unit in the y direction. 
	 * @param dz
	 * 		The movement of the unit in the z direction.
	 * @return True if and only if the movement is 0 or 1 or -1,
	 * 		for dx, dy and dz.
	 * 		| result == (((dx == 0) || (dx == 1) || (dx == -1))
	 * 		|         && ((dy == 0) || (dy == 1) || (dy == -1))
	 * 		|         && ((dz == 0) || (dz == 1) || (dz == -1)))
	 */
	private boolean isValidAdjacentMovement(int dx, int dy, int dz){
		return (((dx == 0) || (dx == 1) || (dx == -1)) && 
				((dy == 0) || (dy == 1) || (dy == -1))&& 
				((dz == 0) || (dz == 1) || (dz == -1)));
	}
	

	/**
	 * Search next possible cubes to move to.
	 * 
	 * @param location
	 * 		The location to check.
	 * @param n_0
	 * 		The level of pathing of the given location.
	 * @effect for all the neigbouring cubes of the given location, if the is passable terrain
	 * 		and if the cube is a valid unit location en if the cube is not already in the queue 
	 * 		of pathing, the cube is added to the queue of pathing, with correspending level of 
	 * 		pathing (value of the key in the map), the given level of pathing n_0, plus 1.
	 * 		| for each cube in positionObj.getNeighbouringCubes(location) :
	 * 		|	if 	(cube.getCubeType().isPassableTerrain() &&
	 * 		|		positionObj.isValidUnitPositionInt(cubeLoc) && !queue.containsKey(cube))
	 * 		| 		then queue.put(cube,n_0+1)
	 */
	private void search(int[] location, int n_0){
		for (Cube cube : getPositionObj().getNeighbouringCubes(location)){
			int[] cubeLoc = cube.getCubePosition();
			if (cube.getCubeType().isPassableTerrain() &&
					getPositionObj().isValidUnitPositionInt(cubeLoc) &&
						!queue.containsKey(cube)){
				queue.put(cube,n_0+1);
			}
		}
	}

	/**
	 * Move to the given end target.
	 * 
	 * @param endTarget
	 * 		The end target to move to.
	 * @effect If the unit is falling and if the unit has an assigned task, the assigned
	 * 		task is interrupted.
	 * 		| if (isFalling())
	 * 		|    if (getAssignedTask() != null)
	 * 		|      then getAssignedTask().interruptTask()
	 * @effect If the given endTarget is not passable terrain and if the unit has an assigned 
	 * 		task, the assigned task is interrupted.
	 * 		|  if (!world.getCubeType(endTarget[0], endTarget[1], endTarget[2]).isPassableTerrain())
	 * 		|    if (getAssignedTask() != null)
	 * 		|       then getAssignedTask().interruptTask()
	 * @note: The unit will not start moving if the unit is currently falling, neither if
	 * 			endTarget equals the current cube.
	 * @effect If the current location of the unit is not equal to the target, and the unit has 
	 * 		an assigned task, a new pending is started.
	 * 		| if (endTarget == currentCubeLocation)
	 * 		| 	if (getAssignedTask() != null)
	 * 		| 		then startNewPending()	
	 * @effect The global target is set to the endtarget.
	 * 		| globalTarget == endTarget
	 * @post The cube of the position of the endTarget is put in the map queue, with as value
	 * 		the level of the pathing which is 0.
	 * 		| queue.put(world.getCube(endTarget[0], endTarget[1], endTarget[2]),0 )
	 * @effect As long as the queue does not contains the current cube of the positionobject
	 * 		of this unit, and the queue is still getting bigger each step, search for new possible
	 * 		cube on the cubes in the queue that have the current level of pathing as their value.
	 * 		| while (!queue.containsKey(currentCube) && expandingQueue) : 
	 * 		|	then 
	 * 		|		for (cube in queue) : 
	 * 		|			if (cube.getValue() == currentLvl)
	 * 		|				then search(cube.getKey().getCubePosition(),currentLvl)
	 * @post If the queue contains the cube of the current location of this unit, the next cube
	 * 		for the path is equal to a neighbouring cube of the current location, with the smallest
	 * 		level of pathing.
	 * 		| for each cube in positionobj.getneighbouring(currentcube) : 
	 * 		|	if (queue.containsKey(cube) && queue.get(cube) <= currentLvl)
	 * 		|		then nextcube == cube
	 * @effect If the queue contains the cube of the current location of this unit, 
	 * 		and if the nextcube is effective, the unit will move to this next cube.
	 * 		| if ((queue.containsKey(cube) && (nextCube != null))
	 * 		|	then moveToAdjacent(nextcube_x-currentCube_x,nextcube_y-currentCube_y,nextcube_z-currentCube_z, true)
	 * @effect If the queue containts the cube of this current location of this unit,
	 * 		and if the nextcube is not effective, clear the queue, set the current level of the path finding
	 * 		on 0 and move again to the endtarget.
	 * 		| if ((queue.containsKey(cube) && (nextCube == null))
	 * 		| 		then queue.isEmpty()
	 * 		|			 currentLvl == 0
	 * 		|			 moveTo(EndTarget)
	 * @effect If the unit has an assigned task, the assigned task is interrupted.
	 * 		| if (getAssignedTask() != null)
	 * 		|	then getAssignedTask().interruptTask()
	 * @effect Clear the queue and set the current level of the path finding on 0.
	 * 		| queue.isEmpty()
	 * 		| currentLvl == 0
	 * @throws IllegalPositionException
	 * 		The cubetype of the target is not passible terrain.
	 * 		| !world.getCubeType(endTarget[0], endTarget[1], endTarget[2]).isPassableTerrain()
	 */
	public void moveTo(int[] endTarget) throws IllegalPositionException {
	
		if (isFalling()) {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
			return;
		}

		if (!world.getCubeType(endTarget[0], endTarget[1], endTarget[2]).isPassableTerrain()) {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
			throw new IllegalPositionException(new double[] {(double) endTarget[0]+0.5, (double) endTarget[1]+0.5, (double) endTarget[2]+0.5});
		}
				
		int[] currentCubeLoc = getPositionObj().getOccupiedCube();
		Cube currentCube = world.getCube(currentCubeLoc[0], currentCubeLoc[1], currentCubeLoc[2]);
		
		if (Arrays.equals(endTarget, currentCubeLoc)){
			if (getAssignedTask() != null) {
				startNewPending();
			}
			return;
		}
		
		globalTarget = endTarget;
					
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
			
			for (Cube cube : getPositionObj().getNeighbouringCubes(currentCubeLoc)){
				if (queue.containsKey(cube) && queue.get(cube) < currentLvl){
					currentLvl = queue.get(cube);
					nextCube = cube;
				}
			}
			if (nextCube == null) {
				for (Cube cube : getPositionObj().getNeighbouringCubes(currentCubeLoc)){
					if (queue.containsKey(cube) && queue.get(cube) == currentLvl){
						currentLvl = queue.get(cube);
						nextCube = cube;
					}
				}
			}
			
			if (nextCube != null) {
				int dx = nextCube.getCubePosition()[0]-currentCube.getCubePosition()[0];
				int dy = nextCube.getCubePosition()[1]-currentCube.getCubePosition()[1];
				int dz = nextCube.getCubePosition()[2]-currentCube.getCubePosition()[2];
				moveToAdjacent(dx,dy,dz, true);	
			}
			else {
				queue = new HashMap<Cube, Integer>();
				currentLvl = 0;
				moveTo(endTarget);
			}
		}
		else {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
		}
		
		queue = new HashMap<Cube, Integer>();
		currentLvl = 0;
	}
	
	/**
	 * Variable registering the level of pathing the unit is in.
	 */
	int currentLvl = 0;
	
	/**
	 * Variable referencing a map collecting all the cubes with the corresponding level of pathing,
	 * of the path. 
	 */
	HashMap<Cube,Integer> queue = new HashMap<Cube, Integer>();

	/**
	 * Variable registering the target to move to.
	 */
	private double[] target = null;
	
	/**
	 * Variable registering the global target to move to.
	 */
	private int[] globalTarget = null;
	
	
	// FALLING
	
	/**
	 * Set falling of this unit to the given boolean.
	 * 
	 * @post The new state of falling of this unit is equal to the given boolean.
	 *		| new.isFalling() == bool
	 */
	private void setFalling(boolean bool){
		this.isFalling = bool;
	}
	
	/**
	 * Check whether or not the unit is falling.
	 * 
	 * @return True if and only if the unit is falling.
	 * 		| result == isFalling
	 */
	@Basic
	private boolean isFalling(){
		return this.isFalling;
	}
	
	/**
	 * Variable referencing whether or not the unit is falling.
	 */
	private boolean isFalling;

	
	
	// WORKING
 
	/**
	 * The unit works at a random neighbouring cube or at its occupying cube.
	 * 
	 * @effect If the unit is falling and if the unit has an assigned task, 
	 * 		the assigned task of the unit is interrupted.
	 * 		| if (isFalling())
	 * 		|   if (getAssignedTask != null)
	 * 		| 		then getAssignedTask.interruptedTask()
	 * @effect The unit starts working at one of the neighbouring cubes if possible.
	 * 		| workAt(random Neighbouringcube.getCubePosition())
	 * @note this function is used in default behaviour.
	 * @note: The unit will not start working if the unit is currently falling.
	 */
	public void work(){
		if (isFalling()) {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();			}
			return;
		}
		List<Cube> neighbs = (getPositionObj().getNeighbouringCubesIncludingOwn(getPositionObj().getOccupiedCube()));
		try {
			workAt(neighbs.get(ConstantsUtils.random.nextInt(neighbs.size())).getCubePosition());
		} catch (IllegalPositionException e) {} //cant work here
	}
	
	/**
	 * The unit starts working at the given cube.
	 * 
	 * @param workTarget
	 * 		The cube at which the unit starts working.
	 * 
	 * @note: The unit will not start working if the unit is currently falling.
	 * @effect If the unit is falling and if the unit has an assigned task, 
	 * 		the assigned task of the unit is interrupted.
	 * 		| if (isFalling())
	 * 		|   if (getAssignedTask != null)
	 * 		| 		then getAssignedTask.interruptedTask()
	 * @effect If the workTarget is not a valid workLocation and if the unit has an assigned task, 
	 * 		the assigned task of the unit is interrupted.
	 * 		| if (!isValidWorkLocation(workTarget))
	 * 		|   if (getAssignedTask != null)
	 * 		| 		then getAssignedTask.interruptedTask()
	 * @post The new workTarget of this unit is equal to the given workTarget.
	 * 		| new.workTarget == workTarget
	 * @post The new workCube of this unit is set to the cube with the location 
	 * 		of the given workTarget.
	 * 		| new.workCube == world.getCube(workTarget[0],workTarget[1],workTarget[2])
	 * @effect The orientation of the unit is set to the center of the workCube.
	 * 		| setOrientationTo(workTarget[0]+0.5 ,workTarget[1]+0.5 ,workTarget[2]+0.5)
	 * @throws IllegalWorkPositionException
	 * 		The workTarget is not a valid work position for this unit.
	 * 		| !(isValidWorkPosition)
	 */			
	public void workAt(int[] workTarget) throws IllegalWorkPositionException{

		if (isFalling()) {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
			return;
		}
		
		if (!isValidWorkLocation(workTarget)){
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
			throw new IllegalWorkPositionException(workTarget);
		}
		
		this.workTarget = workTarget;
		
		Cube targetCube = world.getCube(workTarget[0],workTarget[1],workTarget[2]);
		
		this.workCube = targetCube;
		
		startWorking();
		
		double[] targetCenter = {workTarget[0]+0.5 ,workTarget[1]+0.5 ,workTarget[2]+0.5};
		setOrientationTo(targetCenter);
		
	}
	
	
	/**
	 * Variable registering the cube to work on for this unit.
	 */
	private Cube workCube;
	
	/**
	 * Check whether the given workTarget is a valid location for this unit to work on.
	 * 
	 * @param workTarget
	 * 		The workTarget to check.
	 * @return True if and only if the cube of the workTarget is a neighbouring cube 
	 * 		of the cube of the current location of this unit.
	 * 		| result == positionObj.isNeighBouringCube(workTarget)
	 */
	private boolean isValidWorkLocation(int[] workTarget){
		return (getPositionObj().isNeighBouringCube(workTarget));
	}

	/**
	 * The unit starts working.
	 * 
	 * @effect If the unit is resting and the unit has rested for a time at least long enough to recover
	 * 		one hitpoint, the unit stops resting, starts working and its time remainder to work is equal to 
	 * 		500 divided by the strength of the unit.
	 * 		| if ( isResting() && canHaveRecoverdOneHp() )
	 * 		|	then (new.isWorking() == true)
	 * 		|		&& (setTimeRemainderToWork(500/getStrength()))
	 * @effect If the unit is not resting, the units starts working and its time remainder to work
	 * 		is equal to 500 divided by the strength of the unit.
	 * 		| if (! isResting())
	 * 		|	then ((new.isWorking() == true)
	 * 		|		&& (setTimeRemainderToWork(500/getStrength())))
	 */
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
	 * 		| new.isWorking() == false
	 */
	private void stopWorking(){
		this.working = false;

	/**
	 * Check whether the unit is working.
	 * 
	 * @return True if and only if  the unit is working.
	 * 		| result == working
	 */
	}
	@Basic
	private boolean isWorking(){
		return working;
	}
	
	/**
	 * Check whether the unit is actually working (i.e. chopping wood or mining rock).
	 * 
	 * @return True if and only if the unit is working and the unit is not 
	 * 		actually moving and the worktype is equal to 5.
	 * 		| result == (isWorking() && !isActualMoving() && workType == 5 ) 		
	 */
	@Basic
	public boolean isWorkingShow(){ 
		return (isWorking());
	}
	
	/**
	 * Variable registering whether the unit is working.
	 */
	private boolean working = false;
	
	/**
	 * Return the time remaining to work of this unit.
	 */
	private float getTimeRemainderToWork(){
		return timeRemainderToWork;
	}
	
	/**
	 * Set the time remaining to work of this unit to the given time.
	 * 
	 * @param time
	 * 		The new time remaining to work for this unit.
	 * @post The new time remaining to work of this unit is equal to the given time.
	 *      | new.getTimeRemainderToWork() == time	
	 */
	private void setTimeRemainderToWork(float time){
		this.timeRemainderToWork = time;
	}
	
	/**
	 * Variable registering the time remaining to work.
	 */
	private float timeRemainderToWork = 0;
	
	/**
	 * Variable registering the workTarget of this unit.
	 */
	private int[] workTarget;
	
	/**
	 * Variable to check whether the movement to a global target is interrupted by
	 * resting or working.
	 */
	private boolean interruptRWPermission;

	
	// OBJECT : LOG AND BOULDER
	
	/**
	 * Check whether the unit is carrying a log.
	 * 
	 * @return True if and only if the unit is carrying a log.
	 * 		| result == this.carriedObject instanceof Log)
	 */
	public boolean isCarryingLog() {
		return (this.carriedObject instanceof Log);
	}
	
	/**
	 * Check whether the unit is carrying a boulder.
	 * 
	 * @return True if and only if the unit is carrying a boulder.
	 * 		| result == this.carriedObject instanceof Boulder)
	 */
	public boolean isCarryingBoulder() {
		return (this.carriedObject instanceof Boulder);
	}
	
	/**
	 * Improve the equipment of the unit with a boulder and a log.
	 * 
	 * @param boulder
	 * 		The boulder to improve the equipment.
	 * @param log
	 * 		The log to improve the equipment.
	 * @effect The weight of the unit is increased with the weight of the boulder
	 * 		divided by 3 plus the weight of the log divided by 3.
	 * 		| setWeight(getWeight() + boulder.getWeight()/3 + log.getWeight()/3);
	 * @effect The toughness of the unit is increased with the weight of the boulder
	 * 		divided by 4 plus the weight of the log divided by 4.
	 * 		| setWeight(getWeight() + boulder.getWeight()/3 + log.getWeight()/3);
	 * @effect The boulder is terminated.
	 * 		| boulder.terminate()
	 * @effect The log is terminated.
	 * 		| log.terminate()
	 * @post The boulder is not in the set of boulders of the world.
	 * 		| !world.boulders.contains(boulder)
	 * @post The log is not in the set of logs of the world.
	 * 		| !world.logs.contains(log)
	 */
	protected void improveEquipment(Boulder boulder, Log log){
		setWeight(getWeight() + boulder.getWeight()/3 + log.getWeight()/3);
		setToughness(getToughness() + log.getWeight()/4 + boulder.getWeight()/4);
		
		boulder.terminate();
		log.terminate();
		world.boulders.remove(boulder);
		world.logs.remove(log);
	}
	
	/**
	 * The unit picks up the object.
	 * 
	 * @param object
	 * 		The object to pick up.
	 * @post The new carried object for this unit is equal to the given object.
	 * 		| new.carriedObject == object
	 * @effect The object is terminated.
	 * 		| object.terminate()
	 * @effect The weight of this unit is increased with the weight of the object.
	 * 		| setFreeWeight(getWeight()+object.getWeight())
	 */
	protected void pickUpObject(Object object){
		this.carriedObject = object;
		object.terminate();
		setFreeWeight(getWeight()+object.getWeight());
	}

	/**
	 * The unit drops its object. 
	 * 
	 * @pre The unit carries an object.
	 * @effect The weight of the unit is decreased with the weight of the object the unit
	 * 		carried.
	 * 		| setFreeWeight(getWeight()-this.carriedObject.getWeight())
	 * @effect The carried object is unterminated.
	 * 		| this.carrieObject.revive()
	 * @effect The carried object is set to the location where the unit is.
	 * 		| this.carriedObject.positionObj.setFreeLocation(this.getLocation())
	 * @effect The carried object of this unit is removed.
	 * 		| this.carriedObject == null
	 */
	protected void dropObject(double[] target){
		setFreeWeight(getWeight()-this.carriedObject.getWeight());
		this.carriedObject.revive();
		this.carriedObject.positionObj.setFreeLocation(target);
		this.carriedObject = null;
	}
	
	/**
	 * Drop the object at the the location of this unit.
	 * 
	 * @effect Drop the object of this unit to the location of this unit.
	 * 		|  dropObject(this.getLocation())
	 */
	protected void dropObject(){
		dropObject(this.getLocation());
	}
	
	/**
	 * Variable registering the object of this unit.
	 */
	private Object carriedObject;
	
	
	// FIGHTING
	
	/**
	 * Attack an other unit.
	 * 
	 * @param other
	 * 		 	Unit to attack.
	 * @note The unit will never attack whilst falling.
	 * @effect If the unit is falling and if the unit has an assigned task, 
	 * 		the assigned task of the unit is interrupted.
	 * 		| if (isFalling())
	 * 		|   if (getAssignedTask != null)
	 * 		| 		then getAssignedTask.interruptedTask()
	 * @effect If the unit is not equal to the unit to attack and if the unit to attack
	 * 		is not terminated and if the other unit is on a valid attack position for this
	 * 		unit, and if this unit has an assigned task, the assigned task is interrupted.
	 * 		| if ((this != other) &&  (!other.isTerminated()))
	 *		|    if (!this.isValidAttackPosition(other.positionObj.getOccupiedCube()))
	 * 		|   	if (getAssignedTask != null)
	 * 		| 			then getAssignedTask.interruptedTask()
	 * @effect If the unit is not equal to the unit to attack and if the unit to attack
	 * 		is not terminated and if the unit to attack is of the same faction 
	 * 		as this unit, and if this unit has an assigned task, the assigned task is interrupted.
	 * 		| if ((this != other) &&  (!other.isTerminated()))
	 *		|    if (this.getFaction() == other.getFaction())
	 * 		|   	if (getAssignedTask != null)
	 * 		| 			then getAssignedTask.interruptedTask()
	 * @effect If the unit is not equal to the unit to attack and if the unit to attack
	 * 		is not terminated and if the unit is resting, the unit stops resting.
	 * 		| if ((this != other) &&  (!other.isTerminated()))
	 * 		|	if (this.isResting())
	 * 		|		then this.stopResting()
	 * @effect If the unit is not equal to the unit to attack and if the unit to attack
	 * 		is not terminated, the unit stops working.
	 * 		| if ((this != other) &&  (!other.isTerminated()))
	 * 		|	then this.stopWorking()
	 * @effect If the unit is not equal to the unit to attack, and if the unit to attack
	 * 		is not terminated, 
	 * 		the new orientation is set to the to the orientation in fight.
	 * 		| if ((this != other) &&  (!other.isTerminated()))
	 * 		|	then this.setOrientationInFight(other)
	 * @effect If the unit is not equal to the unit to attack, and if the unit to attack
	 * 		is not terminated,
	 * 		the new attack time of this unit is equal to 1 second.
	 * 		| if ((this != other) &&  (!other.isTerminated()))
	 * 		|	then this.setAttackTime(1)
	 * @throws IllegalAttackPosititonException(other.getOccupiedCube())
	 * 		The unit cannot attack the given other unit.
	 * 		| ! this.isValidAttackPosition(other.getOccupiedCube())
	 * @throws IllegalFightFactionException(this.getFaction)
	 * 		The unit cannot attack the given other unit.
	 * 		| this.getFaction() == other.getFaction() 			
	 */
	public void attack(Unit other) throws IllegalFightFactionException, IllegalAttackPosititonException {
		if (isFalling) {
			if (getAssignedTask() != null) {
				getAssignedTask().interruptTask();
			}
			return;
		}
		if ( (this != other) && (!other.isTerminated()) ) {
			if (!this.isValidAttackPosition(other.getPositionObj().getOccupiedCube())) {
				if (getAssignedTask() != null) {
					getAssignedTask().interruptTask();
				}
				throw new IllegalAttackPosititonException(other.getPositionObj().getOccupiedCube());
			}	
			
			if (this.getFaction() == other.getFaction()){
				if (getAssignedTask() != null) {
					getAssignedTask().interruptTask();
				}
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
	 * 		The position of the cube of the unit to attack.
	 * @return True if and only if the distance between the cube of the unit 
	 * 		and the cube of the unit to attack is 0 or 1 in the x, y and z direction.
	 * 		| result ==((this.getOccupiedCube()[x] - attackCubePosition[x] <= 1)
	 * 				 &&(this.getOccupiedCube()[y] - attackCubePosition[y] <= 1)
	 * 				 &&(this.getOccupiedCube()[z] - attackCubePosition[z] <= 1))
	 */
	private boolean isValidAttackPosition(int[] attackCubePosition){
		return((Math.abs(this.getPositionObj().getOccupiedCube()[0]-attackCubePosition[0]) <=1) && 
				(Math.abs(this.getPositionObj().getOccupiedCube()[1]-attackCubePosition[1]) <=1) &&
				(Math.abs(this.getPositionObj().getOccupiedCube()[2]-attackCubePosition[2]) <=1));	
	}
	
	/**
	 * Set the orientation of the units in the fight to each other.
	 * 
	 * @param other
	 * 		The other unit in the fight.
	 * @effect Set the orientation of this unit in the direction of the other unit.
	 * 		| this.setOrientationTo(other.positionObj.getLocation())
	 * @effect Set the orientation of the other unit in the direction of this unit.
	 * 		| other.setOrientationTo(this.positionObj.getLocation())
	 */
	private void setOrientationInFight(Unit other) {
		this.setOrientationTo(other.getPositionObj().getLocation());
		other.setOrientationTo(this.getPositionObj().getLocation());
	}
	
	/**
	 * Set the orientation of this unit to the given target location.
	 * 
	 * @param target
	 * 		The target location to set the units orientation to.
	 * @effect The orientation of this unit is set in the direction of the target, 
	 * 			using the arc tangents of the distance difference in y direction of the target location and
	 * 			and the location of this unit, and the distance difference in x direction of the target location and 
	 * 			the location of this unit.
	 * 			| this.setOrientation(arctangents(target[y]-this.getLocation()[y],
	 * 			|	target[x]-this.getLocation()[x])
	 */
	private void setOrientationTo(double[] target) {
		double orientUnitThis = Math.atan2(target[1]-this.getPositionObj().getLocation()[1],
				target[0]-this.getPositionObj().getLocation()[0]);
		this.setOrientation(orientUnitThis);
	}
	
	/**
	 * Defend against an other unit.
	 * 
	 * @param other
	 * 		The unit to defend against.
	 * @effect If the faction of this unit is not equal to the faction of the other unit and
	 * 		the unit is not falling and the unit is resting, the unit stops resting.
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	if (this.isResting())
	 * 		|		then this.stopResting()
	 * @effect If the faction of this unit is not equal to the faction of the other unit and
	 * 		the unit is not falling, the unit stops working.
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	then this.stopWorking()
	 * @effect If the faction of this unit is not equal to the faction of the other unit and
	 * 		the unit is not falling, and if the unit has an assigned task, the assigned task
	 * 		is interrupted.
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	if (getAssignedTask() != null)
	 * 		|		then getAssignedTask().interruptTask()
	 *  If the faction of the unit is not equal to the faction of the other unit,
	 *  and the unit is not falling, the possibility to dodge is 0.2 times this units agility,
	 * 	divided by the other units agility.
	 * 	| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 	|	then possibilityDodge = (0.2* this.getAgility()/other.getAgility())
	 * @effect If the faction of the unit is not equal to the faction of the other unit
	 * 		and the unit is not falling, and the unit dodges successfully, the unit's location is set
	 * 		to a random location and its orientation is set in the direction of the attacking unit
	 * 		and the experience is updated with 20 points.
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	if (getPossibilitySucces(possibilityDodge))
	 * 		|		then (this.setRandomLocation && this.setOrientationInFight(other)
	 * 		|		&& updateExperience(20))
	 *	If the faction of the unit is not equal to the faction of the other unit,
	 * 	and the unit is not falling, the possibility to block is equal to 0.25 times the strength plus agility of this unit, 
	 * 	divided by the strength plus agility of the other unit.
	 * 	| possibilityBlock = 0.25*(( this.getStrength() + this.getAgility())/(other.getStrength()+other.getAgility()))
	 * @effect If the faction of the unit is not equal to the faction of the other unit,
	 * 		and the unit is not falling, and if the unit does not dodge succesfully and
	 * 		if the unit blocks succesfully, the experience is updated with 20 points.
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	if ( ! getPossibilitySucces(possibilityDodge))
	 * 		|		if ( getPossibilitySucces(possibilityBlock))
	 * 		|			then updateExperience(20)
	 * @post If the faction of the unit is not equal to the faction of the other unit,
	 * 		and the unit is not falling, and if the unit does not 
	 * 		dodge successfully, and if the unit does not block successfully,
	 * 		the new hitpoints are equal to the old hitpoints reduced with the strength
	 * 		of the other unit divided by 10.	
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	if ( ! getPossibilitySucces(possibilityDodge))
	 * 		|		if (! getPossibilitySucces(possibilityBlock))
	 * 		|			then (new.getHitpoints() = this.getHitpoints - this.getStrength()/10)
	 * @effect If the faction of the unit is not equal to the faction of the other unit,
	 * 		and the unit is not falling, and if the unit fails to dodge, and the unit fails
	 * 		to block, and if the hitpoints are less than or equal to 0, then the hitpoints are set to 0.
	 * 		| if (this != other)
	 * 		|	if ( ! getPossibilitySucces(possibilityDodge))
	 * 		|		if (! getPossibilitySucces(possibilityBlock))
	 * 		|			if (this.getHitpoints) <=0)
	 * 		|				then this.setHitpoints(0)	
	 * @post If the faction of the unit is not equal to the faction of the other unit,
	 * 		and the unit is not falling, and if the unit does not 
	 * 		dodge successfully, and if the unit does not block successfully,
	 * 		the experience of the other unit is updated with 20 points.
	 * 		of the other unit divided by 10.	
	 * 		| if (this.getFaction() != other.getFaction() && !this.isFalling())
	 * 		|	if ( ! getPossibilitySucces(possibilityDodge))
	 * 		|		if (! getPossibilitySucces(possibilityBlock))
	 * 		|			then other.updateExperience(20)
	 */
	public void defend(Unit other){
		
		if (this.getFaction() != other.getFaction() && !this.isFalling()) {
	
			if (this.isResting()) {
			this.stopResting();
			}
			
			stopWorking();
			
			if (getAssignedTask() != null) {
				assignedTask.interruptTask();
			}
			
			double possibilityDodge = (double)(0.2* (double) this.getAgility()/ (double) other.getAgility());
			if (ConstantsUtils.getPossibilitySucces(possibilityDodge)){
				this.getPositionObj().setRandomDodgedLocation();
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
					double newHitpoints = this.getHitpoints() - (double)((double)other.getStrength()/10);
					if (newHitpoints > 0 && newHitpoints < getMaxHitpointsStamina()) {
						this.setHitpoints(newHitpoints);
					}
					else if (newHitpoints <= 0) {
						this.setHitpoints(0);
					}
					else {
						this.setHitpoints(getMaxHitpointsStamina());
					}
					other.updateExperience(20);
				}
			}			
		}
	}
	
	/**
	 * Attack a potential enemy in default behaviour.
	 * 
	 * @effect for each unit in the world, for each neighbouring cube of this unit, if an other unit is on the same cube
	 * 		as a neighbouring cube of this unit, and if the faction of this unit is not equal to the faction of the
	 * 		other unit, and the other unit is not terminated, the unit attacks the other unit, and the other unit defends 
	 * 		against this unit.
	 * 		| for unit in world.getAllUnits()
	 * 		| 	for cube in positionobj.getneighbouringcube()
	 * 		|		if ((unit.getOccupiedCube() == cube.getCubePosition() && this.getFaction() !=
	 * 		|			other.getFaction && !other.isTerminated())
	 * 		|			then this.attack(other)
	 * 		|				 other.defend(this)
	 */
	private void attackPotentialEnemy(){

		for (Unit other : world.getAllUnits()){
			for (Cube cube : (getPositionObj().getNeighbouringCubesIncludingOwn(getPositionObj().getOccupiedCube()))){
				if (Arrays.equals(other.getOccupiedCube(),cube.getCubePosition()) 
						&& other.getFaction() != this.getFaction() && !other.isTerminated){
					try {
						this.attack(other);
						other.defend(this);
					} catch(IllegalFightFactionException e){}//do nothing
					return;
				}
			}
		}
	}
	
	/**
	 * Checks whether the unit is attacking.
	 * 
	 * @return True if and only if the attack time is greater than 0.
	 * 		| result == (getAttacktime() > 0)
	 */
	public boolean isAttacking(){
		return (getAttackTime() > 0);
	}
	
	/**
	 * Set the attack time to the given time.
	 * 
	 * @param time
	 * 		The time to set as the unit's attack time.
	 * @post The new attack time of this unit is equal to the given time.
	 * 		| new.getAttackTime() == time			
	 */
	private void setAttackTime(float time){
		this.attackTime = time;
	}
	
	/**
	 * Return the attack time of this unit.
	 */
	@Basic
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
	 * 		| startResting()
	 */
	public void rest(){
		startResting();
	}
	
	/**
	 * Checks whether the unit is resting.
	 * 
	 * @return True if and only if the unit is resting.
	 * 		| result == resting
	 */
	@Basic 
	public boolean isResting(){
		return this.resting;
	}
	
	/**
	 * The unit starts resting.
	 * 
	 * @effect If the hitpoints of the unit are not equal to the maximum hitpoints or the stamina is not equal
	 * 		to the maximum stamina, the new time resting for the unit is set to 0, the unit stops working.
	 * 		| if ( (getHitpoints() != getMaxHitpointsStamina()) || 
	 * 		|	 ( (getStamina() != getMaxHitpointsStamina()) ))
	 * 		|	then (setTimeResting(0)
	 * 		|		&& stopWorking()
	 * @effect If the hitpoints of the unit are not equal to the maximum hitpoints or the stamina is not equal
	 * 		to the maximum stamina,and if the unit has an assigned task, the assigned task is interrupted.
	 * 		| if ( (getHitpoints() != getMaxHitpointsStamina()) || 
	 * 		|	 ( (getStamina() != getMaxHitpointsStamina()) ))		
	 * 		|		if (getAssignedTask() != null)
	 * 		|			then getAssignedTask().interruptTask()
	 * @effect If the hitpoints of the unit are not equal to the maximum hitpoints or the stamina is not equal
	 * 		to the maximum stamina , the unit is resting, the new initial hitpoints when starting to rest
	 * 		 are set to the current hitpoints and the new initial stamina when starting to rest is set to the current stamina.
	 * 		| if ( (getHitpoints() != getMaxHitpointsStamina()) || 
	 * 		|	 ( (getStamina() != getMaxHitpointsStamina()) ))		
	 * 		|		this.resting = true 
	 * 		|       && setStartRestHitpoints(getHitpoints)
	 * 		|		&& setStartRestStamina(getStamina)
	 * 
	 */
	private void startResting(){
		if ( (getHitpoints() != getMaxHitpointsStamina()) || ( (getStamina() != getMaxHitpointsStamina()) )){
			setTimeResting(0);
			stopWorking();
			
			if (getAssignedTask() != null) {
				assignedTask.interruptTask();
			}
			
			this.resting = true;
			setStartRestHitpoints((int) getHitpoints());
			setStartRestStamina((int) getStamina());
		}
	}
	
	/**
	 * The unit stops resting.
	 * 
	 * @effect If the unit has not rest long enough to have recovered one hitpoint,
	 * 		the new hitpoints are set to the initial hitpoints when the unit 
	 * 		began to rest, and the new stamina is set to the initial stamina 
	 * 		when the unit began to rest.
	 * 		| if (! canHaveRecoverdOneHp())
	 * 		|	then (setHitpoints(getStartRestHitpoints())
	 * @post The unit is not resting
	 * 		| new.isResting() == false
	 * @effect The new time since resting is equal to 0.
	 * 			| setTimeSinceRest(0)
	 * @effect The new time that the unit has rested is equal to a large value.
	 * 			| setTimeResting(MAX_VALUE)
	 */
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
	private void setTimeSinceRest(float time){
		this.timeSinceRest = time;
	}
	
	/**
	 * Return the time since the ending of the last rest.
	 */
	@Basic
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
	private void setTimeResting(float time) {
		this.timeResting = time;
	}
	
	/**
	 * Return the time this unit is resting.
	 */
	@Basic
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
	private void setTimePeriodicRest(float time){
		this.periodicRest = time;
	}
	
	/**
	 * Return the periodic rest of this unit.
	 */
	@Basic
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
	private boolean canHaveRecoverdOneHp(){
		return (getTimeResting() >= ((double) 1/(((double) getToughness()/200.0)/0.2)));

	}
	
	/**
	 * Return the initial hitpoints on the moment the unit started resting.
	 */
	@Basic
	private int getStartRestHitpoints(){
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
	private void setStartRestHitpoints(int hitpoints){
		if (hitpoints < getMaxHitpointsStamina()){
			this.startRestHitpoints = hitpoints;

		}
		else {
			this.startRestHitpoints = getMaxHitpointsStamina();
		}
	}
	
	/**
	 * Variable registering the hitpoints on the moment the unit started resting. 
	 */
	private int startRestHitpoints = 0;
	
	/**
	 * Return the initial stamina on the moment the unit started resting.
	 */
	@Basic
	private int getStartRestStamina(){
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
	private void setStartRestStamina(int stamina){
		if (stamina < getMaxHitpointsStamina()) {
			this.startRestStamina = stamina;
		}
		else {
			this.startRestStamina = getMaxHitpointsStamina();
		}
	}
	
	/**
	 * Variable registering the stamina on the moment the unit started resting.
	 */
	private int startRestStamina = 0;

	
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
	 * @effect If the unit is moving, the unit moves to the target.
	 * 			| moveTo(target)
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
	 * Set a new default behaviour task for the unit to do.
	 * 
	 * Chance 1/4: possibleTask[0] = moveTo(randomPosition),
	 * possibleTask[1] = work, possibleTask[2] = rest, possibleTask[3] = attack potential enemy
	 * @effect If the possible task is move to random position, unit moves to random position.
	 * 			| if (possibleTask[0])
	 * 			|	then moveTo(getRandomPosition)
	 * @effect If the possible task is work, the unit works.
	 * 			| if (possibleTask[1])
	 * 			|	then work()
	 * @effect	if the possible task is rest, the unit rests.
	 * 			| if (possibleTask[2])
	 * 			|	then rest()
	 * @effect	if the possible task is attack potential enemy, the unit attacks a potential
	 * 			if possible.
	 * 			| if (possibleTask[3])
	 * 			|	then attackPotentialEnemy()
	 * @effect  If the unit has an assigned task, the next activity of the assigned task is executed.
	 * 			| if (getAssignedTask() != null)
	 * 			| 	then getAssignedTask.executeTask()
	 */
	private void newDefaultBehaviour(){

		if (getAssignedTask() == null) {
			Task newTask = faction.getScheduler().getHightestUnassignedPriorityTask();
			if (newTask != null) {
				newTask.assignTo(this);	
			}
			else {
				switch (ConstantsUtils.random.nextInt(4)) {
				case 0: try {moveTo(getPositionObj().getRandomPosition());} catch (IllegalPositionException e) {} break;
				case 1: work(); break;						//Exception will never be thrown.
				case 2: rest(); break;
				case 3: attackPotentialEnemy(); break;
				}
			}
		}
		else {
			getAssignedTask().executeTask();	
		}
	}
	
	/**
	 * Variable registering if the default behaviour is enabled.
	 */
	private boolean defaultBehaviour = false;
	
	
	/**
	 * Return the assigned task of this unit.
	 */
	@Basic
	public Task getAssignedTask() {
		return assignedTask;
	}
	
	/**
	 * Stop the task the unit is currently executing.
	 * 
	 * @effect The unit stops working.
	 * 		| stopWorking()
	 * @effect The unit stops moving.
	 * 		| stopMoving()
	 */
	protected void stopTask() {
		stopWorking();
		stopMoving();
	}

	/**
	 * Assign the given task to this unit.
	 * 
	 * @param task
	 * 		The task to assign to this unit.
	 * @effect If the unit has not yet an assigned task,
	 * 		the given task is assigned to this unit.
	 * 		| if (getAssignedTask() == null)
	 * 		| 	then setAssignedTask(task)
	 */
	protected void assignTask(Task task) {
		if (getAssignedTask() == null) {
			setAssignedTask(task);
		}
		else {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Remove the task of this unit.
	 * 
	 * @effect Set the assigned task of this unit on null.
	 * 		| setAssignedTask(null)
	 */
	protected void removeTask() {
		setAssignedTask(null);
	}
	
	/**
	 * Set the assigned task of this unit to the given task. 
	 * 
	 * @param task
	 * 		The task to set to the assigned task.
	 * @post The new assigned task of this unit is equal to the given task.
	 * 		| new.getAssignedTask() == task
	 */
	private void setAssignedTask(Task task){
		this.assignedTask = task;
	}
	

	private Task assignedTask;
}