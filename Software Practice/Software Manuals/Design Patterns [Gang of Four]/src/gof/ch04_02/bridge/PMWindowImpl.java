package gof.ch04_02.bridge;

import gof.ch04_01.adapter.text.Coordinate;
import gof.designpatterns.Bridge;
import gof.ch02_02.structure.Point;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 158-159.
 * An element in the Sample Code for the {@linkplain Bridge} design pattern.
 * This builds on the preview from Section 2.6. See the {@linkplain Window}
 * class for more detail.
 * <p/>
 * Here PM refers to Presentation Manager, an old IBM/Microsoft GUI library.
 * "Window" just refers to the movable subscreen element found in most GUIs, and
 * has nothing to do with Microsoft Windows.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PMWindowImpl extends WindowImpl {

	// Lots of Presentation Manager system-specific state, including:
	//
	// private HPS hps;

	@Override
	public void deviceRectangle(Coordinate lowerLeftX, Coordinate lowerLeftY, Coordinate upperRightX,
			Coordinate upperRightY) {
		Coordinate left = new Coordinate(Math.min(lowerLeftX.getValue(), upperRightX.getValue()));
		Coordinate right = new Coordinate(Math.max(lowerLeftX.getValue(), upperRightX.getValue()));
		Coordinate bottom = new Coordinate(Math.min(lowerLeftY.getValue(), upperRightY.getValue()));
		Coordinate top = new Coordinate(Math.max(lowerLeftY.getValue(), upperRightY.getValue()));

		Point[] corners = new Point[4];
		corners[0] = new Point(left.getValue(), top.getValue());
		corners[1] = new Point(right.getValue(), top.getValue());
		corners[2] = new Point(right.getValue(), bottom.getValue());
		corners[3] = new Point(left.getValue(), bottom.getValue());

		/**
		 * <pre>
		 * if (buildPath(hps, corners) == false) {
		 * 	throw new IOException("Could not build path");
		 * }
		 * </pre>
		 */
	}

	/**
	 * <pre>
	 * private boolean buildPath(HPS hps, Point[] corners) {
	 * 	if (GpiBeginPath(hps, 1L) == false) {
	 * 		return (false);
	 * 	}
	 * 	if (GpiSetCurrentPosition(hps, corners[3])) {
	 * 		return (false);
	 * 	}
	 * 	if (GpiPolyLine(hps, 4L, corners) == GPI_ERROR) {
	 * 		return (false);
	 * 	}
	 * 	if (GpiEndPath(hps) == false) {
	 * 		return (false);
	 * 	}
	 * 	return (true);
	 * }
	 * </pre>
	 */

	@Override
	public void deviceBitmap(byte[] bitmap, Coordinate lowerleftX, Coordinate lowerLeftY) {
		// Draw a bitmap, Presentation Manager style.
	}

}
