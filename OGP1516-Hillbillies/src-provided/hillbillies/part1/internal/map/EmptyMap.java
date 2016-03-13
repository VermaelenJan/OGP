package hillbillies.part1.internal.map;

import hillbillies.common.internal.map.IByteMap3D;

public class EmptyMap implements IByteMap3D {

	private final int nbX;
	private final int nbY;
	private final int nbZ;

	public EmptyMap(int nbX, int nbY, int nbZ) {
		this.nbX = nbX;
		this.nbY = nbY;
		this.nbZ = nbZ;
	}

	@Override
	public void fill(ValueProvider provider) {
	}

	@Override
	public boolean isValidIndex(int x, int y, int z) {
		return 0 <= x && x < nbX && 0 <= y && y < nbY && 0 <= z && z < nbZ;
	}

	@Override
	public byte getValue(int worldX, int worldY, int worldZ) {
		return 0;
	}
	
	@Override
	public void setValue(int x, int y, int z, byte value) {
		if (value != 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getNbX() {
		return nbX;
	}

	@Override
	public int getNbY() {
		return nbY;
	}

	@Override
	public int getNbZ() {
		return nbZ;
	}

	@Override
	public void addListener(Listener listener) {
	}

}
