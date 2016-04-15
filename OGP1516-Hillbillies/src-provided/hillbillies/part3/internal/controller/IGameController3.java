package hillbillies.part3.internal.controller;

import hillbillies.part2.internal.controller.IGameController2;
import hillbillies.part3.internal.ui.IHillbilliesView3;

public interface IGameController3 extends IGameController2 {

	@Override
	IHillbilliesView3 getView();
}
