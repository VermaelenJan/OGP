package hillbillies.model.expression;

import java.util.ArrayList;

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
	public int[] evaluate(Task task, int[] selectedCube, Unit possibleUnit) {
		Log log = null;
		for (Log currLog: possibleUnit.getWorld().getLogsWorld()){
			if (log == null || Position.getDistanceBetween(currLog.getLocation(), possibleUnit.getLocation()) <
										Position.getDistanceBetween(log.getLocation(), possibleUnit.getLocation())) {
				log = currLog;
			}
		}
		if (log == null){
			return null;
		}
		else{
			return log.getOccupiedCube();
		}
	}

	@Override
	public Boolean isWellFormed(Task task, ArrayList<Object> calledBy, Unit possibleUnit) {
		return true;
	}
}
