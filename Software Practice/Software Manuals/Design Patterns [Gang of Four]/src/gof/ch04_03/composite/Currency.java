package gof.ch04_03.composite;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 170. An
 * element of the sample code for the {@linkplain Composite} design pattern.
 * This class gets only a mention in the manual. For more detail see the
 * {@linkplain Equipment} class.</div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Currency {

	private int value;

	public Currency(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
