package gof.ch05_05.mediator;

import gof.ch04_07.proxy.GraphicSupport.MouseEvent;
import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 279. Part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Mediator Mediator} design pattern. In the
 * example here, the Mediator acts like Control Central, to coordinate different
 * widgets in a Font Select dialog.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_05/mediator/UML%20Diagram.jpg"
 * /> </div>
 * 
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
