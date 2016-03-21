package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class Faction {
	
	private final World world;

	protected Faction(World world){
		this.world = world;
	}
	
	Set<Unit> unitsOfFaction = new HashSet<Unit>();
	
	protected void addUnit(Unit unit){
		unitsOfFaction.add(unit);
	}
	
	public Set<Unit> getUnits(){
		return unitsOfFaction;
	}
	
	protected int getNbUnits(){
		return getUnits().size();
	}
	
	private boolean isTerminated;
	
	protected boolean isTerminated(){
		return this.isTerminated;
	}
	
	protected void terminate(){
		this.isTerminated = true;
	}

	protected void checkTerminate() {
		boolean keepFaction = false;
		for (Unit unit : getUnits()){
			if (!unit.isTerminated()){
				keepFaction = true;
			}
		}
		if (!keepFaction){
			this.terminate();
		}
	}

	public World getWorld() {
		return world;
	}
	
}
