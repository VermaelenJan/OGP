package hillbillies.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;





//TODO: write tests for everything
//TODO: CONSTANTS, randomMethods,... 
/**
 * 
 * @author Maxime en Jan
 *
 */
public class Unit {
	
	// WORLD DIMENSIONS
	static int WORLD_X = 50;
	static int WORLD_Y = 50;
	static int WORLD_Z = 50;
	private double CUBE_LENGTH = 1;
	
	
	private double x_pos;
	private double y_pos;
	private double z_pos;
	
	private String name;
	private int weight;
	private int strength;
	private int agility;
	private int toughness;
	private double hitpoints;
	private double stamina;	
	private double orientation;
	
	private static int init_min_val = 25;
	private static int init_max_val = 100;
	private static int min_val = 1;
	private static int max_val = 200;
	private int curr_min_val;
	private int curr_max_val;
	
	private boolean sprinting;
	private double velocity;

	// Target
	private List<Double> target = null;
	
	// Working
	private boolean working;
	private float time_remainder_to_work;
	
	// Attack
	private float attack_time;
	
	// Rest
	private boolean resting;
	private float time_since_rest;
	private float time_resting = Float.MAX_VALUE;
	private double start_hitpoints;
	private double start_stamina;
	private float periodic_rest;
	
	// Default Behaviour
	private boolean default_behaviour;
	
	// Moving
	private boolean isMoving ;
	private List<Integer> global_target;
	
	
	// Random
	Random random = new Random();


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
	 * 			| ! canHaveAsPosition(position)w
	 * @throws IllegalNameException 
	 * 			The given name is not a valid name for a unit.	
	 * 			| ! isValidName(name)
	 * 
	 */
	
	@Raw
	public Unit(List<Integer> CubeLocation, String name, int weight, int strength, int agility, int toughness, 
		double orientation) throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add(CubeLocation.get(0)+CUBE_LENGTH/2);
		location.add(CubeLocation.get(1)+CUBE_LENGTH/2);
		location.add(CubeLocation.get(2)+CUBE_LENGTH/2);
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
	
	@Raw
	public Unit(List<Integer> CubeLocation, String name, int weight, int strength, int agility, int toughness)
			throws IllegalPositionException, IllegalNameException {
		this(CubeLocation, name, weight, strength, agility, toughness,Math.PI/2);
	}	
	/**
	 * 
	 * @param location
	 * 			The location to set.
	 * @throws IllegalPositionException
	 * 			the location is not a valid location for a unit.
	 * 			| !canHaveAsPosition(location)
	 */
	
	@Raw @Model
	private void setLocation(List<Double> location) throws IllegalPositionException{
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
	
	@Raw @Model
	private boolean canHaveAsPosition(List<Double> location) {
		return ((location.get(0) <= WORLD_X) && (location.get(1) <= WORLD_Y) && (location.get(2) <= WORLD_Z) && 
				(location.get(0) >= 0) && (location.get(1) >= 0) && (location.get(2) >= 0));
	}

	/**
	 * 
	 * @return
	 */
	
	@Basic @Raw
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
	
	@Basic @Raw
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
	
	@Raw
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
	
	@Model
	private static boolean isValidName(String name){
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (areValidCharacters(name)));
	}
	
	/**
	 * 
	 * @param name
	 * @return
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
	 * 
	 * @return
	 */
	
	@Basic @Raw 
	public String getName(){
		return this.name;
	}
	

	/**
	 * 
	 * @param weight
	 */
	
	@Raw @Model
	private void setWeight(int weight, boolean flag){
		if (flag){
			curr_min_val = init_min_val;
			curr_max_val = init_max_val;
		}
		else {
			curr_min_val = min_val;
			curr_max_val = max_val;
		}

		if (weight >= (getStrength()+getAgility())/2){
			if (weight < curr_min_val) 
				this.weight = curr_min_val;
			else if ((weight >= curr_min_val) && (weight <= curr_max_val))
				this.weight = weight;
			else if (weight > curr_max_val)
				this.weight = curr_max_val; 
		}
		
		else{
			this.weight = (getStrength()+getAgility())/2;
		}

	}
	
	
	/**
	 * 
	 * @param weight
	 */
	
	@Raw
	public void setWeight(int weight) {
		setWeight(weight, false);
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Basic @Raw
	public int getWeight(){
		return this.weight;
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
	// TODO exceptions(zowel opgave als location)!!!!!!!!!!!!!!!!! jaaa eric, we weten het.
	
	
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
				if (!(global_target == null) &&
						!((getOccupiedCube().get(0) == global_target.get(0)) &&
						(getOccupiedCube().get(1) == global_target.get(1)) &&
						(getOccupiedCube().get(2) == global_target.get(2)) ) ) {
					
					try {
						moveTo(global_target);
					} catch (IllegalPositionException e) {}
					
				}

			}
			
			else{
				List<Double> new_loc = new ArrayList<Double>();
				new_loc.add(getLocation().get(0)+ this.getCurrentSpeed().get(0)*dt);
				new_loc.add(getLocation().get(1)+ this.getCurrentSpeed().get(1)*dt);
				new_loc.add(getLocation().get(2)+ this.getCurrentSpeed().get(2)*dt);
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
				setOrientation(Math.atan2(getCurrentSpeed().get(1),getCurrentSpeed().get(0)));
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
		this.periodic_rest = time;
	}
	
	@Basic @Model
	private float getTimePeriodicRest(){
		return this.periodic_rest;
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
		if (getLocation().get(2)-target_z < 0){
			return 0.5*getBaseSpeed();
		}
		else if (getLocation().get(2)- target_z > 0){
			return 1.2*getBaseSpeed();
		}
		else{
			return getBaseSpeed();
		}
	}
	
	@Basic
	public List<Double> getCurrentSpeed() {
		double distance = getDistanceToTarget();

		if (isSprinting()){
			velocity = getWalkingSpeed(target.get(2))*2;
		}
		else{
			velocity = getWalkingSpeed(target.get(2));
		}
		List<Double> current_speed = new ArrayList<Double>();
		current_speed.add(velocity*(target.get(0)-getLocation().get(0))/distance);
		current_speed.add(velocity*(target.get(1)-getLocation().get(1))/distance);
		current_speed.add(velocity*(target.get(2)-getLocation().get(2))/distance);
		
		return current_speed;
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
		//stopSprinting(); (Hill kept stopping sprinting)
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
		double distance = Math.sqrt(Math.pow((target.get(0)-getLocation().get(0)),2)+Math.pow((target.get(1)-getLocation().get(1)),2)
		+ Math.pow((target.get(2)-getLocation().get(2)),2));
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
		return Math.sqrt(Math.pow((getCurrentSpeed().get(0) ), 2) +
		Math.pow((getCurrentSpeed().get(1) ), 2) + Math.pow((getCurrentSpeed().get(2)), 2));
	}
	
	
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalPositionException {
		moveToAdjacent(dx, dy, dz, false);
	}
	
	// TODO show exception if try to move in more than 1 block, add new exception
	
	@Model
	private void moveToAdjacent(int dx,int dy,int dz, boolean calledBy_moveTo) throws IllegalPositionException{

		List<Double> current_target = new ArrayList<Double>();
		List<Integer> current_cube = getOccupiedCube();
			
		current_target.add((double)(current_cube.get(0)+ dx + CUBE_LENGTH/2));
		current_target.add((double)(current_cube.get(1)+ dy + CUBE_LENGTH/2));
		current_target.add((double)(current_cube.get(2)+ dz + CUBE_LENGTH/2));
		
		if (! canHaveAsPosition(current_target)){			
			throw new IllegalPositionException(current_target);
		}
		if (! (dx == 0 && dy == 0 && dz ==0)) {
			startMoving();
		}
		
		target = current_target;
		if (! calledBy_moveTo) {
			List<Integer> current_target_cube = new ArrayList<Integer>();
			current_target_cube.add(current_cube.get(0)+ dx);
			current_target_cube.add(current_cube.get(1)+ dy);
			current_target_cube.add(current_cube.get(2)+ dz);
			global_target = current_target_cube;
		}
	}
	
			
	public void moveTo(List<Integer>end_target) throws IllegalPositionException {
		global_target = end_target;
		int x_cur = getOccupiedCube().get(0);
		int y_cur = getOccupiedCube().get(1);
		int z_cur = getOccupiedCube().get(2);
		
		int x_tar = end_target.get(0);
		int y_tar = end_target.get(1);
		int z_tar = end_target.get(2);
		
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
			moveToAdjacent(x_res, y_res,z_res, true);
		}
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
		time_remainder_to_work = time;
	}
	
	@Model 
	private float getTimeRemainderToWork(){
		return time_remainder_to_work;
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
		double orient_unit_this = Math.atan2(other.getLocation().get(1)-this.getLocation().get(1),
				other.getLocation().get(0)-this.getLocation().get(0));
		double orient_unit_other = Math.atan2(this.getLocation().get(1)-other.getLocation().get(1),
				this.getLocation().get(0)-other.getLocation().get(0));
		
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
	private List<Double> randomPosition(List<Double>curr_loc){
		List<Double> new_loc = new ArrayList<Double>();
		new_loc.add(curr_loc.get(0)+ (random.nextDouble()*2-1) );
		new_loc.add(curr_loc.get(1)+ (random.nextDouble()*2-1) );
		new_loc.add(curr_loc.get(2));
		if (curr_loc == new_loc) {
			return randomPosition(curr_loc);
		}
		return new_loc;
	}
	
	@Model
	private boolean canHaveAsAttackPosition(List<Integer>attack_cube_position){
		return((Math.abs(this.getOccupiedCube().get(0)-attack_cube_position.get(0)) <=1) && 
				(Math.abs(this.getOccupiedCube().get(1)-attack_cube_position.get(1)) <=1) &&
				(Math.abs(this.getOccupiedCube().get(2)-attack_cube_position.get(2)) <=1));	
	}
	
	@Basic
	public boolean isAttacking(){
		return (getAttackTime() > 0);
	}
	
	@Model
	private void setAttackTime(float time){
		this.attack_time = time;
	}
	
	@Basic @Model
	private float getAttackTime(){
		return this.attack_time;
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
		setTimeResting(0);
		resting = true;
		stopWorking();

		setStartHitpoints(getHitpoints());
		setStartStamina(getStamina());
	}
	
	@Model
	private void stopResting(){
		if (! canHaveRecoverdOneHp()){
			setHitpoints(getStartHitpoints());
			setStamina(getStartStamina());
		}
		resting = false;
		setTimeSinceRest(0);
		setTimeResting(Float.MAX_VALUE);
	}
	
	@Model
	private void setTimeSinceRest(float time){
		this.time_since_rest = time;
	}
	
	@Model
	private float getTimeSinceRest(){
		return this.time_since_rest;
	}
	
	@Model
	private void setTimeResting(float time) {
		this.time_resting = time;
	}
	
	@Basic @Model
	private float getTimeResting() {
		return this.time_resting;
	}
	
	@Model
	private void setStartHitpoints(double hitpoints){
		this.start_hitpoints = hitpoints;
	}
	
	@Basic @Model
	private double getStartHitpoints(){
		return this.start_hitpoints;
	}
	
	@Model
	private void setStartStamina(double stamina){
		this.start_stamina = stamina;
	}
	
	@Basic @Model
	private double getStartStamina(){
		return this.start_stamina;
	}
	
	@Basic
	public boolean isDefaultBehaviourEnabled(){
		return default_behaviour;
	}
	
	
	public void startDefaultBehaviour(){
		default_behaviour = true;
	}
	
	public void stopDefaultBehaviour(){
		default_behaviour = false;
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
	private List<Integer> getRandomPosition(){
		List<Integer> rand_loc = new ArrayList<Integer>();
		rand_loc.add(random.nextInt(WORLD_X-1));
		rand_loc.add(random.nextInt(WORLD_Y-1));
		rand_loc.add(random.nextInt(WORLD_Z-1));
		return rand_loc;
	}
}
