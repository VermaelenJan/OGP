package hillbillies.model.expression;

import java.util.ArrayList;

import hillbillies.model.Log;
import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public class LogPosition extends Expression implements IPosition {

	public LogPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate(Task task, int[] selectedCube) {
		Log log = null;
		for (Log currLog: task.getAssignedUnit().getWorld().getLogsWorld()){
			if (log == null || Position.getDistanceBetween(currLog.getLocation(), task.getAssignedUnit().getLocation()) <
										Position.getDistanceBetween(log.getLocation(), task.getAssignedUnit().getLocation())) {
				log = currLog;
			}
		}
		return log.getOccupiedCube();
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy) {
		return true;
	}

	@Override
	public String toString(Task task, int[] selectedCube) {
		// TODO Auto-generated method stub
		return null;
	}
}
