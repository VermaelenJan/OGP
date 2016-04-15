package hillbillies.part3.internal.ui.viewparts;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hillbillies.common.internal.ui.viewparts.ControlArea;
import hillbillies.model.Faction;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part2.internal.ui.sprites.FactionColors;
import hillbillies.part3.internal.controller.ActionExecutorPart3;
import hillbillies.part3.internal.providers.IGameObjectInfoProvider3;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ogp.framework.util.internal.GUIUtils;
import ogp.framework.util.internal.ResourceUtils;

public class SchedulerControlArea extends ControlArea {

	private class TaskItem {
		private final Task task;
		private final BooleanProperty assigned;
		private final IntegerProperty priority;
		private final StringProperty name;

		public TaskItem(Task task) {
			if (task == null) {
				throw new NullPointerException();
			}
			this.task = task;
			this.assigned = new SimpleBooleanProperty(false);
			this.priority = new SimpleIntegerProperty();
			this.name = new SimpleStringProperty();
			refresh();
		}

		public Task getTask() {
			return task;
		}

		public ObservableBooleanValue getAssigned() {
			return assigned;
		}

		public ObservableIntegerValue getPriority() {
			return priority;
		}

		public ObservableStringValue getName() {
			return name;
		}

		public void refresh() {
			assigned.set(oip.getTaskAssigned(task));
			priority.set(oip.getTaskPriority(task));
			name.set(oip.getTaskName(task));
		}
	}

	private static final String PROGRAM_FOLDER = "resources/tasks";
	private final VBox root;
	private ListView<Faction> factions;

	private ListView<TaskItem> scheduler;

	private ComboBox<String> filenames;
	private Button schedule;

	private final IGameObjectInfoProvider3 oip;
	private final ActionExecutorPart3 ae;
	private ObservableList<Faction> factionList;
	private ObservableList<TaskItem> taskList;

	private final BooleanProperty schedulingDisabled = new SimpleBooleanProperty(false);

	public SchedulerControlArea(IGameObjectInfoProvider3 oip, ActionExecutorPart3 ae) {
		this.oip = oip;
		this.ae = ae;

		this.root = new VBox();

		factionList = FXCollections.observableArrayList();
		factions = new ListView<>(
				new SortedList<>(factionList, (f1, f2) -> oip.getFactionName(f1).compareTo(oip.getFactionName(f2))));
		factions.setCellFactory(lv -> new FactionCell());
		factions.setPrefHeight(230);
		factions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		factions.getSelectionModel().selectedItemProperty().addListener((c, old, nw) -> {
			updateTasks();
		});
		root.getChildren().add(factions);

		HBox scheduleBox = new HBox();
		scheduleBox.setAlignment(Pos.CENTER_LEFT);
		
		filenames = new ComboBox<>();
		scheduleBox.getChildren().add(filenames);

		schedule = GUIUtils.createButtonWithGraphic("resources/schedule.png", "Schedule", e -> schedule());
		scheduleBox.getChildren().add(schedule);

		root.getChildren().add(scheduleBox);

		schedule.disableProperty()
				.bind(Bindings.or(schedulingDisabled,
						Bindings.or(factions.getSelectionModel().selectedItemProperty().isNull(),
								filenames.getSelectionModel().selectedItemProperty().isNull())));

		taskList = FXCollections.observableArrayList();
		scheduler = new ListView<>(taskList);
		scheduler.setCellFactory(lv -> new TaskCell());
		scheduler.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				TaskItem ti = scheduler.getSelectionModel().getSelectedItem();
				if (ti != null) {
					Unit unit = oip.getAssignedUnit(ti.getTask());
					if (unit != null) {
						ae.selectObject(unit);
					}
				}
			}
		});
		root.getChildren().add(scheduler);

		refresh();
		updateFiles();
	}

	private class TaskCell extends ListCell<TaskItem> {
		@Override
		public void updateItem(TaskItem item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null && !empty) {
				textProperty().bind(Bindings.format("%2d | %s", item.getPriority(), item.getName()));
				Circle busy = new Circle(5);
				busy.fillProperty()
						.bind(Bindings.when(item.getAssigned()).then(Color.FORESTGREEN).otherwise(Color.DARKGRAY));
				setGraphic(busy);
			} else {
				setGraphic(null);
				textProperty().unbind();
				setText(null);
			}
		}
	}

	private class FactionCell extends ListCell<Faction> {
		@Override
		public void updateItem(Faction item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null && !empty) {
				setText(oip.getFactionName(item));
				Rectangle rect = new Rectangle(10, 10);
				rect.setFill(FactionColors.factionColors[oip.getFactionIndex(item)][0]);
				setGraphic(rect);
			} else {
				setGraphic(null);
				setText(null);
			}
		}
	}

	private void schedule() {
		ae.parseAndSchedule(factions.getSelectionModel().getSelectedItem(),
				PROGRAM_FOLDER + "/" + filenames.getSelectionModel().getSelectedItem());
	}

	@Override
	public Node getRoot() {
		return root;
	}

	@Override
	public void refresh() {
		updateFactions();
		updateTasks();
	}

	private void updateFactions() {
		Set<Faction> newFactions = new HashSet<>(oip.getActiveFactions());
		factionList.removeIf(f -> !newFactions.contains(f));
		newFactions.removeIf(f -> factionList.contains(f));
		if (!newFactions.isEmpty()) {
			factionList.addAll(newFactions);
		}
	}

	private void updateTasks() {
		Faction faction = factions.getSelectionModel().getSelectedItem();
		if (faction != null) {
			List<Task> newTasks = oip.getTasksForFaction(faction);
			taskList.removeIf(ti -> !newTasks.contains(ti.getTask()));
			newTasks.removeIf(t -> taskList.stream().anyMatch(ti -> ti.getTask() == t));
			List<TaskItem> newTaskItems = newTasks.stream().map(TaskItem::new).collect(Collectors.toList());
			taskList.addAll(newTaskItems);
		} else {
			taskList.clear();
		}
		for (TaskItem ti : taskList) {
			ti.refresh();
		}
	}

	private void updateFiles() {
		try {
			List<String> files = ResourceUtils.listFileNames(PROGRAM_FOLDER)
					.filter(f -> f.toLowerCase().endsWith(".txt")).collect(Collectors.toList());
			filenames.getItems().setAll(files);
			filenames.getSelectionModel().selectFirst();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setSchedulingEnabled(boolean value) {
		this.schedulingDisabled.set(!value);
	}

}
