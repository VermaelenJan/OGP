package hillbillies.part2.internal;

import hillbillies.part1.internal.Part1Options;
import javafx.beans.property.Property;

public class Part2Options extends Part1Options {

	public static final String SHOW_CUBE_ANCHORED = "show_anchored";
	public static final String ONLY_PLAY_FIRST = "limit_factions";

	public Part2Options() {
		super();
		addBooleanOption(SHOW_CUBE_ANCHORED, "Show whether cubes are anchored to the borders", false);
		addBooleanOption(ONLY_PLAY_FIRST, "Only allow player to control first faction", true);
	}

	public Property<Boolean> showAnchored() {
		return getBooleanValue(SHOW_CUBE_ANCHORED);
	}

	public Property<Boolean> onlyControlFirstFaction() {
		return getBooleanValue(ONLY_PLAY_FIRST);
	}

}
