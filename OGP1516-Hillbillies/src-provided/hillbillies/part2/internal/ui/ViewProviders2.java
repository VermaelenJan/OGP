package hillbillies.part2.internal.ui;

import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.ui.ViewProviders;
import hillbillies.part2.internal.controller.ActionExecutorPart2;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import hillbillies.part2.internal.providers.WorldInfoProvider2;

public class ViewProviders2 extends ViewProviders {

	public ViewProviders2(IGameObjectInfoProvider uip, WorldInfoProvider2 wip, ActionExecutorPart2 ae,
			SelectionProvider sp) {
		super(uip, wip, ae, sp);
	}

	@Override
	public ActionExecutorPart2 getAe() {
		return (ActionExecutorPart2) super.getAe();
	}

	@Override
	public IGameObjectInfoProvider getUnitInfoProvider() {
		return (IGameObjectInfoProvider) super.getUnitInfoProvider();
	}

	@Override
	public WorldInfoProvider2 getWip() {
		return (WorldInfoProvider2) super.getWip();
	}

}
