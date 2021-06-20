package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 231. Part
 * of the sample code to illustrate the
 * {@linkplain gof.designpatterns.ChainOfResponsibility ChainOfResponsibility}
 * design pattern. If the Dialog does not have any Help information to provide,
 * it escalates the request to the Application.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_01/chainofresponsibility/UML%20Diagram.jpg"
 * /> </div>
 * 
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
