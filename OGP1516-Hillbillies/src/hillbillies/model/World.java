package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.exceptions.IllegalValueException;
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
		
		for (int xIndex = 1; xIndex<worldX; xIndex++) { //TODO vanaf 0 of 1??
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
		for (int xIndex = 1; xIndex<worldCubes[0].length; xIndex++) { //TODO vanaf 0 of 1??
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
	
	Set<Boulder> boulders = new HashSet<Boulder>();
	
	public Set<Boulder> getBoulders(){
		return boulders;
	}
	
	Set<Log> logs = new HashSet<Log>();
	
	public Set<Log> getLogs(){
		return logs;
	}
	
	Set<Faction> factions = new HashSet<Faction>();
	
	private Set<Faction> getAllFactions(){
		return factions;
	}
	
	public Set<Faction> getActiveFactions(){
		Set<Faction> actives = new HashSet<Faction>();
		
		for (Faction faction : getAllFactions()){
			if (!faction.isTerminated()){
				actives.add(faction);
			}
		}
		return actives;
	}
	
	private int getTotalNbUnits(){
		int nbUnitsSoFar = 0;
		for (Faction faction : getActiveFactions()){
			nbUnitsSoFar += faction.getNbUnits();
		}
		return nbUnitsSoFar;
	}
	
	
	public void spawnUnit(){
		if (getTotalNbUnits() < ConstantsUtils.MAX_UNITS_WORLD){
			Unit newUnit = createRandomUnit();
			if (getActiveFactions().size() < 5){
				Faction newFaction = new Faction(this);	
				newUnit.setFaction(newFaction);
			}
			else{
				try {
					newUnit.setFaction(getSmallestFaction());
					} catch (IllegalValueException e) {/** TODO?**/}
			}
		}
	}
			
	
	private Unit createRandomUnit() {
		Position positionObj = new Position(this);
		Unit unit = new Unit(positionObj.getRandomPosition(),"name",
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24) ,
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24),
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24), 
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24));
		return unit;
	}

	private Faction getSmallestFaction() {
		Faction smallestFaction = null;
		int smallestNb = Integer.MAX_VALUE;
		for (Faction faction : getActiveFactions()){
			if (faction.getNbUnits() < smallestNb) {
				smallestNb = faction.getNbUnits();
				smallestFaction = faction;
			}
		}
		return smallestFaction;
	}

	private ConnectedToBorder CTBTool;
	private TerrainChangeListener terrainChangeListener;

	
}
