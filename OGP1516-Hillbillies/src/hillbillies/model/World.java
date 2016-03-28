package hillbillies.model;

import java.util.Arrays;
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
	
	public World(Cube[][][] worldCubes,TerrainChangeListener terrainChangeListener){
		this.worldCubes = worldCubes;
		this.worldX = worldCubes.length;
		this.worldY = worldCubes[0].length;
		this.worldZ = worldCubes[0][0].length;
		this.terrainChangeListener = terrainChangeListener;
		this.CTBTool = new ConnectedToBorder(worldX,worldY,worldZ);
		
		for (int xIndex = 0; xIndex<worldX; xIndex++) {
			for (int yIndex = 0; yIndex<worldY; yIndex++) {
				for (int zIndex = 0; zIndex<worldZ; zIndex++) {
					if (worldCubes[xIndex][yIndex][zIndex].getCubeType().isPassableTerrain()){
						CTBTool.changeSolidToPassable(xIndex, yIndex, zIndex);
					}
				}	
			}	
		}
		updateConnectedTerrain();
	}

	
	public final int getNbCubesX(){
		return worldX;
	}
	
	public final int getNbCubesY(){
		return worldY;
	}
	
	public final int getNbCubesZ(){
		return worldZ;
	}
	
	int worldX;
	int worldY;
	int worldZ;
	
	private void updateConnectedTerrain() {
		for (int xIndex = 0; xIndex<getNbCubesX(); xIndex++) {
			for (int yIndex = 0; yIndex<getNbCubesY(); yIndex++) {
				for (int zIndex = 0; zIndex<getNbCubesZ(); zIndex++) {
					if ((!(getCubeType(xIndex, yIndex, zIndex).isPassableTerrain()))
							&& (!CTBTool.isSolidConnectedToBorder(xIndex, yIndex, zIndex))){
								caveIn(xIndex,yIndex,zIndex);
					}
				}
			}	
		}
	}
		
	Cube[][][] worldCubes;
	
	public void setCubeType(int x,int y, int z, CubeType cubeType){
		if (cubeType.isPassableTerrain() && !this.getCubeType(x, y, z).isPassableTerrain()){
			CTBTool.changeSolidToPassable(x, y, z);
		}
		else if (!cubeType.isPassableTerrain() && this.getCubeType(x, y, z).isPassableTerrain()) {
			CTBTool.changePassableToSolid(x, y, z);
		}
		worldCubes[x][y][z].setCubeType(cubeType);

	}
	
	public CubeType getCubeType(int x,int y, int z){
		return worldCubes[x][y][z].getCubeType();
	}
	
	protected Cube getCube(int x, int y, int z) {
		return worldCubes[x][y][z];
	}
	
	
	public void advanceTime(double dt){
		
		for (Unit unit : getAllUnits()) {
			unit.advanceTime(dt);
		}
		for (Object object : getBoulders()) {
			object.advanceTime(dt);
		}
		for (Object object : getLogs()) {
			object.advanceTime(dt);
		}
	}
	
	protected void caveIn(int x,int y,int z){
		
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
	
	protected Set<Boulder> getBoulders(){
		return boulders;
	}
	
	public Set<Boulder> getBouldersWorld() {
		Set<Boulder> bouldersInWorld = new HashSet<Boulder>();
		for (Boulder boulder : getBoulders()) {
			if (!boulder.isTerminated()){
				bouldersInWorld.add(boulder);
			}
		}
		return bouldersInWorld;
	}
	
	Set<Log> logs = new HashSet<Log>();
	
	protected Set<Log> getLogs(){
		return logs;
	}
	
	public Set<Log> getLogsWorld() {
		Set<Log> logsInWorld = new HashSet<Log>();
		for (Log log : getLogs()) {
			if (!log.isTerminated()){
				logsInWorld.add(log);
			}
		}
		return logsInWorld;
	}
	
	protected Boulder getBoulderAtCube(int[] location){
		for (Boulder boulder : getBouldersWorld()){
			if (Arrays.equals(boulder.getOccupiedCube(), location)){
				return boulder;
			}
		}
		return null;  // TODO wat als er geen boulder of log is?
	}
	
	protected Log getLogAtCube(int[] location){
		for (Log log : getLogsWorld()){
			if (Arrays.equals(log.getOccupiedCube(), location)){
				return log;
			}
		}
		return null;  // TODO wat als er geen boulder of log is?
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
	
	public Set<Unit> getAllUnits() {
		Set<Unit> allUnitsSoFar = new HashSet<>();
		for (Faction faction : getActiveFactions()) {
			allUnitsSoFar.addAll(faction.getUnits());
		}
		return allUnitsSoFar;
	}
	
	
	public Unit spawnUnit(){
		Unit newUnit = createRandomUnit();
		newUnit.setWorld(this);
		if (getTotalNbUnits() < ConstantsUtils.MAX_UNITS_WORLD){
			if (getActiveFactions().size() < 5){
				Faction newFaction = new Faction(this);	
				newUnit.setFaction(newFaction);
				newFaction.addUnit(newUnit);
			}
			else{
				try {
					newUnit.setFaction(getSmallestFaction());
					getSmallestFaction().addUnit(newUnit);
					} catch (IllegalValueException e) {/** TODO? not in part 2, 100 < 5*50**/}
			}
			return newUnit;
		}
		newUnit.terminate();
		return newUnit;
	}
			
	
	private Unit createRandomUnit() {
		Position positionObj = new Position(this);

		Unit unit = new Unit(positionObj.getRandomPosition(),"Name",
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24) ,
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24),
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24), 
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24),this);

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

	public boolean isSolidConnectedToBorder(int x, int y, int z) {
		return this.CTBTool.isSolidConnectedToBorder(x, y, z);
	}	
}
