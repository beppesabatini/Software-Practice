package gof.ch05_05.mediator;

import java.util.List;
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
public class ListBox extends Widget implements Mediator {

	public ListBox(DialogDirector dialogDirector) {
		super(dialogDirector);
	}

	public final String getSelection() {
		System.out.println(" -- In List Box, user selected Times New Roman");
		return ("Times New Roman");
	}

	public void setList(List<String> listOptions) {
		System.out.println(" -- In ListBox, initializing the list of options: " + listOptions);
	}

	@Override
	public void handleMouse(MouseEvent mouseEvent) {
		System.out.println(" -- ListBox hears a mouse click");
	}
}
