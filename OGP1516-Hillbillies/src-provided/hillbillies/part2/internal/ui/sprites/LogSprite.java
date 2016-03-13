package hillbillies.part2.internal.ui.sprites;

import hillbillies.common.internal.ui.sprites.AbstractSprite;
import hillbillies.model.Log;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ogp.framework.util.internal.ResourceUtils;

public class LogSprite extends AbstractSprite<Log, IGameObjectInfoProvider> {

	private static final Image LOG_IMAGE = ResourceUtils.loadImage("resources/log.png");
	
	private ImageView view;

	public LogSprite(Log log, IGameObjectInfoProvider infoProvider) {
		super(log, infoProvider);
		worldSizeXProperty().set(0.55);
		worldSizeYProperty().set(0.20);
		worldSizeZProperty().set(0.20);
		
		view = new ImageView(LOG_IMAGE);
		view.xProperty().bind(view.getImage().widthProperty().divide(-2));
		view.yProperty().bind(view.getImage().heightProperty().divide(-2));
	}

	@Override
	public void update() {
		double[] position = getInfoProvider().getPosition(getObject());
		worldXProperty().set(position[0]);
		worldYProperty().set(position[1]);
		worldZProperty().set(position[2]);
	}

	@Override
	public Node getGraph() {
		return view;
	}

}
