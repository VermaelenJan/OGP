package ogp.framework.ui;

import java.util.function.Function;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SpriteAnimation {
	private final SpriteSheet spriteSheet;
	private final Timeline animation;
	private final IntegerProperty baseIndex = new SimpleIntegerProperty();

	public SpriteAnimation(SpriteSheet spriteSheet,
			Function<IntegerProperty, SimpleAnimationBuilder<Number>> builder) {
		this.spriteSheet = spriteSheet;
		this.animation = builder.apply(baseIndex).build();
	}

	public Timeline getAnimation() {
		return animation;
	}

	public IntegerProperty getBaseIndex() {
		return baseIndex;
	}

	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
}
