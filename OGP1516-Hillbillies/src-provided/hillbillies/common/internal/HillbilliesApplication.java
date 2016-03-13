package hillbillies.common.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hillbillies.common.internal.controller.GameController;
import hillbillies.common.internal.options.HillbilliesOptions;
import hillbillies.common.internal.ui.IHillbilliesView;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ogp.framework.game.GameLoop;
import ogp.framework.ui.OptionsPanel;
import ogp.framework.util.ModelException;
import ogp.framework.util.internal.ResourceUtils;

public abstract class HillbilliesApplication<O extends HillbilliesOptions, F, V extends IHillbilliesView>
		extends Application {

	private O options;
	private F facade;
	private Label statusLabel;
	private Stage stage;
	private final ExecutorService exec = Executors.newSingleThreadExecutor();

	protected abstract F createFacade();

	protected abstract O createOptions();

	protected abstract GameController<V> createController(F facade, O options) throws ModelException;

	protected abstract V createView(GameController<V> controller, O options);

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		GridPane pane = new GridPane();

		options = createOptions();
		facade = createFacade();

		ImageView logo = new ImageView(ResourceUtils.loadImage("resources/hillbillies.png"));
		pane.add(logo, 0, 0, 2, 1);
		GridPane.setHalignment(logo, HPos.CENTER);

		Node startNode = createStart();
		pane.add(startNode, 0, 1);
		GridPane.setHalignment(startNode, HPos.CENTER);
		GridPane.setValignment(startNode, VPos.CENTER);

		OptionsPanel optionsPanel = new OptionsPanel(options);
		pane.add(optionsPanel.getRoot(), 1, 1);
		GridPane.setValignment(optionsPanel.getRoot(), VPos.CENTER);

		statusLabel = new Label();
		pane.add(statusLabel, 0, 2, 2, 1);

		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(60);
		pane.getColumnConstraints().add(col);

		col = new ColumnConstraints();
		col.setPercentWidth(40);
		pane.getColumnConstraints().add(col);

		RowConstraints row = new RowConstraints();
		row.setPercentHeight(40);
		pane.getRowConstraints().add(row);

		row = new RowConstraints();
		row.setPercentHeight(50);
		pane.getRowConstraints().add(row);

		row = new RowConstraints();
		row.setPercentHeight(10);
		pane.getRowConstraints().add(row);

		Scene startScene = new Scene(pane, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		startScene.getStylesheets().add("resources/style.css");
		stage.setTitle(getTitle());
		stage.setScene(startScene);
		stage.setResizable(false);
		stage.show();

		startNode.requestFocus();
	}
	
	@Override
	public void stop() throws Exception {
		exec.shutdownNow();
	}

	protected Node createStart() {
		Button startButton = new Button();
		startButton.setGraphic(new ImageView(ResourceUtils.loadImage("resources/start.png")));
		startButton.setOnAction(e -> {
			startButton.setDisable(true);
			Task<GameController<?>> task = createSceneTask(facade, options);

			statusLabel.textProperty().bind(task.titleProperty());

			task.setOnFailed(e2 -> {
				task.getException().printStackTrace();
			});
			task.setOnSucceeded(e2 -> {
				GameController<?> game;
				try {
					game = task.get();
				} catch (Exception e1) {
					e1.printStackTrace();
					return;
				}
				IHillbilliesView view = game.getView();
				Scene scene = new Scene(view.getRoot(), Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
				scene.getStylesheets().add("resources/style.css");
				stage.setScene(scene);
				stage.centerOnScreen();
				GameLoop loop = new GameLoop(game);
				loop.setMaxTimeStep(0.20);
				loop.start();
			});

			exec.submit(task);
		});
		return startButton;
	}

	protected String getTitle() {
		return "The Hillbillies";
	}

	private Task<GameController<?>> createSceneTask(F facade, O options) {
		return new Task<GameController<?>>() {
			@Override
			protected GameController<V> call() throws Exception {
				updateTitle("Creating world...");
				GameController<V> controller = createController(facade, options);
				updateTitle("Creating view...");
				V view = createView(controller, options);
				controller.setView(view);
				updateTitle("Done!");
				return controller;
			}
		};
	}

}
