package hillbillies.part3.programs.internal.example;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.TaskParser;

public class ExamplePrinter {
	public static void main(String[] args) throws IOException {
		ITaskFactory<PrintingObject, PrintingObject, PrintingTask> factory = PrintingObjectFactory.create();
		TaskParser<?, ?, PrintingTask> parser = TaskParser.create(factory);

		//Optional<List<PrintingTask>> task = parser.parseFile("resources/tasks/dig.txt", Collections.emptyList());
		//Optional<List<PrintingTask>> task = parser.parseFile("resources/tasks/digtunnel.txt", Collections.emptyList());
		//Optional<List<PrintingTask>> task = parser.parseFile("resources/tasks/digtunnel_if.txt", Collections.emptyList());
		//Optional<List<PrintingTask>> task = parser.parseFile("resources/tasks/goto_10_10_10.txt", Collections.emptyList());
		//Optional<List<PrintingTask>> task = parser.parseFile("resources/tasks/operate_workshop.txt", Collections.emptyList());
		

		Optional<List<PrintingTask>> task = parser.parseString(
				"name: \"operate workshop\"\npriority: -100\nactivities: if (is_solid boulder || carries_item this) then moveTo position_of this; moveTo boulder; fi",
				Collections.emptyList());

		if (task.isPresent()) {
			System.out.println("Parsing successful");
			System.out.println(task.get());
		} else {
			System.out.println("Parsing failed");
			System.out.println(parser.getErrors());
		}
	}

}
