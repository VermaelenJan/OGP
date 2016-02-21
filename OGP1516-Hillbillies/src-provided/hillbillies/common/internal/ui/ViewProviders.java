package hillbillies.common.internal.ui;

import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;

public class ViewProviders {
	private final UnitInfoProvider uip;
	private final WorldInfoProvider wip;
	private final ActionExecutor ae;
	private final SelectionProvider sp;

	public ViewProviders(UnitInfoProvider uip, WorldInfoProvider wip, ActionExecutor ae, SelectionProvider sp) {
		this.uip = uip;
		this.wip = wip;
		this.ae = ae;
		this.sp = sp;
	}

	public UnitInfoProvider getUnitInfoProvider() {
		return uip;
	}

	public WorldInfoProvider getWip() {
		return wip;
	}

	public ActionExecutor getAe() {
		return ae;
	}

	public SelectionProvider getSp() {
		return sp;
	}
}
