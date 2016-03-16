package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

/**
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class World {
	
	public World(CubeType[][][] worldCubes,TerrainChangeListener terrainChangeListener){
		this.terrainChangeListener = terrainChangeListener;
		this.worldCubes = worldCubes;
		ConnectedToBorder CTBTool = new ConnectedToBorder(ConstantsUtils.WORLD_X,ConstantsUtils.WORLD_Y,ConstantsUtils.WORLD_Z);
		
		for (int xIndex = 1; xIndex<ConstantsUtils.WORLD_X; xIndex++) {
			for (int yIndex = 1; yIndex<ConstantsUtils.WORLD_Y; yIndex++) {
				for (int zIndex = 1; zIndex<ConstantsUtils.WORLD_Z; zIndex++) {
					
				}	
			}	
		}	
	}
		
	
	
	
	CubeType[][][] worldCubes = new CubeType[ConstantsUtils.WORLD_X][ConstantsUtils.WORLD_Y][ConstantsUtils.WORLD_Z];
	
	protected void setCubeType(int x,int y, int z, CubeType cubeType){
		worldCubes[x][y][z] = cubeType;
	}
	
	public CubeType getCubeType(int x,int y, int z){
		return worldCubes[x][y][z];
	}
	
	
	public void advanceTime(double dt){
		
	}
	
	
	private TerrainChangeListener terrainChangeListener;

	
}
