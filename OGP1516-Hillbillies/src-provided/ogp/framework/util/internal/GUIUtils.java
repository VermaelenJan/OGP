package ogp.framework.util.internal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import ogp.framework.util.internal.ResourceUtils;

public class GUIUtils {

	public static Button createButtonWithGraphic(String url, String tooltip, EventHandler<ActionEvent> handler) {
		return createButtonWithGraphic(url, tooltip, handler, 90, 50);
	}

	public static Button createButtonWithGraphic(String url, String tooltip, EventHandler<ActionEvent> handler,
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

}
