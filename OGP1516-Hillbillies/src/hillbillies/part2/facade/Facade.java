package hillbillies.part2.facade;

import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.exceptions.IllegalAdjacentPositionException;
import hillbillies.model.exceptions.IllegalAdvanceTimeException;
import hillbillies.model.exceptions.IllegalAttackPosititonException;
import hillbillies.model.exceptions.IllegalNameException;
import hillbillies.model.exceptions.IllegalPositionException;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

public class Facade implements IFacade {

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		Unit unit;
		try {
			unit = new Unit(initialPosition, name, weight, strength, agility, toughness);
		} catch (IllegalPositionException e) {
			throw new ModelException("Illegal Position", e);
		} catch (IllegalNameException e) {
			throw new ModelException("Illegal Name:", e);
		}
		return unit;
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		return unit.getLocation();
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		return unit.getOccupiedCube();
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try {
			unit.setName(newName);
		} catch (IllegalNameException e) {
			throw new ModelException("Illegal Name", e);
		}
	}

	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		unit.setStrength(newValue);
		
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		unit.setAgility(newValue);
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		unit.setToughness(newValue);
	}

	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxHitpointsStamina();
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return (int) unit.getHitpoints();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxHitpointsStamina();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return (int) unit.getStamina();
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try {
			unit.advanceTime(dt);
		} catch (IllegalAdvanceTimeException e) {
			throw new ModelException("Illegal TimePeriod", e);
		}
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try {
			unit.moveToAdjacent(dx,  dy,  dz);
		} catch (IllegalPositionException e) {
			throw new ModelException("Illegal Position", e);
		} catch (IllegalAdjacentPositionException e) {
			throw new ModelException("Illegal Adjacent Position", e);
		}
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getCurrentSpeedMagShow();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isActualMoving();
	}

	@Override
	public void startSprinting(Unit unit) throws ModelException {
		unit.startSprinting();
	}

	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		unit.stopSprinting();
	}

	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.isSprinting();
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try {
			unit.moveTo(cube);
		} catch (IllegalPositionException e) {
			throw new ModelException("Illegal Position", e);
		}
	}

	@Override
	public void work(Unit unit) throws ModelException {
		unit.work();
	}

	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorkingShow();
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		try {
			attacker.attack(defender);
			defender.defend(attacker);
		} catch (IllegalAttackPosititonException e) {
			throw new ModelException("Illegal AttackPosition", e);
		}
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.isAttacking();
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		unit.rest();
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		if (value) {
			unit.startDefaultBehaviour();
		}
		else {
			unit.stopDefaultBehaviour();
		}
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.isDefaultBehaviourEnabled();
	}

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException { //TODO: nakijken!
		
		Cube[][][] worldCubes = 
			new Cube[terrainTypes.length][terrainTypes[0].length][terrainTypes[0][0].length];
		for (int xIndex = 0; xIndex<terrainTypes.length; xIndex++) {
			for (int yIndex = 0; yIndex<terrainTypes[0].length; yIndex++) {
				for (int zIndex = 0; zIndex<terrainTypes[0][0].length; zIndex++) {
					int[] position = {xIndex, yIndex, zIndex};
					hillbillies.model.CubeType cubeType = intToCubeType(terrainTypes[xIndex][yIndex][zIndex]);
					Cube cube = new Cube(position, cubeType);
					worldCubes[xIndex][yIndex][zIndex] = cube;

				}
			}	
		}

		World world = new World(worldCubes, modelListener);
		return world;
		}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getNbCubesX();
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getNbCubesY();
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getNbCubesZ();
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		world.advanceTime(dt);
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		return cubeTypeToInt(world.getCubeType(x, y, z));
	}
	
	private int cubeTypeToInt(hillbillies.model.CubeType cubeType) { //TODO: mag dit? haha
		switch (cubeType) {
		case AIR:
			return 0;
		case ROCK:
			return 1;
		case WOOD:
			return 2;
		case WORKSHOP:
			return 3;
		default:
			return -1;
		}
	}
	
	private hillbillies.model.CubeType intToCubeType(int i) { //TODO: mag dit? haha
		switch (i) {
		case 1:
			return hillbillies.model.CubeType.ROCK;
		case 2:
			return hillbillies.model.CubeType.WOOD;
		case 3:
			return hillbillies.model.CubeType.WORKSHOP;
		default:
			return hillbillies.model.CubeType.AIR;
		}
	}
	

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		world.setCubeType(x, y, z, intToCubeType(value));
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException { //TODO: in worldclass!!
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		Unit unit = world.spawnUnit();
		if (enableDefaultBehavior) {
			unit.startDefaultBehaviour();
		}
		return unit;
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		unit.setWorld(world); //TODO: zeker nakijken (faction-kies-algoritme, ...)
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		return world.getAllUnits(); //TODO: zeker nakijken
	}

	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return (! unit.isTerminated()); //TODO: of werken met hp?
	}

	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		return faction.getUnits();
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		return world.getActiveFactions();
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		return boulder.getLocation();
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		return world.getBoulders();
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		return log.getLocation();
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		return world.getLogs();
	}

}
