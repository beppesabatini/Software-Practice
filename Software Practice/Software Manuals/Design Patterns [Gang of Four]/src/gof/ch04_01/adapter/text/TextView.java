package gof.ch04_01.adapter.text;

import gof.ch04_01.adapter.drawing.TextShape;
import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 146, 147. An element in the illustration of the {@linkplain Adapter} pattern.
 * This is a stand-in for some hypothetical fully-featured text widget. See
 * {@linkplain TextShape} for more detail.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="../drawing/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public abstract class TextView {

	private Coordinate originX;
	private Coordinate originY;
	private Coordinate width;
	private Coordinate height;

	public void setOrigin(Coordinate coordinateX, Coordinate coordinateY) {
		this.originX = coordinateX;
		this.originY = coordinateY;
	}

	public void setExtent(Coordinate coordinateWidth, Coordinate coordinateLength) {
		this.width = coordinateWidth;
		this.height = coordinateLength;
	}

	public Coordinate getOriginX() {
		return originX;
	}

	public void setOriginX(Coordinate originX) {
		this.originX = originX;
	}

	public Coordinate getOriginY() {
		return originY;
	}

	public void setOriginY(Coordinate originY) {
		this.originY = originY;
	}

	public Coordinate getWidth() {
		return width;
	}

	public void setWidth(Coordinate width) {
		this.width = width;
	}

	public Coordinate getHeight() {
		return height;
	}

	public void setHeight(Coordinate height) {
		this.height = height;
	}

	public boolean isEmpty() {
		if (this.originX != null || this.originY != null) {
			return (false);
		}
		if (this.width != null || this.height != null) {
			return (false);
		}
		return (true);
	}
}
