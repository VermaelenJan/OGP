package hillbillies.common.internal.controller;

import javafx.scene.input.MouseEvent;

public class CubeSelectionMode extends AbstractSelectionMode<CubeSelectionMode.Cube> {

	public static final class Cube {
		public final int cubeX, cubeY, cubeZ;

		public Cube(int x, int y, int z) {
			this.cubeX = x;
			this.cubeY = y;
			this.cubeZ = z;
		}
	}

	private boolean oldConsumeSpriteClicks;
	private boolean oldHighlightCurrentTile;

	public CubeSelectionMode(HillbilliesGameController<?> game) {
		super(Cube.class, game);
	}

	@Override
	public void activate() {
		oldConsumeSpriteClicks = getView().getConsumeSpriteClicks();
		oldHighlightCurrentTile = getView().getHighlightCurrentTile();
		getView().setConsumeSpriteClicks(false);
		getView().setHighlightCurrentTile(true);
		super.activate();
	}

	@Override
	public void deactivate() {
		getView().setConsumeSpriteClicks(oldConsumeSpriteClicks);
		getView().setHighlightCurrentTile(oldHighlightCurrentTile);
		super.deactivate();
	}

	@Override
	public void worldPointClicked(double worldX, double worldY, double worldZ, MouseEvent e) {
		select(new Cube(getViewModel().worldPointToWorldCube(worldX), getViewModel().worldPointToWorldCube(worldY),
				getViewModel().worldPointToWorldCube(worldZ)));
	}

}
