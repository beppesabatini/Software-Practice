package gof.ch05_01.chainofresponsibility;

import gof.designpatterns.ChainOfResponsibility;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 229-231. Part of the sample code to illustrate the
 * {@linkplain ChainOfResponsibility} design pattern. This Demo class is not in
 * the manual.
 * <p/>
 * In this example of a simple Chain of Responsibility, an end-user is trying to
 * get Help information concerning a Button widget. The system will first look
 * for a help message in the Button itself, then look in its parent the Dialog,
 * and finally look in the Application. In real life the Application would
 * display a global list of supported help messages.</div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {
	public static void main(String[] args) {
		Application application = new Application(Topic.APPLICATION);
		Dialog dialog = new Dialog(application, Topic.PRINT);
		Button button = new Button(dialog, Topic.PAPER_ORIENTATION);

		askForHelp(application, dialog, button);
		System.out.println();

		System.out.println("Turning off Button help");
		button.setHandler(null, Topic.NO_HELP);
		System.out.println("Asking Button for help:");
		button.handleHelp();
		System.out.println();

		System.out.println("Turning off Dialog help");
		dialog.setHandler(application, Topic.NO_HELP);
		System.out.println("Asking Dialog for help:");
		dialog.handleHelp();
		System.out.println();
	}

	private static void askForHelp(Application application, Dialog dialog, Button button) {
		System.out.println("Asking Button for help:");
		button.handleHelp();

		System.out.println("Asking Dialog for help:");
		dialog.handleHelp();

		System.out.println("Asking Application for help:");
		application.handleHelp();
	}
}
