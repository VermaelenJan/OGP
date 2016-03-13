package hillbillies.part2.internal.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import hillbillies.common.internal.controller.GameController;
import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.selection.Selection;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.internal.Constants;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import hillbillies.part2.internal.providers.WorldInfoProvider2;
import hillbillies.part2.internal.ui.IHillbilliesView2;
import hillbillies.part2.internal.ui.ViewProviders2;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

public class GameControllerPart2 extends GameController<IHillbilliesView2> implements IGameController2 {

	private World world;

	private final GameMap map;

	private final Set<TerrainChangeListener> listeners = new HashSet<>();
	private TerrainChangeListener modelListener = new TerrainChangeListener() {

		@Override
		public void notifyTerrainChanged(int x, int y, int z) {
			for (TerrainChangeListener listener : new HashSet<>(listeners)) {
				listener.notifyTerrainChanged(x, y, z);
			}
		}
	};

	public GameControllerPart2(IFacade facade, Part2Options options, GameMap map) throws ModelException {
		super(facade, options);
		this.map = map;

		int[][][] types = new int[map.getNbTilesX()][map.getNbTilesY()][map.getNbTilesZ()];

		for (int x = 0; x < types.length; x++) {
			for (int y = 0; y < types[x].length; y++) {
				for (int z = 0; z < types[x][y].length; z++) {
					CubeType type = map.getTypeAt(x, y, z);
					types[x][y][z] = type.getByteValue();
				}
			}
		}

		world = facade.createWorld(types, modelListener);

		getSelectionProvider().addListener(e -> selectionUpdated());
	}

	@Override
	public Part2Options getOptions() {
		return (Part2Options) super.getOptions();
	}

	private void selectionUpdated() {
		Selection selection = getSelectionProvider().getSelection();
		if (selection.isSingle()) {
			Object obj = selection.getAnySelected();
			if (obj instanceof Unit) {
				getView().getViewModel().focusObject(obj);
				updateCurrentFaction();
			}
		}
	}

	@Override
	public IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	protected GameMap getGameMap() {
		return map;
	}

	private final WorldInfoProvider2 wip = new WorldInfoProvider2() {

		@Override
		public void addTerrainChangeListener(TerrainChangeListener listener) {
			listeners.add(listener);
		}

		@Override
		public void removeTerrainChangeListener(TerrainChangeListener listener) {
			listeners.remove(listener);
		}

		@Override
		public int getNbZTiles() {
			try {
				return getFacade().getNbCubesZ(world);
			} catch (ModelException e) {
				handleError(e);
				return 50;
			}
		}

		@Override
		public int getNbYTiles() {
			try {
				return getFacade().getNbCubesY(world);
			} catch (ModelException e) {
				handleError(e);
				return 50;
			}
		}

		@Override
		public int getNbXTiles() {
			try {
				return getFacade().getNbCubesX(world);
			} catch (ModelException e) {
				handleError(e);
				return 50;
			}
		}

		@Override
		public CubeType getCubeTypeAt(int x, int y, int z) {
			try {
				CubeType result = CubeType.fromByte((byte) getFacade().getCubeType(world, x, y, z));
				return result;
			} catch (ModelException e) {
				return CubeType.EMPTY;
			}
		};

		@Override
		public boolean isAnchored(int x, int y, int z) {
			try {
				return getFacade().isSolidConnectedToBorder(world, x, y, z);
			} catch (ModelException e) {
				handleError(e);
			}
			return false;
		};

		@Override
		public Set<?> getObjectsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
			Set<Object> result = new HashSet<>();
			result.addAll(getObjectsOfTypeInBox(Unit.class, minX, minY, minZ, maxX, maxY, maxZ, getFacade()::getUnits,
					getFacade()::isAlive, getFacade()::getPosition));
			result.addAll(getObjectsOfTypeInBox(Boulder.class, minX, minY, minZ, maxX, maxY, maxZ,
					getFacade()::getBoulders, b -> true, getFacade()::getPosition));
			result.addAll(getObjectsOfTypeInBox(Log.class, minX, minY, minZ, maxX, maxY, maxZ, getFacade()::getLogs,
					l -> true, getFacade()::getPosition));
			return result;
		}

	};

	@FunctionalInterface
	private static interface MEFunction<T, R> {
		public R apply(T obj) throws ModelException;
	}

	protected <T> Set<T> getObjectsOfTypeInBox(Class<T> type, double minX, double minY, double minZ, double maxX,
			double maxY, double maxZ, MEFunction<World, Set<T>> objects, MEFunction<T, Boolean> isAlive,
			MEFunction<T, double[]> position) {
		try {
			Set<T> objs = objects.apply(world);
			if (objs != null) {
				final double[] min = new double[] { minX, minY, minZ };
				final double[] max = new double[] { maxX, maxY, maxZ };
				return objs.stream().filter(o -> {
					try {
						return isAlive.apply(o) && liesInBox(position.apply(o), min, max);
					} catch (ModelException e) {
						handleError(e);
						return false;
					}
				}).collect(Collectors.toSet());
			}
		} catch (ModelException e) {
			handleError(e);
		}
		return Collections.emptySet();
	}

	protected static boolean liesInBox(double[] position, double[] low, double[] high) {
		return position != null && low[0] <= position[0] && position[0] < high[0] && low[1] <= position[1]
				&& position[1] < high[1] && low[2] <= position[2] && position[2] < high[2];

	}

	@Override
	protected InputMode createDefaultInputMode() {
		return new Part2InputMode(this);
	};

	private final GameObjectInfoProvider uip = new GameObjectInfoProvider(this, this::handleError);

	private final ActionExecutorPart2 wae = new ActionExecutorPart2(this, this::handleError);

	private Object myFaction;

	@Override
	public void updateGame(double dt) {
		try {
			getFacade().advanceTime(world, dt);
		} catch (ModelException e) {
			handleError(e);
		}
	}

	@Override
	public WorldInfoProvider2 getWorldInfoProvider() {
		return wip;
	}

	@Override
	public IGameObjectInfoProvider getUnitInfoProvider() {
		return uip;
	}

	@Override
	public ActionExecutorPart2 getActionExecutor() {
		return wae;
	}

	@Override
	public void createUnit(int cubeX, int cubeY, int cubeZ) {
		throw new NoSuchMethodError("Not implemented");
	}

	@Override
	public void spawnUnits(int n) {
		try {
			for (int i = 0; i < n; i++) {
				Unit unit = getFacade().spawnUnit(getWorld(), false);
				Faction faction = getFacade().getFaction(unit);
				if (myFaction == null) {
					myFaction = faction;
				}
				updateFactions();
				if (faction != myFaction) {
					getFacade().setDefaultBehaviorEnabled(unit, true);
				}
				if (n == 1)
					getSelectionProvider().getSelection().select(unit, true);
			}
		} catch (ModelException e) {
			handleError(e);
		}
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public ViewProviders2 createViewProviders() {
		return new ViewProviders2(getUnitInfoProvider(), getWorldInfoProvider(), getActionExecutor(),
				getSelectionProvider());
	}

	@Override
	public int getFactionIndex(Faction faction) {
		return factionSelectionPool.getOrDefault(faction, FactionData.EMPTY).index;
	}

	@Override
	public void selectNextUnit() {
		updateUnits();
		if (!unitIterator.hasNext())
			unitIterator = unitSelectionPool.iterator();
		if (unitIterator.hasNext())
			getSelectionProvider().getSelection().select(unitIterator.next(), true);
	}

	private final Set<Unit> unitSelectionPool = new HashSet<>();

	private Iterator<Unit> unitIterator;

	private void updateUnits() {
		updateFactions();
		try {
			if (selectedFactionData != null) {
				Set<Unit> units = getFacade().getUnitsOfFaction(selectedFactionData.faction);
				if (units != null && !unitSelectionPool.equals(units)) {
					unitSelectionPool.retainAll(units);
					for (Unit unit : units) {
						unitSelectionPool.add(unit);
					}
					unitIterator = unitSelectionPool.iterator();
					Optional<Unit> selUnit = getSelectedUnit();
					if (selUnit.isPresent() && !unitSelectionPool.contains(selUnit.get())) {
						if (unitIterator.hasNext()) {
							getSelectionProvider().getSelection().select(unitIterator.next(), true);
						} else {
							getSelectionProvider().getSelection().clear();
						}
					}
				}
			} else {
				unitSelectionPool.clear();
			}
		} catch (ModelException e) {
			handleError(e);
		}
	}

	@Override
	public void selectNextFaction() {
		updateFactions();

		if (!factionDataIterator.hasNext()) {
			factionDataIterator = factionSelectionPool.values().iterator();
		}

		if (factionDataIterator.hasNext()) {
			selectedFactionData = factionDataIterator.next();
			Optional<Unit> selectedUnit = getSelectedUnit();
			try {
				if (selectedUnit.isPresent()
						&& getFacade().getFaction(selectedUnit.get()) != selectedFactionData.faction) {
					selectNextUnit();
				}
			} catch (ModelException e) {
				handleError(e);
			}
		}
	}

	protected Optional<Unit> getSelectedUnit() {
		return getSelectionProvider().getSelection().getAnySelected(Unit.class);
	}

	private static final class FactionData {

		public static final FactionData EMPTY = new FactionData(null, -1);

		public final Faction faction;
		public final int index;

		public FactionData(Faction faction, int index) {
			this.faction = faction;
			this.index = index;
		}

		@Override
		public String toString() {
			return faction + " (" + index + ")";
		}

	}

	private void updateCurrentFaction() {
		Optional<Unit> optUnit = getSelectedUnit();
		if (optUnit.isPresent()) {
			try {
				Faction faction = getFacade().getFaction(optUnit.get());
				selectedFactionData = factionSelectionPool.getOrDefault(faction, FactionData.EMPTY);
			} catch (ModelException e) {
				selectedFactionData = FactionData.EMPTY;
			}
		}
	}

	private FactionData selectedFactionData;
	private Map<Faction, FactionData> factionSelectionPool = new HashMap<>();
	private Iterator<FactionData> factionDataIterator = factionSelectionPool.values().iterator();

	private void updateFactions() {
		try {
			Set<Faction> allFactions = getFacade().getActiveFactions(getWorld());
			if (allFactions != null && !allFactions.equals(factionSelectionPool.keySet())) {
				if (allFactions.size() > Constants.MAX_NB_FACTIONS) {
					throw new AssertionError("The GUI only supports " + Constants.MAX_NB_FACTIONS + " factions");
				}
				factionSelectionPool.keySet().retainAll(allFactions);
				for (Faction f : allFactions) {
					factionSelectionPool.computeIfAbsent(f, fac -> new FactionData(fac, getFirstFreeFactionIndex()));
				}
				factionDataIterator = factionSelectionPool.values().iterator();
				if (!factionSelectionPool.containsKey(selectedFactionData)) {
					selectNextFaction();
				}
			}
		} catch (ModelException e) {
			handleError(e);
		}
	}

	private int getFirstFreeFactionIndex() {
		for (int index = 0; index < Constants.MAX_NB_FACTIONS; index++) {
			final int idx = index;
			if (factionSelectionPool.values().stream().noneMatch(fd -> fd.index == idx)) {
				return index;
			}
		}
		throw new AssertionError("Maximum number of GUI factions reached");
	}

	@Override
	public boolean isPlayerUnit(Unit u) {
		try {
			return getFacade().getFaction(u) == myFaction;
		} catch (ModelException e) {
		}
		return true; // let's be gentle
	}

}
