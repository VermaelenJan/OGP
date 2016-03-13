package hillbillies.part2.internal.map;

import java.util.HashSet;
import java.util.Set;

import hillbillies.common.internal.map.IByteMap3D;

/**
 * A memory-efficient 3D map structure.
 * 
 * For internal GUI use only.
 */
public class ByteMap3D implements IByteMap3D {

	private final byte[] tileTypes;
	private final int nbXY;
	private final int nbX, nbY, nbZ;

	public ByteMap3D(int nbX, int nbY, int nbZ) {
		this.nbX = nbX;
		this.nbY = nbY;
		this.nbZ = nbZ;
		this.nbXY = nbX * nbY;
		this.tileTypes = new byte[nbXY * nbZ];
		for (int i = 0; i < tileTypes.length; i++)
			tileTypes[i] = -1;
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
	public boolean isValidIndex(int x, int y, int z) {
		return 0 <= x && x < nbX && 0 <= y && y < nbY && 0 <= z && z < nbZ;
	}

	@Override
	public byte getValue(int x, int y, int z) {
		if (!isValidIndex(x, y, z))
			throw new AssertionError("Invalid coordinates: " + x + ", " + y + ", " + z);

		return tileTypes[z * nbXY + y * nbX + x];
	}

	@Override
	public void setValue(int x, int y, int z, byte value) {
		if (!isValidIndex(x, y, z))
			throw new AssertionError("Invalid coordinates: " + x + ", " + y + ", " + z);

		int index = z * nbXY + y * nbX + x;
		byte oldValue = tileTypes[index];
		if (oldValue != value) {
			tileTypes[index] = value;
			fireChange(x, y, z, oldValue, value);
		}
	}
	
	private void fireChange(int x, int y, int z, byte oldValue, byte newValue) {
		for (Listener listener : listeners) {
			listener.onChange(x, y, z, oldValue, newValue);
		}
	}

	private final Set<Listener> listeners = new HashSet<>();

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	@Override
	public void fill(ValueProvider provider) {
		for (int z = 0; z < nbZ; z++) {
			int offsetZ = z * nbXY;
			for (int y = 0; y < nbY; y++) {
				int offsetY = offsetZ + y * nbX;
				for (int x = 0; x < nbX; x++) {
					int index = offsetY + x;
					byte oldValue = tileTypes[index];
					byte value = provider.getValue(x, y, z);
					if (oldValue != value) {
						tileTypes[index] = value;
						fireChange(x, y, z, oldValue, value);
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int z = 0; z < nbZ; z++) {
			result.append("\n");
			for (int y = nbY - 1; y >= 0; y--) {
				result.append("\n");
				for (int x = 0; x < nbX; x++) {
					result.append(String.format("%2d", getValue(x, y, z)));
				}
			}
		}
		return result.toString();
	}
}
