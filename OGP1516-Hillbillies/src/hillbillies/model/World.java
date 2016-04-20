package hillbillies.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import hillbillies.model.exceptions.IllegalValueException;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;


/**
 * A class of worlds in which units can conduct activities.
 * 
 * @invar Each cube of a world must be either wood, rock, air or workshop.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class World {
	
	/**
	 * Initialize this world with the given worldcubes.
	 * 
	 * @param worldCubes
	 * 		The worldcubes for this new world.
	 * @param terrainChangeListener
	 * 		The terrainChangeListener for this new world.
	 * 
	 * @post The x, y and z dimensions of this new world are equal to 
	 * 	the x, y and z dimensions of the worldcubes.
	 * @effect If a worldcube on a position x,y,z is passable terrain, 
	 * 		the connected to border tool is notified to change from solid to passable.
	 * @effect The terrain is updated.
	 * 
	 */
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
	
	
	/**
	 * Update the solid terrain for all cubes.
	 * 
	 * @effect If any of the cubes is solid terrain and that cube is not connected to the border
	 * 		directly or via other solid cubes, the cube caves in.
	 */
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
	
	/**
	 * Set the cube on x, y and z position to the given cubetype.
	 * 
	 * @param x
	 * 		The x position of the cube to set.
	 * @param y
	 * 		The y position of the cube to set. 
	 * @param z
	 * 		The z position of the cube to set.
	 * @param cubeType
	 * 		The new cubetype for this cube.
	 * 
	 * @effect If the cubetype to set is passable terrain, and the cubetype of the
	 * 		cube on x,y and z position is not passable terrain, the connected to border tool is notified
	 * 		to change solid to passable.
	 * @effect If the cubetype to set is not passable terrain and the cubetype of the 
	 * 		cube on x,y and z position is passable terrain, the connected to border tool is notified
	 * 		to change passable to solid.
	 * @effect The cube at x,y and z position is set in worldcubes to the given cubetype.
	 * @effect The terrain is updated.
	 */
	public void setCubeType(int x,int y, int z, CubeType cubeType){
		if (cubeType.isPassableTerrain() && !this.getCubeType(x, y, z).isPassableTerrain()){
			CTBTool.changeSolidToPassable(x, y, z);
		}
		else if (!cubeType.isPassableTerrain() && this.getCubeType(x, y, z).isPassableTerrain()) {
			CTBTool.changePassableToSolid(x, y, z);
		}
		worldCubes[x][y][z].setCubeType(cubeType);
		updateConnectedTerrain();
	}
	
	/**
	 * Return the cube type at the given x,y and z position.
	 * 
	 * @param x
	 * 		The x position of the cube.
	 * @param y
	 * 		The y position of the cube.
	 * @param z
	 * 		The z position of the cube.
	 * @return The cubetype of the cube at the given x,y and z position of worldcubes.
	 */
	public CubeType getCubeType(int x,int y, int z){
		return worldCubes[x][y][z].getCubeType();
	}
	
	/**
	 * Return the cube at the given x,y and z position.
	 * 
	 * @param x
	 * 		The x position of the cube.
	 * @param y
	 * 		The y position of the cube.
	 * @param z
	 * 		The z position of the cube.
	 * @return The cube at the given x,y and z position of worldcubes.
	 */
	protected Cube getCube(int x, int y, int z) {
		return worldCubes[x][y][z];
	}
	
	/**
	 * Advance the game with time step dt. 
	 * 
	 * @param dt
	 * 		The duration dt which the game is advanced.
	 * @effect time is advanced for all units.
	 * @effect time is advanced for all boulders.
	 * @effect time is advanced forr all logs.
	 */
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
	
	/**
	 * Cave in the solid terrain at given x,y and z position.
	 * 
	 * @param x
	 * 		The x position of the cube to cave in.
	 * @param y
	 * 		The y position of the cube to cave in.
	 * @param z
	 * 		The z position of the cube to cave in.
	 * 
	 * @effect The cubetype of the cube at x,y and z position is air.
	 * @effect The connected to border tool is notified of the changed terrain.
	 * @effect With a chance of 25% , if the cubetype was rock, there is a new bolder
	 * 		on the x,y and z position.
	 * @effect With a chance of 25% , if the cubetype was wood, there is a new log
	 * 		on the x,y and z position.
	 */
	protected void caveIn(int x,int y,int z){
		
		CubeType cubeType = getCubeType(x, y, z);
		CTBTool.changeSolidToPassable(x, y, z);
		setCubeType(x, y, z, CubeType.AIR);
		terrainChangeListener.notifyTerrainChanged(x, y, z);
		
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
	}
	

	
	/**
	 * Check whether the cube at position x,y,z is directly or indirectly
	 * connected to the borders via other solid cubes.
	 * 
	 * @param x
	 * 		The x position to check.
	 * @param y
	 * 		The y position to check.
	 * @param z
	 * 		The z position to check.
	 * @return True if and only if the cube at x,y,z is connected to the borders
	 * 		directly or indirectly via other solid cubes.
	 */
	public boolean isSolidConnectedToBorder(int x, int y, int z) {
		return this.CTBTool.isSolidConnectedToBorder(x, y, z);
	}	

	
	/**
	 * Variable registering the connected to border tool of this world.
	 */
	private ConnectedToBorder CTBTool;
	
	/**
	 * Variable registeirng the terrainchangelistener of this world.
	 */
	private TerrainChangeListener terrainChangeListener;


	
	/**
	 * Variable registering the wordcubes.
	 */
	Cube[][][] worldCubes;

	/**
	 * Return the number of cubes of this world in the x direction.
	 */
	@Basic @Immutable
	public final int getNbCubesX(){
		return worldX;
	}
	
	/**
	 * Return the number of cubes of this world in the y direction.
	 */
	@Basic @Immutable
	public final int getNbCubesY(){
		return worldY;
	}
	
	/**
	 * Return the number of cubes of this world in the z direction.
	 */
	public final int getNbCubesZ(){
		return worldZ;
	}
	
	/**
	 * Variables registering the dimensions of this world.
	 */
	int worldX;
	int worldY;
	int worldZ;

	// BOULDER
	
	/**
	 * Return a set with all the boulders of this world.
	 */
	protected Set<Boulder> getBoulders(){
		return boulders;
	}
	
	/**
	 * Return a set with all the boulders of this world which are not terminated.
	 * 
	 * @effect If the boulder is not terminated, the boulder is in the set to return.
	 */
	public Set<Boulder> getBouldersWorld() {
		Set<Boulder> bouldersInWorld = new HashSet<Boulder>();
		for (Boulder boulder : getBoulders()) {
			if (!boulder.isTerminated()){
				bouldersInWorld.add(boulder);
			}
		}
		return bouldersInWorld;
	}
	
	/**
	 * Return the boulder at the given location.
	 * 
	 * @param location
	 * 		The location to check for a boulder.
	 * @return a boulder if one is present on the given location, null otherwise.
	 */
	protected Boulder getBoulderAtCube(int[] location){
		for (Boulder boulder : getBouldersWorld()){
			if (Arrays.equals(boulder.getOccupiedCube(), location)){
				return boulder;
			}
		}
		return null;  
	}
	
	/**
	 * @invar Each boulder referenced in this set is effective.
	 */
	Set<Boulder> boulders = new HashSet<Boulder>();
	
	
	// LOGS
	
	/**
	 * Return a set with all the logs of this world.
	 */
	protected Set<Log> getLogs(){
		return logs;
	}
	
	/**
	 * Return a set with all the logs of this world which are not terminated.
	 * 
	 * @effect If the log is not terminated, the log is in the set to return.
	 */
	public Set<Log> getLogsWorld() {
		Set<Log> logsInWorld = new HashSet<Log>();
		for (Log log : getLogs()) {
			if (!log.isTerminated()){
				logsInWorld.add(log);
			}
		}
		return logsInWorld;
	}
	
	/**
	 * Return the log at the given location.
	 * 
	 * @param location
	 * 		The location to check for a log.
	 * @return a log if one is present on the given location, null otherwise.
	 */
	protected Log getLogAtCube(int[] location){
		for (Log log : getLogsWorld()){
			if (Arrays.equals(log.getOccupiedCube(), location)){
				return log;
			}
		}
		return null;  
	}
	
	/**
	 * @invar Each log referenced in this set is effective.
	 */
	Set<Log> logs = new HashSet<Log>();

	
	// FACTION 
	
	/**
	 * Return a set with all the factions.
	 */
	private Set<Faction> getAllFactions(){
		return factions;
	}
	
	/**
	 * Return a set with all the active(= not terminated) factions.
	 */
	public Set<Faction> getActiveFactions(){
		Set<Faction> actives = new HashSet<Faction>();
		
		for (Faction faction : getAllFactions()){
			if (!faction.isTerminated()){
				actives.add(faction);
			}
		}
		return actives;
	}
	
	/**
	 * Return the smallest active faction.
	 */
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
	
	/**
	 * Add the given faction to the set of factions of this world.
	 * @param faction
	 * 		The faction to be added.
	 * @pre The given faction is effective and already referencing this world.
	 * @post This world has the given faction as one of its factions.
	 */
	protected void addFaction(Faction faction) {
		assert ((faction != null) && (faction.getWorld() == this) );
		factions.add(faction);
	}
	
	
	/**
	 * @invar Each faction in this set is effective.
	 */
	Set<Faction> factions = new HashSet<Faction>();
	
	
	// UNITS
	
	/**
	 * Return the number of units associated with this world.
	 * 
	 * @return The total number of non terminated units collected in this world. //TODO: niet alleen de nonTerminated worden teruggegeven!
	 */
	private int getTotalNbUnits(){
		int nbUnitsSoFar = 0;
		for (Faction faction : getActiveFactions()){
			nbUnitsSoFar += faction.getNbUnits();
		}
		return nbUnitsSoFar;
	}
	
	/**
	 * Return all the non terminated units of this world.
	 */
	public Set<Unit> getAllUnits() {
		Set<Unit> allUnitsSoFar = new HashSet<>();
		for (Faction faction : getActiveFactions()) {
			allUnitsSoFar.addAll(faction.getUnits());
		}
		return allUnitsSoFar;
	}
	
	/**
	 * Spawn a unit at a random position in this world.
	 * 
	 * @return A new unit at a random position in this world if the unit can
	 * 			be added.
	 * 		  Otherwise, a new unit that is immediatly terminated.
	 */
	public Unit spawnUnit(){
		Unit newUnit = createRandomUnit();
		if(!this.getAllUnits().contains(newUnit)) {
			newUnit.terminate();
		}
		
		return newUnit;
	}

	/**
	 * 
	 * @param unit
	 * 		The unit to add.
	 * @post This world (its smallest faction) contains the unit if the
	 * 		total amount of units of this world is smaller than the maximum
	 * 		amount of units allowed in this world. A new faction was created
	 * 		if the amount of factions was smaller than the maximum allowed amount.
	 * 		
	 */
	protected void addUnit(Unit unit) {
		if (getTotalNbUnits() < ConstantsUtils.MAX_UNITS_WORLD){
			if (getActiveFactions().size() < ConstantsUtils.MAX_FACTIONS){
				Faction newFaction = new Faction(this);	
				unit.setFaction(newFaction);
				newFaction.addUnit(unit);
			}
			else{ // else if (try-catch-condition (max Units in world))
				try {
					unit.setFaction(getSmallestFaction());
					getSmallestFaction().addUnit(unit);
				} catch (IllegalValueException e) {/** TODO? not in part 2, 100 < 5*50**/}
			}
		}
	}
			
	/**
	 * Create a random unit.
	 * 
	 * @return A unit with a random position in this world, with "Name" as default name, and with random attributes
	 * 		   for strength, agility, toughness and weight.
	 */
	private Unit createRandomUnit() {
		Position positionObj = new Position(this);

		Unit unit = new Unit(positionObj.getRandomPosition(),"Name",
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24) ,
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24),
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24), 
				ConstantsUtils.INIT_MIN_VAL+ConstantsUtils.random.nextInt(ConstantsUtils.INIT_MAX_VAL-24),this);
		
		return unit;
	}
}
