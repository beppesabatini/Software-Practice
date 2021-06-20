package gof.ch04_05.facade;

import gof.ch04_05.facade.FacadeSupport.ExpressionNode;
import gof.ch04_05.facade.FacadeSupport.StatementNode;
import gof.designpatterns.Builder;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 189-191.
 * One class from the sample code for an illustration of the
 * {@linkplain gof.designpatterns.Facade Facade} design pattern. See the
 * {@linkplain Compiler} for the actual Facade. </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ProgramNodeBuilder implements Builder {

	private ProgramNode rootNode;

	public ProgramNode getRootNode() {
		return (rootNode);
	}

	public ProgramNodeBuilder() {
		// Stub
	}

	public final StatementNode newVariable(final String variable) {
		StatementNode newVariable = new StatementNode();
		return (newVariable);
	}

	public final ProgramNode newAssignment(ProgramNode variable, ProgramNode expression) {
		ExpressionNode newAssignment = new ExpressionNode();
		return (newAssignment);
	}

	public final StatementNode newReturnStatement(ProgramNode value) {
		StatementNode newReturnStatement = new StatementNode();
		return (newReturnStatement);
	}

	public final ProgramNode newCondition(ProgramNode condition, ProgramNode truePart, ProgramNode falsePart) {
		ExpressionNode newCondition = new ExpressionNode();
		return (newCondition);
	}
}
