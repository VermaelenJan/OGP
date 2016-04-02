package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of factions involving a relation with units who can belong to a faction in a world.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 0
 */
public class Faction {
	
	/**
	 * Initialize this new faction with the given world.
	 * 
	 * @param world
	 * 		The world for this new faction.
	 * @post The world of this new faction is equal to the given world.
	 * @effect This faction is added to the given world.
	 */
	protected Faction(World world){
		this.world = world;
		world.addFaction(this);
	}
	
	/**
	 * Return the world of this faction.
	 */
	@Basic @Raw @Immutable
	public World getWorld() {
		return world;
	}
	
	/**
	 * Variable registering the world of this faction.
	 */
	private final World world;
	
	

	
	/**
	 * Add the given unit to the set of units of this faction.
	 * @param unit
	 * 		The unit to be added.
	 * @post The number of units of this faction is incremented by 1.
	 * @post This faction has the given unit as a unit of its faction.
	 */
	public void addUnit(Unit unit){
		unitsOfFaction.add(unit);
	}
	
	/**
	 * Return the number of units in this faction.
	 */
	protected int getNbUnits(){
		return getUnits().size();
	}
	
	// TODO documentatie get set
	/**
	 * Return a set with al the units of this faction.
	 */
	public Set<Unit> getUnits(){
		return unitsOfFaction;
	}
	
	// TODO documentatie set
	/**
	 * Variable referencing a set collecting all the units of this faction.
	 * 
	 *
	 */
	Set<Unit> unitsOfFaction = new HashSet<Unit>();
	
	
	/**
	 * Check whether this faction is terminated.
	 */
	@Basic
	protected boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Terminate this faction.
	 * 
	 *@post This faction is terminated.
	 */
	protected void terminate(){
		this.isTerminated = true;
	}
	
	// TODO doc for loop
	/**
	 * Check whether or not this faction has units.
	 * 
	 * 
	 */
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
	
	/**
	 * Variable reflecting whether or not this faction is terminated.
	 */
	private boolean isTerminated;
}
