package hillbillies.common.internal.ui.viewmodel;

import java.util.Set;

import hillbillies.common.internal.map.IByteMap3D;
import hillbillies.common.internal.ui.sprites.AbstractSprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;

public interface IViewModel {

	void updateAllInformation();

	void update();

	/** Max z level (in tile coordinates) */
	int getMaxZLevel();

	/** Current z level (in tile coordinates) */
	IntegerProperty currentZLevelProperty();

	/** Current z level (in tile coordinates) */
	default int getCurrentZLevel() {
		return currentZLevelProperty().get();
	}

	default void levelUp() {
		adjustLevel(+1);
	}
	
	default void levelDown() {
		adjustLevel(-1);
	}
	
	void adjustLevel(int dz);
	
	int worldPointToWorldCube(double worldX);

	IByteMap3D getMap();

	ReadOnlyDoubleProperty viewWidthProperty();
	ReadOnlyDoubleProperty viewHeightProperty();

	int getPixelsPerTile();

	IntegerProperty xTileOffsetProperty();

	IntegerProperty yTileOffsetProperty();

	void moveOrigin(double dx, double dy);

	int getNbVisibleTilesX();
	int getNbVisibleTilesY();
	int getLowestVisibleZ(int visibleX, int visibleY);

	Set<AbstractSprite<?, ?>> getVisibleSprites();

	int screenToVisibleTileX(double x);
	int screenToVisibleTileY(double y);

	default double visibleTileToScreenX(int visibleX) {
		return visibleX * getPixelsPerTile();
	}

	default double visibleTileToScreenY(int visibleY) {
		return visibleY * getPixelsPerTile();
	}

	double screenToWorldX(double x);
	double screenToWorldY(double y);
	double screenToWorldZ(double x, double y);


	@FunctionalInterface
	public static interface NewSpriteListener {
		public void newSprite(AbstractSprite<?, ?> sprite);
	}
	
	void addNewSpriteListener(NewSpriteListener listener);


	@FunctionalInterface
	public static interface VisibleTileRefreshListener {
		public void refreshVisibleTile(int visibleX, int visibleY, int visibleZ);
	}

	void addVisibleTileRefreshListener(VisibleTileRefreshListener listener);

	
}
