package hillbillies.part1.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.controller.GameController;
import hillbillies.common.internal.controller.UnitSelectionMode;
import hillbillies.common.internal.controller.CubeSelectionMode;
import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.selection.Selection;
import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;
import ogp.framework.util.ModelException;

public class ActionExecutorPart1<F extends IFacade> extends BaseActionExecutor<F> {

	public ActionExecutorPart1(GameController<F> game, Consumer<ModelException> errorHandler) {
		super(game, errorHandler);
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
		InputMode oldMode = getGame().getCurrentInputMode();
		CubeSelectionMode mode = new CubeSelectionMode(getGame());
		mode.setOnSelected(() -> {
			getGame().switchInputMode(oldMode);
			moveTo(mode.getCubeX(), mode.getCubeY(), mode.getCubeZ());
		});
		getGame().getView().setStatusText("Select a cube to move to");
		getGame().switchInputMode(mode);
	}

	protected Selection getSelection() {
		return getGame().getSelectionProvider().getSelection();
	}

	@Override
	public void initiateCreateUnit() {
		InputMode oldMode = getGame().getCurrentInputMode();
		CubeSelectionMode mode = new CubeSelectionMode(getGame());
		mode.setOnSelected(() -> {
			getGame().switchInputMode(oldMode);
			createUnit(mode.getCubeX(), mode.getCubeY(), mode.getCubeZ());
		});
		getGame().getView().setStatusText("Select a cube to put the new unit on");
		getGame().switchInputMode(mode);
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
		Unit unit = getGame().getNextObject(Unit.class);
		getSelection().select(unit, true);
	}

	@Override
	public void initiateAttack() {
		InputMode oldMode = getGame().getCurrentInputMode();
		UnitSelectionMode selMode = new UnitSelectionMode(getGame());
		selMode.setOnSelected(unit -> {
			getGame().switchInputMode(oldMode);
			if (unit != null) {
				attack(unit);
			}
		});
		getGame().getView().setStatusText("Select a unit to attack");
		getGame().switchInputMode(selMode);

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
