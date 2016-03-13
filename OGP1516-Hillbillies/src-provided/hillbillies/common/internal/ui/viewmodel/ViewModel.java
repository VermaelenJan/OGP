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

public abstract class ViewModel implements IViewModel {

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
		fillMapCache();

		setupSpriteFactory();

		currentZLevelProperty().addListener(e -> updateAllInformation());
		xTileOffset.addListener(e -> updateAllVisibleTiles());
		yTileOffset.addListener(e -> updateAllVisibleTiles());

		mapCache.addListener(this::worldTileChanged);
	}

	protected void worldTileChanged(int worldX, int worldY, int worldZ, byte oldValue, byte newValue) {
		if (isWorldTileVisible(worldX, worldY, worldZ)) {
			int visibleX = worldTileToVisibleTileX(worldX);
			int visibleY = worldTileToVisibleTileY(worldY);
			updateVisibleTileAndNotify(visibleX, visibleY);
		}
	}

	public boolean isWorldTileVisible(int worldTileX, int worldTileY, int worldTileZ) {
		return xTileOffset.get() <= worldTileX && worldTileX < xTileOffset.get() + nbVisibleTilesX
				&& yTileOffset.get() <= worldTileY && worldTileY < yTileOffset.get() + nbVisibleTilesY
				&& getLowestVisibleZ(worldTileToVisibleTileX(worldTileX),
						worldTileToVisibleTileY(worldTileY)) <= worldTileZ
				&& worldTileZ <= getCurrentZLevel();
	}

	public int worldTileToVisibleTileX(int worldTileX) {
		return worldTileX - xTileOffset.get();
	}

	public int worldTileToVisibleTileY(int worldTileY) {
		return worldTileY - yTileOffset.get();
	}

	public UnitInfoProvider getUnitInfoProvider() {
		return uip;
	}

	protected abstract IByteMap3D createMapCache();

	protected abstract void setupSpriteFactory();

	@Override
	public int getNbVisibleTilesX() {
		return nbVisibleTilesX;
	}

	@Override
	public int getNbVisibleTilesY() {
		return nbVisibleTilesY;
	}

	@Override
	public int getMaxZLevel() {
		return maxZLevel;
	}

	/** View width (in pixels) */
	@Override
	public ReadOnlyDoubleProperty viewWidthProperty() {
		return viewWidth;
	}

	/** View width (in pixels) */
	@Override
	public ReadOnlyDoubleProperty viewHeightProperty() {
		return viewHeight;
	}

	@Override
	public IntegerProperty currentZLevelProperty() {
		return currentZLevel;
	}

	@Override
	public void adjustLevel(int dz) {
		int current = getCurrentZLevel();
		int newLevel = Math.max(0, Math.min(getMaxZLevel(), current + dz));
		currentZLevel.set(newLevel);
	}

	@Override
	public IByteMap3D getMap() {
		return mapCache;
	}

	@Override
	public void update() {
		fillMapCache();
		refreshSprites();
	}

	/**
	 * Tile size (in pixels per tile)
	 */
	@Override
	public int getPixelsPerTile() {
		return pixelsPerTile;
	}

	@Override
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

	@Override
	public IntegerProperty xTileOffsetProperty() {
		return xTileOffset;
	}

	@Override
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

	@Override
	public void updateAllInformation() {
		updateAllVisibleTiles();
		updateSpriteZLevels();
	}

	public void updateAllVisibleTiles() {
		for (int visibleX = 0; visibleX < getNbVisibleTilesX(); visibleX++) {
			for (int visibleY = 0; visibleY < getNbVisibleTilesY(); visibleY++) {
				updateVisibleTileAndNotify(visibleX, visibleY);
			}
		}
	}

	protected void updateVisibleTileAndNotify(int visibleX, int visibleY) {
		int visibleZ = getLowestVisibleZ(visibleX, visibleY);
		
		updateVisibleTileZAt(visibleX, visibleY, visibleZ);
		notifyVisibleTileRefreshListeners(visibleX, visibleY, visibleZ);
	}

	private void notifyVisibleTileRefreshListeners(int visibleX, int visibleY, int visibleZ) {
		for (VisibleTileRefreshListener listener : visibleTileRefreshListeners) {
			listener.refreshVisibleTile(visibleX, visibleY, visibleZ);
		}
	}

	protected abstract void updateVisibleTileZAt(int visibleX, int visibleY, int visibleZ);

	private Set<AbstractSprite<?, ?>> visibleSprites = new HashSet<>();

	protected void refreshSprites() {
		Set<Object> visibleObjects = new HashSet<>(getVisibleObjects());

		Set<AbstractSprite<?, ?>> spritesToRemove = new HashSet<>();
		for (AbstractSprite<?, ?> sprite : visibleSprites) {
			if (!visibleObjects.contains(sprite.getObject())) {
				sprite.getGraph().setVisible(false);
				spritesToRemove.add(sprite);
				if (sprite.getGraph() != null && sprite.getGraph().getParent() != null) {
					((Pane) sprite.getGraph().getParent()).getChildren().remove(sprite.getGraph());
				}
			} else {
				sprite.update();
				visibleObjects.remove(sprite.getObject());
			}
		}
		visibleSprites.removeAll(spritesToRemove);

		for (Object object : visibleObjects) {
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


	private final Set<VisibleTileRefreshListener> visibleTileRefreshListeners = new HashSet<>();

	@Override
	public void addVisibleTileRefreshListener(VisibleTileRefreshListener listener) {
		visibleTileRefreshListeners.add(listener);
	}


	private final Set<NewSpriteListener> spriteListeners = new HashSet<>();

	@Override
	public void addNewSpriteListener(NewSpriteListener listener) {
		spriteListeners.add(listener);
	}

	@Override
	public Set<AbstractSprite<?, ?>> getVisibleSprites() {
		return Collections.unmodifiableSet(visibleSprites);
	}

	protected void fillMapCache() {

	}

	@Override
	public abstract int getLowestVisibleZ(int visibleX, int visibleY);

	@Override
	public int screenToVisibleTileX(double screenX) {
		return (int) (screenX / getPixelsPerTile());
	}

	@Override
	public int screenToVisibleTileY(double screenY) {
		return (int) (screenY / getPixelsPerTile());
	}

	@Override
	public double screenToWorldX(double screenX) {
		return xTileOffset.get() * getMeterPerTile() + screenX / getPixelsPerMeter();
	}

	@Override
	public double screenToWorldY(double screenY) {
		return yTileOffset.get() * getMeterPerTile() + screenY / getPixelsPerMeter();
	}

	@Override
	public abstract double screenToWorldZ(double screenX, double screenY);

	protected double getPixelsPerMeter() {
		return pixelsPerTile / getMeterPerTile();
	}

	public double getMeterPerTile() {
		return 1;
	}

	protected abstract Collection<? extends Object> getVisibleObjectsAt(int visibleX, int visibleY);

	@Override
	public int worldPointToWorldCube(double coord) {
		return (int) (coord / getMeterPerTile());
	}

	public double visibleTileToScreen(int visibleTile) {
		return visibleTile * getPixelsPerTile();
	}
}
