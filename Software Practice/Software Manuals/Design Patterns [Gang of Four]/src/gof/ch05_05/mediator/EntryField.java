package gof.ch05_05.mediator;

import gof.ch04_07.proxy.GraphicSupport.MouseEvent;
import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 279. Part of the sample code illustrating the {@linkplain Mediator} design
 * pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class EntryField extends Widget implements Mediator {

	private String text;

	public EntryField(DialogDirector dialogDirector) {
		super(dialogDirector);
	}

	public void setText(final String textEntered) {
		if (textEntered != null && "".equals(textEntered) == false) {
			System.out.println(" -- In Entry Field, setting the text: \"" + textEntered + "\"");
			this.text = textEntered;
		}
	}

	public final String getText() {
		System.out.println(" -- In EntryField, getting the text: " + this.text);
		return (this.text);
	}

	@Override
	public void handleMouse(MouseEvent mouseEvent) {
		System.out.println(" -- EntryField hears a mouse click, don't tell the moderator yet!");
	}
}
