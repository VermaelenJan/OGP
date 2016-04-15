package hillbillies.part3.internal.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import hillbillies.common.internal.Constants;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import hillbillies.common.internal.ui.viewparts.InfoArea;
import hillbillies.common.internal.ui.viewparts.WorldView;
import hillbillies.model.Unit;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.ui.HillbilliesViewPart2;
import hillbillies.part3.internal.controller.ActionExecutorPart3;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import hillbillies.part3.internal.ui.sprites.SelectionMarkerSprite;
import hillbillies.part3.internal.ui.viewmodel.ViewModelPart3;
import hillbillies.part3.internal.ui.viewparts.InfoArea3;
import hillbillies.part3.internal.ui.viewparts.UnitInfoAreaPart3;
import hillbillies.part3.internal.ui.viewparts.WorldViewPart3;
import javafx.geometry.Rectangle2D;

public class HillbilliesViewPart3 extends HillbilliesViewPart2 implements IHillbilliesView3 {

	public HillbilliesViewPart3(ViewProviders3 vp, Part2Options options) {
		super(vp, options);
	}

	@Override
	public IGameObjectInfoProvider3 getUnitInfoProvider() {
		return (IGameObjectInfoProvider3) super.getUnitInfoProvider();
	}

	@Override
	public ActionExecutorPart3 getActionExecutor() {
		return (ActionExecutorPart3) super.getActionExecutor();
	}

	@Override
	protected WorldView createWorldView() {
		return WorldViewPart3.create(getViewModel(), getOptions());
	}

	@Override
	protected ViewModel createViewModel() {
		return new ViewModelPart3(getWorldInfoProvider(), getUnitInfoProvider(),
				new Rectangle2D(0, 0, Constants.WORLD_VIEW_WIDTH, Constants.WORLD_VIEW_HEIGHT), Constants.CUBE_SIZE);
	}

	@Override
	public ViewModelPart3 getViewModel() {
		return (ViewModelPart3) super.getViewModel();
	}

	@Override
	protected InfoArea createInfoArea() {
		return new InfoArea3(getSelectionProvider(), getUnitInfoProvider(), getActionExecutor()) {

			@Override
			protected void registerProviders() {
				registerProvider(Unit.class, () -> new UnitInfoAreaPart3(getUnitInfoProvider(), getActionExecutor()));
			}
		};
	}

	@Override
	public void setSchedulingEnabled(boolean value) {
		((InfoArea3) getInfoArea()).setSchedulingEnabled(value);
	}

	@Override
	public void setHighlightedTiles(Collection<int[]> tiles) {
		if (tiles != null) {
			getViewModel().updateSelectionMarkers(
					tiles.stream().map(xyz -> new SelectionMarkerSprite.SelectionMarker(xyz)).collect(Collectors.toSet()));
		} else {
			getViewModel().updateSelectionMarkers(Collections.emptySet());
		}
	}

}
