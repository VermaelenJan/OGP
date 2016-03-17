package hillbillies.common.internal.controller;

import java.util.HashSet;
import java.util.Set;

import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.inputmodes.UserInputHandler;
import hillbillies.common.internal.options.HillbilliesOptions;
import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.selection.Selection;
import hillbillies.common.internal.ui.IHillbilliesView;
import hillbillies.common.internal.ui.ViewProviders;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.SetChangeListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ogp.framework.util.ModelException;

public abstract class GameController<V extends IHillbilliesView> implements HillbilliesGameController<V> {

	private final Object facade;
	private V view;
	private final HillbilliesOptions options;

	public GameController(Object facade, HillbilliesOptions options) {
		this.facade = facade;
		this.options = options;
		switchInputMode(createDefaultInputMode());
	}

	@Override
	public HillbilliesOptions getOptions() {
		return options;
	}

	private class InputDelegator implements UserInputHandler {

		@Override
		public void worldPointClicked(double worldX, double worldY, double worldZ, MouseEvent e) {
			getCurrentInputMode().worldPointClicked(worldX, worldY, worldZ, e);
		}

		@Override
		public void objectClicked(Object object, MouseEvent e) {
			getCurrentInputMode().objectClicked(object, e);
		}

		@Override
		public void onKeyPressed(KeyEvent e) {
			getCurrentInputMode().onKeyPressed(e);
		}

		@Override
		public void regionSelected(double minWorldX, double minWorldY, double minWorldZ, double maxWorldX,
				double maxWorldY, double maxWorldZ, MouseEvent e) {
			getCurrentInputMode().regionSelected(minWorldX, minWorldY, minWorldZ, maxWorldX, maxWorldY, maxWorldZ, e);
		}

	}

	private final UserInputHandler inputHander = new InputDelegator();

	public void setView(V view) {
		this.view = view;
		view.setUserInputHandler(inputHander);
	}

	@Override
	public Object getFacade() {
		return facade;
	}

	protected void handleError(ModelException e) {
		if (getOptions().printModelExceptionTraces().getValue()) {
			getView().setStatusText("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public V getView() {
		return view;
	}

	@Override
	public abstract void updateGame(double dt);

	public abstract UnitInfoProvider getUnitInfoProvider();

	private static class DefaultSelectionProvider implements SelectionProvider {

		private Selection selection = new Selection();
		private final Set<SetChangeListener<? super Object>> listeners = new HashSet<>();

		@Override
		public Selection getSelection() {
			return selection;
		}

		@Override
		public void setSelection(Selection selection) {
			if (this.selection != null) {
				for (SetChangeListener<? super Object> listener : listeners)
					this.selection.removeListener(listener);
			}
			this.selection = selection;
			if (selection != null) {
				for (SetChangeListener<? super Object> listener : listeners) {
					this.selection.addListener(listener);
				}
			}
		}

		@Override
		public void addListener(SetChangeListener<? super Object> listener) {
			listeners.add(listener);
			if (this.selection != null) {
				selection.addListener(listener);
			}
		}

		@Override
		public void removeListener(SetChangeListener<? super Object> listener) {
			listeners.remove(listener);
			if (this.selection != null) {
				selection.removeListener(listener);
			}
		}
	}

	private SelectionProvider sp = new DefaultSelectionProvider();

	@Override
	public SelectionProvider getSelectionProvider() {
		return sp;
	}

	@Override
	public void exit() {
		Platform.exit();
	}

	protected abstract InputMode createDefaultInputMode();

	private ObjectProperty<InputMode> currentInputMode = new SimpleObjectProperty<>();

	@Override
	public void switchInputMode(InputMode newMode) {
		if (currentInputMode.get() != null) {
			currentInputMode.get().deactivate();
		}
		currentInputMode.set(newMode);
		if (newMode != null) {
			newMode.activate();
		}
	}

	public ObjectProperty<InputMode> currentInputModeProperty() {
		return currentInputMode;
	}

	@Override
	public InputMode getCurrentInputMode() {
		return currentInputMode.get();
	}

	public abstract void createUnit(int cubeX, int cubeY, int cubeZ);

	public abstract ViewProviders createViewProviders();

}
