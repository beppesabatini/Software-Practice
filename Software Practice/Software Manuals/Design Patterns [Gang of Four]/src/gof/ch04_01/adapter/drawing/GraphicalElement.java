package gof.ch04_01.adapter.drawing;

import gof.ch02_02.structure.Point;
import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 140, 146. An element in the illustration of the {@linkplain Adapter} pattern.
 * The manual calls this class "Shape." It is the root or umbrella class for a
 * Graphics library we want to use.
 * <p/>
 * We also want to use a text widget from a different library. See
 * {@linkplain TextShape} for more detail. This class implements an interface,
 * but is still abstract, because it must be further subclassed to form
 * CircleShape, SquareShape, and so on.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public abstract class GraphicalElement implements GraphicalElementGUI {

	private BoundingBox boundingBox;

	public BoundingBox getBoundingBox() {
		return (this.boundingBox);
	}

	public void setBoundingBox(Point bottomLeft, Point topRight) {
		this.boundingBox = new BoundingBox(bottomLeft, topRight);
	}

	public abstract boolean isEmpty();

	public abstract Manipulator createManipulator();
}
