package hillbillies.part3.internal.ui.sprites;

import hillbillies.common.internal.ui.sprites.AbstractSprite;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SelectionMarkerSprite extends AbstractSprite<SelectionMarkerSprite.SelectionMarker, Object> {

	public static class SelectionMarker {

		private final int x;
		private final int y;
		private final int z;

		public SelectionMarker(int[] xyz) {
			this.x = xyz[0];
			this.y = xyz[1];
			this.z = xyz[2];
		}

		public double getX() {
			return x + 0.5;
		}

		public double getY() {
			return y + 0.5;
		}

		public double getZ() {
			return z + 0.5;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SelectionMarker other = (SelectionMarker) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

	}

	private Circle node;

	private Timeline animation = new Timeline();

	public SelectionMarkerSprite(SelectionMarker object, Object infoProvider) {
		super(object, infoProvider);
		this.node = new Circle(5, Color.AQUA);

		worldSizeXProperty().set(0.4);
		worldSizeYProperty().set(0.4);
		worldSizeZProperty().set(0.4);

		worldXProperty().set(object.getX());
		worldYProperty().set(object.getY());
		worldZProperty().set(object.getZ());

		animation.getKeyFrames()
				.add(new KeyFrame(Duration.ZERO, new KeyValue(node.radiusProperty(), 5, Interpolator.EASE_BOTH),
						new KeyValue(node.fillProperty(), Color.AQUA.desaturate(), Interpolator.EASE_BOTH)));
		animation.getKeyFrames()
				.add(new KeyFrame(Duration.seconds(1), new KeyValue(node.radiusProperty(), 8, Interpolator.EASE_BOTH),
						new KeyValue(node.fillProperty(), Color.WHITE, Interpolator.EASE_BOTH)));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setAutoReverse(true);
		animation.play();
	}

	@Override
	public void update() {
	}

	@Override
	public Node getGraph() {
		return node;
	}

}
