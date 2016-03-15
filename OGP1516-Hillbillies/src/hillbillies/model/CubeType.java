/**
 * 
 */
package hillbillies.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * @author Maxime
 *
 */
enum CubeType {
	AIR("air"), ROCK("rock"), WOOD("wood");
	
	
	/**
	 * Initialize this cubetype with the given type.
	 *
	 * @param  type
	 *         The type for this new cubetype.
	 * @post   The type for this new cubetype is equal to the
	 *         given type.
	 *       | new.getCubetType() == type
	 */
	@Raw 
	CubeType(String type) {
		this.type = type;
	}
	
	
	/**
	 * Variable storing the type for this cubetype.
	 */
	protected String type;
}

