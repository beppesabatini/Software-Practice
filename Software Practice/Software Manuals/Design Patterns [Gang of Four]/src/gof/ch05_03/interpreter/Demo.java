package gof.ch05_03.interpreter;

import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 251-254. An element in the sample code illustrating the
 * {@linkplain Interpreter} design pattern. The particular class below is not in
 * the manual. This Demo attempts to evaluate the boolean expression:
 * <p/>
 * <code>(true AND x) OR (y AND (NOT x))</code>
 * <p/>
 * The use case seems to be that a user wants to define a set of canned boolean
 * expressions in which the variables remain unassigned. He then can apply
 * values to the variables by writing to the Context map, and re-evaluate the
 * expressions with the new values.
 * <p/>
 * In real life every language has a native boolean library, and no one would
 * ever have to reinvent it. The only reason to do so would be to teach students
 * about boolean expressions, or to teach developers about the Interpreter
 * design pattern, which is why it's done here. See the mouse-over definition of
 * {@linkplain Interpreter} for some more practical uses for the pattern.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	public static void main(String[] args) {

		Constant trueConstant = new Constant(Boolean.TRUE);
		VariableExpression x = new VariableExpression("X");
		VariableExpression y = new VariableExpression("Y");

		Context context = new Context();
		context.assign(x, false);
		context.assign(y, true);

		AndExpression subExpression01 = new AndExpression(trueConstant, x);
		AndExpression subExpression02 = new AndExpression(y, new NotExpression(x));

		OrExpression orExpression = new OrExpression(subExpression01, subExpression02);
		boolean evaluation = orExpression.evaluate(context);

		// And so, after 250 lines of code, we have an answer:
		System.out.println(" • For (true AND x) OR (y AND (NOT x)), where x = false and y = true");
		System.out.println("    --> Evaluates to: " + evaluation);
		System.out.println();

		context.assign(x, true);
		context.assign(y, false);
		evaluation = orExpression.evaluate(context);
		System.out.println(" • For (true AND x) OR (y AND (NOT x)), where x = true and y = false");
		System.out.println("    --> Evaluates to: " + evaluation);
		System.out.println();

		VariableExpression z = new VariableExpression("Z");
		context.assign(z, true);
		NotExpression notZ = new NotExpression(z);
		subExpression02 = new AndExpression(notZ, new NotExpression(x));
		evaluation = orExpression.evaluate(context);
		System.out.println(" • For (true AND x) OR ((NOT z) AND (NOT x)), where x = true and z = true");
		System.out.println("    --> Evaluates to: " + evaluation);
		System.out.println();

		context.assign(x, false);
		context.assign(z, false);
		orExpression = new OrExpression(subExpression01, subExpression02);
		evaluation = orExpression.evaluate(context);
		System.out.println(" • For (true AND x) OR ((NOT z) AND (NOT x)), where x = false and z = false");
		System.out.println("    --> Evaluates to: " + evaluation);
		System.out.println();

		context.assign(z, true);
		evaluation = orExpression.evaluate(context);
		System.out.println(" • For (true AND x) OR ((NOT z) AND (NOT x)), where x = false and z = true");
		System.out.println("    --> Evaluates to: " + evaluation);
		System.out.println();

	}
}
