package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 231. Part
 * of the sample code to illustrate the
 * {@linkplain gof.designpatterns.ChainOfResponsibility ChainOfResponsibility}
 * design pattern. The manual notes that if a Help request has been passed up
 * the Chain of Responsibility to the Application, it will respond by displaying
 * a list of those Help messages with application-wide scope.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_01/chainofresponsibility/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Application extends HelpHandler implements ChainOfResponsibility {

	public Application(Topic topic) {
		super(null, topic);
	}

	/*
	 * The buck stops here. There is no place else to escalate the request after
	 * this.
	 */
	public void handleHelp() {
		String message = "";
		message += "Application says, ";
		message += "Your computer will not function correctly if it is not plugged in.";
		System.out.println(message);
	}
}
