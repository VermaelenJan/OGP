package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import hillbillies.model.Faction;

public class IllegalFightFactionException extends RuntimeException {
	
	/**
	 * 
	 * Initialize this new illegal fight faction exception with the given faction.
	 *  
	 * @param faction
	 * 			The faction for this new illegal fight faction exception.
	 * 
	 * @post   The faction of this new illegal fight faction exception is equal
	 *         to the given faction.
	 *       | new.getFaction() == faction
	 */
	public IllegalFightFactionException(Faction faction){
		this.faction = faction;
	}
	
	/**
	 * Return the faction registered for this illegal fight faction exception.
	 */
	@Basic @Immutable
	public Faction getFaction(){
		return this.faction;
	}
	
	/**
	 * Variable registering the faction involved in this illegal fight faction exception.
	 */
	private Faction faction;
	
	
	
	private static final long serialVersionUID = -4361966793833551668L;
}
