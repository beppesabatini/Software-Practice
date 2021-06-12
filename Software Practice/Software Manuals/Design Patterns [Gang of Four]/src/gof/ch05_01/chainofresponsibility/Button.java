package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 230. Part
 * of the sample code to illustrate the {@linkplain ChainOfResponsibility}
 * design pattern. If the Button does not have any Help information to provide,
 * it escalates the request to its parent, the Dialog.
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Button extends Widget implements ChainOfResponsibility {

	public Button() {
		super(null, Topic.NO_HELP);
	}

	public Button(Widget parent, Topic topic) {
		super(parent, topic);
	}

	public void handleHelp() {
		if (hasHelp()) {
			System.out.println("Button says, To invoke Button functionality, push the button.");
			return;
		}
		this.parent.handleHelp();
	}
}
