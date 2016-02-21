package hillbillies.common.internal.ui.sprites;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;

public abstract class AbstractSprite<T, InfoProvider> {
	private final DoubleProperty screenX = new SimpleDoubleProperty();
	private final DoubleProperty screenY = new SimpleDoubleProperty();
	private final IntegerProperty depth = new SimpleIntegerProperty();

	private final DoubleProperty worldSizeX = new SimpleDoubleProperty();
	private final DoubleProperty worldSizeY = new SimpleDoubleProperty();
	private final DoubleProperty worldSizeZ = new SimpleDoubleProperty();
	
	private final DoubleProperty worldX = new SimpleDoubleProperty();
	private final DoubleProperty worldY = new SimpleDoubleProperty();
	private final DoubleProperty worldZ = new SimpleDoubleProperty();

	private final DoubleProperty screenSizeX = new SimpleDoubleProperty();
	private final DoubleProperty screenSizeY = new SimpleDoubleProperty();

	/** Pixels / world unit distance */
	private final DoubleProperty pixelsPerWorldUnit = new SimpleDoubleProperty();
	
	private T object;
	private InfoProvider infoProvider;

	protected AbstractSprite(T object, InfoProvider infoProvider) {
		this.object = object;
		this.infoProvider = infoProvider;
		screenSizeX.bind(worldSizeX.multiply(pixelsPerWorldUnit));
		screenSizeY.bind(worldSizeY.multiply(pixelsPerWorldUnit));
	}
	
	public InfoProvider getInfoProvider() {
		return infoProvider;
	}

	public abstract void update();

	public T getObject() {
		return object;
	}

	public abstract Node getGraph();
	
	public DoubleProperty screenXProperty() {
		return screenX;
	}
	
	public DoubleProperty screenYProperty() {
		return screenY;
	}

	public DoubleProperty worldXProperty() {
		return worldX;
	}

	public DoubleProperty worldYProperty() {
		return worldY;
	}

	public DoubleProperty worldZProperty() {
		return worldZ;
	}
	
	public IntegerProperty depthProperty() {
		return depth;
	}
	
	
	public DoubleProperty worldSizeXProperty() {
		return worldSizeX;
	}
		
	public DoubleProperty worldSizeYProperty() {
		return worldSizeY;
	}
	
	public DoubleProperty worldSizeZProperty() {
		return worldSizeZ;
	}

	
	public DoubleProperty screenSizeXProperty() {
		return screenSizeX;
	}
	
	public DoubleProperty screenSizeYProperty() {
		return screenSizeY;
	}
	
	public DoubleProperty pixelsPerMeterProperty() {
		return pixelsPerWorldUnit;
	}
}
