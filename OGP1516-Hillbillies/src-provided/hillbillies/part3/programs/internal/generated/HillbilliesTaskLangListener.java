// Generated from HillbilliesTaskLang.g by ANTLR 4.5.3
package hillbillies.part3.programs.internal.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HillbilliesTaskLangParser}.
 */
public interface HillbilliesTaskLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#task}.
	 * @param ctx the parse tree
	 */
	void enterTask(HillbilliesTaskLangParser.TaskContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#task}.
	 * @param ctx the parse tree
	 */
	void exitTask(HillbilliesTaskLangParser.TaskContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(HillbilliesTaskLangParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(HillbilliesTaskLangParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(HillbilliesTaskLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(HillbilliesTaskLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(HillbilliesTaskLangParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(HillbilliesTaskLangParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(HillbilliesTaskLangParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(HillbilliesTaskLangParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(HillbilliesTaskLangParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(HillbilliesTaskLangParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(HillbilliesTaskLangParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(HillbilliesTaskLangParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HillbilliesTaskLangParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(HillbilliesTaskLangParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HillbilliesTaskLangParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(HillbilliesTaskLangParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code moveToAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void enterMoveToAction(HillbilliesTaskLangParser.MoveToActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code moveToAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void exitMoveToAction(HillbilliesTaskLangParser.MoveToActionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code workAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void enterWorkAction(HillbilliesTaskLangParser.WorkActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code workAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void exitWorkAction(HillbilliesTaskLangParser.WorkActionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code followAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void enterFollowAction(HillbilliesTaskLangParser.FollowActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code followAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void exitFollowAction(HillbilliesTaskLangParser.FollowActionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attackAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void enterAttackAction(HillbilliesTaskLangParser.AttackActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attackAction}
	 * labeled alternative in {@link HillbilliesTaskLangParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void exitAttackAction(HillbilliesTaskLangParser.AttackActionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(HillbilliesTaskLangParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(HillbilliesTaskLangParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(HillbilliesTaskLangParser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(HillbilliesTaskLangParser.FalseLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code positionTestExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPositionTestExpr(HillbilliesTaskLangParser.PositionTestExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code positionTestExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPositionTestExpr(HillbilliesTaskLangParser.PositionTestExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(HillbilliesTaskLangParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(HillbilliesTaskLangParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(HillbilliesTaskLangParser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(HillbilliesTaskLangParser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unitTestExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnitTestExpr(HillbilliesTaskLangParser.UnitTestExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unitTestExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnitTestExpr(HillbilliesTaskLangParser.UnitTestExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readVariableExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterReadVariableExpr(HillbilliesTaskLangParser.ReadVariableExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readVariableExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitReadVariableExpr(HillbilliesTaskLangParser.ReadVariableExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unitExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnitExpr(HillbilliesTaskLangParser.UnitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unitExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnitExpr(HillbilliesTaskLangParser.UnitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(HillbilliesTaskLangParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(HillbilliesTaskLangParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code positionExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPositionExpr(HillbilliesTaskLangParser.PositionExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code positionExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPositionExpr(HillbilliesTaskLangParser.PositionExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(HillbilliesTaskLangParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link HillbilliesTaskLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(HillbilliesTaskLangParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterThisUnit(HillbilliesTaskLangParser.ThisUnitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitThisUnit(HillbilliesTaskLangParser.ThisUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code friendUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterFriendUnit(HillbilliesTaskLangParser.FriendUnitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code friendUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitFriendUnit(HillbilliesTaskLangParser.FriendUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code enemyUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterEnemyUnit(HillbilliesTaskLangParser.EnemyUnitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code enemyUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitEnemyUnit(HillbilliesTaskLangParser.EnemyUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code anyUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterAnyUnit(HillbilliesTaskLangParser.AnyUnitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code anyUnit}
	 * labeled alternative in {@link HillbilliesTaskLangParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitAnyUnit(HillbilliesTaskLangParser.AnyUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code herePosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterHerePosition(HillbilliesTaskLangParser.HerePositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code herePosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitHerePosition(HillbilliesTaskLangParser.HerePositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterLogPosition(HillbilliesTaskLangParser.LogPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitLogPosition(HillbilliesTaskLangParser.LogPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boulderPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterBoulderPosition(HillbilliesTaskLangParser.BoulderPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boulderPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitBoulderPosition(HillbilliesTaskLangParser.BoulderPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code workshopPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterWorkshopPosition(HillbilliesTaskLangParser.WorkshopPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code workshopPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitWorkshopPosition(HillbilliesTaskLangParser.WorkshopPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterLiteralPosition(HillbilliesTaskLangParser.LiteralPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitLiteralPosition(HillbilliesTaskLangParser.LiteralPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nextToPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterNextToPosition(HillbilliesTaskLangParser.NextToPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nextToPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitNextToPosition(HillbilliesTaskLangParser.NextToPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code positionOfPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterPositionOfPosition(HillbilliesTaskLangParser.PositionOfPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code positionOfPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitPositionOfPosition(HillbilliesTaskLangParser.PositionOfPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectedPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void enterSelectedPosition(HillbilliesTaskLangParser.SelectedPositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectedPosition}
	 * labeled alternative in {@link HillbilliesTaskLangParser#position}.
	 * @param ctx the parse tree
	 */
	void exitSelectedPosition(HillbilliesTaskLangParser.SelectedPositionContext ctx);
}
