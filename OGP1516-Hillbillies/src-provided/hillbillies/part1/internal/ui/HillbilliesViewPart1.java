package hillbillies.part1.internal.ui;

import hillbillies.common.internal.Constants;
import hillbillies.common.internal.ui.HillbilliesView;
import hillbillies.common.internal.ui.ViewProviders;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import hillbillies.common.internal.ui.viewparts.InfoArea;
import hillbillies.model.Unit;
import hillbillies.part1.internal.Part1Options;
import hillbillies.part1.internal.controller.IHillbilliesView1;
import hillbillies.part1.internal.ui.viewmodel.IViewModel1;
import hillbillies.part1.internal.ui.viewparts.UnitInfoAreaPart;
import javafx.geometry.Rectangle2D;

public class HillbilliesViewPart1 extends HillbilliesView implements IHillbilliesView1 {

	public HillbilliesViewPart1(ViewProviders vp, Part1Options options) {
		super(vp, options);
	}

	@Override
	public Part1Options getOptions() {
		return (Part1Options) super.getOptions();
	}

	@Override
	protected ViewModel createViewModel() {
		return new ViewModelPart1(getWorldInfoProvider(), getUnitInfoProvider(),
				new Rectangle2D(0, 0, Constants.WORLD_VIEW_WIDTH, Constants.WORLD_VIEW_HEIGHT), Constants.CUBE_SIZE);
	}

	@Override
	public IViewModel1 getViewModel() {
		return (IViewModel1) super.getViewModel();
	}

	@Override
	protected InfoArea createInfoArea() {
		return new InfoArea(getSelectionProvider(), getUnitInfoProvider(), getActionExecutor()) {

			@Override
			protected void registerProviders() {
				registerProvider(Unit.class, () -> new UnitInfoAreaPart(getUnitInfoProvider(), getActionExecutor()));
			}
		};
	}

}
