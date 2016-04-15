package hillbillies.part2.internal.ui.viewparts;

import hillbillies.common.internal.ui.viewparts.WorldView;
import hillbillies.part2.internal.Part2Options;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.ui.viewmodel.ViewModelPart2;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ogp.framework.ui.SpriteSheet;
import ogp.framework.util.internal.Matrix;
import ogp.framework.util.internal.ResourceUtils;

public class WorldViewPart2 extends WorldView {

	public static WorldView create(ViewModelPart2 viewModel, Part2Options options) {
		WorldViewPart2 result = new WorldViewPart2(viewModel, options);
		result.setupViewModel();
		return result;
	}

	@Override
	public Part2Options getOptions() {
		return (Part2Options) super.getOptions();
	}

	private final ImageView[] visibleImageViews;
	private final SpriteSheet spritesheet;

	protected WorldViewPart2(ViewModelPart2 viewModel, Part2Options options) {
		super(viewModel, options);

		this.spritesheet = new SpriteSheet(ResourceUtils.loadImage("resources/forest.png"), 32, 32, 1, 1);

		visibleImageViews = new ImageView[getViewModel().getNbVisibleTilesX() * getViewModel().getNbVisibleTilesY()];

		this.visibleTileImageIndices = new Matrix<>(getViewModel().getNbVisibleTilesX(),
				getViewModel().getNbVisibleTilesY(), -1);
		visibleTileImageIndices
				.addChangeListener((row, col, oldValue, newValue) -> setImageViewTile(row, col, newValue));

		createImageViews();

		viewModel.setTrackAnchored(options.showAnchored().getValue());
		viewModel.getAnchorMatrix().addChangeListener((x, y, old, newValue) -> showAsAnchored(x, y, newValue));

	}

	private static final InnerShadow shadowGreen = new InnerShadow(5.0, Color.GREEN);
	private static final InnerShadow shadowRed = new InnerShadow(5.0, Color.RED);

	static {
		shadowGreen.setBlurType(BlurType.ONE_PASS_BOX);
		shadowRed.setBlurType(BlurType.ONE_PASS_BOX);
	}

	private void showAsAnchored(int visibleX, int visibleY, boolean value) {
		if (getOptions().showAnchored().getValue()) {
			ImageView view = visibleImageViews[getIndexForView(visibleX, visibleY)];
			if (value) {
				view.setEffect(shadowGreen);
			} else {
				view.setEffect(shadowRed);
			}
		}
	}

	@Override
	protected void handleClick(MouseEvent e) {
		double worldX = getViewModel().screenToWorldX(e.getX());
		double worldY = getViewModel().screenToWorldY(e.getY());
		double worldZ = getViewModel().getCurrentZLevel();
		getUserInputHandler().worldPointClicked(worldX, worldY, worldZ, e);
		e.consume();
	}

	@Override
	public ViewModelPart2 getViewModel() {
		return (ViewModelPart2) super.getViewModel();
	}

	protected int getIndexForView(int visibleX, int visibleY) {
		return visibleX + visibleY * getViewModel().getNbVisibleTilesX();
	}
	
	protected ImageView getImageView(int visibleX, int visibleY) {
		return visibleImageViews[getIndexForView(visibleX, visibleY)];
	}

	protected void setImageViewTile(int visibleX, int visibleY, int tileIndex) {
		ImageView view = getImageView(visibleX, visibleY);
		if (tileIndex >= 0) {
			view.setViewport(spritesheet.getViewport(tileIndex));
			view.setVisible(true);
		} else {
			view.setVisible(false);
		}
	}

	private void updateDepth(int visibleX, int visibleY, int newDepth) {
		if (newDepth < tilePanels.length) {
			int index = getIndexForView(visibleX, visibleY);
			ImageView view = visibleImageViews[index];
			((Pane) view.getParent()).getChildren().remove(view);
			tilePanels[newDepth].getChildren().add(view);
		}
	}

	@Override
	protected void setupViewModel() {
		getViewModel().getDepthValues()
				.addChangeListener((row, col, oldValue, newValue) -> updateDepth(row, col, newValue));
		super.setupViewModel();
	}

	private void createImageViews() {
		for (int visibleX = 0; visibleX < getViewModel().getNbVisibleTilesX(); visibleX++) {
			for (int visibleY = 0; visibleY < getViewModel().getNbVisibleTilesY(); visibleY++) {
				ImageView node = spritesheet.createImageView(0);
				node.setLayoutX(getViewModel().visibleTileToScreenX(visibleX));
				node.setLayoutY(getViewModel().visibleTileToScreenY(visibleY));
				/*
				final int vX = visibleX;
				final int vY = visibleY;
				node.setOnMouseClicked(e -> {
					double screenX = getViewModel().visibleTileToScreenX(vX) + e.getX();
					double screenY = getViewModel().visibleTileToScreenY(vY) + e.getY();
					double worldX = getViewModel().screenToWorldX(screenX);
					double worldY = getViewModel().screenToWorldY(screenY);
					double worldZ = getViewModel().getCurrentZLevel();
					//double worldZ = getViewModel().getLowestVisibleZ(vX, vY);
					//getUserInputHandler().worldPointClicked(worldX, worldY, worldZ, e);
					//e.consume();
				});
				*/
				visibleImageViews[getIndexForView(visibleX, visibleY)] = node;
				tilePanels[0].getChildren().add(node);

				showAsAnchored(visibleX, visibleY, getViewModel().getAnchorMatrix().getValueAt(visibleX, visibleY));
			}
		}
	}

	/* x: rows, y: columns */
	private final Matrix<Integer> visibleTileImageIndices;

	@Override
	protected void refreshVisibleTile(int visibleX, int visibleY, int visibleZ) {
		int index = getTileIndexFor(visibleX, visibleY, visibleZ);
		visibleTileImageIndices.setValueAt(visibleX, visibleY, index);
	}

	protected int getTileIndexFor(int visibleX, int visibleY, int visibleZ) {
		if (getViewModel().getCurrentZLevel() - visibleZ > getMaxDepth()) {
			return -1;
		}
		int worldX = getViewModel().visibleTileToWorldTileX(visibleX);
		int worldY = getViewModel().visibleTileToWorldTileY(visibleY);
		int worldZ = visibleZ;

		CubeType type = getViewModel().readTypeFromMap(worldX, worldY, worldZ);

		if (type != null && type != CubeType.EMPTY) {
			int tileIndex;
			switch (type) {
			case ROCKS:
				tileIndex = 334;
				break;
			case TREES:
				tileIndex = 356;
				break;
			case WORKSHOP:
				//tileIndex = 51;
				tileIndex = 379;
				break;
			default:
				tileIndex = -1;
				break;
			}
			return tileIndex;
		} else {
			return -1;
		}

	}
}
