package hillbillies.part3.internal.controller;

import java.util.stream.Collectors;

import hillbillies.common.internal.controller.CubeSelectionMode;
import hillbillies.part3.internal.ui.IHillbilliesView3;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MultiCubeSelectionMode extends CubeSelectionMode {

	public MultiCubeSelectionMode(IGameController3 game) {
		super(game);
	}

	@Override
	public IHillbilliesView3 getView() {
		return (IHillbilliesView3) super.getView();
	}

	@Override
	public void regionSelected(double minWorldX, double minWorldY, double minWorldZ, double maxWorldX, double maxWorldY,
			double maxWorldZ, MouseEvent e) {
		for (int x = (int) minWorldX; x <= maxWorldX; x++) {
			for (int y = (int) minWorldY; y <= maxWorldY; y++) {
				for (int z = (int) minWorldZ; z <= maxWorldZ; z++) {
					select(new Cube(x, y, z));
				}
			}
		}
		e.consume();
		// ignore
	}

	@Override
	protected void select(Cube object) {
		if (object != null) {
			getSelection().toggle(object);
			getView().setHighlightedTiles(getSelection().getObjects(Cube.class).stream()
					.map(c -> new int[] { c.cubeX, c.cubeY, c.cubeZ }).collect(Collectors.toList()));
		} else {
			getOnSelected().accept(null);
		}
		// no onSelected here yet; wait till done
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			for (Cube cube : getSelection().getObjects(Cube.class)) {
				getOnSelected().accept(cube);
			}
			getView().setHighlightedTiles(null);
			getOnSelected().accept(null);
		} else if (e.getCode() == KeyCode.ESCAPE) {
			getView().setHighlightedTiles(null);
			cancel();
		} else {
			super.onKeyPressed(e);
		}
	}

}
