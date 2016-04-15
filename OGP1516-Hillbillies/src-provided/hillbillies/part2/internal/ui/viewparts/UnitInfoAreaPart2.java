package hillbillies.part2.internal.ui.viewparts;

import hillbillies.common.internal.ui.viewparts.ControlArea;
import hillbillies.model.Faction;
import hillbillies.part1.internal.ui.viewparts.UnitInfoAreaPart;
import hillbillies.part2.internal.controller.ActionExecutorPart2;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import hillbillies.part2.internal.ui.sprites.FactionColors;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class UnitInfoAreaPart2 extends UnitInfoAreaPart {

	private Label factionLabel;
	private Label experienceLabel;

	public UnitInfoAreaPart2(IGameObjectInfoProvider uip, ActionExecutorPart2 ae) {
		super(uip, ae);

		factionLabel = new Label();
		experienceLabel = new Label();

		((VBox) getRoot()).getChildren().add(1, factionLabel);
		((VBox) getRoot()).getChildren().add(6, experienceLabel);
	}

	@Override
	protected ActionExecutorPart2 getActionExecutor() {
		return (ActionExecutorPart2) super.getActionExecutor();
	}

	@Override
	protected ControlArea createControlArea() {
		return new UnitControlArea2(getActionExecutor());
	}

	@Override
	protected IGameObjectInfoProvider getUnitInfoProvider() {
		return (IGameObjectInfoProvider) super.getUnitInfoProvider();
	}

	@Override
	public void updateInfo() {
		super.updateInfo();
		Faction faction = getUnitInfoProvider().getFaction(getObject());
		factionLabel.setText(getUnitInfoProvider().getFactionName(faction));
		Rectangle rect = new Rectangle(10, 10);
		rect.setFill(FactionColors.factionColors[getUnitInfoProvider().getFactionIndex(faction)][0]);
		factionLabel.setGraphic(rect);
		experienceLabel.setText("Exp: " + getUnitInfoProvider().getExperiencePoints(getObject()));
	}

}
