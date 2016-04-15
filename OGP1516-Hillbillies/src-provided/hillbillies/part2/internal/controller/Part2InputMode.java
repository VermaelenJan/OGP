package hillbillies.part2.internal.controller;

import hillbillies.part1.internal.controller.Part1InputMode;
import hillbillies.part2.internal.Constants;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Part2InputMode extends Part1InputMode {

	public Part2InputMode(IGameController2 controller) {
		super(controller);
	}

	@Override
	protected ActionExecutorPart2 getActionExecutor() {
		return (ActionExecutorPart2) super.getActionExecutor();
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.C) {
			getActionExecutor().spawnUnits(1);
			e.consume();
		} else if (e.getCode() == KeyCode.W) {
			getActionExecutor().initiateWork();
			e.consume();
		} else if (e.isShiftDown() && e.getCode() == KeyCode.TAB) {
			getActionExecutor().selectNextFaction();
			e.consume();
		} else if (e.getCode() == KeyCode.S) {
			getActionExecutor().spawnUnits(Constants.UNITS_TO_SPAWN);
		} else if (e.getCode() == KeyCode.ESCAPE && !getSelection().isEmpty()) {
			getSelection().clear();
		} else {
			super.onKeyPressed(e);
		}
	}

}
