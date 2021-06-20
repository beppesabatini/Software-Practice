package gof.ch05_03.interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain gof.designpatterns.Interpreter Interpreter} design pattern.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class VariableExpression implements BooleanExpression {

	private String expressionName;

	public VariableExpression(final String expressionName) {
		this.expressionName = expressionName;
	}

	@Override
	public boolean evaluate(Context context) {
		Boolean evaluation = context.lookup(this);
		return evaluation;
	}

	@Override
	public BooleanExpression copy() {
		return new VariableExpression(this.expressionName);
	}

	/**
	 * Not functional either here or in the manual (p. 253), do not use.
	 */
	@Override
	@Deprecated
	public BooleanExpression replace(String expressionName, BooleanExpression booleanExpression) {
		if (expressionName.equals(this.expressionName)) {
			return booleanExpression.copy();
		}
		return (new VariableExpression(this.expressionName));
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation.
	}
}
