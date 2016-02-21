package hillbillies.common.internal.inputmodes;

public interface InputMode extends UserInputHandler {

	void activate();
	void deactivate();
	
	void setOnActivate(Runnable handler); 
	void setOnDeactivate(Runnable handler); 
	
}
