package ogp.framework.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import ogp.framework.util.internal.Options;
import ogp.framework.util.internal.Options.Option;

public class OptionsPanel {

	private final VBox root;
	private final Options options;

	public OptionsPanel(Options options) {
		this.options = options;
		this.root = new VBox();
		root.setAlignment(Pos.CENTER_LEFT);
		initializeRegistry();
		for (Option<?> option : options.getOptions()) {
			Node control = createControl(option);
			if (control != null)
				root.getChildren().add(control);
		}
	}

	protected void initializeRegistry() {
		addCreator(Boolean.class, this::createBooleanControl);
		addCreator(Integer.class, this::createIntegerControl);
	}

	protected <T> void addCreator(Class<T> type, Function<Option<T>, Node> creator) {
		creatorRegistry.put(type, creator);
	}

	private final Map<Class<?>, Function<? extends Option<?>, Node>> creatorRegistry = new HashMap<>();

	protected Node createBooleanControl(Option<Boolean> option) {
		CheckBox cb = new CheckBox(option.getDescription());
		cb.selectedProperty().bindBidirectional(option.currentValueProperty());
		return cb;
	}

	protected Node createIntegerControl(Option<Integer> option) {
		Spinner<Integer> sp = new Spinner<>(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE));
		sp.getValueFactory().valueProperty().bindBidirectional(option.currentValueProperty());
		return sp;
	}

	private <T> Node createControl(Option<T> option) {
		@SuppressWarnings("unchecked")
		Function<Option<T>, Node> creator = (Function<Option<T>, Node>) creatorRegistry.get(option.getType());
		if (creator != null)
			return creator.apply(option);
		else
			System.out.println("Can't find control for option type " + option.getType());
		return null;
	}

	public Options getOptions() {
		return options;
	}

	public Node getRoot() {
		return root;
	}

}
