
package hillbillies.model;

/**
 * @author Maxime
 *
 */
public class Cube {
	
	public Cube(int[] position, CubeType cubeType){
		this.cubeType = cubeType;
		
	}
	public CubeType getCubeType(){
		return this.type;
	}
	
	
	private CubeType cubeType;
}
