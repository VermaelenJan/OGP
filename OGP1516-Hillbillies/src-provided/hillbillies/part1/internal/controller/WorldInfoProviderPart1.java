package hillbillies.part1.internal.controller;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;
import hillbillies.part1.internal.map.EmptyMap;
import ogp.framework.util.ModelException;

public class WorldInfoProviderPart1 implements WorldInfoProvider {

	private final GameControllerPart1 game;
	private final Consumer<ModelException> errorHandler;

	public WorldInfoProviderPart1(GameControllerPart1 game, Consumer<ModelException> errorHandler) {
		this.game = game;
		this.errorHandler = errorHandler;
	}

	protected GameControllerPart1 getGame() {
		return game;
	}

	protected IFacade getFacade() {
		return getGame().getFacade();
	}

	public EmptyMap getMap() {
		return getGame().getMap();
	}

	protected Set<Unit> getUnits() {
		return getGame().getUnits();
	}

	@Override
	public int getNbZTiles() {
		return getMap().getNbZ();
	}

	@Override
	public int getNbYTiles() {
		return getMap().getNbY();
	}

	@Override
	public int getNbXTiles() {
		return getMap().getNbX();
	}

	@Override
	public Set<?> getObjectsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		Set<Unit> result = getUnits().stream()
				.filter(u -> unitLiesInBox(u, new double[] { minX, minY, minZ }, new double[] { maxX, maxY, maxZ }))
				.collect(Collectors.toSet());
		return result;
	}

	private boolean unitLiesInBox(Unit unit, double[] low, double[] high) {
		try {
			double[] position = getFacade().getPosition(unit);
			return position != null && liesInBox(position, low, high);
		} catch (ModelException e) {
			errorHandler.accept(e);
			return false;
		}
	}

	private boolean liesInBox(double[] position, double[] low, double[] high) {
		return low[0] <= position[0] && position[0] < high[0] && low[1] <= position[1] && position[1] < high[1]
				&& low[2] <= position[2] && position[2] < high[2];
	};

}
