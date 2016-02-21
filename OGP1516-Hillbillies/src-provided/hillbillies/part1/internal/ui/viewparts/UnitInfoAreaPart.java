package hillbillies.part1.internal.ui.viewparts;

import java.util.Optional;

import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.ui.viewparts.ControlArea;
import hillbillies.common.internal.ui.viewparts.InfoAreaPart;
import hillbillies.model.Unit;
import javafx.beans.binding.When;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
//if you get a compile error here, see readme.txt
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ogp.framework.util.internal.ResourceUtils;

public class UnitInfoAreaPart implements InfoAreaPart<Unit> {

	private static int nbStatuses = 0;
	private static final int DEFAULT = nbStatuses++;
	private static final int MOVING = nbStatuses++;
	private static final int SPRINTING = nbStatuses++;
	private static final int RESTING = nbStatuses++;
	private static final int FIGHTING = nbStatuses++;
	private static final int WORKING = nbStatuses++;

	private final VBox root;
	private final Label nameLabel;
	private final Label healthLabel;
	private final Label positionLabel;

	private final ToggleButton[] statuses;
	private Unit unit;

	private final Background fillNo = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
	private final Background fillYes = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));

	private final UnitInfoProvider uip;
	private final ActionExecutor ae;
	private ControlArea controlArea;
	private Label speedLabel;
	private Label attrLabel;

	private ToggleButton createToggle(String url) {
		ToggleButton result = new ToggleButton();
		ImageView iv = new ImageView(ResourceUtils.loadImage(url));
		result.setGraphic(iv);
		iv.setFitHeight(20);
		iv.setPreserveRatio(true);
		return result;
	}

	public UnitInfoAreaPart(UnitInfoProvider uip, ActionExecutor ae) {
		this.uip = uip;
		this.ae = ae;
		this.root = new VBox();
		this.nameLabel = new Label();
		nameLabel.setStyle("-fx-font-weight: bold");
		this.positionLabel = new Label();
		this.speedLabel = new Label();
		this.attrLabel = new Label();
		this.healthLabel = new Label();
		this.statuses = new ToggleButton[nbStatuses];
		statuses[DEFAULT] = createToggle("resources/D.png");
		statuses[MOVING] = createToggle("resources/M.png");
		statuses[SPRINTING] = createToggle("resources/S.png");
		statuses[RESTING] = createToggle("resources/R.png");
		statuses[FIGHTING] = createToggle("resources/F.png");
		statuses[WORKING] = createToggle("resources/W.png");

		HBox statusBox = new HBox();
		for (ToggleButton statusButton : statuses) {
			statusButton.setDisable(true);
			statusButton.setOpacity(1);
			statusButton.backgroundProperty()
					.bind(new When(statusButton.selectedProperty()).then(fillYes).otherwise(fillNo));
			statusBox.getChildren().add(statusButton);
		}

		root.getChildren().add(nameLabel);
		root.getChildren().add(positionLabel);
		root.getChildren().add(speedLabel);
		root.getChildren().add(attrLabel);
		root.getChildren().add(healthLabel);
		root.getChildren().add(statusBox);

		this.controlArea = createControlArea();
		if (controlArea != null)
			root.getChildren().add(controlArea.getRoot());

		nameLabel.setOnMouseClicked(e -> updateName());
	}

	protected ActionExecutor getActionExecutor() {
		return ae;
	}

	protected UnitInfoProvider getUnitInfoProvider() {
		return uip;
	}

	protected ControlArea createControlArea() {
		return new UnitControlArea1(getActionExecutor());
	}

	protected void updateName() {
		// if you get a compile error here, see readme.txt
		TextInputDialog dialog = new TextInputDialog(nameLabel.getText());
		dialog.setTitle("Rename unit");
		dialog.setHeaderText("Rename unit");
		dialog.setContentText("Enter a new name for the unit:");

		dialog.showAndWait().ifPresent(newName -> ae.setName(newName));
	}

	@Override
	public Node getRoot() {
		return root;
	}

	@Override
	public void setObject(Unit unit) {
		this.unit = unit;
		updateInfo();
	}

	@Override
	public void updateInfo() {
		nameLabel.setText(uip.getName(unit));
		healthLabel.setText("H: " + uip.getHitpoints(unit) + "/" + uip.getMaxHitpoints(unit) + " | S: "
				+ uip.getStaminapoints(unit) + "/" + uip.getMaxStaminapoints(unit));
		Optional<double[]> posOpt = uip.getPosition(unit);
		if (posOpt.isPresent()) {
			double[] pos = posOpt.get();
			positionLabel.setText(String.format("Position: (%.1f,%.1f,%.1f)", pos[0], pos[1], pos[2]));
		} else {
			positionLabel.setText("position null");
		}
		speedLabel.setText(String.format("Speed: %.2f m/s", uip.getSpeed(unit)));
		attrLabel.setText(String.format("A: %d | S: %d | T: %d | W: %d", uip.getAgility(unit), uip.getStrength(unit), uip.getToughness(unit), uip.getWeight(unit)));
		statuses[DEFAULT].setSelected(uip.isDefaultBehaviorEnabled(unit));
		statuses[MOVING].setSelected(uip.isWalking(unit));
		statuses[SPRINTING].setSelected(uip.isSprinting(unit));
		statuses[RESTING].setSelected(uip.isResting(unit));
		statuses[FIGHTING].setSelected(uip.isAttacking(unit));
		statuses[WORKING].setSelected(uip.isWorking(unit));
	}
}
