package gof.ch04_01.adapter.text;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 146, 147. This object is used in the illustration of the
 * {@linkplain gof.designpatterns.Adapter Adapter} pattern. The TextView class
 * is a stand-in for some hypothetical fully-featured text widget. See
 * {@linkplain gof.ch04_01.adapter.drawing.TextShape TextShape} for more
 * detail.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_01/adapter/drawing/UML%20Diagram.jpg"
 * /> </div>
 * 
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
