package hillbillies.common.internal;

public interface Constants {

	public static final double WINDOW_WIDTH = 1024;
	public static final double WINDOW_HEIGHT = 768;

	public static final double MINIMAP_SIZE = 200;

	public static final double SIDEBAR_WIDTH = MINIMAP_SIZE + 50;

	public static final int CUBE_SIZE = 32; // pixels per cube

	public static final double WORLD_VIEW_WIDTH = CUBE_SIZE * ((int) ((WINDOW_WIDTH - SIDEBAR_WIDTH - 50) / CUBE_SIZE));
	public static final double WORLD_VIEW_HEIGHT = CUBE_SIZE * ((int) ((WINDOW_HEIGHT - 50) / CUBE_SIZE));

}
