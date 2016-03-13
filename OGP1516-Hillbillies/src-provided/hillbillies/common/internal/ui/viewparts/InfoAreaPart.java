package hillbillies.common.internal.ui.viewparts;

import javafx.scene.Node;

public interface InfoAreaPart<T> {
	public Node getRoot();

	void updateInfo();

	public void setObject(T object);
	
	public T getObject();
}
