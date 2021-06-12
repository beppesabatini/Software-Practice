package gof.ch04_02.bridge;

import gof.ch04_01.adapter.text.Coordinate;
import gof.designpatterns.Bridge;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 156-159.
 * An element in the Sample Code for the {@linkplain Bridge} design pattern. See
 * the {@linkplain Window} class for more detail.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class IconWindow extends Window {

	private final byte[] bitmapName = "logoBitmap".getBytes();

	public IconWindow(View view) {
		super(view);
	}

	@Override
	public void drawContents() {
		System.out.println("Drawing an icon window");
		WindowImpl windowImpl = getWindowImpl();
		if (windowImpl != null) {
			windowImpl.deviceBitmap(bitmapName, new Coordinate(0f), new Coordinate(0f));
		}

	}

}
