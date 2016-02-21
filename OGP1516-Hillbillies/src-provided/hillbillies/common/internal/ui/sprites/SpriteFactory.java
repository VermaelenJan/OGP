package hillbillies.common.internal.ui.sprites;

import java.util.function.Function;

import ogp.framework.util.internal.GenericFactory;

public class SpriteFactory extends GenericFactory {

	public static final SpriteFactory INSTANCE = new SpriteFactory();

	protected SpriteFactory() {
		super();
	}

	public <T> void registerSpriteSupplier(Class<T> type, Function<T, AbstractSprite<T, ?>> supplier) {
		registerSupplier(type, o -> {
			AbstractSprite<T, ?> sprite = supplier.apply(o);
			sprite.update();
			return sprite;
		});
	}

}
