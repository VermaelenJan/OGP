package hillbillies.common.internal.ui;

import hillbillies.common.internal.Constants;
import hillbillies.common.internal.inputmodes.UserInputHandler;
import hillbillies.common.internal.options.HillbilliesOptions;
import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import hillbillies.common.internal.ui.viewparts.InfoArea;
import hillbillies.common.internal.ui.viewparts.LevelSlider;
import hillbillies.common.internal.ui.viewparts.MiniMap;
import hillbillies.common.internal.ui.viewparts.WorldView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ogp.framework.game.IGameView;
import ogp.framework.ui.FPSCounter;

public abstract class HillbilliesView implements IGameView {

	private final BorderPane root;

	private final MiniMap<?> miniMap;
	private final WorldView worldView;

	private final VBox leftPanel;
	private final LevelSlider levelSlider;
	private final ViewModel viewModel;

	private final ViewProviders providers;
	private final Label status;
	private final HillbilliesOptions options;
	private InfoArea infoArea;

	public HillbilliesView(ViewProviders providers, HillbilliesOptions options) {
		this.providers = providers;
		this.options = options;
		this.root = new BorderPane();

		viewModel = createViewModel();
		viewModel.refreshVisibleInformation();

		this.worldView = createWorldView();

		this.miniMap = createMiniMap();

		this.levelSlider = new LevelSlider(viewModel);

		this.leftPanel = new VBox();
		leftPanel.setPrefWidth(Constants.SIDEBAR_WIDTH);
		root.setLeft(leftPanel);

		HBox mmSlider = new HBox();
		mmSlider.getChildren().add(miniMap.getRoot());
		mmSlider.getChildren().add(levelSlider.getRoot());
		HBox.setMargin(levelSlider.getRoot(), new Insets(5));
		leftPanel.getChildren().add(mmSlider);

		FPSCounter fps = new FPSCounter();
		Label debugInfo = new Label();
		debugInfo.textProperty().bind(Bindings.format("%.1f fps", fps.fpsEstimateProperty()));
		leftPanel.getChildren().add(debugInfo);

		this.infoArea = createInfoArea();
		leftPanel.getChildren().add(infoArea.getRoot());

		VBox statusBox = new VBox();
		root.setTop(statusBox);
		this.status = new Label("Status");
		status.setStyle("-fx-font-size: 16;");
		statusBox.getChildren().add(status);

		root.setCenter(worldView.getRoot());
		worldView.getRoot().setId("worldview");

		fps.start();
	}

	public HillbilliesOptions getOptions() {
		return options;
	}

	public UnitInfoProvider getUnitInfoProvider() {
		return providers.getUnitInfoProvider();
	}

	public WorldInfoProvider getWorldInfoProvider() {
		return providers.getWip();
	}

	public ActionExecutor getActionExecutor() {
		return providers.getAe();
	}

	public SelectionProvider getSelectionProvider() {
		return providers.getSp();
	}

	private UserInputHandler input;

	public void setUserInputHandler(UserInputHandler input) {
		this.input = input;
		root.addEventHandler(KeyEvent.KEY_PRESSED, e -> input.onKeyPressed(e));
		this.worldView.setUserInputHandler(input);
	}

	public UserInputHandler getUserInputHandler() {
		return input;
	}

	public ViewModel getViewModel() {
		return viewModel;
	}

	protected abstract InfoArea createInfoArea();

	protected WorldView createWorldView() {
		return WorldView.create(this.getViewModel(), this.getOptions());
	}

	protected abstract ViewModel createViewModel();

	protected MiniMap<?> createMiniMap() {
		return new MiniMap<Void>(viewModel);
	}

	public Parent getRoot() {
		return root;
	}

	@Override
	public void refreshDisplay() {
		viewModel.update();
		infoArea.refresh();
		status.setText(getStatusText());
	}

	private String statusText = "";

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
}
