package hillbillies.part1.internal.ui.viewparts;

import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.ui.viewparts.ControlArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ogp.framework.util.internal.ResourceUtils;

public class UnitControlArea1 extends ControlArea {

	private final VBox root;

	private final GridPane arrows;

	private ToggleGroup group;

	private ToggleButton zplus;

	private ToggleButton zeq;

	private ToggleButton zminus;

	private ActionExecutor ae;

	public UnitControlArea1(ActionExecutor ae) {
		this.ae = ae;
		this.root = new VBox();

		root.setAlignment(Pos.TOP_CENTER);
		root.setFillWidth(true);
		
		this.arrows = new GridPane();

		group = new ToggleGroup();
		zplus = new RadioButton();
		zplus.setGraphic(new ImageView(ResourceUtils.loadImage("resources/zplus.png")));
		zplus.setUserData(+1);
		zplus.setTooltip(new Tooltip("Move to higher Z-level"));
		zeq = new RadioButton();
		zeq.setGraphic(new ImageView(ResourceUtils.loadImage("resources/zeq.png")));
		zeq.setUserData(0);
		zeq.setTooltip(new Tooltip("Stay at current Z-level"));
		zminus = new RadioButton();
		zminus.setGraphic(new ImageView(ResourceUtils.loadImage("resources/zminus.png")));
		zminus.setUserData(-1);
		zminus.setTooltip(new Tooltip("Move to lower Z-level"));

		zplus.setToggleGroup(group);
		zeq.setToggleGroup(group);
		zminus.setToggleGroup(group);
		zeq.setSelected(true);

		arrows.add(zplus, 0, 0);
		arrows.add(zeq, 0, 1);
		arrows.add(zminus, 0, 2);

		int arrowButtonSize = 32;
		Button upLeft = createButtonWithGraphic("resources/left-up.png", "Move NW", e -> moveAdjacent(-1, -1),
				arrowButtonSize, arrowButtonSize);
		Button up = createButtonWithGraphic("resources/up.png", "Move N", e -> moveAdjacent(0, -1), arrowButtonSize,
				arrowButtonSize);

		Button upRight = createButtonWithGraphic("resources/right-up.png", "Move NE", e -> moveAdjacent(1, -1),
				arrowButtonSize, arrowButtonSize);

		Button left = createButtonWithGraphic("resources/left.png", "Move W", e -> moveAdjacent(-1, 0), arrowButtonSize,
				arrowButtonSize);

		Button current = createButtonWithGraphic("resources/updown.png", "Move straight up or down",
				e -> moveAdjacent(0, 0), arrowButtonSize, arrowButtonSize);

		Button right = createButtonWithGraphic("resources/right.png", "Move E", e -> moveAdjacent(1, 0),
				arrowButtonSize, arrowButtonSize);

		Button bottomLeft = createButtonWithGraphic("resources/left-down.png", "Move SW", e -> moveAdjacent(-1, 1),
				arrowButtonSize, arrowButtonSize);

		Button bottom = createButtonWithGraphic("resources/down.png", "Move S", e -> moveAdjacent(0, 1),
				arrowButtonSize, arrowButtonSize);

		Button bottomRight = createButtonWithGraphic("resources/right-down.png", "Move SE", e -> moveAdjacent(1, 1),
				arrowButtonSize, arrowButtonSize);

		arrows.add(upLeft, 1, 0);
		arrows.add(up, 2, 0);
		arrows.add(upRight, 3, 0);
		arrows.add(left, 1, 1);
		arrows.add(current, 2, 1);
		arrows.add(right, 3, 1);
		arrows.add(bottomLeft, 1, 2);
		arrows.add(bottom, 2, 2);
		arrows.add(bottomRight, 3, 2);

		root.getChildren().add(arrows);

		FlowPane flow = new FlowPane();
		flow.setPrefWrapLength(250);

		flow.getChildren().add(createButtonWithGraphic("resources/default.png", "Toggle default behavior",
				e -> ae.toggleDefaultBehavior()));
		flow.getChildren().add(createButtonWithGraphic("resources/move.png", "Move to cube", e -> ae.initiateMove()));
		flow.getChildren()
				.add(createButtonWithGraphic("resources/sprint.png", "Toggle sprinting", e -> ae.toggleSprint()));
		flow.getChildren()
				.add(createButtonWithGraphic("resources/attack.png", "Attack other unit", e -> ae.initiateAttack()));
		flow.getChildren().add(createButtonWithGraphic("resources/rest.png", "Start resting", e -> ae.rest()));
		flow.getChildren().add(createButtonWithGraphic("resources/work.png", "Start working", e -> ae.work()));

		root.getChildren().add(flow);
	}

	private void moveAdjacent(int dx, int dy) {
		int dz = (int) group.getSelectedToggle().getUserData();
		ae.moveToAdjacent(dx, dy, dz);
	}

	protected Button createButtonWithGraphic(String url, String tooltip, EventHandler<ActionEvent> handler) {
		return createButtonWithGraphic(url, tooltip, handler, 90, 50);
	}

	protected Button createButtonWithGraphic(String url, String tooltip, EventHandler<ActionEvent> handler,
			double width, double height) {
		Button button = new Button();
		button.setGraphic(new ImageView(ResourceUtils.loadImage(url)));
		button.setTooltip(new Tooltip(tooltip));
		button.setMinSize(width, height);
		button.setPrefSize(width, height);
		button.setMaxSize(width, height);
		button.setOnAction(handler);
		return button;
	}

	@Override
	public Node getRoot() {
		return root;
	}

	public void refreshDisplay() {
	}

}
