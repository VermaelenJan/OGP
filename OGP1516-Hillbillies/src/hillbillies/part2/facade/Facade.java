package hillbillies.part2.facade;

import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

public class Facade implements IFacade {

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWeight(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startSprinting(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void work(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

}
