package hillbillies.part2.listener;

/**
 * A listener for communicating terrain changes to the GUI.
 * 
 * You must invoke the {@link #notifyTerrainChanged(int, int, int)} method from
 * your code whenever a cube in the world changes type, such that the GUI knows
 * update the display of that cube.
 * 
 * You do not have to create an implementation of this interface yourself. For
 * testing purposes, you may use an instance of
 * {@link DefaultTerrainChangeListener} (which does nothing).
 */
public interface TerrainChangeListener {

	/**
	 * Notify the GUI that the terrain cube at the given position has been
	 * changed.
	 * 
	 * @param x
	 *            The x-coordinate of the cube that has changed type
	 * @param y
	 *            The y-coordinate of the cube that has changed type
	 * @param z
	 *            The z-coordinate of the cube that has changed type
	 */
	public void notifyTerrainChanged(int x, int y, int z);
}
