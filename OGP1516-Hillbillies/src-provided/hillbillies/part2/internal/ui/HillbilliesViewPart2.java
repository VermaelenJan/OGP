package hillbillies.part2.internal.ui;

import hillbillies.common.internal.Constants;
import hillbillies.common.internal.ui.HillbilliesView;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import hillbillies.common.internal.ui.viewparts.InfoArea;
import hillbillies.common.internal.ui.viewparts.MiniMap;
import hillbillies.common.internal.ui.viewparts.WorldView;
import hillbillies.model.Unit;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.controller.ActionExecutorPart2;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import hillbillies.part2.internal.providers.WorldInfoProvider2;
import hillbillies.part2.internal.ui.viewmodel.ViewModelPart2;
import hillbillies.part2.internal.ui.viewparts.InfoArea2;
import hillbillies.part2.internal.ui.viewparts.MiniMapPart2;
import hillbillies.part2.internal.ui.viewparts.UnitInfoAreaPart2;
import hillbillies.part2.internal.ui.viewparts.WorldViewPart2;
import javafx.geometry.Rectangle2D;

public class HillbilliesViewPart2 extends HillbilliesView implements IHillbilliesView2 {

	public HillbilliesViewPart2(ViewProviders2 vp, Part2Options options) {
		super(vp, options);
	}

	@Override
	public Part2Options getOptions() {
		return (Part2Options) super.getOptions();
	}

	@Override
	public WorldInfoProvider2 getWorldInfoProvider() {
		return (WorldInfoProvider2) super.getWorldInfoProvider();
	}

	@Override
	public IGameObjectInfoProvider getUnitInfoProvider() {
		return (IGameObjectInfoProvider) super.getUnitInfoProvider();
	}

	@Override
	public ActionExecutorPart2 getActionExecutor() {
		return (ActionExecutorPart2) super.getActionExecutor();
	}

	@Override
	protected WorldView createWorldView() {
		return WorldViewPart2.create(this.getViewModel(), this.getOptions());
	}

	@Override
	protected ViewModel createViewModel() {
		return new ViewModelPart2(getWorldInfoProvider(), getUnitInfoProvider(),
				new Rectangle2D(0, 0, Constants.WORLD_VIEW_WIDTH, Constants.WORLD_VIEW_HEIGHT), Constants.CUBE_SIZE);
	}

	@Override
	public ViewModelPart2 getViewModel() {
		return (ViewModelPart2) super.getViewModel();
	}

	@Override
	protected MiniMap<?> createMiniMap() {
		return new MiniMapPart2(getViewModel());
	}

	@Override
	protected InfoArea createInfoArea() {
		return new InfoArea2(getSelectionProvider(), getUnitInfoProvider(), getActionExecutor()) {

			@Override
			protected void registerProviders() {
				registerProvider(Unit.class, () -> new UnitInfoAreaPart2(getUnitInfoProvider(), getActionExecutor()));
			}
		};
	}

}
