package hillbillies.common.internal.map;

/**
 * For internal GUI use only.
 * 
 * Interface for an efficient 3D map that notifies listeners when changes are made.
 * Contains byte values.
 */
public interface IByteMap3D {

	@FunctionalInterface
	public static interface ValueProvider {
		public byte getValue(int x, int y, int z);
	}
	

	@FunctionalInterface
	public static interface Listener {
		public void onChange(int x, int y, int z, byte oldValue, byte newValue);
	}


	default void fill(ValueProvider provider) {
		for (int x = 0; x < getNbX(); x++) {
			for (int y = 0; y < getNbY(); y++) {
				for (int z = 0; z < getNbZ(); z++) {
					setValue(x, y, z, provider.getValue(x, y, z));
				}
			}
		}
	}

	boolean isValidIndex(int worldX, int worldY, int worldZ);
	
	byte getValue(int worldX, int worldY, int worldZ);
	void setValue(int x, int y, int z, byte value);

	int getNbX();
	int getNbY();
	int getNbZ();

	void addListener(Listener listener);

}
