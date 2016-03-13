package hillbillies.common.internal.ui.viewparts;

import hillbillies.common.internal.ui.viewmodel.IViewModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LevelSlider {

	private final Slider slider;
	private final VBox rootBox;

	public LevelSlider(IViewModel config) {
		this.rootBox = new VBox();
		slider = new Slider(0, config.getMaxZLevel(), config.getCurrentZLevel());
		slider.setOrientation(Orientation.VERTICAL);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		slider.valueProperty().bindBidirectional(config.currentZLevelProperty());

		Label zLabel = new Label();
		zLabel.textProperty().bind(Bindings.format("z=%d", config.currentZLevelProperty()));
		zLabel.setMinWidth(50);
		rootBox.getChildren().add(slider);
		rootBox.getChildren().add(zLabel);
		
		VBox.setVgrow(slider, Priority.ALWAYS);
	}

	public Node getRoot() {
		return rootBox;
	}
}
