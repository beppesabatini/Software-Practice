package gof.ch05_01.chainofresponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 230. Part
 * of the sample code to illustrate the
 * {@linkplain gof.designpatterns.ChainOfResponsibility ChainOfResponsibility}
 * design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_01/chainofresponsibility/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Widget extends HelpHandler {

	public static final String DEBUG = "false";

	protected Widget parent;

	protected Widget(Widget parent, Topic topic) {
		super(parent, topic);
		this.parent = parent;
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Initialized parent: " + this.parent);
		}
	}
}
