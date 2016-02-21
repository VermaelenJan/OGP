package hillbillies.common.internal.providers;

import java.util.Optional;

import hillbillies.model.Unit;

public interface UnitInfoProvider {

	Optional<double[]> getPosition(Unit unit);
	
	double getSizeX(Unit unit);
	double getSizeY(Unit unit);
	double getSizeZ(Unit unit);
	
	int getOrientationInDegrees(Unit unit);
	
	boolean isSelected(Unit unit);
	boolean isAttacking(Unit unit);
	boolean isWorking(Unit unit);
	boolean isWalking(Unit unit);
	boolean isResting(Unit unit);
	boolean isSprinting(Unit unit);
	
	String getName(Unit unit);

	int getHitpoints(Unit unit);
	int getMaxHitpoints(Unit unit);

	int getStaminapoints(Unit unit);
	int getMaxStaminapoints(Unit unit);

	boolean isDefaultBehaviorEnabled(Unit unit);

	double getSpeed(Unit unit);

	int getAgility(Unit unit);
	int getStrength(Unit unit);
	int getToughness(Unit unit);
	int getWeight(Unit unit);


}
