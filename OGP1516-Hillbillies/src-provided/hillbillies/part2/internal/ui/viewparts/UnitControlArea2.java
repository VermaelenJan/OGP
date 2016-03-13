package hillbillies.part2.internal.ui.viewparts;

import hillbillies.part1.internal.ui.viewparts.UnitControlArea1;
import hillbillies.part2.internal.controller.ActionExecutorPart2;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class UnitControlArea2 extends UnitControlArea1 {

	public UnitControlArea2(ActionExecutorPart2 ae) {
		super(ae);
		FlowPane flow = getCommandsFlow();
		Button workButton = (Button) flow.getChildren().get(flow.getChildren().size() - 1);
		workButton.setOnAction(e -> ae.initiateWork());
	}

}
