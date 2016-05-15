package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class with constants used in this project.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
@Value
public final class ConstantsUtils {
	

	/**
	 * Variable registering the cube length.
	 */
	static final double CUBE_LENGTH = 1;
	
	
	/**
	 * Variables registering the (initial) minimum and maximum value for attributes of a unit.
	 */
	static final int INIT_MIN_VAL = 25;
	static final int INIT_MAX_VAL = 100;
	static final int MIN_VAL = 1;
	static final int MAX_VAL = 200;
	
	/**
	 * Variable registering a randomizer.
	 */
	public static final Random random = new Random();
	
	/**
	 * Checks whether the a possibility is greater than a random number between 0 and 1.
	 * @param possibility
	 * 			The possibility in the specific case.
	 * @return True if and only if the the possibility is greater than the random number chosen.
	 */
	@Model
	protected static boolean getPossibilitySucces(double possibility){
		return (random.nextDouble() <= possibility);
	}
	
	/**
	 * Variable registering the minimum weight of an object.
	 */
	static final int MIN_OBJECT_WEIGHT = 10;
	/**
	 * Variable registering the minimum weight of an object.
	 */
	static final int MAX_OBJECT_WEIGHT = 50;
	
	/**
	 * Variable registering the falling speed of a unit or object.
	 */
	static final double FALLING_SPEED = 3;
	
	/**
	 * Variable registering the maximum number of units in a faction.
	 */
	static final int MAX_UNITS_FACTION = 50;
	
	/**
	 * Variable registering the maximum number of units in a world.
	 */
	static final int MAX_UNITS_WORLD = 100;
	
	/**
	 * Variable registering the maximum number of factions in a world.
	 */
	static final int MAX_FACTIONS = 5;
	
	/**
	 * Variable registering the time pending.
	 */
	static final double PEND_TIME = 0.001;
	
	/**
	 * Variable registering the maximum time before the unit has to rest.
	 */
	static final int AUTO_REST_TIME = 180;
}
