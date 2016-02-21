package hillbillies.common.internal.ui.viewmodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import hillbillies.common.internal.map.IByteMap3D;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.common.internal.ui.sprites.AbstractSprite;
import hillbillies.common.internal.ui.sprites.SpriteFactory;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public abstract class ViewModel {

	/** Width of the world view, in pixels */
	private final DoubleProperty viewWidth = new SimpleDoubleProperty();
	/** Height of the world view, in pixels */
	private final DoubleProperty viewHeight = new SimpleDoubleProperty();

	private final DoubleProperty _worldWindowOriginX = new SimpleDoubleProperty();
	private final DoubleProperty _worldWindowOriginY = new SimpleDoubleProperty();

	/** Tile coordinate of the top left tile that is visible */
	private final IntegerProperty xTileOffset = new SimpleIntegerProperty();
	private final IntegerProperty yTileOffset = new SimpleIntegerProperty();

	/** Bottom Z-coordinate of the topmost XY slice that is visible */
	private final IntegerProperty currentZLevel = new SimpleIntegerProperty(0);

	private final WorldInfoProvider wip;
	private final UnitInfoProvider uip;

	private final IByteMap3D mapCache;

	private final int nbVisibleTilesX;
	private final int nbVisibleTilesY;

	private final int pixelsPerTile;

	/** Bottom Z-coordinate of the topmost XY slice in the world */
	private final int maxZLevel;

	public ViewModel(WorldInfoProvider wip, UnitInfoProvider uip, Rectangle2D initialView, int pixelsPerTile) {
		this.wip = wip;
		this.uip = uip;
		this.pixelsPerTile = pixelsPerTile;
		this.maxZLevel = wip.getNbZTiles() - 1;

		viewWidth.set(Math.min(wip.getNbXTiles() * pixelsPerTile, initialView.getWidth()));
		viewHeight.set(Math.min(wip.getNbYTiles() * pixelsPerTile, initialView.getHeight()));

		_worldWindowOriginX.set(0);
		_worldWindowOriginY.set(0);

		this.nbVisibleTilesX = 1 + (int) (viewWidth.get() / pixelsPerTile);
		this.nbVisibleTilesY = 1 + (int) (viewHeight.get() / pixelsPerTile);

		// tile = m / (m/tile)
		xTileOffset.bind(_worldWindowOriginX.divide(getMeterPerTile()));
		yTileOffset.bind(_worldWindowOriginY.divide(getMeterPerTile()));

		this.mapCache = createMapCache();
		updateMapCache();

		setupSpriteFactory();

		currentZLevelProperty().addListener(e -> refreshVisibleInformation());
		xTileOffset.addListener(e -> refreshVisibleInformation());
		yTileOffset.addListener(e -> refreshVisibleInformation());
	}

	public UnitInfoProvider getUnitInfoProvider() {
		return uip;
	}

	protected abstract IByteMap3D createMapCache();

	protected abstract void setupSpriteFactory();

	public int getNbVisibleTilesX() {
		return nbVisibleTilesX;
	}

	public int getNbVisibleTilesY() {
		return nbVisibleTilesY;
	}

	/** Max z level (in tile coordinates) */
	public int getMaxZLevel() {
		return maxZLevel;
	}

	/** View width (in pixels) */
	public ReadOnlyDoubleProperty viewWidthProperty() {
		return viewWidth;
	}

	/** View width (in pixels) */
	public ReadOnlyDoubleProperty viewHeightProperty() {
		return viewHeight;
	}

	/** Current z level (in tile coordinates) */
	public IntegerProperty currentZLevelProperty() {
		return currentZLevel;
	}

	/** Current z level (in tile coordinates) */
	public int getCurrentZLevel() {
		return currentZLevel.get();
	}

	public void adjustLevel(int dz) {
		int current = getCurrentZLevel();
		int newLevel = Math.max(0, Math.min(getMaxZLevel(), current + dz));
		currentZLevel.set(newLevel);
	}

	public void levelUp() {
		adjustLevel(+1);
	}

	public void levelDown() {
		adjustLevel(-1);
	}

	public IByteMap3D getMap() {
		return mapCache;
	}

	public void update() {
		updateMapCache();
		refreshSprites();
	}

	/**
	 * Tile size (in pixels per tile)
	 */
	public int getPixelsPerTile() {
		return pixelsPerTile;
	}

	public void moveOrigin(double dxPixels, double dyPixels) {
		int totalWidthInPixels = wip.getNbXTiles() * getPixelsPerTile();
		int totalHeightInPixels = wip.getNbYTiles() * getPixelsPerTile();

		double x = Math.max(0, Math.min(_worldWindowOriginX.get() + dxPixels / getPixelsPerMeter(),
				(totalWidthInPixels - viewWidth.get()) / getPixelsPerMeter()));
		double y = Math.max(0, Math.min(_worldWindowOriginY.get() + dyPixels / getPixelsPerMeter(),
				(totalHeightInPixels - viewHeight.get()) / getPixelsPerMeter()));
		_worldWindowOriginX.set(x);
		_worldWindowOriginY.set(y);
	}

	public WorldInfoProvider getWorldInfoProvider() {
		return wip;
	}

	public int visibleTileToWorldTileX(int visibleX) {
		return visibleX + xTileOffset.get();
	}

	public double visibleTileToWorldPointX(int visibleX) {
		return visibleTileToWorldTileX(visibleX) * getMeterPerTile();
	}

	public double visibleTileToWorldPointY(int visibleY) {
		return visibleTileToWorldTileY(visibleY) * getMeterPerTile();
	}

	public int visibleTileToWorldTileY(int visibleY) {
		return visibleY + yTileOffset.get();
	}

	public IntegerProperty xTileOffsetProperty() {
		return xTileOffset;
	}

	public IntegerProperty yTileOffsetProperty() {
		return yTileOffset;
	}

	public Set<Object> getVisibleObjects() {
		Set<Object> result = new HashSet<>();
		for (int visibleX = 0; visibleX < nbVisibleTilesX; visibleX++) {
			for (int visibleY = 0; visibleY < nbVisibleTilesY; visibleY++) {
				result.addAll(getVisibleObjectsAt(visibleX, visibleY));
			}
		}
		return result;
	}

	public void refreshVisibleInformation() {
		for (int visibleX = 0; visibleX < getNbVisibleTilesX(); visibleX++) {
			for (int visibleY = 0; visibleY < getNbVisibleTilesY(); visibleY++) {
				int visibleZ = calculateVisibleZFromMap(visibleX, visibleY);
				updateVisibleZLevelAt(visibleX, visibleY, visibleZ);
				for (RefreshListener listener : refreshListeners) {
					listener.refreshInfo(visibleX, visibleY, visibleZ);
				}
			}
		}
		updateSpriteZLevels();
	}

	protected abstract void updateVisibleZLevelAt(int visibleX, int visibleY, int visibleZ);

	private Set<AbstractSprite<?, ?>> visibleSprites = new HashSet<>();

	protected void refreshSprites() {
		Set<Object> visibleUnits = new HashSet<>(getVisibleObjects());

		Set<AbstractSprite<?, ?>> spritesToRemove = new HashSet<>();
		for (AbstractSprite<?, ?> sprite : visibleSprites) {
			if (!visibleUnits.contains(sprite.getObject())) {
				sprite.getGraph().setVisible(false);
				spritesToRemove.add(sprite);
				if (sprite.getGraph() != null && sprite.getGraph().getParent() != null) {
					((Pane) sprite.getGraph().getParent()).getChildren().remove(sprite.getGraph());
				}
			} else {
				sprite.update();
				visibleUnits.remove(sprite.getObject());
			}
		}
		visibleSprites.removeAll(spritesToRemove);

		for (Object object : visibleUnits) {
			AbstractSprite<?, ?> newSprite = SpriteFactory.INSTANCE.create(object);
			visibleSprites.add(newSprite);
			newSprite.screenXProperty().bind(newSprite.worldXProperty().multiply(getPixelsPerMeter())
					.subtract(xTileOffset.multiply(getPixelsPerTile())));
			newSprite.screenYProperty().bind(newSprite.worldYProperty().multiply(getPixelsPerMeter())
					.subtract(yTileOffset.multiply(getPixelsPerTile())));
			newSprite.pixelsPerMeterProperty().set(getPixelsPerMeter());
			for (NewSpriteListener listener : spriteListeners) {
				listener.newSprite(newSprite);
			}
			newSprite.worldZProperty().addListener(s -> updateSpriteZLevel(newSprite));
			updateSpriteZLevel(newSprite);
		}
	}

	private void updateSpriteZLevels() {
		for (AbstractSprite<?, ?> sprite : visibleSprites) {
			updateSpriteZLevel(sprite);
		}
	}

	private void updateSpriteZLevel(AbstractSprite<?, ?> sprite) {
		int minSpriteZ = (int) ((sprite.worldZProperty().get() - sprite.worldSizeZProperty().get() / 2));
		int maxSpriteZ = (int) ((sprite.worldZProperty().get() + sprite.worldSizeZProperty().get() / 2));
		int depthToShow = 0;
		if (maxSpriteZ < getCurrentZLevel()) {
			depthToShow = getCurrentZLevel() - maxSpriteZ;
		} else if (minSpriteZ >= getCurrentZLevel()) {
			depthToShow = getCurrentZLevel() - minSpriteZ;
		}

		sprite.depthProperty().set(depthToShow);
	}

	@FunctionalInterface
	public static interface RefreshListener {
		public void refreshInfo(int visibleX, int visibleY, int visibleZ);
	}

	private final Set<RefreshListener> refreshListeners = new HashSet<>();

	public void addRefreshListener(RefreshListener listeners) {
		refreshListeners.add(listeners);
	}

	@FunctionalInterface
	public static interface NewSpriteListener {
		public void newSprite(AbstractSprite<?, ?> sprite);
	}

	private final Set<NewSpriteListener> spriteListeners = new HashSet<>();

	public void addNewSpriteListener(NewSpriteListener listener) {
		spriteListeners.add(listener);
	}

	public Set<AbstractSprite<?, ?>> getVisibleSprites() {
		return Collections.unmodifiableSet(visibleSprites);
	}

	protected void updateMapCache() {

	}

	public abstract int calculateVisibleZFromMap(int visibleX, int visibleY);

	public int screenToVisibleTileX(double screenX) {
		return (int) (screenX / getPixelsPerTile());
	}

	public int screenToVisibleTileY(double screenY) {
		return (int) (screenY / getPixelsPerTile());
	}

	public double screenToWorldX(double screenX) {
		return xTileOffset.get() * getMeterPerTile() + screenX / getPixelsPerMeter();
	}

	public double screenToWorldY(double screenY) {
		return yTileOffset.get() * getMeterPerTile() + screenY / getPixelsPerMeter();
	}

	public abstract double screenToWorldZ(double screenX, double screenY);

	protected double getPixelsPerMeter() {
		return pixelsPerTile / getMeterPerTile();
	}

	public double getMeterPerTile() {
		return 1;
	}

	protected abstract Collection<? extends Object> getVisibleObjectsAt(int visibleX, int visibleY);

	public int worldPointToWorldCube(double coord) {
		return (int) (coord / getMeterPerTile());
	}
}
