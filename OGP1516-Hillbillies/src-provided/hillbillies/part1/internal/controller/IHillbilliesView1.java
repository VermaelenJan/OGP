package hillbillies.part1.internal.controller;

import hillbillies.common.internal.ui.IHillbilliesView;
import hillbillies.part1.internal.ui.viewmodel.IViewModel1;

public interface IHillbilliesView1 extends IHillbilliesView {

	@Override
	IViewModel1 getViewModel();
}
