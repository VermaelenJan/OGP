package hillbillies.part1.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.controller.GameController;
import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.part1.facade.IFacade;
import ogp.framework.util.ModelException;

public abstract class BaseActionExecutor<F extends IFacade> implements ActionExecutor {
	private final F facade;
	private final Consumer<ModelException> errorHandler;
	private final GameController<F> game;
	
	public BaseActionExecutor(GameController<F> game, Consumer<ModelException> errorHandler) {
		this.game = game;
		this.facade = game.getFacade();
		this.errorHandler = errorHandler;
	}
	
	public GameController<F> getGame() {
		return game;
	}
	
	public F getFacade() {
		return facade;
	}
	
	protected void handleError(ModelException e) {
		errorHandler.accept(e);
	}

}
