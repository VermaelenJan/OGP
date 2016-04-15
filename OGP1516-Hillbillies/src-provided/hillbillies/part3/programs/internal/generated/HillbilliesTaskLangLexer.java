// Generated from HillbilliesTaskLang.g by ANTLR 4.5.3
package hillbillies.part3.programs.internal.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HillbilliesTaskLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, ISSOLID=38, 
		ISPASSABLE=39, ISFRIEND=40, ISENEMY=41, ISALIVE=42, CARRIESITEM=43, INT=44, 
		STRING=45, IDENTIFIER=46, WS=47;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
		"T__33", "T__34", "T__35", "T__36", "ISSOLID", "ISPASSABLE", "ISFRIEND", 
		"ISENEMY", "ISALIVE", "CARRIESITEM", "INT", "STRING", "ESC", "IDENTIFIER", 
		"WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'name'", "':'", "'priority'", "'activities'", "':='", "';'", "'while'", 
		"'do'", "'done'", "'break'", "'if'", "'then'", "'else'", "'fi'", "'print'", 
		"'moveTo'", "'work'", "'follow'", "'attack'", "'true'", "'false'", "'('", 
		"')'", "'!'", "'&&'", "'||'", "'this'", "'friend'", "'enemy'", "'any'", 
		"'here'", "'log'", "'boulder'", "'workshop'", "','", "'next_to'", "'selected'", 
		"'is_solid'", "'is_passable'", "'is_friend'", "'is_enemy'", "'is_alive'", 
		"'carries_item'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "ISSOLID", "ISPASSABLE", "ISFRIEND", "ISENEMY", "ISALIVE", 
		"CARRIESITEM", "INT", "STRING", "IDENTIFIER", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public HillbilliesTaskLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "HillbilliesTaskLang.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\61\u0185\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3"+
		"\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3"+
		"\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3"+
		"\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3"+
		",\3,\3,\3,\3-\5-\u0161\n-\3-\5-\u0164\n-\3-\6-\u0167\n-\r-\16-\u0168\3"+
		".\3.\3.\7.\u016e\n.\f.\16.\u0171\13.\3.\3.\3/\3/\3/\3/\5/\u0179\n/\3\60"+
		"\3\60\7\60\u017d\n\60\f\60\16\60\u0180\13\60\3\61\3\61\3\61\3\61\3\u016f"+
		"\2\62\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\2_\60a\61\3\2\5\4\2C\\c|\5\2\62"+
		";C\\c|\4\2\13\f\"\"\u018a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67"+
		"\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2"+
		"\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2"+
		"\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2_"+
		"\3\2\2\2\2a\3\2\2\2\3c\3\2\2\2\5h\3\2\2\2\7j\3\2\2\2\ts\3\2\2\2\13~\3"+
		"\2\2\2\r\u0081\3\2\2\2\17\u0083\3\2\2\2\21\u0089\3\2\2\2\23\u008c\3\2"+
		"\2\2\25\u0091\3\2\2\2\27\u0097\3\2\2\2\31\u009a\3\2\2\2\33\u009f\3\2\2"+
		"\2\35\u00a4\3\2\2\2\37\u00a7\3\2\2\2!\u00ad\3\2\2\2#\u00b4\3\2\2\2%\u00b9"+
		"\3\2\2\2\'\u00c0\3\2\2\2)\u00c7\3\2\2\2+\u00cc\3\2\2\2-\u00d2\3\2\2\2"+
		"/\u00d4\3\2\2\2\61\u00d6\3\2\2\2\63\u00d8\3\2\2\2\65\u00db\3\2\2\2\67"+
		"\u00de\3\2\2\29\u00e3\3\2\2\2;\u00ea\3\2\2\2=\u00f0\3\2\2\2?\u00f4\3\2"+
		"\2\2A\u00f9\3\2\2\2C\u00fd\3\2\2\2E\u0105\3\2\2\2G\u010e\3\2\2\2I\u0110"+
		"\3\2\2\2K\u0118\3\2\2\2M\u0121\3\2\2\2O\u012a\3\2\2\2Q\u0136\3\2\2\2S"+
		"\u0140\3\2\2\2U\u0149\3\2\2\2W\u0152\3\2\2\2Y\u0163\3\2\2\2[\u016a\3\2"+
		"\2\2]\u0178\3\2\2\2_\u017a\3\2\2\2a\u0181\3\2\2\2cd\7p\2\2de\7c\2\2ef"+
		"\7o\2\2fg\7g\2\2g\4\3\2\2\2hi\7<\2\2i\6\3\2\2\2jk\7r\2\2kl\7t\2\2lm\7"+
		"k\2\2mn\7q\2\2no\7t\2\2op\7k\2\2pq\7v\2\2qr\7{\2\2r\b\3\2\2\2st\7c\2\2"+
		"tu\7e\2\2uv\7v\2\2vw\7k\2\2wx\7x\2\2xy\7k\2\2yz\7v\2\2z{\7k\2\2{|\7g\2"+
		"\2|}\7u\2\2}\n\3\2\2\2~\177\7<\2\2\177\u0080\7?\2\2\u0080\f\3\2\2\2\u0081"+
		"\u0082\7=\2\2\u0082\16\3\2\2\2\u0083\u0084\7y\2\2\u0084\u0085\7j\2\2\u0085"+
		"\u0086\7k\2\2\u0086\u0087\7n\2\2\u0087\u0088\7g\2\2\u0088\20\3\2\2\2\u0089"+
		"\u008a\7f\2\2\u008a\u008b\7q\2\2\u008b\22\3\2\2\2\u008c\u008d\7f\2\2\u008d"+
		"\u008e\7q\2\2\u008e\u008f\7p\2\2\u008f\u0090\7g\2\2\u0090\24\3\2\2\2\u0091"+
		"\u0092\7d\2\2\u0092\u0093\7t\2\2\u0093\u0094\7g\2\2\u0094\u0095\7c\2\2"+
		"\u0095\u0096\7m\2\2\u0096\26\3\2\2\2\u0097\u0098\7k\2\2\u0098\u0099\7"+
		"h\2\2\u0099\30\3\2\2\2\u009a\u009b\7v\2\2\u009b\u009c\7j\2\2\u009c\u009d"+
		"\7g\2\2\u009d\u009e\7p\2\2\u009e\32\3\2\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1"+
		"\7n\2\2\u00a1\u00a2\7u\2\2\u00a2\u00a3\7g\2\2\u00a3\34\3\2\2\2\u00a4\u00a5"+
		"\7h\2\2\u00a5\u00a6\7k\2\2\u00a6\36\3\2\2\2\u00a7\u00a8\7r\2\2\u00a8\u00a9"+
		"\7t\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7p\2\2\u00ab\u00ac\7v\2\2\u00ac"+
		" \3\2\2\2\u00ad\u00ae\7o\2\2\u00ae\u00af\7q\2\2\u00af\u00b0\7x\2\2\u00b0"+
		"\u00b1\7g\2\2\u00b1\u00b2\7V\2\2\u00b2\u00b3\7q\2\2\u00b3\"\3\2\2\2\u00b4"+
		"\u00b5\7y\2\2\u00b5\u00b6\7q\2\2\u00b6\u00b7\7t\2\2\u00b7\u00b8\7m\2\2"+
		"\u00b8$\3\2\2\2\u00b9\u00ba\7h\2\2\u00ba\u00bb\7q\2\2\u00bb\u00bc\7n\2"+
		"\2\u00bc\u00bd\7n\2\2\u00bd\u00be\7q\2\2\u00be\u00bf\7y\2\2\u00bf&\3\2"+
		"\2\2\u00c0\u00c1\7c\2\2\u00c1\u00c2\7v\2\2\u00c2\u00c3\7v\2\2\u00c3\u00c4"+
		"\7c\2\2\u00c4\u00c5\7e\2\2\u00c5\u00c6\7m\2\2\u00c6(\3\2\2\2\u00c7\u00c8"+
		"\7v\2\2\u00c8\u00c9\7t\2\2\u00c9\u00ca\7w\2\2\u00ca\u00cb\7g\2\2\u00cb"+
		"*\3\2\2\2\u00cc\u00cd\7h\2\2\u00cd\u00ce\7c\2\2\u00ce\u00cf\7n\2\2\u00cf"+
		"\u00d0\7u\2\2\u00d0\u00d1\7g\2\2\u00d1,\3\2\2\2\u00d2\u00d3\7*\2\2\u00d3"+
		".\3\2\2\2\u00d4\u00d5\7+\2\2\u00d5\60\3\2\2\2\u00d6\u00d7\7#\2\2\u00d7"+
		"\62\3\2\2\2\u00d8\u00d9\7(\2\2\u00d9\u00da\7(\2\2\u00da\64\3\2\2\2\u00db"+
		"\u00dc\7~\2\2\u00dc\u00dd\7~\2\2\u00dd\66\3\2\2\2\u00de\u00df\7v\2\2\u00df"+
		"\u00e0\7j\2\2\u00e0\u00e1\7k\2\2\u00e1\u00e2\7u\2\2\u00e28\3\2\2\2\u00e3"+
		"\u00e4\7h\2\2\u00e4\u00e5\7t\2\2\u00e5\u00e6\7k\2\2\u00e6\u00e7\7g\2\2"+
		"\u00e7\u00e8\7p\2\2\u00e8\u00e9\7f\2\2\u00e9:\3\2\2\2\u00ea\u00eb\7g\2"+
		"\2\u00eb\u00ec\7p\2\2\u00ec\u00ed\7g\2\2\u00ed\u00ee\7o\2\2\u00ee\u00ef"+
		"\7{\2\2\u00ef<\3\2\2\2\u00f0\u00f1\7c\2\2\u00f1\u00f2\7p\2\2\u00f2\u00f3"+
		"\7{\2\2\u00f3>\3\2\2\2\u00f4\u00f5\7j\2\2\u00f5\u00f6\7g\2\2\u00f6\u00f7"+
		"\7t\2\2\u00f7\u00f8\7g\2\2\u00f8@\3\2\2\2\u00f9\u00fa\7n\2\2\u00fa\u00fb"+
		"\7q\2\2\u00fb\u00fc\7i\2\2\u00fcB\3\2\2\2\u00fd\u00fe\7d\2\2\u00fe\u00ff"+
		"\7q\2\2\u00ff\u0100\7w\2\2\u0100\u0101\7n\2\2\u0101\u0102\7f\2\2\u0102"+
		"\u0103\7g\2\2\u0103\u0104\7t\2\2\u0104D\3\2\2\2\u0105\u0106\7y\2\2\u0106"+
		"\u0107\7q\2\2\u0107\u0108\7t\2\2\u0108\u0109\7m\2\2\u0109\u010a\7u\2\2"+
		"\u010a\u010b\7j\2\2\u010b\u010c\7q\2\2\u010c\u010d\7r\2\2\u010dF\3\2\2"+
		"\2\u010e\u010f\7.\2\2\u010fH\3\2\2\2\u0110\u0111\7p\2\2\u0111\u0112\7"+
		"g\2\2\u0112\u0113\7z\2\2\u0113\u0114\7v\2\2\u0114\u0115\7a\2\2\u0115\u0116"+
		"\7v\2\2\u0116\u0117\7q\2\2\u0117J\3\2\2\2\u0118\u0119\7u\2\2\u0119\u011a"+
		"\7g\2\2\u011a\u011b\7n\2\2\u011b\u011c\7g\2\2\u011c\u011d\7e\2\2\u011d"+
		"\u011e\7v\2\2\u011e\u011f\7g\2\2\u011f\u0120\7f\2\2\u0120L\3\2\2\2\u0121"+
		"\u0122\7k\2\2\u0122\u0123\7u\2\2\u0123\u0124\7a\2\2\u0124\u0125\7u\2\2"+
		"\u0125\u0126\7q\2\2\u0126\u0127\7n\2\2\u0127\u0128\7k\2\2\u0128\u0129"+
		"\7f\2\2\u0129N\3\2\2\2\u012a\u012b\7k\2\2\u012b\u012c\7u\2\2\u012c\u012d"+
		"\7a\2\2\u012d\u012e\7r\2\2\u012e\u012f\7c\2\2\u012f\u0130\7u\2\2\u0130"+
		"\u0131\7u\2\2\u0131\u0132\7c\2\2\u0132\u0133\7d\2\2\u0133\u0134\7n\2\2"+
		"\u0134\u0135\7g\2\2\u0135P\3\2\2\2\u0136\u0137\7k\2\2\u0137\u0138\7u\2"+
		"\2\u0138\u0139\7a\2\2\u0139\u013a\7h\2\2\u013a\u013b\7t\2\2\u013b\u013c"+
		"\7k\2\2\u013c\u013d\7g\2\2\u013d\u013e\7p\2\2\u013e\u013f\7f\2\2\u013f"+
		"R\3\2\2\2\u0140\u0141\7k\2\2\u0141\u0142\7u\2\2\u0142\u0143\7a\2\2\u0143"+
		"\u0144\7g\2\2\u0144\u0145\7p\2\2\u0145\u0146\7g\2\2\u0146\u0147\7o\2\2"+
		"\u0147\u0148\7{\2\2\u0148T\3\2\2\2\u0149\u014a\7k\2\2\u014a\u014b\7u\2"+
		"\2\u014b\u014c\7a\2\2\u014c\u014d\7c\2\2\u014d\u014e\7n\2\2\u014e\u014f"+
		"\7k\2\2\u014f\u0150\7x\2\2\u0150\u0151\7g\2\2\u0151V\3\2\2\2\u0152\u0153"+
		"\7e\2\2\u0153\u0154\7c\2\2\u0154\u0155\7t\2\2\u0155\u0156\7t\2\2\u0156"+
		"\u0157\7k\2\2\u0157\u0158\7g\2\2\u0158\u0159\7u\2\2\u0159\u015a\7a\2\2"+
		"\u015a\u015b\7k\2\2\u015b\u015c\7v\2\2\u015c\u015d\7g\2\2\u015d\u015e"+
		"\7o\2\2\u015eX\3\2\2\2\u015f\u0161\7-\2\2\u0160\u015f\3\2\2\2\u0160\u0161"+
		"\3\2\2\2\u0161\u0164\3\2\2\2\u0162\u0164\7/\2\2\u0163\u0160\3\2\2\2\u0163"+
		"\u0162\3\2\2\2\u0164\u0166\3\2\2\2\u0165\u0167\4\62;\2\u0166\u0165\3\2"+
		"\2\2\u0167\u0168\3\2\2\2\u0168\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169"+
		"Z\3\2\2\2\u016a\u016f\7$\2\2\u016b\u016e\5]/\2\u016c\u016e\13\2\2\2\u016d"+
		"\u016b\3\2\2\2\u016d\u016c\3\2\2\2\u016e\u0171\3\2\2\2\u016f\u0170\3\2"+
		"\2\2\u016f\u016d\3\2\2\2\u0170\u0172\3\2\2\2\u0171\u016f\3\2\2\2\u0172"+
		"\u0173\7$\2\2\u0173\\\3\2\2\2\u0174\u0175\7^\2\2\u0175\u0179\7$\2\2\u0176"+
		"\u0177\7^\2\2\u0177\u0179\7^\2\2\u0178\u0174\3\2\2\2\u0178\u0176\3\2\2"+
		"\2\u0179^\3\2\2\2\u017a\u017e\t\2\2\2\u017b\u017d\t\3\2\2\u017c\u017b"+
		"\3\2\2\2\u017d\u0180\3\2\2\2\u017e\u017c\3\2\2\2\u017e\u017f\3\2\2\2\u017f"+
		"`\3\2\2\2\u0180\u017e\3\2\2\2\u0181\u0182\t\4\2\2\u0182\u0183\3\2\2\2"+
		"\u0183\u0184\b\61\2\2\u0184b\3\2\2\2\n\2\u0160\u0163\u0168\u016d\u016f"+
		"\u0178\u017e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
