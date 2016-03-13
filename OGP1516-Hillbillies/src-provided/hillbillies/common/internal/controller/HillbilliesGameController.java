package hillbillies.common.internal.controller;

import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.options.HillbilliesOptions;
import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.common.internal.ui.IHillbilliesView;
import ogp.framework.game.IGameController;

public interface HillbilliesGameController<V extends IHillbilliesView> extends IGameController<V> {
	
	SelectionProvider getSelectionProvider();

	ActionExecutor getActionExecutor();

	WorldInfoProvider getWorldInfoProvider();

	Object getFacade();

	InputMode getCurrentInputMode();
	
	void switchInputMode(InputMode newMode);
	
	HillbilliesOptions getOptions();
}
