package gof.ch05_10.templatemethod;

import gof.designpatterns.TemplateMethod;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 329. Supporting the sample code illustrating the
 * {@linkplain gof.designpatterns.TemplateMethod TemplateMethod}. Actually the
 * manual provides almost no sample code for this pattern, and this is mostly
 * invented.
 * <p/>
 * The concept is that an abstract class is being used as a broad template for
 * page display, and the details are filled in by overriding its functions in a
 * concrete subclass, with more detailed and appropriate methods. These are
 * called TemplateMethods.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_10/templatemethod/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class DailyReportView extends ViewTemplate implements TemplateMethod {

	@Override
	protected void setFocus() {
		System.out.println("Requesting actual current data from provider");
	}

	@Override
	protected void doDisplay() {
		System.out.println("Displaying daily report using user's specified theme and fields");
		System.out.println("Storing report in DB and emailing report to specified mailing list");
	}

	@Override
	protected void resetFocus() {
		System.out.println("If user has specified focus field use that value");
		System.out.println("Otherwise return focus to the system default");
	}

}
