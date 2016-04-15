package hillbillies.part3.internal.ui;

import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.part2.internal.providers.WorldInfoProvider2;
import hillbillies.part2.internal.ui.ViewProviders2;
import hillbillies.part3.internal.controller.ActionExecutorPart3;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;

public class ViewProviders3 extends ViewProviders2 {

	public ViewProviders3(IGameObjectInfoProvider3 uip, WorldInfoProvider2 wip, ActionExecutorPart3 ae,
			SelectionProvider sp) {
		super(uip, wip, ae, sp);
	}
	
	@Override
	public IGameObjectInfoProvider3 getUnitInfoProvider() {
		return (IGameObjectInfoProvider3) super.getUnitInfoProvider();
	}

	@Override
	public ActionExecutorPart3 getAe() {
		return (ActionExecutorPart3) super.getAe();
	}

}
