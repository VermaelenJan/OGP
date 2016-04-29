package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of cubes which occur in a world.
 * 
 * @invar The cubetype of a cube is either rock,wood, air of workshop for 
 * 		any cube.
 * @invar The position of a cube must be a valid position for any cube. 
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.1
 *
 */
public class Cube {
	
	/**
	 * Initialize this new cube with the given position and the given cubetype.
	 * 
	 * @param position
	 * 		The position for this new cube.
	 * @param cubeType
	 * 		The cube type for this new cube.
	 * @effect The position of this new cube is set to the given position.
	 * @effect The cubetype of this new cube is set to the given cubetype.
	 * 
	 */
	public Cube(int[] position, CubeType cubeType){
		setCubeType(cubeType);
		setCubePosition(position);
	}
	
	/**
	 * Set the position of the cube to the given position.
	 * 
	 * @param position
	 * 		The position to set to the position of this cube.
	 * @post The new position of this cube is equal to the given position.
	 */
	@Raw
	protected void setCubePosition(int[] position){
		this.position = position;
	}
	
	/**
	 * Return the position of this cube.
	 */
	@Basic @Raw
	public int[] getCubePosition() {
		return this.position;
	}
	
	/**
	 * Variable registering the position of this cube.
	 */
	private int[] position;
	

	/**
	 * Set the cubetype of the cube to the given position.
	 * 
	 * @param cubeType
	 * 		The cubetype to set to the cubetype of this cube.
	 * @post The new cubetype of this cube is equal to the given cubetype.
	 */
	@Raw
	public void setCubeType(CubeType cubeType){
		this.cubeType = cubeType;
	}
	
	/**
	 * Return the cubetype of this cube.
	 */
	@Basic @Raw
	public CubeType getCubeType(){
		return this.cubeType;
	}

	/**
	 * Variable registering the cubetype of this cube.
	 */
	private CubeType cubeType;


}

