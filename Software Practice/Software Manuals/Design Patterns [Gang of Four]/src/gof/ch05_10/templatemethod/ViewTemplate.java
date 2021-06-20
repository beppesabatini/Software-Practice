package gof.ch05_10.templatemethod;

import gof.designpatterns.TemplateMethod;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 329. Supporting the sample code illustrating the
 * {@linkplain gof.designpatterns.TemplateMethod TemplateMethod}. Actually the
 * manual provides almost no sample code for this pattern, and this class is
 * original.
 * <p/>
 * One possible use case, would be an application in an early stage of
 * development, with some steps of its operation being implemented by filler or
 * dummy or hard-coded functionality. These can be gradually overridden by truly
 * functional methods as development progresses.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_10/templatemethod/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class ViewTemplate implements TemplateMethod {

	public ViewTemplate() {
		System.out.println("Under Construction: Here the page will request the latest data");
	}

	protected void calculateViewValues() {
		System.out.println("Under Construction: this is where values will be calculated.");
		System.out.println("Using canned test data");
	}

	protected void setFocus() {
		System.out.println("Defaulting to main viewport");
	}

	protected void doDisplay() {
		System.out.println("Displaying table of canned test data");
	}

	protected void resetFocus() {
		System.out.println("This is where we will retake focus for the module dashboard");
	}

	public void display() {
		setFocus();
		doDisplay();
		resetFocus();
	}

}
