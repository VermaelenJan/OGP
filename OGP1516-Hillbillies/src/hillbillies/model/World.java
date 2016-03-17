package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

/**
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class World {
	
	public World(CubeType[][][] worldCubes,TerrainChangeListener terrainChangeListener){
		this.worldCubes = worldCubes;
		this.worldX = worldCubes[0].length;
		this.worldY = worldCubes[1].length;
		this.worldZ = worldCubes[2].length;
		this.terrainChangeListener = terrainChangeListener;
		this.CTBTool = new ConnectedToBorder(worldX,worldY,worldZ);
		
		for (int xIndex = 1; xIndex<worldX; xIndex++) {
			for (int yIndex = 1; yIndex<worldY; yIndex++) {
				for (int zIndex = 1; zIndex<worldZ; zIndex++) {
					if (!worldCubes[xIndex][yIndex][zIndex].isPassableTerrain()){
						CTBTool.changeSolidToPassable(xIndex, yIndex, zIndex);
					}
				}	
			}	
		}
		
		updateConnectedTerrain();
	}
	
	public int getNbCubesX(){
		return worldX;
	}
	
	public int getNbCubesY(){
		return worldY;
	}
	
	public int getNbCubesZ(){
		return worldZ;
	}
	
	int worldX;
	int worldY;
	int worldZ;
	
	private void updateConnectedTerrain() {
		for (int xIndex = 1; xIndex<worldCubes[0].length; xIndex++) {
			for (int yIndex = 1; yIndex<worldCubes[1].length; yIndex++) {
				for (int zIndex = 1; zIndex<worldCubes[2].length; zIndex++) {
					if (!CTBTool.isSolidConnectedToBorder(xIndex, yIndex, zIndex)){
						caveIn(xIndex,yIndex,zIndex);
					}
				}
			}	
		}
	}
		
	CubeType[][][] worldCubes;
	
	protected void setCubeType(int x,int y, int z, CubeType cubeType){
		worldCubes[x][y][z] = cubeType;
	}
	
	public CubeType getCubeType(int x,int y, int z){
		return worldCubes[x][y][z];
	}
	
	
	public void advanceTime(double dt){
		
	}
	
	private void caveIn(int x,int y,int z){
		
		CubeType cubeType = getCubeType(x, y, z);
		setCubeType(x, y, z, CubeType.AIR);
		terrainChangeListener.notifyTerrainChanged(x, y, z);
		CTBTool.changeSolidToPassable(x, y, z);
		
		double [] location = {x+0.5,y+0.5,z+0.5};
		
		if (ConstantsUtils.getPossibilitySucces(0.25)){
			if (cubeType == CubeType.ROCK){
				Boulder boulder = new Boulder(this,location);
				boulders.add(boulder);
			}
			else if (cubeType == CubeType.WOOD){
				Log log = new Log(this,location);
				logs.add(log);
			}
		}
	updateConnectedTerrain();
	}
	
	List<Boulder> boulders = new ArrayList<Boulder>();
	
	List<Log> logs = new ArrayList<Log>();
			
	
	private ConnectedToBorder CTBTool;
	private TerrainChangeListener terrainChangeListener;

	
}
