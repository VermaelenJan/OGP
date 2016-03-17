package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class Faction {
	
	Set<Unit> unitsOfFaction = new HashSet<Unit>();
	
	protected void addUnit(Unit unit){
		unitsOfFaction.add(unit);
	}
	
	protected Set<Unit> getUnits(){
		return unitsOfFaction;
	}	
}
