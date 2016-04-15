package hillbillies.part3.internal.ui.viewmodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import hillbillies.common.internal.ui.sprites.SpriteFactory;
import hillbillies.part2.internal.providers.WorldInfoProvider2;
import hillbillies.part2.internal.ui.viewmodel.ViewModelPart2;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import hillbillies.part3.internal.ui.sprites.SelectionMarkerSprite;
import hillbillies.part3.internal.ui.sprites.SelectionMarkerSprite.SelectionMarker;
import javafx.geometry.Rectangle2D;

public class ViewModelPart3 extends ViewModelPart2 {

	public ViewModelPart3(WorldInfoProvider2 wip, IGameObjectInfoProvider3 uip, Rectangle2D initialView, int tileSize) {
		super(wip, uip, initialView, tileSize);
	}

	@Override
	public IGameObjectInfoProvider3 getUnitInfoProvider() {
		return (IGameObjectInfoProvider3) super.getUnitInfoProvider();
	}

	@Override
	protected Collection<? extends Object> getVisibleObjectsAt(int visibleX, int visibleY) {
		Collection<Object> result = new HashSet<>(super.getVisibleObjectsAt(visibleX, visibleY));
		result.addAll(getSelectionMarkers(visibleX, visibleY));
		return result;
	}

	@Override
	protected void setupSpriteFactory() {
		super.setupSpriteFactory();
		SpriteFactory.INSTANCE.registerSpriteSupplier(SelectionMarker.class, m -> new SelectionMarkerSprite(m, null));
	}

	private final Set<SelectionMarker> markers = new HashSet<>();

	private Collection<SelectionMarker> getSelectionMarkers(int visibleX, int visibleY) {
		return markers.stream()
				.filter(m -> visibleTileToWorldPointX(visibleX) == (int) m.getX()
						&& visibleTileToWorldPointY(visibleY) == (int) m.getY()
						&& m.getZ() >= getLowestVisibleZ(visibleX, visibleY) && m.getZ() < getCurrentZLevel() + 1)
				.collect(Collectors.toSet());
	}

	public void updateSelectionMarkers(Set<SelectionMarker> newMarkers) {
		markers.retainAll(newMarkers);
		markers.addAll(newMarkers);
		updateAllVisibleTiles();
	}

}
