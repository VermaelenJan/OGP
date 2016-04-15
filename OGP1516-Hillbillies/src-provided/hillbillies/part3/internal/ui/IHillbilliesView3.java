package hillbillies.part3.internal.ui;

import java.util.Collection;

import hillbillies.part2.internal.ui.IHillbilliesView2;

public interface IHillbilliesView3 extends IHillbilliesView2 {

	void setHighlightedTiles(Collection<int[]> tiles);

	void setSchedulingEnabled(boolean value);
}
