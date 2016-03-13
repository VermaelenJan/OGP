package hillbillies.part2.internal.ui.sprites;

import java.util.List;

import hillbillies.part1.internal.ui.sprites.UnitSprite;
import ogp.framework.ui.SpriteSheet;
import ogp.framework.util.internal.ResourceUtils;

public class SpriteSheets {

	public static final SpriteSheet defaultSpritesheet = UnitSprite.defaultSpritesheet;

	public static final SpriteSheet woodSpritesheet = new SpriteSheet(
			ResourceUtils.loadImage("resources/peasant_wood.png"), 72, 72, 1, 1);
	public static final SpriteSheet boulderSpritesheet = new SpriteSheet(
			ResourceUtils.loadImage("resources/peasant_boulder.png"), 72, 72, 1, 1);

	public static final List<SpriteSheet> defaultPerFaction = FactionColors
			.createFactionColorVariants(defaultSpritesheet);
	public static final List<SpriteSheet> woodPerFaction = FactionColors.createFactionColorVariants(woodSpritesheet);
	public static final List<SpriteSheet> boulderPerFaction = FactionColors
			.createFactionColorVariants(boulderSpritesheet);

}
