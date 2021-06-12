package gof.ch04_01.adapter.text;

import gof.ch04_01.adapter.drawing.TextShape;
import gof.designpatterns.Adapter;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 146, 147. An element in the illustration of the {@linkplain Adapter} pattern.
 * See {@linkplain TextShape} for more detail. This class doesn't seem to be
 * particularly meaningful. Perhaps it's an artifact from an older language that
 * didn't support floats.</div>
 * <link rel="stylesheet" href="../../../styles/gof.css">
 */
public class Coordinate {
	private float value;

	public Coordinate(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
