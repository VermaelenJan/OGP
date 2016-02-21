package ogp.framework.game;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class GameLoop {
	private final AnimationTimer mainLoop;

	private BooleanProperty paused = new SimpleBooleanProperty(false);
	private DoubleProperty inGameTime = new SimpleDoubleProperty();

	private final double DEFAULT_MAX_IN_GAME_TIMESTEP = Double.POSITIVE_INFINITY;

	private double maxTimeStep;

	public GameLoop(IGameController game) {
		IGameView view = game.getView();
		this.maxTimeStep = DEFAULT_MAX_IN_GAME_TIMESTEP;
		mainLoop = new AnimationTimer() {
			private long previous = 0;

			@Override
			public void handle(long now) {
				if (!paused.get()) {
					if (previous > 0) {
						double dt = (now - previous) / 1e9;
						double dtStep = Math.min(dt, getMaxTimeStep());
						for (int i = 0; i < dtStep / dt; i++) {
							inGameTime.set(inGameTime.get() + dtStep);
							game.updateGame(dtStep);
						}
					}
					view.refreshDisplay();
				}
				previous = now;
			}
		};
	}

	protected double getMaxTimeStep() {
		return maxTimeStep;
	}

	public void setMaxTimeStep(double maxTimeStep) {
		this.maxTimeStep = maxTimeStep;
	}

	public DoubleProperty inGameTimeProperty() {
		return inGameTime;
	}

	public BooleanProperty pausedProperty() {
		return paused;
	}

	public void start() {
		mainLoop.start();
	}

	public void stop() {
		mainLoop.stop();
	}

	public void togglePause() {
		paused.set(!paused.get());
	}
}
