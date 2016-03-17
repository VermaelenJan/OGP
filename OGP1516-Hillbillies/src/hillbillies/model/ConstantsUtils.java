package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Model;

/**
 * @author Win
 * @version 1.0
 */
final class ConstantsUtils {
	

	// WORLD DIMENSION
	static final double CUBE_LENGTH = 1;
	
	// MIN,MAX VALUES (for properties)
	static final int INIT_MIN_VAL = 25;
	static final int INIT_MAX_VAL = 100;
	static final int MIN_VAL = 1;
	static final int MAX_VAL = 200;
	
	// Randomizer
	static final Random random = new Random();
	
	/**
	 * Checks whether the unit's defend is successful.
	 * @param possibility
	 * 			The possibility to defend successful.
	 * @return True if and only if the the unit has defended successful.
	 * 			| result == (randomDouble <= possibility)
	 */
	@Model
	protected static boolean getPossibilitySucces(double possibility){
		return (random.nextDouble() <= possibility);
	}
	
	// LOG
	static final int MIN_OBJECT_WEIGHT = 10;	
	static final int MAX_OBJECT_WEIGHT = 50;
	
	// FALLING
	static final double FALLING_SPEED = 3;
	
	// MAX UNITS
	static final int MAX_UNITS_FACTION = 50;
	static final int MAX_UNITS_WORLD = 100;
}
