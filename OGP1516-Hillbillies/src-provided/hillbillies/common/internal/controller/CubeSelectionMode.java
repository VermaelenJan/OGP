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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cubeX;
			result = prime * result + cubeY;
			result = prime * result + cubeZ;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cube other = (Cube) obj;
			if (cubeX != other.cubeX)
				return false;
			if (cubeY != other.cubeY)
				return false;
			if (cubeZ != other.cubeZ)
				return false;
			return true;
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
