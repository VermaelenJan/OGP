package hillbillies.common.internal.ui;

import hillbillies.common.internal.inputmodes.UserInputHandler;
import hillbillies.common.internal.ui.viewmodel.IViewModel;
import javafx.scene.Parent;
import ogp.framework.game.IGameView;

public interface IHillbilliesView extends IGameView {

	IViewModel getViewModel();
	
	void setUserInputHandler(UserInputHandler handler);

	boolean getConsumeSpriteClicks();

	boolean getHighlightCurrentTile();

	void setConsumeSpriteClicks(boolean b);

	void setHighlightCurrentTile(boolean b);

	Parent getRoot();

}
