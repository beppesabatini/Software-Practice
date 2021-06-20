package gof.ch02_02.structure;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 38.
 * Part of the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Composite Composite} design pattern.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Point {

	private float pointX;
	private float pointY;

	public Point(float pointX, float pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
	}

	public float getPointX() {
		return pointX;
	}

	public void setPointX(float pointX) {
		this.pointX = pointX;
	}

	public float getPointY() {
		return pointY;
	}

	public void setPointY(float pointY) {
		this.pointY = pointY;
	}
}
