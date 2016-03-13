package hillbillies.part2.internal;

import hillbillies.part2.internal.ui.sprites.FactionColors;

public interface Constants extends hillbillies.common.internal.Constants {

	/**
	 * Maximum number of factions. Requires at least as many color themes to be
	 * defined in {@link FactionColors}.
	 */
	public static final int MAX_NB_FACTIONS = 8;
	
	/**
	 * Number of units to spawn from spawnMany
	 */
	public static final int UNITS_TO_SPAWN = 20;
}
