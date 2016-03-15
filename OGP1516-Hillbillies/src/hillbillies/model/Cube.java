
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
		return this.cubeType;
	}
	
	public void setCubeType(CubeType cubeType){
		this.cubeType = cubeType;
	}

	private CubeType cubeType;
	
	
}

