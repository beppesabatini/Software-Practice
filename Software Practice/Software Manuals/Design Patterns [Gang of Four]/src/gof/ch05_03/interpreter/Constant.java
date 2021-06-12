package gof.ch05_03.interpreter;

import gof.designpatterns.Interpreter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 252. An element in the sample code illustrating the {@linkplain Interpreter}
 * design pattern. The Constant class does not appear in the manual, it is only
 * mentioned in passing.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Constant implements BooleanExpression {

	private Boolean trueOrFalse;

	public Constant(Boolean trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}

	@Override
	public boolean evaluate(Context context) {
		return this.trueOrFalse;
	}

	@Override
	public BooleanExpression replace(String originalString, BooleanExpression booleanExpression) {
		return booleanExpression;
	}

	@Override
	public BooleanExpression copy() {
		return new Constant(this.trueOrFalse);
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation
	}
}
