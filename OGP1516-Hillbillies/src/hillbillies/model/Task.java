package hillbillies.model;

public class Task {

	protected Task(String name, int priority, Statement activity, int[] selectedCube ){
		setName(name);
		setPriority(priority);
		setActivity(activity);
		setSelectedCube(selectedCube);
	}
	
	// NAME  
	public String getName(){
		return this.name;
	}
	
	private void setName(String name){
		this.name = name;
	}
	
	private String name;

	
	// PRIORITY
	public int getPriority(){
		return this.priority;
	}

	protected void setPriority(int priority){
		this.priority = priority;
	}
	
	private int priority;

	
	// ACTIVITY
	
	public Statement getActivity(){
		return this.activity;
	}
	
	private void setActivity(Statement activity){
		this.activity = activity;
	}
	
	private Statement activity;

	// SELECTED CUBE
	
	public int[] getSelectedCube(){
		return this.selectedCube;
	}
	
	private void setSelectedCube(int[] selectedCube){
		this.selectedCube = selectedCube;
	}
	
	private int[] selectedCube;	
	

}




