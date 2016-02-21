package hillbillies.part1.internal.controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import hillbillies.common.internal.controller.GameController;
import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.common.internal.ui.ViewProviders;
import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;
import hillbillies.part1.internal.Part1Options;
import hillbillies.part1.internal.map.EmptyMap;
import ogp.framework.util.ModelException;

public class GameControllerPart1 extends GameController<IFacade> {

	private final EmptyMap map;

	private final Set<Unit> units = new HashSet<>();

	public GameControllerPart1(IFacade facade, Part1Options options) {
		super(facade, options);
		this.map = new EmptyMap(50, 50, 50);
	}

	@Override
	public Part1Options getOptions() {
		return (Part1Options) super.getOptions();
	}

	@Override
	protected InputMode createDefaultInputMode() {
		return new Part1InputMode(this);
	}

	public EmptyMap getMap() {
		return map;
	}

	private final WorldInfoProvider wip = new WorldInfoProviderPart1(this, this::handleError);

	private final UnitInfoProvider uip = new UnitInfoProviderPart1<>(this, this::handleError);

	private final ActionExecutor wae = new ActionExecutorPart1<>(this, this::handleError);

	@Override
	public void updateGame(double dt) {
		for (Unit unit : units) {
			try {
				getFacade().advanceTime(unit, dt);
			} catch (ModelException e) {
				e.printStackTrace();
				System.out.println("Error while advancing time; continuing...");
			}
		}
	}

	@Override
	public WorldInfoProvider getWorldInfoProvider() {
		return wip;
	}

	@Override
	public UnitInfoProvider getUnitInfoProvider() {
		return uip;
	}

	@Override
	public ActionExecutor getActionExecutor() {
		return wae;
	}

	public Set<Unit> getUnits() {
		return units;
	}

	@Override
	public void createUnit(int x, int y, int z) {
		try {
			int weight = 50;
			int agility = 50;
			int strength = 50;
			int toughness = 50;
			Unit unit = getFacade().createUnit("Hillbilly", new int[] { x, y, z }, weight, agility, strength, toughness,
					false);
			if (unit != null) {
				units.add(unit);
				getView().setStatusText("Unit created");
			} else {
				getView().setStatusText("New unit was null :(");
			}
		} catch (ModelException e) {
			handleError(e);
		}
	}

	private Queue<Object> selectionQueue = new LinkedList<>();

	@Override
	public <T> T getNextObject(Class<T> type) {
		if (selectionQueue.isEmpty()) {
			selectionQueue.addAll(units);
		}
		for (Object obj : selectionQueue) {
			if (type.isInstance(obj)) {
				selectionQueue.remove(obj);
				return type.cast(obj);
			}
		}
		return null;
	}

	@Override
	public ViewProviders createViewProviders() {
		return new ViewProviders(getUnitInfoProvider(), getWorldInfoProvider(), getActionExecutor(),
				getSelectionProvider());
	}

}
