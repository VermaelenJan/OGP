package hillbillies.part3.internal.ui.viewparts;

import hillbillies.common.internal.ui.viewparts.WorldView;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.ui.viewmodel.ViewModelPart2;
import hillbillies.part2.internal.ui.viewparts.WorldViewPart2;

public class WorldViewPart3 extends WorldViewPart2 {

	public static WorldView create(ViewModelPart2 viewModel, Part2Options options) {
		WorldViewPart3 result = new WorldViewPart3(viewModel, options);
		result.setupViewModel();
		return result;
	}

	protected WorldViewPart3(ViewModelPart2 viewModel, Part2Options options) {
		super(viewModel, options);
	}

}
