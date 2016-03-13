package hillbillies.part2.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.controller.UnitSelectionMode;
import hillbillies.model.Unit;
import hillbillies.part1.internal.controller.ActionExecutorPart1;
import hillbillies.part2.facade.IFacade;
import ogp.framework.util.ModelException;

public class ActionExecutorPart2 extends ActionExecutorPart1 {

	public ActionExecutorPart2(IGameController2 game, Consumer<ModelException> errorHandler) {
		super(game, errorHandler);
	}

	@Override
	public IGameController2 getGame() {
		return (IGameController2) super.getGame();
	}

	@Override
	public IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	public void initiateWork() {
		selectCube("Select a cube to work on", c -> workAt(c.cubeX, c.cubeY, c.cubeZ));
	}

	@Override
	@Deprecated
	public void work() {
		throw new NoSuchMethodError("this method is no longer supported");
	}

	@Override
	public void attack(Unit defender) {
		if (!canControlSelectedUnits())
			return;
		super.attack(defender);
	}

	@Override
	public void initiateAttack() {
		if (!canControlSelectedUnits())
			return;
		super.initiateAttack();
	}

	@Override
	public void initiateMove() {
		if (!canControlSelectedUnits())
			return;
		super.initiateMove();
	}

	@Override
	public void moveTo(int cubeX, int cubeY, int cubeZ) {
		if (!canControlSelectedUnits())
			return;
		super.moveTo(cubeX, cubeY, cubeZ);
	}

	@Override
	public void moveToAdjacent(int dx, int dy, int dz) {
		if (!canControlSelectedUnits())
			return;
		super.moveToAdjacent(dx, dy, dz);
	}

	@Override
	public void rest() {
		if (!canControlSelectedUnits())
			return;
		super.rest();
	}

	@Override
	public void toggleSprint() {
		if (!canControlSelectedUnits())
			return;
		super.toggleSprint();
	}

	@Override
	public void toggleDefaultBehavior() {
		if (!canControlSelectedUnits())
			return;
		super.toggleDefaultBehavior();
	}

	public void workAt(int cubeX, int cubeY, int cubeZ) {
		if (!canControlSelectedUnits())
			return;
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().workAt(unit, cubeX, cubeY, cubeZ);
				getGame().getView().setStatusText("Working on selected cube");
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	@Override
	protected UnitSelectionMode createUnitSelectionMode() {
		return new UnitSelectionMode2(getGame());
	}

	protected boolean canControlSelectedUnits() {
		if (!getGame().getOptions().onlyControlFirstFaction().getValue()) {
			return true;
		}
		boolean allPlayerControlled = getSelection().getObjects(Unit.class).stream()
				.allMatch(u -> getGame().isPlayerUnit(u));
		if (!allPlayerControlled) {
			getGame().getView().setStatusText("I don't listen to you. Try someone else!");
		}
		return allPlayerControlled;
	}

	public void selectNextFaction() {
		getGame().selectNextFaction();
	}

	public void spawnUnits(int n) {
		getGame().spawnUnits(n);
	}

}
