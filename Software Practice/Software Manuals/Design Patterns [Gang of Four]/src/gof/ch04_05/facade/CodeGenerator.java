package gof.ch04_05.facade;

import java.io.ByteArrayInputStream;

import gof.ch04_05.facade.FacadeSupport.ExpressionNode;
import gof.ch04_05.facade.FacadeSupport.StatementNode;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 190. An
 * element in the sample code for the {@linkplain gof.designpatterns.Facade
 * Facade} design pattern. See the {@linkplain Compiler} class for the actual
 * Facade.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class CodeGenerator implements Visitor {

	private ByteArrayInputStream inputStream;

	public CodeGenerator(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
		System.out.println("Initialized inputStream: " + this.inputStream);
	}

	abstract void visit(StatementNode statementNode);

	abstract void visit(ExpressionNode expressionNode);
}
