package hillbillies.common.internal.controller;

import javafx.scene.input.MouseEvent;

public class CubeSelectionMode extends DefaultInputMode {

	private int cubeX;
	private int cubeY;
	private int cubeZ;

	public CubeSelectionMode(GameController<?> game) {
		super(game);
	}
	
	@Override
	public void activate() {
		super.activate();
	}
	
	@Override
	public void worldPointClicked(double worldX, double worldY, double worldZ, MouseEvent e) {
		this.cubeX = getViewModel().worldPointToWorldCube(worldX);
		this.cubeY = getViewModel().worldPointToWorldCube(worldY);
		this.cubeZ = getViewModel().worldPointToWorldCube(worldZ);
		onSelected.run();
	}
	
	private Runnable onSelected;
	
	public void setOnSelected(Runnable onSelected) {
		this.onSelected = onSelected;
	}
	
	public int getCubeX() {
		return cubeX;
	}
	
	public int getCubeY() {
		return cubeY;
	}
	
	public int getCubeZ() {
		return cubeZ;
	}
}
