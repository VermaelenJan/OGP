package hillbillies.part3.programs.internal.example;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import hillbillies.part3.programs.SourceLocation;

class PrintingObject {

	private boolean indented = false;

	private final StringBuilder repr = new StringBuilder();

	public PrintingObject(SourceLocation loc, String method, Object... args) {
		if (loc != null) {
			method = loc + " " + method;
		}
		if (args.length > 1) {
			indented(method + "(");
			indented = true;
			for (int i = 0; i < args.length; i++) {
				indented(repr(args[i]), args.length > 1 && i < args.length - 1);
			}
			indented = false;
			indented(")");
		} else {
			String msg = method + "( ";
			if (args.length > 0) {
				msg += repr(args[0]).toString().trim();
			}
			msg += " )";
			indented(msg);
		}
	}

	private String repr(Object object) {
		if (object instanceof String) {
			object = "\"" + object + "\"";
		} else if (object instanceof List<?>) {
			object = String.format("Arrays.toList(%s)", ((List<?>) object)
					.stream().map(this::repr)
					.collect(Collectors.joining(", ### Arrays.toList\n")));
		}
		if (object != null) {
			return object.toString();
		} else {
			return "null";
		}
	}

	@Override
	public String toString() {
		return repr.toString();
	}

	private void indented(Object object) {
		indented(object, false);
	}

	private void indented(Object object, boolean appendComma) {
		if (object != null) {
			String str = object.toString();
			StringTokenizer tok = new StringTokenizer(str, "\n");
			while (tok.hasMoreTokens()) {
				if (indented)
					repr.append("| ");
				repr.append(tok.nextToken());
				if (!tok.hasMoreTokens() && appendComma) {
					repr.append(",");
				}
				repr.append("\n");
			}
		} else {
			if (indented)
				repr.append("  null");
		}

	}
}
