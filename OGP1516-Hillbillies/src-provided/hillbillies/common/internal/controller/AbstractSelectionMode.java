package hillbillies.common.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.selection.Selection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class AbstractSelectionMode<T> extends DefaultInputMode {

	private final Class<T> type;

	protected AbstractSelectionMode(Class<T> type, HillbilliesGameController<?> game) {
		super(game);
		this.type = type;
	}

	private Selection tempSelection = new Selection();
	private Selection oldSelection;

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ESCAPE) {
			cancel();
		} else {
			super.onKeyPressed(e);
		}
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
		if (type.isInstance(object)) {
			select(type.cast(object));
		} else {
			super.objectClicked(object, e);
		}
	}

	protected void cancel() {
		select(null);
	}

	protected void select(T object) {
		if (object != null)
			tempSelection.select(object, true);
		onSelected.accept(object);
	}

	private Consumer<T> onSelected;

	public void setOnSelected(Consumer<T> onSelected) {
		this.onSelected = onSelected;
	}
}
