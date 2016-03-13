package hillbillies.common.internal.providers;

import hillbillies.common.internal.selection.Selection;
import javafx.collections.SetChangeListener;

public interface SelectionProvider {

	public Selection getSelection();

	public void setSelection(Selection selection);

	void addListener(SetChangeListener<? super Object> listener);

	void removeListener(SetChangeListener<? super Object> listener);
}
