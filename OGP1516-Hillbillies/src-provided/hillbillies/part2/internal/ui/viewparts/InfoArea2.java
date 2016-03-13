package hillbillies.part2.internal.ui.viewparts;

import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.ui.viewparts.InfoArea;
import hillbillies.part2.internal.Constants;
import hillbillies.part2.internal.controller.ActionExecutorPart2;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import ogp.framework.util.internal.GUIUtils;

public abstract class InfoArea2 extends InfoArea {

	public InfoArea2(SelectionProvider selection, IGameObjectInfoProvider infoProvider, ActionExecutorPart2 ae) {
		super(selection, infoProvider, ae);
	}

	@Override
	protected IGameObjectInfoProvider getUnitInfoProvider() {
		return (IGameObjectInfoProvider) super.getUnitInfoProvider();
	}

	@Override
	protected ActionExecutorPart2 getActionExecutor() {
		return (ActionExecutorPart2) super.getActionExecutor();
	}

	@Override
	protected void onSelectionUpdated() {
		if (getSelection().isEmpty()) {
			setCurrentPart(null);
			getRoot().getChildren().add(GUIUtils.createButtonWithGraphic("resources/spawn.png", "Spawn a new unit",
					ae -> getActionExecutor().spawnUnits(1)));
			getRoot().getChildren().add(GUIUtils.createButtonWithGraphic("resources/spawnstar.png", "Spawn 20 units",
					ae -> getActionExecutor().spawnUnits(Constants.UNITS_TO_SPAWN)));
		} else {
			super.onSelectionUpdated();
		}
	}
}
