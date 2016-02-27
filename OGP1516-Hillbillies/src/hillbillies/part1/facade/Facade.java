package hillbillies.part1.facade;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.IllegalAttackPosititonException;
import hillbillies.model.IllegalNameException;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;
import ogp.framework.util.ModelException;

//TODO: complete

public class Facade implements IFacade {

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		List<Integer> position = new ArrayList<Integer>();
		position.add(initialPosition[0]); position.add(initialPosition[1]); position.add(initialPosition[2]);
		Unit unit = null;
		try {
			unit = new Unit(position, name, weight, strength, agility, toughness);
		} catch (IllegalPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unit;
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		List<Double> position = unit.getLocation();
		double[] location = {position.get(0), position.get(1), position.get(2)};
		return location;
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		List<Integer> cube = unit.getOccupiedCube();
		int[] locCube = {cube.get(0), cube.get(1), cube.get(2)};
		return locCube;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		unit.advanceTime(dt);
		
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try {
			unit.moveToAdjacent(dx,  dy,  dz);
		} catch (IllegalPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getCurrentSpeedMag();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
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
		List<Integer> target = new ArrayList<Integer>();
		target.add(cube[0]); target.add(cube[1]); target.add(cube[2]);
		try {
			unit.moveTo(target);
		} catch (IllegalPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void work(Unit unit) throws ModelException {
		unit.work();
		
	}

	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		try {
			attacker.attack(defender);
			defender.defend(attacker);
		} catch (IllegalAttackPosititonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
