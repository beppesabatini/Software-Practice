package gof.ch04_02.bridge;

import gof.ch04_01.adapter.text.Coordinate;
import gof.designpatterns.Bridge;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 55-56,
 * 158-159. An element in the Sample Code for the {@linkplain Bridge} design
 * pattern. This expandes on the preview from Section 2.6. See the
 * {@linkplain Window} class for more detail.
 * <p/>
 * Here "XWindow" refers to an old Unix GUI library. "Window" just refers to the
 * movable subscreen element found in most GUIs, and has nothing to do with
 * Microsoft Windows.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class XWindowImpl extends WindowImpl {

//  Lots of X window system-specific state, including: 
//	
//	private Display display;
//	private Drawable windowId;
//	private GC graphicContext;

	public XWindowImpl() {
		// Stub
	}

	@SuppressWarnings("unused")
	@Override
	public void deviceRectangle(Coordinate lowerLeftX, Coordinate lowerLeftY, Coordinate upperRightX,
			Coordinate upperRightY) {
		int originX = Math.round(Math.min(lowerLeftX.getValue(), upperRightX.getValue()));
		int originY = Math.round(Math.min(lowerLeftY.getValue(), upperRightY.getValue()));
		int width = Math.round(Math.abs(lowerLeftX.getValue() - upperRightX.getValue()));
		int height = Math.round(Math.abs(lowerLeftY.getValue() - upperRightY.getValue()));
		/**
		 * XDrawRectangle(display, windowId, graphicContext, originX, originY, width,
		 * height);
		 */
	}

	@Override
	public void deviceBitmap(byte[] bitmap, Coordinate lowerleftX, Coordinate lowerLeftY) {
		// Stub
		// Draw the bitmap, XWindow-style.
	}
}