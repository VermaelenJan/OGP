package hillbillies.common.internal.ui.viewparts;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import hillbillies.common.internal.providers.ActionExecutor;
import hillbillies.common.internal.providers.SelectionProvider;
import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.selection.Selection;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ogp.framework.util.internal.GenericFactory;

public abstract class InfoArea {

	private final VBox root;
	private final UnitInfoProvider infoProvider;
	private final SelectionProvider selectionProvider;
	private SetChangeListener<? super Object> listener = new SetChangeListener<Object>() {

		@Override
		public void onChanged(javafx.collections.SetChangeListener.Change<? extends Object> change) {
			onSelectionUpdated();
		}
	};
	private final ActionExecutor ae;

	public InfoArea(SelectionProvider selection, UnitInfoProvider infoProvider, ActionExecutor ae) {
		this.selectionProvider = selection;
		selection.getSelection().addListener(listener);
		this.root = new VBox();
		this.infoProvider = infoProvider;
		this.ae = ae;

		registerProviders();
		onSelectionUpdated();
	}
	
	protected ActionExecutor getActionExecutor() {
		return ae;
	}
	
	protected UnitInfoProvider getUnitInfoProvider() {
		return infoProvider;
	}

	private final PartFactory partFactory = new PartFactory();
	private InfoAreaPart<Object> currentPart;

	protected abstract void registerProviders();
	
	protected <T> void registerProvider(Class<T> type, Supplier<? extends InfoAreaPart<T>> supplier) {
		partFactory.register(type, supplier);
	}

	public static class PartFactory {
		private final GenericFactory infoAreaFactory = new GenericFactory();
		private final Map<Class<?>, InfoAreaPart<?>> cache = new HashMap<>();

		public <T> void register(Class<T> type, Supplier<? extends InfoAreaPart<T>> supplier) {
			infoAreaFactory.registerSupplier(type, o -> supplier.get());
		}

		public <T> InfoAreaPart<T> getPartFor(T object) {
			if (object == null) {
				return null;
			}
			@SuppressWarnings("unchecked")
			InfoAreaPart<T> part = (InfoAreaPart<T>) cache.get(object.getClass());
			if (part == null) {
				part = infoAreaFactory.create(object);
				cache.put(object.getClass(), part);
			}
			part.setObject(object);
			return part;
		}
	}
	
	protected Selection getSelection() {
		return selectionProvider.getSelection();
	}

	protected void onSelectionUpdated() {
		root.getChildren().clear();
		currentPart = null;
		if (getSelection().isSingle()) {
			currentPart = partFactory.getPartFor(getSelection().getAnySelected());
			if (currentPart != null)
				root.getChildren().add(currentPart.getRoot());
		} else if (getSelection().isMulti()) {
			root.getChildren().add(new Label("(multiple selected)"));
		} else {
			root.getChildren().add(new Label("(no selection)"));
		}
	}

	public Node getRoot() {
		return root;
	}

	public void refresh() {
		if (currentPart != null)
			currentPart.updateInfo();
	}

}
