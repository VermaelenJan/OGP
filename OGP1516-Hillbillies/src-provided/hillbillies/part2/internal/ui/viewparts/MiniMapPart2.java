package hillbillies.part2.internal.ui.viewparts;

import hillbillies.common.internal.ui.viewparts.MiniMap;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.ui.viewmodel.ViewModelPart2;
import javafx.scene.paint.Color;

public class MiniMapPart2 extends MiniMap<CubeType> {

	public MiniMapPart2(ViewModelPart2 config) {
		super(config);
	}

	@Override
	protected CubeType getTypeFor(byte newValue) {
		return CubeType.values()[newValue];
	}
	
	@Override
	protected Color getColorFor(CubeType type) {
		Color color = Color.BLACK;
		if (type != null) {
			switch (type) {
			case EMPTY:
				break;
			case ROCKS:
				color = Color.SADDLEBROWN;
				break;
			case TREES:
				color = Color.LIGHTGREEN;
				break;
			case WORKSHOP:
				color = Color.MEDIUMPURPLE;
				break;
			default:
				break;
			}
		}
		return color;
	}
}
