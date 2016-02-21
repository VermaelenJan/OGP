package hillbillies.common.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.selection.Selection;
import hillbillies.model.Unit;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class UnitSelectionMode extends DefaultInputMode {

	private Selection tempSelection = new Selection();
	private Selection oldSelection;

	public UnitSelectionMode(GameController<?> game) {
		super(game);
	}
	
	@Override
	public void activate() {
		super.activate();
		this.oldSelection = getSelection();
		tempSelection.selectAll(getSelection().getObjects(), true);
		getGameController().getSelectionProvider().setSelection(tempSelection);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getGameController().getSelectionProvider().setSelection(oldSelection);
	}
	
	@Override
	public void objectClicked(Object object, MouseEvent e) {
		if (object instanceof Unit) {
			select((Unit)object);
		} else {
			super.objectClicked(object, e);
		}
	}

	private void select(Unit object) {
		tempSelection.select(object, true);
		onSelected.accept(object);
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.TAB) {
			getActionExecutor().selectNext();
			e.consume();
		}
		if (e.getCode() == KeyCode.ENTER) {
			Unit unit = getSelection().getAnySelected(Unit.class);
			if (unit != null) {
				select(unit);
				e.consume();
			}
		} else {
			super.onKeyPressed(e);
		}
	}

	private Consumer<Unit> onSelected;

	public void setOnSelected(Consumer<Unit> onSelected) {
		this.onSelected = onSelected;
	}

}
