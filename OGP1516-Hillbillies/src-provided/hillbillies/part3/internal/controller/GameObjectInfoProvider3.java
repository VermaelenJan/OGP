package hillbillies.part3.internal.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part2.internal.controller.GameObjectInfoProvider;
import hillbillies.part3.facade.IFacade;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import ogp.framework.util.ModelException;

public class GameObjectInfoProvider3 extends GameObjectInfoProvider implements IGameObjectInfoProvider3 {

	public GameObjectInfoProvider3(IGameController3 game, Consumer<ModelException> errorHandler) {
		super(game, errorHandler);
	}

	@Override
	protected IGameController3 getGame() {
		return (IGameController3) super.getGame();
	}

	@Override
	protected IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	@Override
	public Set<Faction> getActiveFactions() {
		try {
			Set<Faction> result = getFacade().getActiveFactions(getGame().getWorld());
			if (result != null)
				return result;
		} catch (ModelException e) {
			handleError(e);
		}
		return Collections.emptySet();
	}

	@Override
	public List<Task> getTasksForFaction(Faction faction) {
		List<Task> result = new ArrayList<>();
		try {
			Scheduler scheduler = getFacade().getScheduler(faction);
			if (scheduler != null) {
				Iterator<Task> it = getFacade().getAllTasksIterator(scheduler);
				while (it != null && it.hasNext()) {
					result.add(it.next());
				}
			}
		} catch (ModelException e) {
			handleError(e);
		}
		return result;
	}

	@Override
	public boolean getTaskAssigned(Task task) {
		try {
			return getFacade().getAssignedUnit(task) != null;
		} catch (ModelException e) {
			handleError(e);
			return false;
		}
	}

	@Override
	public String getTaskName(Task task) {
		try {
			return getFacade().getName(task);
		} catch (ModelException e) {
			handleError(e);
			return "";
		}
	}

	@Override
	public int getTaskPriority(Task task) {
		try {
			return getFacade().getPriority(task);
		} catch (ModelException e) {
			handleError(e);
			return 0;
		}
	}

	@Override
	public Unit getAssignedUnit(Task task) {
		try {
			return getFacade().getAssignedUnit(task);
		} catch (ModelException e) {
			handleError(e);
			return null;
		}
	}

	@Override
	public Task getAssignedTask(Unit unit) {
		try {
			return getFacade().getAssignedTask(unit);
		} catch (ModelException e) {
			handleError(e);
			return null;
		}
	}
}
