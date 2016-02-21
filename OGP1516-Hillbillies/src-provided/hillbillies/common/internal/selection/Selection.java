package hillbillies.common.internal.selection;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

public class Selection {

	private final ObservableSet<Object> selection = FXCollections.observableSet();

	public boolean isSelected(Object object) {
		return selection.contains(object);
	}

	public void select(Object object, boolean clearPrevious) {
		if (clearPrevious)
			clear();
		selection.add(object);
	}

	public void selectAll(Collection<?> objects, boolean clearOld) {
		if (clearOld)
			clear();
		selection.addAll(objects);
	}

	public Object getAnySelected() {
		return selection.iterator().next();
	}

	public Set<Object> getObjects() {
		return Collections.unmodifiableSet(selection);
	}

	public boolean isEmpty() {
		return selection.isEmpty();
	}

	public boolean isMulti() {
		return selection.size() > 1;
	}

	public void clear() {
		selection.clear();
	}

	public <T> Set<T> getObjects(Class<T> type) {
		return selection.stream().filter(type::isInstance).map(type::cast).collect(Collectors.toSet());
	}

	public void addListener(SetChangeListener<? super Object> listener) {
		selection.addListener(listener);
	}

	public void removeListener(SetChangeListener<? super Object> listener) {
		selection.removeListener(listener);
	}

	public boolean isSingle() {
		return selection.size() == 1;
	}

	public <T> T getAnySelected(Class<T> type) {
		return getObjects(type).iterator().next();
	}
}
