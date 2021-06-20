package gof.ch04_05.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import java.io.ByteArrayInputStream;

/**
 * <div class="javadoc-text">More non-functional stubs. Most of these are only
 * mentioned in passing in the manual. These are included here only to get other
 * stubs to compile.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class FacadeSupport {

	public static class Token {
		// Stub
	}

	public static class RISCCodeGenerator extends CodeGenerator {

		public RISCCodeGenerator(ByteArrayInputStream inputStream) {
			super(inputStream);
		}

		void visit(StatementNode statementNode) {
			// Stub
		}

		void visit(ExpressionNode expressionNode) {
			// Stub
		}
	}

	public static class Parser {

		public void parse(Scanner scanner, ProgramNodeBuilder programNodeBuilder) {
			// Stub
		}
	}

	public static class StatementNode extends ProgramNode {
		public void add(ProgramNode programNode) {
			// Stub
		}

		public void remove(ProgramNode programNode) {
			// Stub
		}

		public void traverse(CodeGenerator codeGenerator) {
			// Stub
		}
	}

	/* From p. 191 in the manual. */
	public static class ExpressionNode extends ProgramNode {

		private List<ProgramNode> childNodes;

		public ExpressionNode() {
			this.childNodes = new ArrayList<ProgramNode>();
		}

		public void add(ProgramNode programNode) {
			// Stub
		}

		public void remove(ProgramNode programNode) {
			// Stub
		}

		public void traverse(CodeGenerator codeGenerator) {
			codeGenerator.visit(this);

			ListIterator<ProgramNode> childNodeIterator = this.childNodes.listIterator();
			while (childNodeIterator.hasNext()) {
				ProgramNode currentProgramNode = childNodeIterator.next();
				currentProgramNode.traverse(codeGenerator);
			}

		}
	}
}
