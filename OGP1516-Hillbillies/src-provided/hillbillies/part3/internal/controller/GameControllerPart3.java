package hillbillies.part3.internal.controller;

import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.controller.GameControllerPart2;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part3.facade.IFacade;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import hillbillies.part3.internal.ui.IHillbilliesView3;
import hillbillies.part3.internal.ui.ViewProviders3;
import ogp.framework.util.ModelException;

public class GameControllerPart3 extends GameControllerPart2 implements IGameController3 {

	public GameControllerPart3(IFacade facade, Part2Options options, GameMap map) throws ModelException {
		super(facade, options, map);
	}
	
	@Override
	public IHillbilliesView3 getView() {
		return (IHillbilliesView3) super.getView();
	}

	@Override
	public hillbillies.part3.facade.IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	@Override
	public IGameObjectInfoProvider3 getUnitInfoProvider() {
		return (IGameObjectInfoProvider3) super.getUnitInfoProvider();
	}

	@Override
	protected GameObjectInfoProvider3 createGameObjectInfoProvider() {
		return new GameObjectInfoProvider3(this, this::handleError);
	}

	@Override
	public ActionExecutorPart3 getActionExecutor() {
		return (ActionExecutorPart3) super.getActionExecutor();
	}

	@Override
	protected ActionExecutorPart3 createActionExecutor() {
		return new ActionExecutorPart3(this, this::handleError);
	}

	@Override
	public ViewProviders3 createViewProviders() {
		return new ViewProviders3(getUnitInfoProvider(), getWorldInfoProvider(), getActionExecutor(),
				getSelectionProvider());
	}

}
