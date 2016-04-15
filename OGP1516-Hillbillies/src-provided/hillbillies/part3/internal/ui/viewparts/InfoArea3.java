package hillbillies.part3.internal.ui.viewparts;

import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.part2.internal.Constants;
import hillbillies.part2.internal.ui.viewparts.InfoArea2;
import hillbillies.part3.internal.controller.ActionExecutorPart3;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import javafx.scene.layout.HBox;
import ogp.framework.util.internal.GUIUtils;

public abstract class InfoArea3 extends InfoArea2 {

	private SchedulerControlArea control;

	public InfoArea3(SelectionProvider selection, IGameObjectInfoProvider3 infoProvider, ActionExecutorPart3 ae) {
		super(selection, infoProvider, ae);
	}

	@Override
	protected IGameObjectInfoProvider3 getUnitInfoProvider() {
		return (IGameObjectInfoProvider3) super.getUnitInfoProvider();
	}

	@Override
	protected ActionExecutorPart3 getActionExecutor() {
		return (ActionExecutorPart3) super.getActionExecutor();
	}

	@Override
	protected void onSelectionUpdated() {
		if (getSelection().isEmpty()) {
			setCurrentPart(null);
			HBox spawnBox = new HBox();
			getRoot().getChildren().add(spawnBox);
			spawnBox.getChildren().add(GUIUtils.createButtonWithGraphic("resources/spawn.png", "Spawn a new unit",
					ae -> getActionExecutor().spawnUnits(1)));
			spawnBox.getChildren().add(GUIUtils.createButtonWithGraphic("resources/spawnstar.png", "Spawn 20 units",
					ae -> getActionExecutor().spawnUnits(Constants.UNITS_TO_SPAWN)));
			control = new SchedulerControlArea(getUnitInfoProvider(), getActionExecutor());
			getRoot().getChildren().add(control.getRoot());
		} else {
			control = null;
			super.onSelectionUpdated();
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		if (control != null) {
			control.refresh();
		}
	}

	public void setSchedulingEnabled(boolean value) {
		control.setSchedulingEnabled(value);
	}
}
