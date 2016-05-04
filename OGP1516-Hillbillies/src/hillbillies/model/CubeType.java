package hillbillies.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * An enumeration introducing different cube types used for cubes
 * in a world.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.1
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
		this.setType(type);
	}
	
	/**
	 * Checks whether the terrain is passable.
	 *  @return True if and only if the type is air or the type is workshop. 
	 */
	public boolean isPassableTerrain(){
		return ((this.getType() == "air") || (this.getType() == "workshop"));
	}
	
		
	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}


	/**
	 * Variable registering the type of this cubetype.
	 */
	private String type;
}

