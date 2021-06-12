package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 230. Part
 * of the sample code to illustrate the {@linkplain ChainOfResponsibility}
 * design pattern.</div>
 * 
 * <pre></pre>
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
