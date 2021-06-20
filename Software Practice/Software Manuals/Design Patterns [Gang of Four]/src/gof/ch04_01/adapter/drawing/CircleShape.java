package gof.ch04_01.adapter.drawing;

/**
 * <div class="javadoc-text">Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Adapter Adapter} pattern. Not in the manual
 * and not functional. See {@linkplain TextShape} for more detail.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_01/adapter/drawing/UML%20Diagram.jpg"
 * /> </div>
 * 
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
