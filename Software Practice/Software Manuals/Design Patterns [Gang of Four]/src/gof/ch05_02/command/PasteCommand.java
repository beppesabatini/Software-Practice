package gof.ch05_02.command;

import gof.ch05_02.command.CommandSupport.Document;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 239-240.
 * Sample code to illustrate the {@linkplain gof.designpatterns.Command Command}
 * design pattern.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PasteCommand extends Command {

	private Document document;

	public PasteCommand(Document document) {
		this.document = document;
	}

	@Override
	public void execute() {
		this.document.paste();
	}

}
