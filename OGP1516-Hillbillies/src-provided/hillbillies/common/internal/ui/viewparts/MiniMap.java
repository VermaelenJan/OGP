package hillbillies.common.internal.ui.viewparts;

import hillbillies.common.internal.Constants;
import hillbillies.common.internal.map.IByteMap3D;
import hillbillies.common.internal.ui.viewmodel.ViewModel;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MiniMap<T> {

	private StackPane root;

	/** pixels per tile */
	private double mmPixelsPerTile;

	private double width;
	private double height;

	private final IByteMap3D map;
	private final Canvas image;

	private final GraphicsContext gc;

	private final ViewModel viewModel;

	private final Rectangle viewport;

	public MiniMap(ViewModel viewModel) {
		this.viewModel = viewModel;
		map = viewModel.getMap();
		mmPixelsPerTile = Math.min(Constants.MINIMAP_SIZE / map.getNbX(), Constants.MINIMAP_SIZE / map.getNbY());

		this.width = mmPixelsPerTile * map.getNbX();
		this.height = mmPixelsPerTile * map.getNbY();
		this.image = new Canvas(width, height);
		this.gc = image.getGraphicsContext2D();
		this.root = new StackPane();
		root.getChildren().add(image);

		viewport = new Rectangle();
		setupViewport();
		root.getChildren().add(viewport);

		viewModel.currentZLevelProperty().addListener(c -> updateImage());
		map.addListener((x, y, z, oldValue, newValue) -> updateImageAt(x, y, z, getTypeFor(newValue)));
		updateImage();
	}

	protected T getTypeFor(byte newValue) {
		return null;
	}

	protected Color getColorFor(T type) {
		return Color.BLACK;
	}

	private void setupViewport() {
		viewport.setManaged(false);
		viewport.setStroke(Color.ORANGE);
		viewport.setStrokeWidth(2.0);
		viewport.setFill(Color.ORANGE.deriveColor(0, 1, 1, 0.5));
		viewport.widthProperty()
				.bind(viewModel.viewWidthProperty().multiply(mmPixelsPerTile).divide(viewModel.getPixelsPerTile()));
		viewport.heightProperty()
				.bind(viewModel.viewHeightProperty().multiply(mmPixelsPerTile).divide(viewModel.getPixelsPerTile()));
		viewport.xProperty().bind(viewModel.xTileOffsetProperty().multiply(mmPixelsPerTile));
		viewport.yProperty().bind(viewModel.yTileOffsetProperty().multiply(mmPixelsPerTile));
		viewport.setPickOnBounds(true);
		viewport.setCursor(Cursor.OPEN_HAND);
		viewport.setOnMousePressed(e -> {
			viewport.setCursor(Cursor.CLOSED_HAND);
			viewport.setUserData(new Point2D(e.getX(), e.getY()));
		});
		viewport.setOnMouseDragged(e -> {
			Point2D point = (Point2D) viewport.getUserData();
			if (point != null) {
				double dxMMpx = e.getX() - point.getX();
				double dxPx = dxMMpx / mmPixelsPerTile * viewModel.getPixelsPerTile();
				double dyMMpx = e.getY() - point.getY();
				double dyPx = dyMMpx / mmPixelsPerTile * viewModel.getPixelsPerTile();
				viewModel.moveOrigin(dxPx, dyPx);
				viewport.setUserData(new Point2D(e.getX(), e.getY()));
			}
		});
		viewport.setOnMouseReleased(e -> {
			viewport.setUserData(null);
			viewport.setCursor(Cursor.OPEN_HAND);
		});
	}

	public Node getRoot() {
		return root;
	}

	protected void updateImageAt(int x, int y, int z, T type) {
		gc.setFill(getColorFor(type));
		gc.fillRect(x * mmPixelsPerTile, y * mmPixelsPerTile, Math.ceil(mmPixelsPerTile), Math.ceil(mmPixelsPerTile));
	}

	protected void updateImage() {
		int nbX = map.getNbX();
		int nbY = map.getNbY();
		int z = viewModel.currentZLevelProperty().get();

		for (int x = 0; x < nbX; x++) {
			for (int y = 0; y < nbY; y++) {
				T type = getTypeFor(map.getValue(x, y, z));
				updateImageAt(x, y, z, type);
			}
		}
	}

}
