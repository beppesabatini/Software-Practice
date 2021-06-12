package gof.ch04_01.adapter.drawing;

import gof.ch02_02.structure.Point;
import gof.ch04_01.adapter.text.Coordinate;
import gof.ch04_01.adapter.text.TextManipulator;
import gof.ch04_01.adapter.text.TextView;
import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 140, 146. An illustration of the {@linkplain Adapter} pattern. The premise is
 * that a developer wants to use a graphics library to build a certain
 * application, but also wants to use a text widget from a different library in
 * that same app. This TextShape class acts as an Adapter, so that the text
 * widget can support the same interface as the graphics widgets, and be used as
 * if it were in the same library.
 * <p/>
 * The example in the manual uses C++ and double inheritance to implement the
 * Adapter. Java of course does not support double inheritance, but fortunately
 * single inheritance works just fine to solve this problem. We subclass the
 * text widget, and satisfy the interface for the graphics widget. If either
 * GraphicalElement or TextView were more complicated, a different approach
 * might be necessary to combine functionality from both types in one class.
 * <p/>
 * The manual gives this code as an example of using Adapter at the class level.
 * Adapter can also be implemented at the instantiation level, by initializing
 * TextShape with a TextView instance, and storing and using the TextView as a
 * private variable (Design Patterns pp 147, 148).</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public class TextShape extends TextView implements Adapter, GraphicalElementGUI {

	private BoundingBox boundingBox;

	@Override
	public BoundingBox getBoundingBox() {
		return (this.boundingBox);
	}

	/**
	 * Here is the whole point of the {@link Adapter} design pattern. We take the
	 * arguments for a typical graphics widget, and use them--"adapt" them--to
	 * initialize a text widget from a different library.
	 */
	@Override
	public void setBoundingBox(Point bottomLeft, Point topRight) {

		/*
		 * Set the value in the graphics widget (the interface we are implementing).
		 */
		this.boundingBox = new BoundingBox(bottomLeft, topRight);
		/*
		 * Set the value in the text widget (our superclass). In real life, we might or
		 * might not have to maintain both versions in parallel. For now, assume we need
		 * both.
		 */
		Coordinate width = new Coordinate(topRight.getPointX() - bottomLeft.getPointX());
		Coordinate height = new Coordinate(topRight.getPointY() - bottomLeft.getPointY());
		this.setExtent(width, height);

		Coordinate originX = new Coordinate(bottomLeft.getPointX());
		Coordinate originY = new Coordinate(topRight.getPointY() - width.getValue());
		this.setOrigin(originX, originY);

	}

	@Override
	public boolean isEmpty() {
		// Using the functionality from the original text widget here.
		return this.isEmpty();
	}

	@Override
	public Manipulator createManipulator() {
		return new TextManipulator(this);
	}

}
