package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 231. Part
 * of the sample code to illustrate the {@linkplain ChainOfResponsibility}
 * design pattern. If the Dialog does not have any Help information to provide,
 * it escalates the request to the Application.
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Dialog extends Widget implements ChainOfResponsibility {

	public Dialog() {
		super(null, Topic.NO_HELP);
	}

	public Dialog(HelpHandler helpHandler, Topic topic) {
		super(null, Topic.NO_HELP);
		setHandler(helpHandler, topic);
	}

	public void handleHelp() {
		if (hasHelp()) {
			System.out.println("Dialog says, To respond to a Dialog, press \"Yes\" or \"No.\"");
			return;
		}
		/*
		 * The Dialog doesn't have a parent widget, so instead invoke a "successor,"
		 * next in the chain of responsibility. In this case it will be the Application.
		 */
		this.successor.handleHelp();
	}
}
