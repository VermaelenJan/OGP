package hillbillies.common.internal.controller;

import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.inputmodes.UserInputHandler;
import hillbillies.common.internal.options.HillbilliesOptions;
import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.common.internal.selection.Selection;
import hillbillies.common.internal.ui.HillbilliesView;
import hillbillies.common.internal.ui.ViewProviders;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ogp.framework.game.IGameController;
import ogp.framework.util.ModelException;

public abstract class GameController<F> implements IGameController {

	private final F facade;
	private HillbilliesView view;
	private final HillbilliesOptions options;

	public GameController(F facade, HillbilliesOptions options) {
		this.facade = facade;
		this.options = options;
		switchInputMode(createDefaultInputMode());
	}

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

	public void setView(HillbilliesView view) {
		this.view = view;
		view.setUserInputHandler(inputHander);
	}

	public F getFacade() {
		return facade;
	}

	protected void handleError(ModelException e) {
		getView().setStatusText("ERROR: " + e.getMessage());
		e.printStackTrace();
	}

	@Override
	public HillbilliesView getView() {
		return view;
	}

	@Override
	public abstract void updateGame(double dt);

	public abstract ActionExecutor getActionExecutor();

	public abstract UnitInfoProvider getUnitInfoProvider();

	public abstract WorldInfoProvider getWorldInfoProvider();

	private static class DefaultSelectionProvider implements SelectionProvider {

		private Selection selection = new Selection();

		@Override
		public Selection getSelection() {
			return selection;
		}

		@Override
		public void setSelection(Selection selection) {
			this.selection = selection;
		}
	}

	private SelectionProvider sp = new DefaultSelectionProvider();

	public SelectionProvider getSelectionProvider() {
		return sp;
	}

	@Override
	public void exit() {
		Platform.exit();
	}

	protected abstract InputMode createDefaultInputMode();

	private ObjectProperty<InputMode> currentInputMode = new SimpleObjectProperty<>();

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

	public InputMode getCurrentInputMode() {
		return currentInputMode.get();
	}

	public void keyPressed(KeyEvent e) {
		getCurrentInputMode().onKeyPressed(e);
	}

	public abstract void createUnit(int cubeX, int cubeY, int cubeZ);

	public abstract ViewProviders createViewProviders();

}
