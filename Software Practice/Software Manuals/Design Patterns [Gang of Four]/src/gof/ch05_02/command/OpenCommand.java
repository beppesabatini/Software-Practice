package gof.ch05_02.command;

import gof.ch05_02.command.CommandSupport.Application;
import gof.ch05_02.command.CommandSupport.Document;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 239-240.
 * Sample code to illustrate the {@linkplain gof.designpatterns.Command Command}
 * design pattern. This OpenCommand class and the {@linkplain PasteCommand}
 * class are each hard-coded to specific bits of functionality.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class OpenCommand extends Command {

	private Application application;
	private String response;

	public OpenCommand(Application application) {
		this.application = application;
	}

	@Override
	public void execute() {
		this.response = askUser();
		if (this.response != null) {
			Document document = new Document(this.response);
			application.addDocument(document);
			document.open();
		}
	}

	protected final String askUser() {
		// Pretend we prompted the end-user to enter a name for a Document.
		return ("demoDocument");
	}
}
