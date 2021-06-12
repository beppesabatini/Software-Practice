package gof.ch05_03.interpreter;

import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain Interpreter} design pattern. This class is not in the manual,
 * it's extrapolated from the AndExpression class.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class NotExpression implements BooleanExpression, Interpreter {

	private BooleanExpression operand;

	public NotExpression(BooleanExpression operand) {
		this.operand = operand;
	}

	public boolean evaluate(Context context) {
		boolean evaluation = this.operand.evaluate(context) == true ? false : true;
		return (evaluation);
	}

	public BooleanExpression copy() {
		return (new NotExpression(operand.copy()));
	}

	public BooleanExpression replace(String expressionName, BooleanExpression booleanExpression) {
		BooleanExpression expression = operand.replace(expressionName, booleanExpression);
		return (new NotExpression(expression));
	}

	public void finalize() {
		// Clean-up before deallocation;
	}

}
