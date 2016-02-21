package hillbillies.common.internal.inputmodes;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface UserInputHandler {

	void worldPointClicked(double worldX, double worldY, double worldZ, MouseEvent e);

	void objectClicked(Object object, MouseEvent e);

	void onKeyPressed(KeyEvent e);

	void regionSelected(double minWorldX, double minWorldY, double minWorldZ, double maxWorldX, double maxWorldY,
			double maxWorldZ, MouseEvent e);

}
