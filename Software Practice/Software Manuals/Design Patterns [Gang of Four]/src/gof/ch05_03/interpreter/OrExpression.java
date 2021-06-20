package gof.ch05_03.interpreter;

import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain gof.designpatterns.Interpreter Interpreter} design pattern. This
 * class is not in the manual, it's extrapolated from the
 * {@linkplain AndExpression} class. See the mouse-over definition of
 * Interpreter for more detail. </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_03/interpreter/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class OrExpression implements BooleanExpression, Interpreter {

	private BooleanExpression operand01;
	private BooleanExpression operand02;

	public OrExpression(BooleanExpression operand01, BooleanExpression operand02) {
		this.operand01 = operand01;
		this.operand02 = operand02;
	}

	public boolean evaluate(Context context) {
		return (this.operand01.evaluate(context) || this.operand02.evaluate(context));
	}

	public BooleanExpression copy() {
		return (new OrExpression(operand01.copy(), operand02.copy()));
	}

	public BooleanExpression replace(String expressionName, BooleanExpression booleanExpression) {
		BooleanExpression expression01 = operand01.replace(expressionName, booleanExpression);
		BooleanExpression expression02 = operand02.replace(expressionName, booleanExpression);
		return (new OrExpression(expression01, expression02));
	}

	public void finalize() {
		// Clean-up before deallocation;
	}

}
