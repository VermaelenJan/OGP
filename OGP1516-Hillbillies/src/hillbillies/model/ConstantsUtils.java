package hillbillies.model;

import java.util.Random;

/**
 * @author Win
 * @version 1.0
 */
final class ConstantsUtils {
	

	// WORLD DIMENSIONS
	static final int WORLD_X = 50;
	static final int WORLD_Y = 50;
	static final int WORLD_Z = 50;
	static final double CUBE_LENGTH = 1;
	
	// MIN,MAX VALUES (for properties)
	static final int INIT_MIN_VAL = 25;
	static final int INIT_MAX_VAL = 100;
	static final int MIN_VAL = 1;
	static final int MAX_VAL = 200;
	
	// Randomizer
	static final Random random = new Random();
	
	// LOG
	static final int MIN_LOG_WEIGHT = 10;	
	static final int MAX_LOG_WEIGHT = 50;
}
