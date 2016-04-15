package hillbillies.part3.programs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import hillbillies.model.Task;
import hillbillies.part3.programs.internal.ParserVisitor;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangLexer;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser;
import ogp.framework.util.internal.ResourceUtils;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Parser for Hillbillies tasks.
 * 
 * To use this class, first create an implementation of {@link ITaskFactory}:
 * 
 * <pre>
 * <code>
 * ITaskFactory&lt;MyExpression, MyStatement, Task&gt; factory = new TaskFactory();
 * </code>
 * </pre>
 * 
 * The easiest way to use this class for parsing a Task given as a String is via
 * the {@link #parseTask(String, ITaskFactory)} method:
 * 
 * <pre>
 * <code>
 * Task task = TaskParser.parseTask(text, factory);
 * </code>
 * </pre>
 * 
 * For more control, create an instance of this class:
 * 
 * <pre>
 * <code>
 * TaskParser&lt;MyExpression, MyStatement, Task&gt; parser = new TaskParser<>(factory);
 * </code>
 * </pre>
 * 
 * Finally, parse a string or file: <code><pre>
 * Optional&lt;Task&gt; parseResult = parser.parse(textToParse);
 * </pre></code>
 * 
 * If parsing is successful, <code>parseResult.isPresent()</code> returns true
 * and <code>parseResult.get()</code> returns the created task.
 * 
 * If parsing was not successful, <code>parseResult.isPresent()</code> returns
 * false and <code>parser.getErrors()</code> can be used to retrieve the list of
 * errors during parsing.
 * 
 *
 * @param E
 *            The type of expressions
 * @param S
 *            The type of statements
 * @param T
 *            The type of Task
 */
public class TaskParser<E, S, T> {

	private final ITaskFactory<E, S, T> factory;

	private final List<String> errors = new ArrayList<>();

	protected TaskParser(ITaskFactory<E, S, T> factory) {
		this.factory = factory;
	}

	public ITaskFactory<E, S, T> getFactory() {
		return factory;
	}

	/**
	 * Returns the tasks that results from parsing the given string, or
	 * Optional.empty() if parsing has failed.
	 * 
	 * When parsing has failed, the error messages can be retrieved with the
	 * getErrors() method.
	 * 
	 * @param selectedCubes
	 *            The cubes which should be used as selected cubes in the
	 *            created tasks.
	 */
	public Optional<List<T>> parseString(String string, List<int[]> selectedCubes) {
		return parse(new ANTLRInputStream(string), selectedCubes);
	}

	/**
	 * Returns the tasks that results from parsing the file with the given name,
	 * or Optional.empty() if parsing has failed.
	 * 
	 * When parsing has failed, the error messages can be retrieved with the
	 * getErrors() method.
	 * 
	 * @param selectedCubes
	 *            The cubes which should be used as selected cubes in the
	 *            created tasks.
	 */
	public Optional<List<T>> parseFile(String filename, List<int[]> selectedCubes) throws IOException {
		return parse(new ANTLRInputStream(ResourceUtils.openResource(filename)), selectedCubes);
	}

	/**
	 * Returns the tasks that results from parsing the given CharStream, or
	 * Optional.empty() if parsing has failed.
	 * 
	 * When parsing has failed, the error messages can be retrieved with the
	 * getErrors() method.
	 * 
	 * @param selectedCubes
	 *            The cubes which should be used as selected cubes in the
	 *            created tasks.
	 * 
	 * @note For more convenient methods, see the
	 *       {@link #parseTasksFromString(String, ITaskFactory, List)},
	 *       {@link #parseTasksFromFile(String, ITaskFactory, List)},
	 *       {@link #parseString(String, List)} and
	 *       {@link #parseFile(String, List)} methods.
	 */
	protected Optional<List<T>> parse(CharStream input, List<int[]> selectedCubes) {
		reset();

		HillbilliesTaskLangLexer lexer = new HillbilliesTaskLangLexer(input);
		HillbilliesTaskLangParser parser = new HillbilliesTaskLangParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
					int charPositionInLine, String msg, RecognitionException e) {
				errors.add(msg + " (" + line + ", " + charPositionInLine + ")");
			}
		});
		ParserVisitor<E, S, T> visitor = new ParserVisitor<>(factory);
		try {
			visitor.visit(parser.task());
			if (errors.isEmpty()) {
				return Optional.ofNullable(factory.createTasks(visitor.getName(), visitor.getPriority(),
						visitor.getActivity(), selectedCubes));
			}
		} catch (Exception e) {
			e.printStackTrace();
			errors.add(e.toString());
		}
		return Optional.empty();
	}

	protected void reset() {
		this.errors.clear();
	}

	public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	/**
	 * Create a new parser from the given factory.
	 * 
	 * @param factory
	 * @return
	 */
	public static <E, S, T> TaskParser<E, S, T> create(ITaskFactory<E, S, T> factory) {
		return new TaskParser<>(factory);
	}

	/**
	 * Parse task text using the given factory.
	 * 
	 * @param text
	 *            The text to parse
	 * @param factory
	 *            The factory to use
	 * @param selectedCubes
	 *            The cubes which should be used as selected cubes in the
	 *            created tasks.
	 * @return The list of task, if any, or null if an error occurred during
	 *         parsing.
	 */
	public static <E> List<Task> parseTasksFromString(String text, ITaskFactory<E, ?, Task> factory,
			List<int[]> selectedCubes) {
		TaskParser<?, ?, Task> parser = create(factory);
		Optional<List<Task>> result = parser.parseString(text, selectedCubes);
		if (result.isPresent()) {
			return result.get();
		} else {
			System.out.println("Parsing failed: " + parser.getErrors());
			return null;
		}
	}

	/**
	 * Parse task from a file using the given factory.
	 * 
	 * @param filename
	 *            The filename from which to read the task description
	 * @param factory
	 *            The factory to use
	 * @param selectedCubes
	 *            The cubes which should be used as selected cubes in the
	 *            created tasks.
	 * @return The list of tasks, if any, or null if an error occurred during
	 *         parsing.
	 */
	public static <E> List<Task> parseTasksFromFile(String filename, ITaskFactory<E, ?, Task> factory,
			List<int[]> selectedCubes) throws IOException {
		TaskParser<?, ?, Task> parser = create(factory);
		Optional<List<Task>> result = parser.parseFile(filename, selectedCubes);
		if (result.isPresent()) {
			return result.get();
		} else {
			System.out.println("Parsing failed: " + parser.getErrors());
			return null;
		}
	}
}
