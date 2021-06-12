package gof.ch04_02.bridge;

import gof.ch04_01.adapter.text.Coordinate;
import gof.designpatterns.Bridge;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 156-159.
 * An element in the Sample code for the {@linkplain Bridge} design pattern. See
 * the {@linkplain Window} class for more detail.<div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class WindowImpl implements Bridge {

	protected WindowImpl() {

	}

	/**
	 * <pre>
	 * abstract public void implTop();
	 * 
	 * abstract public void implBottom();
	 * 
	 * abstract public void implSetOrigin(final Point lowerLeft);
	 * 
	 * abstract public void implSetExtent(final Point upperRight);
	 *
	 * abstract public void deviceText(final String displayText, Coordinate startPointX, Coordinate startPointY);
	 * 
	 * ... and more.
	 * 
	 * </pre>
	 */

	abstract public void deviceRectangle(Coordinate lowerLeftX, Coordinate lowerLeftY, Coordinate upperRightX,
			Coordinate upperRightY);

	abstract public void deviceBitmap(final byte[] bitmap, Coordinate lowerleftX, Coordinate lowerLeftY);
}
