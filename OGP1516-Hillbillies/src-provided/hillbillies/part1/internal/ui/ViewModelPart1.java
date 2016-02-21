package hillbillies.part1.internal.ui;

import java.util.Collection;

import hillbillies.common.internal.map.IByteMap3D;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.common.internal.ui.sprites.SpriteFactory;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import hillbillies.model.Unit;
import hillbillies.part1.internal.map.EmptyMap;
import hillbillies.part1.internal.ui.sprites.UnitSprite;
import javafx.geometry.Rectangle2D;

public class ViewModelPart1 extends ViewModel {

	public ViewModelPart1(WorldInfoProvider wip, UnitInfoProvider uip, Rectangle2D initialView, int tileSize) {
		super(wip, uip, initialView, tileSize);
	}

	@Override
	protected void setupSpriteFactory() {
		SpriteFactory.INSTANCE.registerSpriteSupplier(Unit.class, u -> new UnitSprite(u, getUnitInfoProvider()));
	}

	@Override
	protected IByteMap3D createMapCache() {
		return new EmptyMap(getWorldInfoProvider().getNbXTiles(), getWorldInfoProvider().getNbYTiles(),
				getWorldInfoProvider().getNbZTiles());
	}

	@Override
	public int calculateVisibleZFromMap(int visibleX, int visibleY) {
		return 0;
	}

	@Override
	protected Collection<? extends Object> getVisibleObjectsAt(int visibleX, int visibleY) {
		return getWorldInfoProvider().getObjectsInBox(visibleTileToWorldPointX(visibleX), visibleTileToWorldPointY(visibleY),
				0, visibleTileToWorldPointX(visibleX + 1), visibleTileToWorldPointY(visibleY + 1),
				(getCurrentZLevel() + 1) * getMeterPerTile());
	}
	
	@Override
	public double screenToWorldZ(double x, double y) {
		return getCurrentZLevel();
	}

	@Override
	protected void updateVisibleZLevelAt(int visibleX, int visibleY, int visibleZ) {
		// TODO: ignore
	}

}
