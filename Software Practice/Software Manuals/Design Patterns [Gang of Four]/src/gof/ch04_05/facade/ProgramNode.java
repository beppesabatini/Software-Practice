package gof.ch04_05.facade;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 190. An
 * element in the sample code for the {@linkplain Facade} design pattern. See
 * the {@linkplain Compiler} class for the actual Facade.
 * <p/>
 * This particular class, ProgramNode, is cited by the manual as an example of
 * the {@linkplain Composite} design pattern, because it is the abstract root
 * object for a hierarchy of different subclass Nodes, which have been
 * generalized to the point at which they can all be handled in a similar
 * manner. Or, that's the plan at least. For now it's a non-functional
 * stub.</div>
 * 
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
abstract class ProgramNode implements Composite {

	public ProgramNode() {

	}

	// Program node manipulation:
	public void getSourcePosition(int line, int index) {
		/*
		 * Another stub. This would do something like dictate or reveal where we are in
		 * the document being parsed.
		 */
	}

	// Child manipulation:
	abstract public void add(ProgramNode programNode);

	abstract public void remove(ProgramNode programNode);

	abstract public void traverse(CodeGenerator codeGenerator);

}
