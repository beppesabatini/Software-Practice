package gof.ch05_03.interpreter;

import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain gof.designpatterns.Interpreter Interpreter} design pattern. See
 * the mouse-over definition of Interpreter for more detail. </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_03/interpreter/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class AndExpression implements BooleanExpression, Interpreter {

	private BooleanExpression operand01;
	private BooleanExpression operand02;

	public AndExpression(BooleanExpression operand01, BooleanExpression operand02) {
		this.operand01 = operand01;
		this.operand02 = operand02;
	}

	public boolean evaluate(Context context) {
		boolean evaluation01 = this.operand01.evaluate(context);
		boolean evaluation02 = this.operand02.evaluate(context);
		return (evaluation01 && evaluation02);
	}

	public BooleanExpression copy() {
		return (new AndExpression(operand01.copy(), operand02.copy()));
	}

	public BooleanExpression replace(String expressionName, BooleanExpression booleanExpression) {
		BooleanExpression expression01 = operand01.replace(expressionName, booleanExpression);
		BooleanExpression expression02 = operand02.replace(expressionName, booleanExpression);
		return (new AndExpression(expression01, expression02));
	}

	public void finalize() {
		// Clean-up before deallocation;
	}

}
