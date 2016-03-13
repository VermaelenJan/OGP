package hillbillies.part2.internal.ui;

import hillbillies.part1.internal.controller.IHillbilliesView1;
import hillbillies.part2.internal.ui.viewmodel.IViewModel2;

public interface IHillbilliesView2 extends IHillbilliesView1 {
	
	@Override
	public IViewModel2 getViewModel();
	
}
