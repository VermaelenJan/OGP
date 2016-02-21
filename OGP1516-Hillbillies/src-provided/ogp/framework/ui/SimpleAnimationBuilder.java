package ogp.framework.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;

public class SimpleAnimationBuilder<T> {

	public static <T> SimpleAnimationBuilder<T> create(WritableValue<T> property, Duration duration,
			Interpolator interpolator) {
		return new SimpleAnimationBuilder<>(property, duration, interpolator);
	}

	private WritableValue<T> property;
	private Duration duration;
	private Interpolator interpolator;
	private double timeMultiplier;

	private List<KeyFrame> keyFrames = new ArrayList<>();
	private int cycleCount = 1;

	public SimpleAnimationBuilder(WritableValue<T> property, Duration duration, Interpolator interpolator) {
		this.property = property;
		this.duration = duration;
		this.interpolator = interpolator;
		this.timeMultiplier = 0;
	}

	public SimpleAnimationBuilder<T> atNext(T value) {
		return this.at(++timeMultiplier, value);
	}

	public SimpleAnimationBuilder<T> at(double multiplier, T value) {
		keyFrames.add(new KeyFrame(duration.multiply(multiplier), new KeyValue(property, value, interpolator)));
		return this;
	}

	public SimpleAnimationBuilder<T> withCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
		return this;
	}

	public Timeline build() {
		Timeline result = new Timeline();
		result.getKeyFrames().addAll(keyFrames);
		result.setCycleCount(cycleCount);
		return result;
	}
}
