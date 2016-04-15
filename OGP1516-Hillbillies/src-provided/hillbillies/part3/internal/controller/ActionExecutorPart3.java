package hillbillies.part3.internal.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import hillbillies.common.internal.controller.AbstractSelectionMode;
import hillbillies.common.internal.controller.CubeSelectionMode.Cube;
import hillbillies.common.internal.inputmodes.InputMode;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part2.internal.controller.ActionExecutorPart2;
import hillbillies.part3.facade.IFacade;
import hillbillies.part3.programs.TaskParser;
import hillbillies.part3.programs.internal.SelectedChecker;
import ogp.framework.util.ModelException;

public class ActionExecutorPart3 extends ActionExecutorPart2 {

	public ActionExecutorPart3(IGameController3 game, Consumer<ModelException> errorHandler) {
		super(game, errorHandler);
	}

	@Override
	public IGameController3 getGame() {
		return (IGameController3) super.getGame();
	}

	@Override
	public IFacade getFacade() {
		return (IFacade) super.getFacade();
	}

	public void parseAndSchedule(Faction faction, String filename) {
		final Scheduler scheduler = getScheduler(faction);

		if (SelectedChecker.containsSelected(filename)) {
			getGame().getView().setSchedulingEnabled(false);
			selectMultiCube("Select cubes and press Enter to finalize", cubes -> {
				try {
					scheduleTasks(scheduler, filename, cubes.stream().map(c -> new int[] { c.cubeX, c.cubeY, c.cubeZ })
							.collect(Collectors.toList()));
				} finally {
					getGame().getView().setSchedulingEnabled(true);
				}
			}, () -> getGame().getView().setSchedulingEnabled(true));
		} else {
			scheduleTasks(scheduler, filename, Collections.emptyList());
		}
	}

	private Scheduler getScheduler(Faction faction) {
		try {
			return getFacade().getScheduler(faction);
		} catch (ModelException e) {
			handleError(e);
		}
		return null;
	}

	protected void scheduleTasks(Scheduler scheduler, String filename, List<int[]> cubes) {
		try {
			List<Task> tasks = TaskParser.parseTasksFromFile(filename, getFacade().createTaskFactory(), cubes);
			if (tasks == null) {
				throw new ModelException("Parsing file " + filename + " failed.");
			} else {
				for (Task task : tasks) {
					getFacade().schedule(scheduler, task);
				}
				getGame().getView().setStatusText("Scheduled " + tasks.size() + " tasks.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			getGame().getView().setStatusText("Could not load file: " + e.getMessage());
		} catch (ModelException e) {
			handleError(e);
		}
	}

	protected void selectMultiCube(String info, Consumer<Collection<Cube>> action, Runnable cancelAction) {
		selectMany(info, createMultiCubeSelectionMode(), action, cancelAction);
	}

	protected <T> void selectMany(String info, AbstractSelectionMode<T> selMode, Consumer<Collection<T>> action,
			Runnable cancelAction) {
		getGame().getView().setStatusText(info);
		InputMode oldMode = getGame().getCurrentInputMode();
		final Collection<T> result = new HashSet<>();
		selMode.setOnCanceled(() -> {
			cancelAction.run();
			getGame().switchInputMode(oldMode);
		});
		selMode.setOnSelected(object -> {
			if (object != null) {
				result.add(object);
			} else {
				action.accept(result);
				getGame().switchInputMode(oldMode);
			}
		});
		getGame().switchInputMode(selMode);
	}

	protected MultiCubeSelectionMode createMultiCubeSelectionMode() {
		return new MultiCubeSelectionMode(getGame());
	}

	public void clearSelection() {
		getSelection().clear();
	}

	public void selectObject(Unit unit) {
		getSelection().select(unit, true);
	}

	public void track(Unit unit) {
		getGame().getView().getViewModel().focusObject(unit);
	}
}
