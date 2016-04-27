// Generated from HillbilliesTaskLang.g by ANTLR 4.5.3
package hillbillies.part3.programs.internal.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HillbilliesTaskLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HillbilliesTaskLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#task}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTask(HillbilliesTaskLangParser.TaskContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(HillbilliesTaskLangParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(HillbilliesTaskLangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(HillbilliesTaskLangParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(HillbilliesTaskLangParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(HillbilliesTaskLangParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(HillbilliesTaskLangParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HillbilliesTaskLangParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(HillbilliesTaskLangParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code moveToAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMoveToAction(HillbilliesTaskLangParser.MoveToActionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code workAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWorkAction(HillbilliesTaskLangParser.WorkActionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code followAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFollowAction(HillbilliesTaskLangParser.FollowActionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code attackAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttackAction(HillbilliesTaskLangParser.AttackActionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(HillbilliesTaskLangParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(HillbilliesTaskLangParser.FalseLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code positionTestExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositionTestExpr(HillbilliesTaskLangParser.PositionTestExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(HillbilliesTaskLangParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(HillbilliesTaskLangParser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unitTestExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitTestExpr(HillbilliesTaskLangParser.UnitTestExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code readVariableExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadVariableExpr(HillbilliesTaskLangParser.ReadVariableExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unitExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitExpr(HillbilliesTaskLangParser.UnitExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(HillbilliesTaskLangParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code positionExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositionExpr(HillbilliesTaskLangParser.PositionExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(HillbilliesTaskLangParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisUnit(HillbilliesTaskLangParser.ThisUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code friendUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFriendUnit(HillbilliesTaskLangParser.FriendUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code enemyUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnemyUnit(HillbilliesTaskLangParser.EnemyUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code anyUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnyUnit(HillbilliesTaskLangParser.AnyUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code herePosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHerePosition(HillbilliesTaskLangParser.HerePositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogPosition(HillbilliesTaskLangParser.LogPositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boulderPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoulderPosition(HillbilliesTaskLangParser.BoulderPositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code workshopPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWorkshopPosition(HillbilliesTaskLangParser.WorkshopPositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralPosition(HillbilliesTaskLangParser.LiteralPositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nextToPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNextToPosition(HillbilliesTaskLangParser.NextToPositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code positionOfPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositionOfPosition(HillbilliesTaskLangParser.PositionOfPositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectedPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectedPosition(HillbilliesTaskLangParser.SelectedPositionContext ctx);
}
