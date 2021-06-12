package gof.ch04_01.adapter.drawing;

import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">An element in the illustration of the
 * {@linkplain Adapter} pattern. Not in the manual and not functional.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public class CircleShape extends GraphicalElement {

	@Override
	public boolean isEmpty() {
		// Stub
		return false;
	}

	@Override
	public Manipulator createManipulator() {
		 // Stub
		return null;
	}

}
