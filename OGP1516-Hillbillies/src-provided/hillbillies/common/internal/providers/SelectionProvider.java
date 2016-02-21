package hillbillies.common.internal.providers;

import hillbillies.common.internal.selection.Selection;

public interface SelectionProvider {

	public Selection getSelection();

	public void setSelection(Selection selection);
}
