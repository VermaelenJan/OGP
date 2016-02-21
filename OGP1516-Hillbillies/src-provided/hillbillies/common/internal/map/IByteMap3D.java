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


	void fill(ValueProvider provider);

	boolean isValidIndex(int worldX, int worldY, int worldZ);
	
	byte getValue(int worldX, int worldY, int worldZ);

	int getNbX();
	int getNbY();
	int getNbZ();

	void addListener(Listener listener);

}
