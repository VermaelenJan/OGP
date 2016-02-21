package hillbillies.common.internal.providers;

import java.util.Set;

public interface WorldInfoProvider {
	
	public int getNbXTiles();

	public int getNbYTiles();

	public int getNbZTiles();
	
	/**
	 * All objects in the box [minX, maxX[, [minY,maxY[, [minZ,maxZ[
	 * 
	 * @param minX
	 * @param minY
	 * @param minZ
	 * @param maxX (exclusive)
	 * @param maxY (exclusive)
	 * @param maxZ (exclusive)
	 * @return
	 */
	public Set<?> getObjectsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

}
