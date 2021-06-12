package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 229-231.
 * Part of the sample code to illustrate the {@linkplain ChainOfResponsibility}
 * design pattern. The manual (p. 232) notes that in some systems the Handler
 * seen here is called an EventHandler or a Bureaucrat or a Responder.</div>
 * 
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class HelpHandler {

	public static final String DEBUG = "false";

	protected HelpHandler successor;
	protected Topic topic;

	public HelpHandler(HelpHandler helpHandler, Topic topic) {
		if (topic == null) {
			topic = Topic.NO_HELP;
		}
		this.successor = helpHandler;
		this.topic = topic;
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Initialized successor: " + this.successor + " and topic: " + this.topic.name());
		}
	}

	public HelpHandler() {
		this.topic = Topic.NO_HELP;
	}

	public boolean hasHelp() {
		return (this.topic != Topic.NO_HELP);
	}

	public void setHandler(HelpHandler helpHandler, Topic topic) {
		this.successor = helpHandler;
		this.topic = topic;
	}

	public void handleHelp() {
		if (this.successor != null) {
			this.successor.handleHelp();
			return;
		}
		System.out.println("Sorry, no help is available");
	}
}
