package gof.ch04_01.adapter.drawing;

import gof.ch02_02.structure.Point;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 140, 146, 147. An element in the illustration of the
 * {@linkplain gof.designpatterns.Adapter Adapter} pattern. This interface is
 * not in the manual. See {@linkplain TextShape} for more detail.</div>
 * <div class="javadoc-diagram"> 
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_01/adapter/drawing/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public interface GraphicalElementGUI {

	public BoundingBox getBoundingBox();

	public void setBoundingBox(Point bottomLeft, Point topRight);

	public boolean isEmpty();

	public Manipulator createManipulator();
}
