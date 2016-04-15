package hillbillies.part2.internal.ui.sprites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hillbillies.part2.internal.Constants;
import javafx.scene.paint.Color;
import ogp.framework.ui.SpriteSheet;

public class FactionColors {

	public static final Color[][] factionColors = { //
			/* faction 0 */ { Color.rgb(164, 0, 0), Color.rgb(124, 0, 0), Color.rgb(92, 4, 0), Color.rgb(68, 4, 0) }, //
			/* faction 1 */ { Color.rgb(12, 72, 204), Color.rgb(4, 40, 160), Color.rgb(0, 0, 116),
					Color.rgb(0, 4, 76) }, //
			/* faction 2 */ { Color.rgb(44, 180, 148), Color.rgb(20, 132, 92), Color.rgb(4, 84, 44),
					Color.rgb(0, 40, 12) }, //
			/* faction 3 */ { Color.rgb(152, 72, 176), Color.rgb(116, 44, 132), Color.rgb(80, 24, 88),
					Color.rgb(44, 8, 44) }, //
			/* faction 4 */ { Color.rgb(248, 140, 20), Color.rgb(200, 96, 16), Color.rgb(152, 60, 16),
					Color.rgb(108, 32, 12) }, //
			/* faction 5 */ { Color.rgb(40, 40, 60), Color.rgb(28, 28, 44), Color.rgb(20, 20, 32),
					Color.rgb(12, 12, 20) }, //
			/* faction 6 */ { Color.rgb(244, 244, 244), Color.rgb(152, 152, 180), Color.rgb(84, 84, 128),
					Color.rgb(36, 40, 76) }, //
			/* faction 7 */ { Color.rgb(104, 64, 20), Color.rgb(124, 88, 32), Color.rgb(144, 112, 48),
					Color.rgb(0, 0, 0) }, //
	};

	/* The index of the faction on which the image files are based */
	private static final int BASE_FACTION = 0;

	private static final List<Map<Color, Color>> factionColorMaps = new ArrayList<>();

	static {
		if (factionColors.length < Constants.MAX_NB_FACTIONS) {
			throw new AssertionError(
					"MAX_NB_FACTIONS too high; only " + factionColors.length + " color themes are provided.");
		}
		for (int faction = 0; faction < factionColors.length; faction++) {
			factionColorMaps.add(createFactionColorMap(faction));
		}
	}

	private static HashMap<Color, Color> createFactionColorMap(int faction) {
		HashMap<Color, Color> result = new HashMap<>();
		if (faction != BASE_FACTION) {
			for (int colorIndex = 0; colorIndex < factionColors[BASE_FACTION].length; colorIndex++) {
				result.put(factionColors[BASE_FACTION][colorIndex], factionColors[faction][colorIndex]);
			}
		}
		return result;
	}

	private static Map<Color, Color> getColorMapFor(int teamIndex) {
		return factionColorMaps.get(teamIndex);
	}

	public static List<SpriteSheet> createFactionColorVariants(SpriteSheet spriteSheet) {
		List<SpriteSheet> result = new ArrayList<>(factionColors.length);
		for (int faction = 0; faction < factionColors.length; faction++) {
			if (faction == BASE_FACTION) {
				result.add(spriteSheet);
			} else {
				result.add(spriteSheet.replaceColors(getColorMapFor(faction)));
			}
		}
		return result;
	}

}
