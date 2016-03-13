package hillbillies.part2.internal.ui.sprites;

import hillbillies.common.internal.ui.sprites.AbstractSprite;
import hillbillies.model.Boulder;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ogp.framework.util.internal.ResourceUtils;

public class BoulderSprite extends AbstractSprite<Boulder, IGameObjectInfoProvider> {

	private static final Image BOULDER_IMAGE = ResourceUtils.loadImage("resources/boulder.png");
	
	private ImageView view;

	public BoulderSprite(Boulder boulder, IGameObjectInfoProvider infoProvider) {
		super(boulder, infoProvider);
		worldSizeXProperty().set(0.5);
		worldSizeYProperty().set(0.5);
		worldSizeZProperty().set(0.5);

		view = new ImageView(BOULDER_IMAGE);
		view.scaleXProperty().bind(screenSizeXProperty().divide(view.getImage().widthProperty()));
		view.scaleYProperty().bind(screenSizeYProperty().divide(view.getImage().heightProperty()));
		
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
