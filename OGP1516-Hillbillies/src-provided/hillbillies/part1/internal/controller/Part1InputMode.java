package hillbillies.part1.internal.controller;

import hillbillies.common.internal.controller.DefaultInputMode;
import hillbillies.common.internal.controller.GameController;
import hillbillies.part1.facade.IFacade;
import javafx.scene.input.KeyEvent;

public class Part1InputMode extends DefaultInputMode {

	public Part1InputMode(GameController<? extends IFacade> controller) {
		super(controller);
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		switch (e.getCode()) {
		case C:
			getActionExecutor().initiateCreateUnit();
			e.consume();
			break;
		case W:
			getActionExecutor().work();
			e.consume();
			break;
		case R:
			getActionExecutor().rest();
			e.consume();
			break;
		case A:
			getActionExecutor().initiateAttack();
			e.consume();
			break;
		case TAB:
			getActionExecutor().selectNext();
			e.consume();
			break;
		case Y:
			getActionExecutor().moveToAdjacent(-1, -1, getZ(e));
			e.consume();
			break;
		case U:
			getActionExecutor().moveToAdjacent(0, -1, getZ(e));
			e.consume();
			break;
		case I:
			getActionExecutor().moveToAdjacent(+1, -1, getZ(e));
			e.consume();
			break;
		case H:
			getActionExecutor().moveToAdjacent(-1, 0, getZ(e));
			e.consume();
			break;
		case J:
			getActionExecutor().moveToAdjacent(0, 0, getZ(e));
			e.consume();
			break;
		case B:
			getActionExecutor().moveToAdjacent(-1, 1, getZ(e));
			e.consume();
			break;
		case N:
			getActionExecutor().moveToAdjacent(0, 1, getZ(e));
			e.consume();
			break;
		case M:
		case COMMA: // azerty
			getActionExecutor().moveToAdjacent(+1, 1, getZ(e));
			e.consume();
			break;
		case P: // Path
			getActionExecutor().initiateMove();
			e.consume();
			break;
		case S:
			getActionExecutor().toggleSprint();
			e.consume();
		default:
			super.onKeyPressed(e);
		}
	}

	private int getZ(KeyEvent e) {
		if (e.isControlDown()) {
			return +1;
		} else if (e.isAltDown()) {
			return -1;
		} else {
			return 0;
		}
	}

}
