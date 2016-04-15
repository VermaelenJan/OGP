package hillbillies.part1.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.controller.AbstractSelectionMode;
import hillbillies.common.internal.controller.BaseActionExecutor;
import hillbillies.common.internal.controller.CubeSelectionMode;
import hillbillies.common.internal.controller.CubeSelectionMode.Cube;
import hillbillies.common.internal.controller.UnitSelectionMode;
import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.selection.Selection;
import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;
import ogp.framework.util.ModelException;

public class ActionExecutorPart1 extends BaseActionExecutor {

	public ActionExecutorPart1(IGameController1<?> game, Consumer<ModelException> errorHandler) {
		super(game, errorHandler);
	}

	@Override
	public IGameController1<?> getGame() {
		return (IGameController1<?>) super.getGame();
	}

	@Override
	public IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	@Override
	public void toggleDefaultBehavior() {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().setDefaultBehaviorEnabled(unit, !getFacade().isDefaultBehaviorEnabled(unit));
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	@Override
	public void initiateMove() {
		selectCube("Select a cube to move to", c -> moveTo(c.cubeX, c.cubeY, c.cubeZ));
	}

	protected Selection getSelection() {
		return getGame().getSelectionProvider().getSelection();
	}

	@Override
	public void initiateCreateUnit() {
		selectCube("Select a cube to put the new unit on", c -> createUnit(c.cubeX, c.cubeY, c.cubeZ));
	}

	@Override
	public void createUnit(int cubeX, int cubeY, int cubeZ) {
		getGame().createUnit(cubeX, cubeY, cubeZ);
	}

	@Override
	public void setName(String newName) {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().setName(unit, newName);
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	@Override
	public void selectNext() {
		getGame().selectNextUnit();
	}

	@Override
	public void initiateAttack() {
		selectUnit("Select a unit to attack", unit -> attack(unit));
	}

	protected void selectUnit(String info, Consumer<Unit> action) {
		select(info, createUnitSelectionMode(), action);
	}

	protected void selectCube(String info, Consumer<Cube> action) {
		select(info, createCubeSelectionMode(), action);
	}

	protected <T> void select(String info, AbstractSelectionMode<T> selMode, Consumer<T> action) {
		getGame().getView().setStatusText(info);
		InputMode oldMode = getGame().getCurrentInputMode();
		selMode.setOnCanceled(() -> getGame().switchInputMode(oldMode));
		selMode.setOnSelected(unit -> {
			getGame().switchInputMode(oldMode);
			if (unit != null) {
				action.accept(unit);
			}
		});
		getGame().switchInputMode(selMode);
	}

	protected UnitSelectionMode createUnitSelectionMode() {
		return new UnitSelectionMode(getGame());
	}

	protected CubeSelectionMode createCubeSelectionMode() {
		return new CubeSelectionMode(getGame());
	}

	@Override
	public void attack(Unit defender) {
		for (Unit attacker : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().fight(attacker, defender);
				getGame().getView().setStatusText("Fighting");
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	@Override
	public void moveToAdjacent(int dx, int dy, int dz) {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().moveToAdjacent(unit, dx, dy, dz);
			} catch (ModelException e) {
				handleError(e);
			}
		}
	};

	@Override
	public void moveTo(int cubeX, int cubeY, int cubeZ) {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().moveTo(unit, new int[] { cubeX, cubeY, cubeZ });
				getGame().getView().setStatusText("Moving to selected cube");
			} catch (ModelException e) {
				handleError(e);
			}
		}
	};

	@Override
	public void rest() {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().rest(unit);
				getGame().getView().setStatusText("Resting");
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	@Override
	public void work() {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				getFacade().work(unit);
				getGame().getView().setStatusText("Working");
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	@Override
	public void toggleSprint() {
		for (Unit unit : getSelection().getObjects(Unit.class)) {
			try {
				if (getFacade().isSprinting(unit)) {
					getFacade().stopSprinting(unit);
				} else {
					getFacade().startSprinting(unit);
				}
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

}
