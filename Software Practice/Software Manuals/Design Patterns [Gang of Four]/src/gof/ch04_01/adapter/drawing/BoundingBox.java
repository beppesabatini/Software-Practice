package gof.ch04_01.adapter.drawing;

import gof.ch02_02.structure.Point;
import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 140, 146, 147. An element in the illustration of the {@linkplain Adapter}
 * pattern. See {@linkplain TextShape} for more detail.</div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public class BoundingBox {

	private Point bottomLeft;
	private Point topRight;

	public BoundingBox(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}

	public Point getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(Point bottomLeft) {
		this.bottomLeft = bottomLeft;
	}

	public Point getTopRight() {
		return topRight;
	}

	public void setTopRight(Point topRight) {
		this.topRight = topRight;
	}

}