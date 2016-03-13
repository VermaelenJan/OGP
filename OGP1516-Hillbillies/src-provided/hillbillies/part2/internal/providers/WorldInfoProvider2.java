package hillbillies.part2.internal.providers;

import hillbillies.common.internal.providers.WorldInfoProvider;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.listener.TerrainChangeListener;

public interface WorldInfoProvider2 extends WorldInfoProvider {
	public CubeType getCubeTypeAt(int x, int y, int z);
	
	public void addTerrainChangeListener(TerrainChangeListener listener);
	public void removeTerrainChangeListener(TerrainChangeListener listener);

	public boolean isAnchored(int x, int y, int z);
}
