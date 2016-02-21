package hillbillies.common.internal.providers;

import hillbillies.model.Unit;

public interface ActionExecutor {

	void moveToAdjacent(int dx, int dy, int dz);

	void initiateMove();
	
	void moveTo(int cubeX, int cubeY, int cubeZ);

	void rest();

	void work();
	
	void attack(Unit unit);

	void toggleSprint();

	void selectNext();

	void setName(String text);

	void initiateCreateUnit();

	void createUnit(int cubeX, int cubeY, int cubeZ);

	void initiateAttack();

	void toggleDefaultBehavior();

}
