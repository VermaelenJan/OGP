package hillbillies.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import hillbillies.model.Faction;

public class IllegalFightFactionException extends RuntimeException {
	

	public IllegalFightFactionException(Faction faction){
		this.faction = faction;
	}
	
	
	@Basic @Immutable
	public Faction getFaction(){
		return this.faction;
	}
		
	private Faction faction;
	
	
	
	private static final long serialVersionUID = -4361966793833551668L;
}
