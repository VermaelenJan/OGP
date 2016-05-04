package hillbillies.model.expression;

import hillbillies.model.Log;
import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class LogPosition extends Expression implements IPosition {

	public LogPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate(Unit unit, int[] selectedCube, Task task) {
		Log log = null;
		for (Log currLog: unit.getWorld().getLogsWorld()){
			if (log == null || Position.getDistanceBetween(currLog.getLocation(), unit.getLocation()) <
										Position.getDistanceBetween(log.getLocation(), unit.getLocation())) {
				log = currLog;
			}
		}
		return log.getOccupiedCube();
	}

	@Override
	public String toString() {
		return "LogPosition";
	}
}
