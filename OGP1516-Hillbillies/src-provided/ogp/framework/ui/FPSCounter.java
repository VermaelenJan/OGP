package ogp.framework.ui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FPSCounter {

	private final DoubleProperty fpsEstimate = new SimpleDoubleProperty();
	private final AnimationTimer timer;

	public FPSCounter() {
		timer = new AnimationTimer() {

			private long start = 0;
			private int frameCount;

			@Override
			public void handle(long now) {
				if (start == 0) {
					start = now;
				} else {
					double dt = (now - start) / 1e9;
					if (dt > 1) {
						fpsEstimate.set(frameCount / dt);
						frameCount = 0;
						start = now;
					} else {
						frameCount++;
					}
				}
			}
		};
	}
	
	public DoubleProperty fpsEstimateProperty() {
		return fpsEstimate;
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}
}
