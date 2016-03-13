package hillbillies.common.internal.controller;

import java.util.Optional;

import hillbillies.model.Unit;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UnitSelectionMode extends AbstractSelectionMode<Unit> {

	public UnitSelectionMode(HillbilliesGameController<?> game) {
		super(Unit.class, game);
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.TAB && !e.isShiftDown()) {
			getActionExecutor().selectNext();
			e.consume();
		}
		if (e.getCode() == KeyCode.ENTER) {
			Optional<Unit> unit = getSelection().getAnySelected(Unit.class);
			if (unit.isPresent()) {
				select(unit.get());
				e.consume();
			}
		} else {
			super.onKeyPressed(e);
		}
	}

}
