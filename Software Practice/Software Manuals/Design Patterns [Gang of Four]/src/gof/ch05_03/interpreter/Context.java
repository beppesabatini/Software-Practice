package gof.ch05_03.interpreter;

import java.util.HashMap;
import java.util.Map;
import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain Interpreter} design pattern. In the manual, this class is only
 * mentioned in passing.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Context {
	private Map<VariableExpression, Boolean> contextValues;

	public Context() {
		this.contextValues = new HashMap<VariableExpression, Boolean>();
	}

	public boolean lookup(final VariableExpression variableExpression) {
		Boolean contextValue = this.contextValues.get(variableExpression);
		if (contextValue == null) {
			contextValue = false;
		}
		return (contextValue);
	}

	public void assign(VariableExpression variableExpression, boolean trueOrFalse) {
		this.contextValues.put(variableExpression, trueOrFalse);
	}
}
