package hillbillies.part2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hillbillies.common.internal.HillbilliesApplication;
import hillbillies.common.internal.controller.GameController;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.controller.GameControllerPart2;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.map.GameMapReader;
import hillbillies.part2.internal.ui.HillbilliesViewPart2;
import hillbillies.part2.internal.ui.IHillbilliesView2;
import hillbillies.part2.internal.ui.ViewProviders2;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import ogp.framework.util.ModelException;
import ogp.framework.util.internal.ResourceUtils;

public class Part2 extends HillbilliesApplication<Part2Options, IFacade, IHillbilliesView2> {

	private static final String LEVEL_FILE_EXTENSION = ".wrld";
	private static final String LEVELS_PATH = "resources/";

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	protected String getTitle() {
		return super.getTitle() + " - Part 2";
	}

	@Override
	protected GameControllerPart2 createController(IFacade facade, Part2Options options) throws ModelException {
		GameMap map = null;
		try {
			map = new GameMapReader().readFromResource(LEVELS_PATH + levelFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new GameControllerPart2(facade, options, map);
	}

	private String levelFilename = null;

	@Override
	protected Node createStart() {
		Node startButton = super.createStart();
		VBox startGroup = new VBox(10);
		ComboBox<String> levelChoice = new ComboBox<>();
		levelChoice.setOnAction(e -> levelFilename = levelChoice.getValue());
		levelChoice.getItems().addAll(getLevelFilenames());
		if (!levelChoice.getItems().isEmpty()) {
			levelChoice.getSelectionModel().clearAndSelect(0);
			levelFilename = levelChoice.getItems().get(0);
		} else {
			startButton.setDisable(true);
		}
		startGroup.getChildren().add(levelChoice);
		startGroup.getChildren().add(startButton);
		startGroup.setAlignment(Pos.CENTER);
		return startGroup;
	}

	private List<String> getLevelFilenames() {
		try {
			return ResourceUtils.listFileNames(LEVELS_PATH).filter(f -> f.toLowerCase().endsWith(LEVEL_FILE_EXTENSION))
					.collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	protected IFacade createFacade() {
		return new Facade();
	}

	@Override
	protected Part2Options createOptions() {
		Part2Options options = new Part2Options();
		return options;
	}

	@Override
	protected IHillbilliesView2 createView(GameController<IHillbilliesView2> controller, Part2Options options) {
		return new HillbilliesViewPart2((ViewProviders2) controller.createViewProviders(), options);
	}

}
