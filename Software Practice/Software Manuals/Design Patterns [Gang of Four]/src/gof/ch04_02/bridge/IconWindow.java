package gof.ch04_02.bridge;

import gof.ch04_01.adapter.text.Coordinate;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 156-159.
 * An element in the sample code for the {@linkplain gof.designpatterns.Bridge
 * Bridge} design pattern. See the local {@linkplain Window} class for more
 * detail.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_02/bridge/UML%20Diagram.jpg"
 * /> </div>
 * 
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
