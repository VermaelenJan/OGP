package hillbillies.part2.internal.ui.viewmodel;

import java.util.Collection;
import java.util.Optional;

import hillbillies.common.internal.map.IByteMap3D;
import hillbillies.common.internal.ui.sprites.SpriteFactory;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.part2.internal.map.ByteMap3D;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import hillbillies.part2.internal.providers.WorldInfoProvider2;
import hillbillies.part2.internal.ui.sprites.BoulderSprite;
import hillbillies.part2.internal.ui.sprites.LogSprite;
import hillbillies.part2.internal.ui.sprites.UnitSprite2;
import javafx.geometry.Rectangle2D;
import ogp.framework.util.internal.Matrix;

public class ViewModelPart2 extends ViewModel implements IViewModel2 {

	private final Matrix<Integer> depthValues;
	private final Matrix<Boolean> anchored;

	public ViewModelPart2(WorldInfoProvider2 wip, IGameObjectInfoProvider uip, Rectangle2D initialView, int tileSize) {
		super(wip, uip, initialView, tileSize);
		getWorldInfoProvider().addTerrainChangeListener(this::updateMapCacheAt);
		this.depthValues = new Matrix<>(getNbVisibleTilesX(), getNbVisibleTilesY(), 0);
		this.anchored = new Matrix<>(getNbVisibleTilesX(), getNbVisibleTilesY(), false);
	}

	private boolean trackAnchored = false;

	public void setTrackAnchored(boolean trackAnchored) {
		this.trackAnchored = trackAnchored;
	}

	public Matrix<Boolean> getAnchorMatrix() {
		return anchored;
	}

	@Override
	public IGameObjectInfoProvider getUnitInfoProvider() {
		return (IGameObjectInfoProvider) super.getUnitInfoProvider();
	}

	@Override
	protected void setupSpriteFactory() {
		SpriteFactory.INSTANCE.registerSpriteSupplier(Unit.class, u -> new UnitSprite2(u, getUnitInfoProvider()));
		SpriteFactory.INSTANCE.registerSpriteSupplier(Boulder.class,
				obj -> new BoulderSprite(obj, getUnitInfoProvider()));
		SpriteFactory.INSTANCE.registerSpriteSupplier(Log.class, obj -> new LogSprite(obj, getUnitInfoProvider()));
	}

	protected int getDepthValueAt(int visibleX, int visibleY) {
		return depthValues.getValueAt(visibleX, visibleY);
	}

	protected void setDepthValueAt(int visibleX, int visibleY, int value) {
		depthValues.setValueAt(visibleX, visibleY, value);
	}

	@Override
	protected Collection<? extends Object> getVisibleObjectsAt(int visibleX, int visibleY) {
		int bottomZ = visibleTileToWorldTileZ(visibleX, visibleY);
		// get visible objects in z-aligned column on the (visibleX,
		// visibleY) tile

		return getWorldInfoProvider().getObjectsInBox(visibleTileToWorldPointX(visibleX),
				visibleTileToWorldPointY(visibleY), bottomZ * getMeterPerTile(), visibleTileToWorldPointX(visibleX + 1),
				visibleTileToWorldPointY(visibleY + 1), (getCurrentZLevel() + 1) * getMeterPerTile());
	}

	public int visibleTileToWorldTileZ(int visibleX, int visibleY) {
		return getCurrentZLevel() - getDepthValueAt(visibleX, visibleY);
	}

	@Override
	protected void updateVisibleTileZAt(int visibleX, int visibleY, int visibleZ) {
		setDepthValueAt(visibleX, visibleY, getCurrentZLevel() - visibleZ);
	}

	@Override
	protected IByteMap3D createMapCache() {
		return new ByteMap3D(getWorldInfoProvider().getNbXTiles(), getWorldInfoProvider().getNbYTiles(),
				getWorldInfoProvider().getNbZTiles());
	}

	@Override
	public WorldInfoProvider2 getWorldInfoProvider() {
		return (WorldInfoProvider2) super.getWorldInfoProvider();
	}

	@Override
	protected void fillMapCache() {
		getMap().fill((x, y, z) -> getWorldInfoProvider().getCubeTypeAt(x, y, z).getByteValue());
	}

	protected void updateMapCacheAt(int x, int y, int z) {
		getMap().setValue(x, y, z, getWorldInfoProvider().getCubeTypeAt(x, y, z).getByteValue());
	}

	@Override
	public void update() {
		// super.update(); // skip, to not fill entire map again (we're using
		// listener to selectively update)
		refreshSprites();
		updateAnchored();
	}

	protected void updateAnchored() {
		if (!trackAnchored)
			return;
		int nbTilesX = getWorldInfoProvider().getNbXTiles();
		int nbTilesY = getWorldInfoProvider().getNbYTiles();

		for (int visibleX = 0; visibleX < getNbVisibleTilesX(); visibleX++) {
			for (int visibleY = 0; visibleY < getNbVisibleTilesY(); visibleY++) {
				int tileX = visibleTileToWorldTileX(visibleX);
				int tileY = visibleTileToWorldTileY(visibleY);
				if (tileX < nbTilesX && tileY < nbTilesY) {
					boolean value = getWorldInfoProvider().isAnchored(tileX, tileY,
							visibleTileToWorldTileZ(visibleX, visibleY));
					anchored.setValueAt(visibleX, visibleY, value);
				}
			}
		}
	}

	@Override
	public double screenToWorldZ(double x, double y) {
		int visibleX = screenToVisibleTileX(x);
		int visibleY = screenToVisibleTileY(y);
		return visibleTileToWorldTileZ(visibleX, visibleY);
	}

	@Override
	public int getLowestVisibleZ(int visibleX, int visibleY) {
		int worldX = visibleTileToWorldTileX(visibleX);
		int worldY = visibleTileToWorldTileY(visibleY);

		int currentZ = getCurrentZLevel();
		CubeType type = readTypeFromMap(worldX, worldY, currentZ);
		while (currentZ > 0 && (type == null || type == CubeType.EMPTY)) {
			currentZ -= 1;
			type = readTypeFromMap(worldX, worldY, currentZ);
		}
		return currentZ;
	}

	public CubeType readTypeFromMap(int worldX, int worldY, int z) {
		if (getMap().isValidIndex(worldX, worldY, z)) {
			int index = getMap().getValue(worldX, worldY, z);
			return CubeType.values()[index];
		} else {
			return CubeType.EMPTY;
		}
	}

	public Matrix<Integer> getDepthValues() {
		return depthValues;
	}

	@Override
	public void focusObject(Object object) {
		if (object instanceof Unit) {
			focusUnit((Unit) object);
		} else {
			throw new NoSuchMethodError("Not implemented");
		}
	}

	protected void focusUnit(Unit unit) {
		Optional<double[]> position = getUnitInfoProvider().getPosition(unit);
		if (position.isPresent()) {
			double[] pos = position.get();
			ensureVisible(pos[0], pos[1], pos[2]);
		}
	}

	private void ensureVisible(double worldX, double worldY, double worldZ) {
		currentZLevelProperty().set((int) worldZ);
		int border = 5;
		double minVisibleX = visibleTileToWorldPointX(Math.min(border, getNbVisibleTilesX() - 1));
		double maxVisibleX = visibleTileToWorldPointX(Math.max(0, getNbVisibleTilesX() - border));
		double minVisibleY = visibleTileToWorldPointY(Math.min(border, getNbVisibleTilesY() - 1));
		double maxVisibleY = visibleTileToWorldPointY(Math.max(0, getNbVisibleTilesY() - border));
		double dx = 0;
		double dy = 0;
		if (worldX < minVisibleX) {
			dx = worldX - minVisibleX;
		} else if (worldX > maxVisibleX) {
			dx = worldX - maxVisibleX;
		}
		if (worldY < minVisibleY) {
			dy = worldY - minVisibleY;
		} else if (worldY > maxVisibleY) {
			dy = worldY - maxVisibleY;
		}
		moveOrigin(dx * getPixelsPerMeter(), dy * getPixelsPerMeter());
	}
}
