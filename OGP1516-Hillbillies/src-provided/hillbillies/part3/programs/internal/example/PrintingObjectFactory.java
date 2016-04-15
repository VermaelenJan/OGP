package hillbillies.part3.programs.internal.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;

import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;

public class PrintingObjectFactory {

	/**
	 * Creates an implementation of IProgramFactory where the implementation of
	 * each interface method (except createProgram) just creates a
	 * PrintingObject with all arguments.
	 */
	@SuppressWarnings("unchecked")
	public static ITaskFactory<PrintingObject, PrintingObject, PrintingTask> create() {

		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("createTasks")) {
					return Collections
							.singletonList(new PrintingTask((String) args[0], (int) args[1], (PrintingObject) args[2]));
				}
				if (args != null) {
					SourceLocation sourceLocation = (SourceLocation) args[args.length - 1];
					if (args.length >= 1) {
						return new PrintingObject(sourceLocation, method.getName(),
								Arrays.copyOfRange(args, 0, args.length - 1));
					} else {
						return new PrintingObject(sourceLocation, method.getName());
					}
				} else {
					return new PrintingObject(null, method.getName());
				}
			}
		};

		return (ITaskFactory<PrintingObject, PrintingObject, PrintingTask>) Proxy
				.newProxyInstance(ITaskFactory.class.getClassLoader(), new Class[] { ITaskFactory.class }, handler);
	}
}
