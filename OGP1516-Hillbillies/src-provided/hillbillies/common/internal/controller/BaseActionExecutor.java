package hillbillies.common.internal.controller;

import java.util.function.Consumer;

import hillbillies.common.internal.providers.ActionExecutor;
import ogp.framework.util.ModelException;

public abstract class BaseActionExecutor implements ActionExecutor {
	private final Object facade;
	private final Consumer<ModelException> errorHandler;
	private final HillbilliesGameController<?> game;
	
	public BaseActionExecutor(HillbilliesGameController<?> game, Consumer<ModelException> errorHandler) {
		this.game = game;
		this.facade = game.getFacade();
		this.errorHandler = errorHandler;
	}
	
	public HillbilliesGameController<?> getGame() {
		return game;
	}
	
	public Object getFacade() {
		return facade;
	}
	
	protected void handleError(ModelException e) {
		errorHandler.accept(e);
	}

}
