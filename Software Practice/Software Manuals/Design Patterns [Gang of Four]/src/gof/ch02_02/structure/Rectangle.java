package gof.ch02_02.structure;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], pp. 20,
 * 38, 39. An early introduction to the {@linkplain Composite} design
 * pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Rectangle extends GlyphImpl {

	private Point upperLeft;
	private Point lowerRight;

	public float getArea() {
		float width = lowerRight.getPointX() - upperLeft.getPointX();
		float height = lowerRight.getPointY() - upperLeft.getPointY();
		float area = width * height;
		return (area);
	}

	public Rectangle(Point upperLeft, Point lowerRight) {
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	public void drawRectangle(Window parentWindow) {
		parentWindow.drawRectangle();
	}

	public boolean intersects(Point point) {
		float pointX = point.getPointX();
		float pointY = point.getPointY();
		float minX = upperLeft.getPointX();
		float maxX = lowerRight.getPointX();
		float minY = upperLeft.getPointY();
		float maxY = lowerRight.getPointY();

		if (pointX < minX || pointX > maxX) {
			return (false);
		}
		if (pointY < minY || pointY > maxY) {
			return (false);
		}

		return (true);
	}
}
