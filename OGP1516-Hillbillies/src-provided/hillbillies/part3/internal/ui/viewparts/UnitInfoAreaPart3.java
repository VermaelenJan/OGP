package hillbillies.part3.internal.ui.viewparts;

import hillbillies.common.internal.ui.viewparts.ControlArea;
import hillbillies.model.Task;
import hillbillies.part2.internal.ui.viewparts.UnitControlArea2;
import hillbillies.part2.internal.ui.viewparts.UnitInfoAreaPart2;
import hillbillies.part3.internal.controller.ActionExecutorPart3;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class UnitInfoAreaPart3 extends UnitInfoAreaPart2 {

	private Label taskLabel;

	public UnitInfoAreaPart3(IGameObjectInfoProvider3 uip, ActionExecutorPart3 ae) {
		super(uip, ae);

		taskLabel = new Label();
		Polygon arrow = new Polygon(3, 8, 15, 0, 3, -8);
		arrow.setFill(Color.WHITE);
		taskLabel.setGraphic(arrow);
		arrow.setOnMouseClicked(e -> getActionExecutor().clearSelection());

		((VBox) getRoot()).getChildren().add(2, taskLabel);

		VBox root = (VBox) getRoot();
		Node nameLabel = root.getChildren().remove(0);
		Circle tracker = new Circle(5);
		tracker.setFill(Color.RED);
		tracker.setStroke(Color.WHITE);
		tracker.setStrokeWidth(2.0);
		tracker.setOnMouseClicked(e -> track());
		Circle smallTracker = new Circle(2);
		smallTracker.setFill(Color.WHITE);
		smallTracker.setMouseTransparent(true);
		Group trackerGrp = new Group(tracker, smallTracker);
		HBox name = new HBox(nameLabel, trackerGrp);
		name.setSpacing(10);
		name.setAlignment(Pos.CENTER_LEFT);
		root.getChildren().add(0, name);

	}

	protected void track() {
		getActionExecutor().track(getObject());
	}

	@Override
	protected ActionExecutorPart3 getActionExecutor() {
		return (ActionExecutorPart3) super.getActionExecutor();
	}

	@Override
	protected ControlArea createControlArea() {
		return new UnitControlArea2(getActionExecutor());
	}

	@Override
	protected IGameObjectInfoProvider3 getUnitInfoProvider() {
		return (IGameObjectInfoProvider3) super.getUnitInfoProvider();
	}

	@Override
	public void updateInfo() {
		super.updateInfo();
		Task task = getUnitInfoProvider().getAssignedTask(getObject());
		if (task != null) {
			taskLabel.setText("Assigned task: " + getUnitInfoProvider().getTaskName(task));
		} else {
			taskLabel.setText("(no assigned task)");
		}
	}

}
