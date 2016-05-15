package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of factions involving a relation with units who can belong to a faction in a world.
 * 
 * @invar Each faction must have proper units.
 * @invar Each faction must have an effective world.
 * @invar Each faction must have an effective scheduler.
 * 
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 */
public class Faction {
	
	/**
	 * Initialize this new faction as a non-terminated faction with no units yet.
	 * 
	 * @param world
	 * 		The world for this new faction.
	 * @post The world of this new faction is equal to the given world.
	 * @post This new faction has no units yet.
	 * @effect This faction is added to the given world.
	 * @post A new scheduler is initialized for this faction and 
	 * 		this faction is added as faction of the new scheduler.
	 */
	protected Faction(World world){
		this.world = world;
		world.addFaction(this);
		this.scheduler = new Scheduler(this);
	}
	
	/**
	 * Return the world of this faction.
	 */
	@Basic @Raw @Immutable
	protected World getWorld() {
		return world;
	}
	
	/**
	 * Variable registering the world of this faction.
	 */
	private final World world;
	
	/**
	 * Check whether this faction has the given unit as one of its
	 * units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 */
	@Basic @Raw
	protected boolean hasAsUnit(@Raw Unit unit){
		return unitsOfFaction.contains(unit);
	}
	
	/**
	 * Check whether this faction can have the given unit as one 
	 * of its units.
	 * 
	 * @param unit
	 * 		The unit to check
	 * @return True if and only if the unit is effective and that unit can have
	 * 		this faction as its faction.	 	
	 */
	@Raw
	protected boolean canHaveAsUnit(@Raw Unit unit){
		return ((unit != null ) && (unit.canHaveAsFaction(this)));
	}
	
	
	/**
	 * Check whether this faction has proper units attached to it.
	 * 
	 * @return True if and only if this faction can have each of the
	 *         unit attached to it as one of its units,
	 *         and if each of these units references this faction as
	 *         the faction to which they are attached.
	 */
	protected boolean hasProperUnit(){
		for (Unit unit : unitsOfFaction){
			if (!canHaveAsUnit(unit))
				return false;
			if (unit.getFaction() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Add the given unit to the set of units of this faction.
	 * 
	 * @param unit
	 * 		The unit to be added.
	 * @pre  The given unit is effective and already references this faction.
	 * @post The number of units of this faction is incremented by 1.
	 * @post This faction has the given unit as one of its units.
	 */
	protected void addUnit(Unit unit){
		assert ((unit != null) && (unit.getFaction() == this));
		unitsOfFaction.add(unit);
	}
	
	/**
	 * Return the number of units in this faction.
	 * 
	 * @Return The total number of units collected in this faction.
	 */
	protected int getNbUnits(){

		int nbUnits = 0;
		for (Unit unit : getUnits()){
			if (!unit.isTerminated()){
				nbUnits++;
			}
		}
		return nbUnits;
		// If we only want terminated and non terminated units
		//return getUnits().size();
	}
	

	/**
	 * Return a set with all the units of this faction.
	 */
	public Set<Unit> getUnits(){
		return unitsOfFaction;
	}
	

	/**
	 * Variable referencing a set collecting all the units of this faction.
	 *
	 * @invar The referenced set is effective.
	 * @invar Each unit registered in the referenced set is effective.
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
	
	/**
	 * Check whether or not this faction has units, terminates this faction if not.
	 * @post If this faction consists a unit that is not terminated, the faction is not terminated.
	 * @post If this faction does not consist of a unit anymore, the faction is terminated.
	 */
	protected void checkTerminate() {
		if (unitsOfFaction.size() == 0){
			terminate();
		}
	}
	
	/**
	 * Remove the given unit from the set of units of this faction.
	 * 
	 * @param unit
	 * 		The unit to be removed.
	 * @post This faction no longer has the given unit as one of its units.
	 */
	protected void removeUnit(Unit unit){
		unitsOfFaction.remove(unit);
	}
	
	/**
	 * Variable reflecting whether or not this faction is terminated.
	 */
	private boolean isTerminated;
	
	
	/**
	 * Get the scheduler of this faction.
	 */
	@Basic @Raw
	public Scheduler getScheduler() {
		return this.scheduler;
	}
	
	/**
	 * Variable registering the scheduler for this faction.
	 */
	private Scheduler scheduler;
}
