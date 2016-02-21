package hillbillies.common.internal.controller;

import java.util.Set;

import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.selection.Selection;
import hillbillies.common.internal.ui.HillbilliesView;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class DefaultInputMode implements InputMode {
	private final GameController<?> gameController;
	private Runnable deactivationHandler;
	private Runnable activationHandler;

	public DefaultInputMode(GameController<?> game) {
		this.gameController = game;
	}

	public HillbilliesView getView() {
		return getGameController().getView();
	}

	public ViewModel getViewModel() {
		return getView().getViewModel();
	}

	public GameController<?> getGameController() {
		return gameController;
	}

	protected Selection getSelection() {
		return getGameController().getSelectionProvider().getSelection();
	}

	protected ActionExecutor getActionExecutor() {
		return getGameController().getActionExecutor();
	}

	@Override
	public void activate() {
		if (activationHandler != null) {
			activationHandler.run();
		}
	}

	@Override
	public void deactivate() {
		if (deactivationHandler != null) {
			deactivationHandler.run();
		}
	}

	@Override
	public void setOnActivate(Runnable handler) {
		this.activationHandler = handler;
	}

	@Override
	public void setOnDeactivate(Runnable handler) {
		this.deactivationHandler = handler;
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		switch (e.getCode()) {
		case UP:
		case KP_UP:
			if (e.isShiftDown() || e.isControlDown()) {
				getViewModel().levelUp();
			} else if (e.isAltDown()) {
				getViewModel().levelDown();
			}
			break;
		case DOWN:
		case KP_DOWN:
			if (e.isShiftDown() || e.isAltDown()) {
				getViewModel().levelDown();
			} else if (e.isControlDown()) {
				getViewModel().levelUp();
			}
			break;
		case ESCAPE:
			gameController.exit();
			e.consume();
			break;
		default:
			break;
		}
	}

	@Override
	public void objectClicked(Object object, MouseEvent e) {
		getSelection().select(object, !(e.isControlDown() || e.isMetaDown()));
	}

	@Override
	public void regionSelected(double minWorldX, double minWorldY, double minWorldZ, double maxWorldX, double maxWorldY,
			double maxWorldZ, MouseEvent e) {
		Set<?> objects = getGameController().getWorldInfoProvider().getObjectsInBox(minWorldX, minWorldY, minWorldZ,
				maxWorldX, maxWorldY, maxWorldZ);
		boolean clearPrevious = !(e.isControlDown() || e.isMetaDown());
		getSelection().selectAll(objects, clearPrevious);
	}

	@Override
	public void worldPointClicked(double worldX, double worldY, double worldZ, MouseEvent e) {
		if (e.getClickCount() == 2) {
			getActionExecutor().moveTo(getViewModel().worldPointToWorldCube(worldX),
					getViewModel().worldPointToWorldCube(worldY), getViewModel().worldPointToWorldCube(worldZ));
		}
	}
}
