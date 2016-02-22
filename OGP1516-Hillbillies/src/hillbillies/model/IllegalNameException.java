package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalNameException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2008524175821070107L;
	 
	private final String name;
	
	public IllegalNameException(String name){
		this.name = name;
	}
	
	@Basic @Immutable
	public String getName(){
		return this.name;
	}
	
	

}
