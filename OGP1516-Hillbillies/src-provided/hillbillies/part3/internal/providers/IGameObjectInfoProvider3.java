package hillbillies.part3.internal.providers;

import java.util.List;
import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;

public interface IGameObjectInfoProvider3 extends IGameObjectInfoProvider {

	Set<Faction> getActiveFactions();

	List<Task> getTasksForFaction(Faction faction);
	
	String getTaskName(Task task);
	
	int getTaskPriority(Task task);
	
	boolean getTaskAssigned(Task task);

	Unit getAssignedUnit(Task task);

	Task getAssignedTask(Unit object);
}
