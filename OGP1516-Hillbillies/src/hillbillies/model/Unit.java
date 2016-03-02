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
 * @invar  The weight of each unit must be a valid weight for any
 *         unit.
 *       | isValidWeight(getWeight())
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
	
	
	private double xPos;
	private double yPos;
	private double zPos;
	
	private String name;
	private int weight;
	private int strength;
	private int agility;
	private int toughness;
	private double hitpoints;
	private double stamina;	
	private double orientation;
	
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
	 * @effect the given hitpoints are set as the hitpoints of this new unit.
	 * 			| setHitpoints(hitpoints)
	 * @effect the given stamina is set as the stamina of this new unit. 		
	 * 			| setStamina(stamina)													
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
	 * 
	 * Returns the coordinates of the cube the unit is in.
	 */
	@Basic @Raw
	public int[] getOccupiedCube() {
		double[] location = this.getLocation();
		int[] position = {(int) location[0], (int) location[1], (int) location[2]};
		return(position);
	}
	
	
	
	/**
	 * 
	 * Return the name of this unit.
	 */
	@Basic @Raw 
	public String getName(){
		return this.name;
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
	 * 
	 * Return the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight(){
		return this.weight;
	}	
	
	
	/**
	 * 
	 * @param   weight
	 *          The new weight for this unit.
	 * @param   flag
	 * 			A flag to mark that the method is used in the constructor.
	 * @post	If the given flag is true,the weight is set for the first time in the constructor,
	 * 		    the current minimum value of the weight is the initial minimum value 
	 * 		    of the weight of the constructor and the current maximum value of the weight 
	 *          is the initial maximum value of the weight of the constructor.
	 *          | if (flag)
	 *				then ((new.curr_min_val == INIT_MIN_VAL) && (new.curr_max_val = INIT_MAX_VAL))
	 * @post    If the given flag is false,the weight is already set once in the constructor,
	 * 		    the current minimum value of the weight is the minimum value 
	 * 		    of the weight after the constructor and the current maximum value of the weight 
	 *          is the maximum value of the weight after the constructor.
	 *          | if (! flag)
	 *				then ((new.curr_min_val == MIN_VAL) && (new.curr_max_val = MAX_VAL))
	 * @post    if the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is smaller than the current minimum value of the weight,
	 * 			the weight of this unit is equal to the current minimum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight < curr_min_val)
	 * 			|		then (new.weight == curr_min_val) 	
	 * @post	if the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the current minimum value and smaller than
	 * 			the current maximum value, the weight of this unit is equal to the given weight.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if ((weight >= curr_min_val) && (weight <= curr_max_val))
	 * 			|		then (new.weight == weight)	
	 * @post    if the given weight is greater than or equal to the units strength plus agility, divided by 2, 
	 * 			and if the given weight is greater than the current maximum value of the weight,
	 * 			the weight of this unit is equal to the current maximum value.
	 * 			| if (weight >= (getStrength()+getAgility())/2)
	 * 			|	if (weight > curr_max_val)
	 * 			|		then (new.weight == curr_max_val) 
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
	 * 
	 * @param	weight
	 *	     	The new weight for this unit.
	 * @effect	The weight of the unit is set with the flag on false.
	 * 			| setWeight(weight,false)
	 */
	@Raw
	public void setWeight(int weight) {
		setWeight(weight, false);
	}
	

	
	/**
	 * 
	 * @return
	 */
	
	@Basic 
	public int getStrength(){
		return this.strength;
	}
	
	/**
	 * 
	 * @param strength
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
	 * 
	 * @param strength
	 */
	
	@Raw
	public void setStrength(int strength) {
		setStrength(strength, false);
	}
	
	/**
	 * 
	 * @param agility
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
	 * 
	 * @param agility
	 */
	
	@Raw
	public void setAgility(int agility) {
		setAgility(agility, false);
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Basic
	public int getAgility(){
		return this.agility;
	}
	
	/**
	 * 
	 * @param toughness
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
	 * 
	 * @param toughness
	 */
	
	@Raw
	public void setToughness(int toughness) {
		setToughness(toughness, false);
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Basic @Raw
	public int getToughness(){
		return this.toughness;
	}


	/**
	 * 
	 * @return
	 */
	
	@Basic @Raw
	public int getMaxHitpointsStamina() {
		 return (int) Math.ceil((((double) getWeight())*((double) getToughness()))/50);
	}
	
	/**
	 * 
	 * @param hitpoints
	 * @return
	 */
	
	@Raw 
	private boolean hasValidHitpoints(double hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param stamina
	 * @return
	 */
	
	@Raw
	private boolean hasValidStamina(double stamina){
		return ((stamina >= 0) && (stamina <= getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param hitpoints
	 * 
	 * @pre
	 */
	
	@Raw
	private void setHitpoints(double hitpoints){
		assert hasValidHitpoints(hitpoints);
		this.hitpoints = hitpoints;
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Basic  
	public double getHitpoints(){
		return this.hitpoints;
	}
	
	/**
	 * 
	 * @param stamina
	 * 
	 * @pre
	 */
	
	@Raw @Model
	private void setStamina(double stamina){
		assert hasValidStamina(stamina);
		this.stamina = stamina;
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Basic 
	public double getStamina(){
		return this.stamina;
	}
	
	/**
	 * 
	 * @param orientation
	 */
	
	@Raw @Model
	private void setOrientation(double orientation){
		this.orientation = orientation%(2*Math.PI);
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Basic 
	public double getOrientation(){
		return this.orientation;
	}
	
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
				
				int nb_times_period = (int)Math.floor((double)(getTimePeriodicRest()/0.2));
				setTimePeriodicRest((float)(getTimePeriodicRest() % 0.2));
				double new_hitpoints = getHitpoints() + ((double)getToughness()/200)*(double)(nb_times_period);
				double new_stamina = getStamina() + ((double) getToughness()/100)*(double)(nb_times_period);
				
				
				if (new_hitpoints <= getMaxHitpointsStamina()){
					setHitpoints(new_hitpoints);
				}
				else if (new_stamina <= getMaxHitpointsStamina()){
					setHitpoints(getMaxHitpointsStamina());
					setStamina(new_stamina);				
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
				} catch (IllegalPositionException e) {}
				if (!(globalTarget == null) &&
						!((getOccupiedCube()[0] == globalTarget[0]) &&
						(getOccupiedCube()[1] == globalTarget[1]) &&
						(getOccupiedCube()[2] == globalTarget[2]) ) ) {
					try {
						moveTo(globalTarget);
					} catch (IllegalPositionException e) {} 
				}
			}
			
			else{
				double[] new_loc = {getLocation()[0]+ this.getCurrentSpeed()[0]*dt,
									getLocation()[1]+ this.getCurrentSpeed()[1]*dt,
									getLocation()[2]+ this.getCurrentSpeed()[2]*dt};
				try {
					setLocation(new_loc);
				} catch (IllegalPositionException e) {}
				
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
	private double getWalkingSpeed(double target_z){
		if (getLocation()[2]-target_z < 0){
			return 0.5*getBaseSpeed();
		}
		else if (getLocation()[2]- target_z > 0){
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
	
	
	public void startSprinting(){
		sprinting = true;
	}
	
	public void stopSprinting(){
		sprinting = false;
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
			isMoving = true;
		}
	}
	
	@Model 
	private void stopMoving(){
		isMoving = false;

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
		
		int[] current_cube = getOccupiedCube();
		double[] current_target = {	(double)(current_cube[0]+ dx + CUBE_LENGTH/2), 
									(double)(current_cube[1]+ dy + CUBE_LENGTH/2),
									(double)(current_cube[2]+ dz + CUBE_LENGTH/2)};

		
		if (! isValidLocation(current_target)){			
			throw new IllegalPositionException(current_target);
		}
		if (! (dx == 0 && dy == 0 && dz ==0)) {
			startMoving();
		}
		
		target = current_target;
		if (! calledBy_moveTo) {
			int[] current_target_cube = {current_cube[0] + dx, current_cube[1]+ dy, current_cube[2] + dz};
			globalTarget = current_target_cube;
		}
	}
	
			
	public void moveTo(int[] end_target) throws IllegalPositionException {
		
		globalTarget = end_target;
		
		
		int x_cur = getOccupiedCube()[0];
		int y_cur = getOccupiedCube()[1];
		int z_cur = getOccupiedCube()[2];
		
		int x_tar = end_target[0];
		int y_tar = end_target[1];
		int z_tar = end_target[2];
		
		if (x_cur != x_tar || y_cur != y_tar || z_cur != z_tar){
			int x_res;
			int y_res;
			int z_res;
			
			// x
			if (x_cur == x_tar){
				x_res = 0;
			}
			else if (x_cur < x_tar){
				x_res = 1;
			}
			else{
				x_res = -1;
			}
			
			// y 
			if (y_cur == y_tar){
				y_res = 0;
			}
			else if (y_cur < y_tar){
				y_res = 1;
			}
			else{
				y_res = -1;
			}
			
			// z
			if (z_cur == z_tar){
				z_res = 0;
			}
			else if (z_cur < z_tar){
				z_res = 1;
			}
			else{
				z_res = -1;
			}
			try {
				moveToAdjacent(x_res, y_res,z_res, true);
			} catch (IllegalAdjacentPositionException e) {}
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
		}
		working = true;
		setTimeRemainderToWork((float) 500/getStrength());
	}
	
	@Model
	private void stopWorking(){
		working = false;

	}
	@Basic
	public boolean isWorking(){
		return working;
	}
	
	@Model
	private void setTimeRemainderToWork(float time){
		timeRemainderToWork = time;
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
		double orient_unit_this = Math.atan2(other.getLocation()[1]-this.getLocation()[1],
				other.getLocation()[0]-this.getLocation()[0]);
		double orient_unit_other = Math.atan2(this.getLocation()[1]-other.getLocation()[1],
				this.getLocation()[0]-other.getLocation()[0]);
		
		this.setOrientation(orient_unit_this);
		other.setOrientation(orient_unit_other);
	}
	
	
	public void defend(Unit other){
		if (this != other) {
			if (this.isResting()) {
			this.stopResting();
			}
			
			stopWorking();
			
			double possibility_dodge = (double)(0.2* (double) this.getAgility()/ (double) other.getAgility());
			if (getDefendSucces(possibility_dodge)){
				this.setRandomLocation();
				this.setOrientationInFight(other);

			}
			
			else{
				double possibility_block = (double)(0.25*(((double) this.getStrength()+ (double) this.getAgility())/
						((double) ((double) other.getStrength()+(double) other.getAgility()))));
				
				if ( ! getDefendSucces(possibility_block)){

					this.setHitpoints(this.getHitpoints()-(double)( (double) other.getStrength()/10));
					if (this.getHitpoints() < 0) {
						this.setHitpoints(0);
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
	private double[] randomPosition(double[] curr_loc){
		double[] new_loc = {curr_loc[0]+ (random.nextDouble()*2-1), curr_loc[1]+ (random.nextDouble()*2-1), curr_loc[2]};
		if (curr_loc == new_loc) {
			return randomPosition(curr_loc);
		}
		return new_loc;
	}
	
	@Model
	private boolean canHaveAsAttackPosition(int[] attack_cube_position){
		return((Math.abs(this.getOccupiedCube()[0]-attack_cube_position[0]) <=1) && 
				(Math.abs(this.getOccupiedCube()[1]-attack_cube_position[1]) <=1) &&
				(Math.abs(this.getOccupiedCube()[2]-attack_cube_position[2]) <=1));	
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
		int possible_task = random.nextInt(3);
		if (possible_task == 0){
			try {
				moveTo(getRandomPosition());
			} catch (IllegalPositionException e) {}
		}
		else if (possible_task == 1){
			work();
		}
		
		else{
			rest();
		}
	}
	
	@Model
	private int[] getRandomPosition(){
		int[] rand_loc = {random.nextInt(WORLD_X-1), random.nextInt(WORLD_Y-1), random.nextInt(WORLD_Z-1)};
		return rand_loc;
	}
}
