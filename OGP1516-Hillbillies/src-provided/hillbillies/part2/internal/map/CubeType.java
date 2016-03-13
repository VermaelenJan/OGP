package hillbillies.part2.internal.map;

/**
 * For internal use only.
 */
public enum CubeType {
	EMPTY, ROCKS, TREES, WORKSHOP;

	public byte getByteValue() {
		return (byte) this.ordinal();
	}

	public static CubeType fromByte(byte value) {
		return values()[value];
	}
}
