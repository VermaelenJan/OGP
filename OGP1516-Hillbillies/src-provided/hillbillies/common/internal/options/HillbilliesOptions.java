package hillbillies.common.internal.options;

import javafx.beans.property.Property;
import ogp.framework.util.internal.Options;

public class HillbilliesOptions extends Options {

	public static final String ENABLE_BLUR = "enable_blur";
	public static final String ENABLE_SHADOWS = "enable_shadows";
	public static final String ENABLE_DARKEN = "enable_darken";
	public static final String ENABLE_GRID = "enable_grid";
	public static final String ENABLE_REVERSE_SCROLL = "enable_rev_scroll";
	public static final String ENABLE_GRID_COORDINATES = "enable_grid_coord";
	public static final String PRINT_MODEL_EXCEPTIONS = "print_traces";
	
	public HillbilliesOptions() {
		addBooleanOption(ENABLE_BLUR, "Blur lower z-levels", true);
		addBooleanOption(ENABLE_SHADOWS, "Show shadows", true);
		addBooleanOption(ENABLE_DARKEN, "Darken lower z-levels (recommended)", true);
		addBooleanOption(ENABLE_GRID, "Show grid", false);
		addBooleanOption(ENABLE_GRID_COORDINATES, "Show grid coordinates", false);
		addBooleanOption(ENABLE_REVERSE_SCROLL, "Reverse scroll direction", true);
		addBooleanOption(PRINT_MODEL_EXCEPTIONS, "Print ModelException stack traces", true);
	}

	public Property<Boolean> blurEnabled() {
		return getBooleanValue(ENABLE_BLUR);
	}

	public Property<Boolean> shadowEnabled() {
		return getBooleanValue(ENABLE_SHADOWS);
	}

	public Property<Boolean> gridEnabled() {
		return getBooleanValue(ENABLE_GRID);
	}

	public Property<Boolean> reverseScrollEnabled() {
		return getBooleanValue(ENABLE_REVERSE_SCROLL);
	}

	public Property<Boolean> darkenEnabled() {
		return getBooleanValue(ENABLE_DARKEN);
	}

	public Property<Boolean> showGridCoordinatesEnabled() {
		return getBooleanValue(ENABLE_GRID_COORDINATES);
	}

	public Property<Boolean> printModelExceptionTraces() {
		return getBooleanValue(PRINT_MODEL_EXCEPTIONS);
	}

}
