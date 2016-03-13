package hillbillies.part2.internal.controller;

import java.util.function.Consumer;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.part1.internal.controller.UnitInfoProviderPart1;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import ogp.framework.util.ModelException;

public class GameObjectInfoProvider extends UnitInfoProviderPart1 implements IGameObjectInfoProvider {

	public GameObjectInfoProvider(IGameController2 game, Consumer<ModelException> errorHandler) {
		super(game, errorHandler);
	}

	@Override
	protected IGameController2 getGame() {
		return (IGameController2) super.getGame();
	}

	@Override
	protected IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	@Override
	public int getExperiencePoints(Unit unit) {
		try {
			return getFacade().getExperiencePoints(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return -1;
	}

	@Override
	public boolean isCarryingLog(Unit unit) {
		try {
			return getFacade().isCarryingLog(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) {
		try {
			return getFacade().isCarryingBoulder(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return false;
	}

	@Override
	public Faction getFaction(Unit unit) {
		try {
			return getFacade().getFaction(unit);
		} catch (ModelException e) {
			handleError(e);
		}
		return null;
	}

	@Override
	public int getFactionIndex(Faction faction) {
		return getGame().getFactionIndex(faction);
	}

	@Override
	public String getFactionName(Faction faction) {
		return "Faction " + getGame().getFactionIndex(faction);
	}

	@Override
	public double[] getPosition(Boulder object) {
		try {
			return getFacade().getPosition(object);
		} catch (ModelException e) {
			handleError(e);
		}
		return null;
	}

	@Override
	public double[] getPosition(Log object) {
		try {
			return getFacade().getPosition(object);
		} catch (ModelException e) {
			handleError(e);
		}
		return null;
	}
}
