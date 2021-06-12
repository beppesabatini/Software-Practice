package gof.ch04_01.adapter.drawing;

import gof.ch02_02.structure.Point;
import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 140, 146, 147. An element in the illustration of the {@linkplain Adapter}
 * pattern. This interface is not in the manual. See {@linkplain TextShape} for
 * more detail.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public interface GraphicalElementGUI {

	public BoundingBox getBoundingBox();

	public void setBoundingBox(Point bottomLeft, Point topRight);

	public boolean isEmpty();

	public Manipulator createManipulator();
}
