package hillbillies.part1.internal.controller;

import hillbillies.common.internal.controller.HillbilliesGameController;
import hillbillies.part1.facade.IFacade;
import hillbillies.part1.internal.Part1Options;

public interface IGameController1<V extends IHillbilliesView1> extends HillbilliesGameController<V> {
	
	@Override
	IFacade getFacade();
	
	public void createUnit(int x, int y, int z);

	void selectNextUnit();
	
	@Override
	Part1Options getOptions();

}
