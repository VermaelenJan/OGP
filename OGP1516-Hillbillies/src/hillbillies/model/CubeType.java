/**
 * 
 */
package hillbillies.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * @author Maxime
 *
 */
public enum CubeType {
	AIR("air"), ROCK("rock"), WOOD("wood"), WORKSHOP("workshop");
	
	
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
	
	
	protected boolean isPassableTerrain(){
		return ((this.type == "air") || (this.type == "workshop"));
	}
	
		
	/**
	 * Variable storing the type for this cubetype.
	 */
	protected String type;
}

