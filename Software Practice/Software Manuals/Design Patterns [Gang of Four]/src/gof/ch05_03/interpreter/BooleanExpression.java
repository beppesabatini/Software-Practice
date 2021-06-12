package gof.ch05_03.interpreter;

import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain Interpreter} design pattern.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface BooleanExpression {

	public boolean evaluate(Context context);

	public BooleanExpression replace(final String originalString, BooleanExpression booleanExpression);

	public BooleanExpression copy();

	public void finalize();
}
