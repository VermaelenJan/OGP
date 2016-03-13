package ogp.framework.ui;

import java.util.function.Function;
import java.util.function.Supplier;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SpriteAnimation {
	private final Supplier<SpriteSheet> spriteSheet;
	private final Timeline animation;
	private final IntegerProperty baseIndex = new SimpleIntegerProperty();

	public SpriteAnimation(Supplier<SpriteSheet> spriteSheet,
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
		return spriteSheet.get();
	}
}
