package hillbillies.model;


/**
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class World {
	
	CubeType[][][] worldCubes = new CubeType[ConstantsUtils.WORLD_X][ConstantsUtils.WORLD_Y][ConstantsUtils.WORLD_Z];
	
	protected void setCubeType(int x,int y, int z, CubeType cubeType){
		worldCubes[x][y][z] = cubeType;
	}
	
	public CubeType getCubeType(int x,int y, int z){
		return worldCubes[x][y][z];
	}
	

	
}
