// Generated from HillbilliesTaskLang.g by ANTLR 4.5.3
package hillbillies.part3.programs.internal.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HillbilliesTaskLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		ISSOLID=39, ISPASSABLE=40, ISFRIEND=41, ISENEMY=42, ISALIVE=43, CARRIESITEM=44, 
		INT=45, STRING=46, IDENTIFIER=47, WS=48;
	public static final int
		RULE_task = 0, RULE_statements = 1, RULE_statement = 2, RULE_assignmentStatement = 3, 
		RULE_whileStatement = 4, RULE_breakStatement = 5, RULE_ifStatement = 6, 
		RULE_printStatement = 7, RULE_actionStatement = 8, RULE_expression = 9, 
		RULE_unit = 10, RULE_position = 11;
	public static final String[] ruleNames = {
		"task", "statements", "statement", "assignmentStatement", "whileStatement", 
		"breakStatement", "ifStatement", "printStatement", "actionStatement", 
		"expression", "unit", "position"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'name'", "':'", "'priority'", "'activities'", "':='", "';'", "'while'", 
		"'do'", "'done'", "'break'", "'if'", "'then'", "'else'", "'fi'", "'print'", 
		"'moveTo'", "'work'", "'follow'", "'attack'", "'true'", "'false'", "'('", 
		"')'", "'!'", "'&&'", "'||'", "'this'", "'friend'", "'enemy'", "'any'", 
		"'here'", "'log'", "'boulder'", "'workshop'", "','", "'next_to'", "'position_of'", 
		"'selected'", "'is_solid'", "'is_passable'", "'is_friend'", "'is_enemy'", 
		"'is_alive'", "'carries_item'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "ISSOLID", "ISPASSABLE", "ISFRIEND", "ISENEMY", "ISALIVE", 
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

	@Override
	public String getGrammarFileName() { return "HillbilliesTaskLang.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HillbilliesTaskLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class TaskContext extends ParserRuleContext {
		public Token name;
		public Token priority;
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode STRING() { return getToken(HillbilliesTaskLangParser.STRING, 0); }
		public TerminalNode INT() { return getToken(HillbilliesTaskLangParser.INT, 0); }
		public TaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_task; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterTask(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitTask(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitTask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TaskContext task() throws RecognitionException {
		TaskContext _localctx = new TaskContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_task);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(T__0);
			setState(25);
			match(T__1);
			setState(26);
			((TaskContext)_localctx).name = match(STRING);
			setState(27);
			match(T__2);
			setState(28);
			match(T__1);
			setState(29);
			((TaskContext)_localctx).priority = match(INT);
			setState(30);
			match(T__3);
			setState(31);
			match(T__1);
			setState(32);
			statements();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public StatementContext statement;
		public List<StatementContext> stmts = new ArrayList<StatementContext>();
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			((StatementsContext)_localctx).statement = statement();
			((StatementsContext)_localctx).stmts.add(((StatementsContext)_localctx).statement);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__9) | (1L << T__10) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(35);
				((StatementsContext)_localctx).statement = statement();
				((StatementsContext)_localctx).stmts.add(((StatementsContext)_localctx).statement);
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public PrintStatementContext printStatement() {
			return getRuleContext(PrintStatementContext.class,0);
		}
		public ActionStatementContext actionStatement() {
			return getRuleContext(ActionStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(47);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				assignmentStatement();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 2);
				{
				setState(42);
				whileStatement();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(43);
				ifStatement();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 4);
				{
				setState(44);
				breakStatement();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 5);
				{
				setState(45);
				printStatement();
				}
				break;
			case T__15:
			case T__16:
			case T__17:
			case T__18:
				enterOuterAlt(_localctx, 6);
				{
				setState(46);
				actionStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentStatementContext extends ParserRuleContext {
		public Token variableName;
		public ExpressionContext value;
		public TerminalNode IDENTIFIER() { return getToken(HillbilliesTaskLangParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterAssignmentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitAssignmentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignmentStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			((AssignmentStatementContext)_localctx).variableName = match(IDENTIFIER);
			setState(50);
			match(T__4);
			setState(51);
			((AssignmentStatementContext)_localctx).value = expression(0);
			setState(52);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext condition;
		public StatementsContext body;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__6);
			setState(55);
			((WhileStatementContext)_localctx).condition = expression(0);
			setState(56);
			match(T__7);
			setState(57);
			((WhileStatementContext)_localctx).body = statements();
			setState(58);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(T__9);
			setState(61);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public ExpressionContext condition;
		public StatementsContext ifbody;
		public StatementsContext elsebody;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(T__10);
			setState(64);
			((IfStatementContext)_localctx).condition = expression(0);
			setState(65);
			match(T__11);
			setState(66);
			((IfStatementContext)_localctx).ifbody = statements();
			setState(69);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(67);
				match(T__12);
				setState(68);
				((IfStatementContext)_localctx).elsebody = statements();
				}
			}

			setState(71);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintStatementContext extends ParserRuleContext {
		public ExpressionContext value;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterPrintStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitPrintStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitPrintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatementContext printStatement() throws RecognitionException {
		PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_printStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T__14);
			setState(74);
			((PrintStatementContext)_localctx).value = expression(0);
			setState(75);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionStatementContext extends ParserRuleContext {
		public ActionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionStatement; }
	 
		public ActionStatementContext() { }
		public void copyFrom(ActionStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MoveToActionContext extends ActionStatementContext {
		public ExpressionContext target;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MoveToActionContext(ActionStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterMoveToAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitMoveToAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitMoveToAction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WorkActionContext extends ActionStatementContext {
		public ExpressionContext target;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WorkActionContext(ActionStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterWorkAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitWorkAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitWorkAction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AttackActionContext extends ActionStatementContext {
		public ExpressionContext target;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AttackActionContext(ActionStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterAttackAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitAttackAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitAttackAction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FollowActionContext extends ActionStatementContext {
		public ExpressionContext target;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FollowActionContext(ActionStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterFollowAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitFollowAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitFollowAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionStatementContext actionStatement() throws RecognitionException {
		ActionStatementContext _localctx = new ActionStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_actionStatement);
		try {
			setState(93);
			switch (_input.LA(1)) {
			case T__15:
				_localctx = new MoveToActionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				match(T__15);
				setState(78);
				((MoveToActionContext)_localctx).target = expression(0);
				setState(79);
				match(T__5);
				}
				break;
			case T__16:
				_localctx = new WorkActionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				match(T__16);
				setState(82);
				((WorkActionContext)_localctx).target = expression(0);
				setState(83);
				match(T__5);
				}
				break;
			case T__17:
				_localctx = new FollowActionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				match(T__17);
				setState(86);
				((FollowActionContext)_localctx).target = expression(0);
				setState(87);
				match(T__5);
				}
				break;
			case T__18:
				_localctx = new AttackActionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(89);
				match(T__18);
				setState(90);
				((AttackActionContext)_localctx).target = expression(0);
				setState(91);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NotExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseLiteralContext extends ExpressionContext {
		public FalseLiteralContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterFalseLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitFalseLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitFalseLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PositionTestExprContext extends ExpressionContext {
		public Token test;
		public ExpressionContext argument;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ISSOLID() { return getToken(HillbilliesTaskLangParser.ISSOLID, 0); }
		public TerminalNode ISPASSABLE() { return getToken(HillbilliesTaskLangParser.ISPASSABLE, 0); }
		public PositionTestExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterPositionTestExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitPositionTestExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitPositionTestExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExprContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueLiteralContext extends ExpressionContext {
		public TrueLiteralContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterTrueLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitTrueLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitTrueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnitTestExprContext extends ExpressionContext {
		public Token test;
		public ExpressionContext argument;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ISFRIEND() { return getToken(HillbilliesTaskLangParser.ISFRIEND, 0); }
		public TerminalNode ISENEMY() { return getToken(HillbilliesTaskLangParser.ISENEMY, 0); }
		public TerminalNode ISALIVE() { return getToken(HillbilliesTaskLangParser.ISALIVE, 0); }
		public TerminalNode CARRIESITEM() { return getToken(HillbilliesTaskLangParser.CARRIESITEM, 0); }
		public UnitTestExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterUnitTestExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitUnitTestExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitUnitTestExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReadVariableExprContext extends ExpressionContext {
		public Token variable;
		public TerminalNode IDENTIFIER() { return getToken(HillbilliesTaskLangParser.IDENTIFIER, 0); }
		public ReadVariableExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterReadVariableExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitReadVariableExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitReadVariableExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnitExprContext extends ExpressionContext {
		public UnitContext unit() {
			return getRuleContext(UnitContext.class,0);
		}
		public UnitExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterUnitExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitUnitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitUnitExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PositionExprContext extends ExpressionContext {
		public PositionContext position() {
			return getRuleContext(PositionContext.class,0);
		}
		public PositionExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterPositionExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitPositionExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitPositionExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExprContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AndExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				_localctx = new ReadVariableExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(96);
				((ReadVariableExprContext)_localctx).variable = match(IDENTIFIER);
				}
				break;
			case 2:
				{
				_localctx = new PositionExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(97);
				position();
				}
				break;
			case 3:
				{
				_localctx = new UnitExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(98);
				unit();
				}
				break;
			case 4:
				{
				_localctx = new TrueLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(99);
				match(T__19);
				}
				break;
			case 5:
				{
				_localctx = new FalseLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(100);
				match(T__20);
				}
				break;
			case 6:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(101);
				match(T__21);
				setState(102);
				((ParenExprContext)_localctx).expr = expression(0);
				setState(103);
				match(T__22);
				}
				break;
			case 7:
				{
				_localctx = new PositionTestExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(105);
				((PositionTestExprContext)_localctx).test = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ISSOLID || _la==ISPASSABLE) ) {
					((PositionTestExprContext)_localctx).test = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(106);
				((PositionTestExprContext)_localctx).argument = expression(5);
				}
				break;
			case 8:
				{
				_localctx = new UnitTestExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(107);
				((UnitTestExprContext)_localctx).test = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ISFRIEND) | (1L << ISENEMY) | (1L << ISALIVE) | (1L << CARRIESITEM))) != 0)) ) {
					((UnitTestExprContext)_localctx).test = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(108);
				((UnitTestExprContext)_localctx).argument = expression(4);
				}
				break;
			case 9:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(109);
				match(T__23);
				setState(110);
				((NotExprContext)_localctx).expr = expression(3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(119);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new AndExprContext(new ExpressionContext(_parentctx, _parentState));
						((AndExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(113);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(114);
						match(T__24);
						setState(115);
						((AndExprContext)_localctx).right = expression(3);
						}
						break;
					case 2:
						{
						_localctx = new OrExprContext(new ExpressionContext(_parentctx, _parentState));
						((OrExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(116);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(117);
						match(T__25);
						setState(118);
						((OrExprContext)_localctx).right = expression(2);
						}
						break;
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnitContext extends ParserRuleContext {
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
	 
		public UnitContext() { }
		public void copyFrom(UnitContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AnyUnitContext extends UnitContext {
		public AnyUnitContext(UnitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterAnyUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitAnyUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitAnyUnit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EnemyUnitContext extends UnitContext {
		public EnemyUnitContext(UnitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterEnemyUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitEnemyUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitEnemyUnit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ThisUnitContext extends UnitContext {
		public ThisUnitContext(UnitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterThisUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitThisUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitThisUnit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FriendUnitContext extends UnitContext {
		public FriendUnitContext(UnitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterFriendUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitFriendUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitFriendUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_unit);
		try {
			setState(128);
			switch (_input.LA(1)) {
			case T__26:
				_localctx = new ThisUnitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(T__26);
				}
				break;
			case T__27:
				_localctx = new FriendUnitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				match(T__27);
				}
				break;
			case T__28:
				_localctx = new EnemyUnitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				match(T__28);
				}
				break;
			case T__29:
				_localctx = new AnyUnitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(127);
				match(T__29);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PositionContext extends ParserRuleContext {
		public PositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_position; }
	 
		public PositionContext() { }
		public void copyFrom(PositionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NextToPositionContext extends PositionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NextToPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterNextToPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitNextToPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitNextToPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogPositionContext extends PositionContext {
		public LogPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterLogPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitLogPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitLogPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoulderPositionContext extends PositionContext {
		public BoulderPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterBoulderPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitBoulderPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitBoulderPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WorkshopPositionContext extends PositionContext {
		public WorkshopPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterWorkshopPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitWorkshopPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitWorkshopPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelectedPositionContext extends PositionContext {
		public SelectedPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterSelectedPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitSelectedPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitSelectedPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PositionOfPositionContext extends PositionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PositionOfPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterPositionOfPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitPositionOfPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitPositionOfPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralPositionContext extends PositionContext {
		public Token x;
		public Token y;
		public Token z;
		public List<TerminalNode> INT() { return getTokens(HillbilliesTaskLangParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(HillbilliesTaskLangParser.INT, i);
		}
		public LiteralPositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterLiteralPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitLiteralPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitLiteralPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class HerePositionContext extends PositionContext {
		public HerePositionContext(PositionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).enterHerePosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HillbilliesTaskLangListener ) ((HillbilliesTaskLangListener)listener).exitHerePosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HillbilliesTaskLangVisitor ) return ((HillbilliesTaskLangVisitor<? extends T>)visitor).visitHerePosition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PositionContext position() throws RecognitionException {
		PositionContext _localctx = new PositionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_position);
		try {
			setState(146);
			switch (_input.LA(1)) {
			case T__30:
				_localctx = new HerePositionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				match(T__30);
				}
				break;
			case T__31:
				_localctx = new LogPositionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				match(T__31);
				}
				break;
			case T__32:
				_localctx = new BoulderPositionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(132);
				match(T__32);
				}
				break;
			case T__33:
				_localctx = new WorkshopPositionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(133);
				match(T__33);
				}
				break;
			case T__21:
				_localctx = new LiteralPositionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(134);
				match(T__21);
				setState(135);
				((LiteralPositionContext)_localctx).x = match(INT);
				setState(136);
				match(T__34);
				setState(137);
				((LiteralPositionContext)_localctx).y = match(INT);
				setState(138);
				match(T__34);
				setState(139);
				((LiteralPositionContext)_localctx).z = match(INT);
				setState(140);
				match(T__22);
				}
				break;
			case T__35:
				_localctx = new NextToPositionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(141);
				match(T__35);
				setState(142);
				((NextToPositionContext)_localctx).expr = expression(0);
				}
				break;
			case T__36:
				_localctx = new PositionOfPositionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(143);
				match(T__36);
				setState(144);
				((PositionOfPositionContext)_localctx).expr = expression(0);
				}
				break;
			case T__37:
				_localctx = new SelectedPositionContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(145);
				match(T__37);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\62\u0097\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3"+
		"\7\3\'\n\3\f\3\16\3*\13\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4\62\n\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\5\bH\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n`\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13r\n\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\7\13z\n\13\f\13\16\13}\13\13\3\f\3\f\3\f\3\f\5\f"+
		"\u0083\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\5\r\u0095\n\r\3\r\2\3\24\16\2\4\6\b\n\f\16\20\22\24\26\30\2\4\3\2"+
		")*\3\2+.\u00a8\2\32\3\2\2\2\4$\3\2\2\2\6\61\3\2\2\2\b\63\3\2\2\2\n8\3"+
		"\2\2\2\f>\3\2\2\2\16A\3\2\2\2\20K\3\2\2\2\22_\3\2\2\2\24q\3\2\2\2\26\u0082"+
		"\3\2\2\2\30\u0094\3\2\2\2\32\33\7\3\2\2\33\34\7\4\2\2\34\35\7\60\2\2\35"+
		"\36\7\5\2\2\36\37\7\4\2\2\37 \7/\2\2 !\7\6\2\2!\"\7\4\2\2\"#\5\4\3\2#"+
		"\3\3\2\2\2$(\5\6\4\2%\'\5\6\4\2&%\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2"+
		"\2)\5\3\2\2\2*(\3\2\2\2+\62\5\b\5\2,\62\5\n\6\2-\62\5\16\b\2.\62\5\f\7"+
		"\2/\62\5\20\t\2\60\62\5\22\n\2\61+\3\2\2\2\61,\3\2\2\2\61-\3\2\2\2\61"+
		".\3\2\2\2\61/\3\2\2\2\61\60\3\2\2\2\62\7\3\2\2\2\63\64\7\61\2\2\64\65"+
		"\7\7\2\2\65\66\5\24\13\2\66\67\7\b\2\2\67\t\3\2\2\289\7\t\2\29:\5\24\13"+
		"\2:;\7\n\2\2;<\5\4\3\2<=\7\13\2\2=\13\3\2\2\2>?\7\f\2\2?@\7\b\2\2@\r\3"+
		"\2\2\2AB\7\r\2\2BC\5\24\13\2CD\7\16\2\2DG\5\4\3\2EF\7\17\2\2FH\5\4\3\2"+
		"GE\3\2\2\2GH\3\2\2\2HI\3\2\2\2IJ\7\20\2\2J\17\3\2\2\2KL\7\21\2\2LM\5\24"+
		"\13\2MN\7\b\2\2N\21\3\2\2\2OP\7\22\2\2PQ\5\24\13\2QR\7\b\2\2R`\3\2\2\2"+
		"ST\7\23\2\2TU\5\24\13\2UV\7\b\2\2V`\3\2\2\2WX\7\24\2\2XY\5\24\13\2YZ\7"+
		"\b\2\2Z`\3\2\2\2[\\\7\25\2\2\\]\5\24\13\2]^\7\b\2\2^`\3\2\2\2_O\3\2\2"+
		"\2_S\3\2\2\2_W\3\2\2\2_[\3\2\2\2`\23\3\2\2\2ab\b\13\1\2br\7\61\2\2cr\5"+
		"\30\r\2dr\5\26\f\2er\7\26\2\2fr\7\27\2\2gh\7\30\2\2hi\5\24\13\2ij\7\31"+
		"\2\2jr\3\2\2\2kl\t\2\2\2lr\5\24\13\7mn\t\3\2\2nr\5\24\13\6op\7\32\2\2"+
		"pr\5\24\13\5qa\3\2\2\2qc\3\2\2\2qd\3\2\2\2qe\3\2\2\2qf\3\2\2\2qg\3\2\2"+
		"\2qk\3\2\2\2qm\3\2\2\2qo\3\2\2\2r{\3\2\2\2st\f\4\2\2tu\7\33\2\2uz\5\24"+
		"\13\5vw\f\3\2\2wx\7\34\2\2xz\5\24\13\4ys\3\2\2\2yv\3\2\2\2z}\3\2\2\2{"+
		"y\3\2\2\2{|\3\2\2\2|\25\3\2\2\2}{\3\2\2\2~\u0083\7\35\2\2\177\u0083\7"+
		"\36\2\2\u0080\u0083\7\37\2\2\u0081\u0083\7 \2\2\u0082~\3\2\2\2\u0082\177"+
		"\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0081\3\2\2\2\u0083\27\3\2\2\2\u0084"+
		"\u0095\7!\2\2\u0085\u0095\7\"\2\2\u0086\u0095\7#\2\2\u0087\u0095\7$\2"+
		"\2\u0088\u0089\7\30\2\2\u0089\u008a\7/\2\2\u008a\u008b\7%\2\2\u008b\u008c"+
		"\7/\2\2\u008c\u008d\7%\2\2\u008d\u008e\7/\2\2\u008e\u0095\7\31\2\2\u008f"+
		"\u0090\7&\2\2\u0090\u0095\5\24\13\2\u0091\u0092\7\'\2\2\u0092\u0095\5"+
		"\24\13\2\u0093\u0095\7(\2\2\u0094\u0084\3\2\2\2\u0094\u0085\3\2\2\2\u0094"+
		"\u0086\3\2\2\2\u0094\u0087\3\2\2\2\u0094\u0088\3\2\2\2\u0094\u008f\3\2"+
		"\2\2\u0094\u0091\3\2\2\2\u0094\u0093\3\2\2\2\u0095\31\3\2\2\2\13(\61G"+
		"_qy{\u0082\u0094";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
