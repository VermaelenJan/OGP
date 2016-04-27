package hillbillies.part3.programs.internal;

import java.util.stream.Collectors;

import org.antlr.v4.misc.CharSupport;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangBaseVisitor;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangLexer;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.AndExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.AnyUnitContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.AssignmentStatementContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.AttackActionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.BoulderPositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.BreakStatementContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.EnemyUnitContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.FalseLiteralContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.FollowActionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.FriendUnitContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.HerePositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.IfStatementContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.LiteralPositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.LogPositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.MoveToActionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.NextToPositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.NotExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.OrExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.ParenExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.PositionOfPositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.PositionTestExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.PrintStatementContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.ReadVariableExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.SelectedPositionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.StatementsContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.TaskContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.ThisUnitContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.TrueLiteralContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.UnitTestExprContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.WhileStatementContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.WorkActionContext;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.WorkshopPositionContext;

public class ParserVisitor<E, S, T> extends HillbilliesTaskLangBaseVisitor<Void> {

	private class StatementVisitor extends HillbilliesTaskLangBaseVisitor<S> {

		@Override
		public S visitAssignmentStatement(AssignmentStatementContext ctx) {
			return getFactory().createAssignment(ctx.variableName.getText(), expressions.visit(ctx.value),
					toSourceLocation(ctx));
		}

		@Override
		public S visitWhileStatement(WhileStatementContext ctx) {
			return getFactory().createWhile(expressions.visit(ctx.condition), statements.visit(ctx.body),
					toSourceLocation(ctx));
		}

		@Override
		public S visitIfStatement(IfStatementContext ctx) {
			S ifBody = statements.visit(ctx.ifbody);
			S elseBody = null;
			if (ctx.elsebody != null)
				elseBody = statements.visit(ctx.elsebody);
			return getFactory().createIf(expressions.visit(ctx.condition), ifBody, elseBody, toSourceLocation(ctx));
		}

		@Override
		public S visitBreakStatement(BreakStatementContext ctx) {
			return getFactory().createBreak(toSourceLocation(ctx));
		}

		@Override
		public S visitPrintStatement(PrintStatementContext ctx) {
			return getFactory().createPrint(expressions.visit(ctx.value), toSourceLocation(ctx));
		}

		@Override
		public S visitStatements(StatementsContext ctx) {
			if (ctx.stmts.size() != 1) {
				return getFactory().createSequence(ctx.stmts.stream().map(this::visit).collect(Collectors.toList()),
						toSourceLocation(ctx));
			} else {
				return visit(ctx.stmts.get(0));
			}
		}

		@Override
		public S visitMoveToAction(MoveToActionContext ctx) {
			return getFactory().createMoveTo(expressions.visit(ctx.target), toSourceLocation(ctx));
		}

		@Override
		public S visitWorkAction(WorkActionContext ctx) {
			return getFactory().createWork(expressions.visit(ctx.target), toSourceLocation(ctx));
		}

		@Override
		public S visitFollowAction(FollowActionContext ctx) {
			return getFactory().createFollow(expressions.visit(ctx.target), toSourceLocation(ctx));
		}

		@Override
		public S visitAttackAction(AttackActionContext ctx) {
			return getFactory().createAttack(expressions.visit(ctx.target), toSourceLocation(ctx));
		}
	}


	private class ExpressionVisitor extends HillbilliesTaskLangBaseVisitor<E> {


		@Override
		public E visitThisUnit(ThisUnitContext ctx) {
			return getFactory().createThis(toSourceLocation(ctx));
		}

		@Override
		public E visitFriendUnit(FriendUnitContext ctx) {
			return getFactory().createFriend(toSourceLocation(ctx));
		}

		@Override
		public E visitEnemyUnit(EnemyUnitContext ctx) {
			return getFactory().createEnemy(toSourceLocation(ctx));
		}

		@Override
		public E visitAnyUnit(AnyUnitContext ctx) {
			return getFactory().createAny(toSourceLocation(ctx));
		}

		@Override
		public E visitTrueLiteral(TrueLiteralContext ctx) {
			return getFactory().createTrue(toSourceLocation(ctx));
		}
		
		@Override
		public E visitFalseLiteral(FalseLiteralContext ctx) {
			return getFactory().createFalse(toSourceLocation(ctx));
		}
		
		@Override
		public E visitNotExpr(NotExprContext ctx) {
			return getFactory().createNot(visit(ctx.expr), toSourceLocation(ctx));
		}

		@Override
		public E visitAndExpr(AndExprContext ctx) {
			return getFactory().createAnd(visit(ctx.left), visit(ctx.right), toSourceLocation(ctx));
		}

		@Override
		public E visitOrExpr(OrExprContext ctx) {
			return getFactory().createOr(visit(ctx.left), visit(ctx.right), toSourceLocation(ctx));
		}

		@Override
		public E visitPositionTestExpr(PositionTestExprContext ctx) {
			E pos = expressions.visit(ctx.argument);

			switch (ctx.test.getType()) {
			case HillbilliesTaskLangLexer.ISSOLID:
				return getFactory().createIsSolid(pos, toSourceLocation(ctx));
			case HillbilliesTaskLangLexer.ISPASSABLE:
				return getFactory().createIsPassable(pos, toSourceLocation(ctx));
			}
			throw new AssertionError("Unknown token type in position test expression: " + ctx.test);
		}

		@Override
		public E visitUnitTestExpr(UnitTestExprContext ctx) {
			switch (ctx.test.getType()) {
			case HillbilliesTaskLangLexer.ISFRIEND:
				return getFactory().createIsFriend(expressions.visit(ctx.argument), toSourceLocation(ctx));
			case HillbilliesTaskLangLexer.ISENEMY:
				return getFactory().createIsEnemy(expressions.visit(ctx.argument), toSourceLocation(ctx));
			case HillbilliesTaskLangLexer.ISALIVE:
				return getFactory().createIsAlive(expressions.visit(ctx.argument), toSourceLocation(ctx));
			case HillbilliesTaskLangLexer.CARRIESITEM:
				return getFactory().createCarriesItem(expressions.visit(ctx.argument), toSourceLocation(ctx));
			}
			throw new AssertionError("Unknown token type in unit test expression: " + ctx.test);
		}

		@Override
		public E visitHerePosition(HerePositionContext ctx) {
			return getFactory().createHerePosition(toSourceLocation(ctx));
		}

		@Override
		public E visitLogPosition(LogPositionContext ctx) {
			return getFactory().createLogPosition(toSourceLocation(ctx));
		}

		@Override
		public E visitBoulderPosition(BoulderPositionContext ctx) {
			return getFactory().createBoulderPosition(toSourceLocation(ctx));
		}

		@Override
		public E visitWorkshopPosition(WorkshopPositionContext ctx) {
			return getFactory().createWorkshopPosition(toSourceLocation(ctx));
		}

		@Override
		public E visitLiteralPosition(LiteralPositionContext ctx) {
			return getFactory().createLiteralPosition(toInt(ctx.x), toInt(ctx.y), toInt(ctx.z), toSourceLocation(ctx));
		}

		@Override
		public E visitSelectedPosition(SelectedPositionContext ctx) {
			return getFactory().createSelectedPosition(toSourceLocation(ctx));
		}

		@Override
		public E visitNextToPosition(NextToPositionContext ctx) {
			return getFactory().createNextToPosition(visit(ctx.expr), toSourceLocation(ctx));
		}
		
		@Override
		public E visitPositionOfPosition(PositionOfPositionContext ctx) {
			return getFactory().createPositionOf(visit(ctx.expr), toSourceLocation(ctx));
		}
		
		@Override
		public E visitReadVariableExpr(ReadVariableExprContext ctx) {
			return getFactory().createReadVariable(ctx.variable.getText(), toSourceLocation(ctx));
		}
		
		@Override
		public E visitParenExpr(ParenExprContext ctx) {
			return visit(ctx.expr);
		}


	}

	private final ITaskFactory<E, S, T> factory;
	private String name;
	private int priority;

	private final StatementVisitor statements = new StatementVisitor();
	private final ExpressionVisitor expressions = new ExpressionVisitor();

	private S activity;

	public ParserVisitor(ITaskFactory<E, S, T> factory) {
		this.factory = factory;
	}

	private SourceLocation toSourceLocation(ParserRuleContext ctx) {
		int line = ctx.getStart().getLine();
		int column = ctx.getStart().getCharPositionInLine();
		return new SourceLocation(line, column);
	}

	public ITaskFactory<E, S, T> getFactory() {
		return factory;
	}

	public int toInt(Token z) {
		return Integer.parseInt(z.getText());
	}

	public String getName() {
		return name;
	}

	public int getPriority() {
		return priority;
	}

	public S getActivity() {
		return activity;
	}

	@Override
	public Void visitTask(TaskContext ctx) {
		this.name = CharSupport.getStringFromGrammarStringLiteral(ctx.name.getText());
		this.priority = Integer.parseInt(ctx.priority.getText());
		this.activity = statements.visit(ctx.statements());
		return super.visitTask(ctx);
	}

}
