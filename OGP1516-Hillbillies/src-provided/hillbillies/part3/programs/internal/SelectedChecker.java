package hillbillies.part3.programs.internal;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangBaseVisitor;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangLexer;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.SelectedPositionContext;
import ogp.framework.util.internal.ResourceUtils;

public class SelectedChecker extends HillbilliesTaskLangBaseVisitor<Boolean> {

	@Override
	public Boolean visitSelectedPosition(SelectedPositionContext ctx) {
		return true;
	}

	@Override
	protected Boolean defaultResult() {
		return false;
	}

	@Override
	protected Boolean aggregateResult(Boolean aggregate, Boolean nextResult) {
		return aggregate || nextResult;
	}

	public static boolean containsSelected(InputStream input) {
		try {
			HillbilliesTaskLangLexer lexer = new HillbilliesTaskLangLexer(new ANTLRInputStream(input));
			HillbilliesTaskLangParser parser = new HillbilliesTaskLangParser(new CommonTokenStream(lexer));
			// disable printing to console
			lexer.removeErrorListeners();
			parser.removeErrorListeners();
			return new SelectedChecker().visit(parser.task());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean containsSelected(String filename) {
		try {
			return containsSelected(ResourceUtils.openResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
