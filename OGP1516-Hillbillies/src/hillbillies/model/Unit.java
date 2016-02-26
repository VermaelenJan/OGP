package hillbillies.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



//TODO: write tests for everything
//TODO: CONSTANTS, randomMethods,... 
//TODO: fix flag
//TODO: setters in exceptions
/**
 * 
 * @author
 *
 */
public class Unit {
	
	static int WORLD_X = 50;
	static int WORLD_Y = 50;
	static int WORLD_Z = 50;
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
	private double CUBE_LENGTH = 1;
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
	private float time_resting;
	private double start_hitpoints;
	private double start_stamina;
	
	// Default Behaviour
	private boolean default_behaviour;
	
	//TODO set/get/check moving
	private boolean isMoving ;
//	private List<Integer> global_target;
	
	
	// Random
	Random random = new Random();

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
		double orientation) throws IllegalPositionException, IllegalNameException {
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
	
	public Unit(List<Double> location, String name, int weight, int strength, int agility, int toughness)
			throws IllegalPositionException, IllegalNameException {
		this(location, name, weight, strength, agility, toughness,Math.PI/2);
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
	private boolean canHaveAsPosition(List<Double> location) {
		return ((location.get(0) <= WORLD_X) && (location.get(1) <= WORLD_Y) && (location.get(2) <= WORLD_Z) && 
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
		return ((name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && (areValidCharacters(name)));
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
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
	private double getMaxHitpointsStamina() {
		 return (double) Math.ceil((((double) this.getWeight())*((double) this.getToughness()))/50);
	}
	
	/**
	 * 
	 * @param hitpoints
	 * @return
	 */
	private boolean hasValidHitpoints(double hitpoints){
		return ((hitpoints >= 0) && (hitpoints <= this.getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param stamina
	 * @return
	 */
	private boolean hasValidStamina(double stamina){
		return ((stamina >= 0) && (stamina <= this.getMaxHitpointsStamina()));
	}
	
	/**
	 * 
	 * @param hitpoints
	 * 
	 * @pre
	 */
	public void setHitpoints(double hitpoints){
		assert this.hasValidHitpoints(hitpoints);
		this.hitpoints = hitpoints;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getHitpoints(){
		return this.hitpoints;
	}
	
	/**
	 * 
	 * @param stamina
	 * 
	 * @pre
	 */
	public void setStamina(double stamina){
		assert this.hasValidStamina(stamina);
		this.stamina = stamina;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getStamina(){
		return this.stamina;
	}
	
	/**
	 * 
	 * @param orientation
	 */
	public void setOrientation(double orientation){
		this.orientation = orientation%(2*Math.PI);
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
	// TODO exceptions(zowel opgave als location)!!!!!!!!!!!!!!!!! jaaa eric, we weten het.
	
	public void advanceTime(double dt){
		
		
		if (isAttacking()){
			setAttackTime(getAttackTime() - (float)(dt));
			if (getAttackTime()<= 0){
				setAttackTime(0);
//				try {
//					moveTo(global_target);
//				} catch (IllegalPositionException e) {}
			}
		}
		
		
		else if (isWorking() && canHaveAsHavingRecoverdOneHp()){
			setTimeRemainderToWork(this.getTimeRemainderToWork()-(float)dt);	
			if (this.getTimeRemainderToWork() < 0){
				stopWorking();
			}
		}
		
		
		else if (isResting()){
			setTimeResting(getTimeResting() + (float) dt);
			double new_hitpoints = (getHitpoints()+ (double)(getToughness()/200))*dt/0.2;
			double new_stamina = (getStamina() + (double)(getToughness()/100))*dt/0.2;
			if (new_hitpoints< getMaxHitpointsStamina()){
				setHitpoints(new_hitpoints);
			}
			else if (new_stamina < getMaxHitpointsStamina()){
				setHitpoints(getMaxHitpointsStamina());
				setStamina(new_stamina);
				
			}
			
			else{
				setStamina(getMaxHitpointsStamina());
			}
			
			if (isDefaultBehaviourEnabled() && getStamina() == getMaxHitpointsStamina()){
				stopResting();
			}
		}
		

		

		else if (isMoving()){
			
			if (this.Arrived(dt)){
				stopMoving();
				try {
					this.setLocation(target);
				} catch (IllegalPositionException e) {}
			}
			
			else{
				List<Double> new_loc = new ArrayList<Double>();
				new_loc.add(this.getLocation().get(0)+ this.getCurrentSpeed().get(0)*dt);
				new_loc.add(this.getLocation().get(1)+ this.getCurrentSpeed().get(1)*dt);
				new_loc.add(this.getLocation().get(2)+ this.getCurrentSpeed().get(2)*dt);
				try {
					this.setLocation(new_loc);
				} catch (IllegalPositionException e) {}
				
				if ((isSprinting()) && (getStamina()>0)){
					setStamina(getStamina()- dt*10);
				}
				else{
					stopSprinting();
				}
				
				this.setOrientation(Math.atan2(this.getCurrentSpeed().get(1),this.getCurrentSpeed().get(0)));
			}
						
		}
		
		setTimeSinceRest(getTimeSinceRest() + (float)dt);
		if (getTimeSinceRest() > 180){
			startResting();
		}
	}
	
	private boolean canHaveAsHavingRecoverdOneHp(){
		return (getTimeResting() > (float)(1/(this.toughness/200)/0.2));
	}
		
	
	private double getBaseSpeed(){
		return 1.5*(this.getStrength()+this.getAgility())/(2*this.getWeight());
	}
	
	private double getWalkingSpeed(double target_z){
		if (this.getLocation().get(2)-target_z == -1){
			return 0.5*this.getBaseSpeed();
		}
		else if (this.getLocation().get(2)- target_z == 1){
			return 1.2*this.getBaseSpeed();
		}
		else{
			return this.getBaseSpeed();
		}
	}
	
	public List<Double> getCurrentSpeed() {
		double distance = this.getDistanceToTarget();

		if (isSprinting()){
			velocity = this.getWalkingSpeed(target.get(2))*2;
		}
		else{
			velocity = this.getWalkingSpeed(target.get(2));
		}
		List<Double> current_speed = new ArrayList<Double>();
		current_speed.add(velocity*(target.get(0)-this.getLocation().get(0))/distance);
		current_speed.add(velocity*(target.get(1)-this.getLocation().get(1))/distance);
		current_speed.add(velocity*(target.get(2)-this.getLocation().get(2))/distance);
		
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
	
	private void startMoving(){
		if ( isResting() && (canHaveAsHavingRecoverdOneHp())){
			stopResting();
		}
		isMoving = true;
	}
	
	private void stopMoving(){
		isMoving = false;
		stopSprinting();
	}
	
	public boolean isMoving(){
		return isMoving;
	}
	
	public double getDistanceToTarget() {
		double distance = Math.sqrt(Math.pow((target.get(0)-this.getLocation().get(0)),2)+Math.pow((target.get(1)-this.getLocation().get(1)),2)
		+ Math.pow((target.get(2)-this.getLocation().get(2)),2));
		return distance;
	}
	
	private boolean Arrived(double dt){
		
		return (this.getDistanceToTarget() < dt*Math.sqrt((Math.pow((this.getCurrentSpeed().get(0) ), 2))) +
				Math.pow((this.getCurrentSpeed().get(1) ), 2));	
	}
	
	// TODO show exception if try to move in more than 1 block
	
	public void moveToAdjacent(int dx,int dy,int dz) throws IllegalPositionException{

		List<Double> current_target = new ArrayList<Double>();
		List<Integer> current_cube = getOccupiedCube();
			
		current_target.add((double)(current_cube.get(0)+ dx + CUBE_LENGTH/2));
		current_target.add((double)(current_cube.get(1)+ dy + CUBE_LENGTH/2));
		current_target.add((double)(current_cube.get(2)+ dz + CUBE_LENGTH/2));
		
		if (! canHaveAsPosition(current_target)){			
			throw new IllegalPositionException(current_target);
		}
		startMoving();
		
		target = current_target;
	}
	
			
	public void moveTo(List<Integer>end_target) throws IllegalPositionException {
		
//		global_target = end_target;
		
		int x_cur = this.getOccupiedCube().get(0);
		int y_cur = this.getOccupiedCube().get(1);
		int z_cur = this.getOccupiedCube().get(2);
		
		int x_tar = end_target.get(0);
		int y_tar = end_target.get(1);
		int z_tar = end_target.get(2);
				
		while ((x_cur == x_tar)){
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
			

			moveToAdjacent(x_res, y_res,z_res);
		}
			

	}
	
	public void work(){
		startWorking();
	}
	
	private void startWorking(){
		working = true;
		setTimeRemainderToWork((float) 500/getStrength());
	}
	
	private void stopWorking(){
		working = false;

	}
	
	public boolean isWorking(){
		return working;
	}
	
	private void setTimeRemainderToWork(float time){
		time_remainder_to_work = time;
	}
	
	private float getTimeRemainderToWork(){
		return time_remainder_to_work;
	}
	
	
	public void attack(Unit other) throws IllegalAttackPosititonException {
		if (!this.canHaveAsAttackPosition(other.getOccupiedCube())) {
			throw new IllegalAttackPosititonException(other.getOccupiedCube());
		}
		
		stopResting();	
		
//		stopMoving();
		
		double orient_attack = Math.atan2(other.getLocation().get(1)-this.getLocation().get(1),
				other.getLocation().get(0)-this.getLocation().get(0));
		double orient_defend = Math.atan2(this.getLocation().get(1)-other.getLocation().get(1),
				this.getLocation().get(0)-other.getLocation().get(0));
		
		this.setOrientation(orient_attack);
		other.setOrientation(orient_defend);
			
		setAttackTime(1);
		
	}
	
	public void defend(Unit other){
		
		stopResting();
		
		double possibility_dodge = (double)(0.2*this.getAgility()/other.getAgility());
		if (getDefendSucces(possibility_dodge)){
			setRandomLocation();
		}
		
		else{
			double possibility_block = (double)(0.25*(this.getStrength()+this.getAgility()/(other.getStrength()+other.getAgility())));
			
			if ( ! getDefendSucces(possibility_block)){
				this.setHitpoints(this.getHitpoints()-(double)(other.getStrength()/10));
			}
								
			

		}
		
	}
	
	private void setRandomLocation(){
		try {
			this.setLocation(randomPosition(this.getLocation()));
		} catch (IllegalPositionException e) {
			setRandomLocation();
		}
	}
	
	private List<Double> randomPosition(List<Double>curr_loc){
		List<Double> new_loc = new ArrayList<Double>();
		new_loc.add(curr_loc.get(0)+ (random.nextDouble()*2-1) );
		new_loc.add(curr_loc.get(1)+ (random.nextDouble()*2-1) );
		new_loc.add(curr_loc.get(2));
		return new_loc;
	}
	
	private boolean canHaveAsAttackPosition(List<Integer>attack_cube_position){
		return((Math.abs(this.getOccupiedCube().get(0)-attack_cube_position.get(0)) <=1) && 
				(Math.abs(this.getOccupiedCube().get(1)-attack_cube_position.get(1)) <=1) &&
				(Math.abs(this.getOccupiedCube().get(2)-attack_cube_position.get(2)) <=1));	
	}
	
	public boolean isAttacking(){
		return (this.getAttackTime() > 0);
	}
	
	private void setAttackTime(float time){
		attack_time = time;
	}
	
	private float getAttackTime(){
		return attack_time;
	}
	
	private boolean getDefendSucces(double x){
		return (random.nextDouble() < x);
	}
	
	public void rest(){
		startResting();
	}
	
	public boolean isResting(){
		return resting;
	}
		
	private void startResting(){
		setTimeResting(0);
		resting = true;
		stopWorking();
		stopMoving();
		setStartHitpoints(getHitpoints());
		setStartStamina(getStamina());
	}
	private void stopResting(){
		if (! canHaveAsHavingRecoverdOneHp()){
			setHitpoints(getStartHitpoints());
			setStamina(getStartStamina());
		}
		resting = false;
		setTimeSinceRest(0);
	}
	
	private void setTimeSinceRest(float time){
		this.time_since_rest = time;
	}
	
	private float getTimeSinceRest(){
		return this.time_since_rest;
	}
	
	private void setTimeResting(float time) {
		this.time_resting = time;
	}
	
	private float getTimeResting() {
		return this.time_resting;
	}
	
	private void setStartHitpoints(double hitpoints){
		this.start_hitpoints = hitpoints;
	}
	private double getStartHitpoints(){
		return this.start_hitpoints;
	}
	private void setStartStamina(double stamina){
		this.start_stamina = stamina;
	}
	private double getStartStamina(){
		return this.start_stamina;
	}
	public boolean isDefaultBehaviourEnabled(){
		return this.default_behaviour;
	}
	public void startDefaultBehaviour(){
		this.default_behaviour = true;
	}
	
	public void stopDefaultBehaviour(){
		this.default_behaviour = false;
	}
	
	// TODO getRandomPosition
	private void newDefaultBehaviour(){
		int possible_task = random.nextInt(3);
		if (possible_task == 0){
			moveTo(getRandomPosition());
		}
		else if (possible_task == 1){
			work();
		}
		
		else{
			rest();
		}
	}
	
	public List<Double> getRandomPosition(){
		return null;
		// TODO
	}
}
