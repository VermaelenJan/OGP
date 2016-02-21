package hillbillies.part1.internal.controller;

import java.util.Optional;
import java.util.function.Consumer;

import hillbillies.common.internal.selection.Selection;
import hillbillies.common.internal.controller.GameController;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;
import ogp.framework.util.ModelException;

public class UnitInfoProviderPart1<F extends IFacade> implements UnitInfoProvider {
	private final F facade;
	private final Consumer<ModelException> errorHandler;
	private final GameController<F> game;

	public UnitInfoProviderPart1(GameController<F> game, Consumer<ModelException> errorHandler) {
		this.game = game;
		this.facade = game.getFacade();
		this.errorHandler = errorHandler;
	}

	protected F getFacade() {
		return facade;
	}

	protected GameController<F> getGame() {
		return game;
	}

	protected void handleError(ModelException e) {
		errorHandler.accept(e);
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) {
		try {
			return getFacade().isDefaultBehaviorEnabled(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public int getHitpoints(Unit unit) {
		try {
			return getFacade().getCurrentHitPoints(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public int getMaxHitpoints(Unit unit) {
		try {
			return getFacade().getMaxHitPoints(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public int getStaminapoints(Unit unit) {
		try {
			return getFacade().getCurrentStaminaPoints(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public int getMaxStaminapoints(Unit unit) {
		try {
			return getFacade().getMaxStaminaPoints(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public String getName(Unit unit) {
		try {
			return getFacade().getName(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return "";
	}

	@Override
	public boolean isWorking(Unit unit) {
		try {
			return getFacade().isWorking(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public boolean isWalking(Unit unit) {
		try {
			return getFacade().isMoving(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public boolean isResting(Unit unit) {
		try {
			return getFacade().isResting(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public boolean isSprinting(Unit unit) {
		try {
			return getFacade().isSprinting(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	protected Selection getSelection() {
		return getGame().getSelectionProvider().getSelection();
	}

	@Override
	public boolean isSelected(Unit unit) {
		return getSelection().isSelected(unit);
	}

	@Override
	public boolean isAttacking(Unit unit) {
		try {
			return getFacade().isAttacking(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public double getSizeZ(Unit unit) {
		return 1 - 1e-6; // TODO
	}

	@Override
	public double getSizeY(Unit unit) {
		return 1 - 1e-6; // TODO
	}

	@Override
	public double getSizeX(Unit unit) {
		return 1 - 1e-6; // TODO
	}

	@Override
	public int getOrientationInDegrees(Unit unit) {
		try {
			double orientationInRadians = facade.getOrientation(unit);
			return (int) ((180.0 * orientationInRadians / Math.PI) + 360) % 360;
		} catch (ModelException e) {
			handleError(e);
		}
		return 0;
	}

	@Override
	public Optional<double[]> getPosition(Unit unit) {
		try {
			return Optional.ofNullable(getFacade().getPosition(unit));
		} catch (ModelException e) {
			handleError(e);
		}
		return Optional.empty();
	}

	@Override
	public double getSpeed(Unit unit) {
		try {
			return getFacade().getCurrentSpeed(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return Double.NaN;
	}

	@Override
	public int getAgility(Unit unit) {
		try {
			return getFacade().getAgility(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public int getStrength(Unit unit) {
		try {
			return getFacade().getStrength(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public int getToughness(Unit unit) {
		try {
			return getFacade().getToughness(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public int getWeight(Unit unit) {
		try {
			return getFacade().getWeight(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

}
