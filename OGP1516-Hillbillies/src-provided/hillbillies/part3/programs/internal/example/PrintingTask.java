package hillbillies.part3.programs.internal.example;

class PrintingTask {

	private String name;
	private int priority;
	private PrintingObject activity;

	public PrintingTask(String name, int priority, PrintingObject activity) {
		if (activity == null) {
			throw new NullPointerException("activity null");
		}
		if (name == null) {
			throw new NullPointerException("name must not be null");
		}
		this.name = name;
		this.priority = priority;
		this.activity = activity;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nPriority: " + priority + "\nActivity:" + activity.toString();
	}
}
