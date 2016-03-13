package hillbillies.part2.internal.controller;

import hillbillies.common.internal.controller.UnitSelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UnitSelectionMode2 extends UnitSelectionMode {

	public UnitSelectionMode2(IGameController2 game) {
		super(game);
	}

	@Override
	public IGameController2 getGameController() {
		return (IGameController2) super.getGameController();
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.TAB && e.isShiftDown()) {
			getGameController().selectNextFaction();
			e.consume();
		} else {
			super.onKeyPressed(e);
		}
	}
}
