package gof.ch05_05.mediator;

import java.util.List;
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
