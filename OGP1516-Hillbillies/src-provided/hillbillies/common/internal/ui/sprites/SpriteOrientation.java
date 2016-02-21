package hillbillies.common.internal.ui.sprites;

public enum SpriteOrientation {
	NORTH(0), //
	NORTHEAST(1), //
	EAST(2), //
	SOUTHEAST(3), //
	SOUTH(4), //
	SOUTHWEST(3, true), //
	WEST(2, true), //
	NORTHWEST(1, true);

	private final int offset;
	private final boolean hflip;

	private SpriteOrientation(int offset) {
		this(offset, false);
	}

	private SpriteOrientation(int offset, boolean hflip) {
		this.offset = offset;
		this.hflip = hflip;
	}

	public boolean getHFlip() {
		return hflip;
	}

	public int getOffset() {
		return offset;
	}

	public static SpriteOrientation fromDegrees(int degrees) {
		if (degrees < 0 || degrees >= 360) {
			throw new IllegalArgumentException();
		}
		int index = 2 + ((2 * degrees) / 45 + 1) / 2;
		return SpriteOrientation.values()[index % 8];
	}
}
