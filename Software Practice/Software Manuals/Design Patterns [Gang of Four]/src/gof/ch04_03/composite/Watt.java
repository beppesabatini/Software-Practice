package gof.ch04_03.composite;

import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 170. An
 * element of the sample code for the {@linkplain Composite} and
 * {@linkplain Visitor} design patterns. This class gets no more than a mention
 * in the manual. For more detail see the {@linkplain Equipment} class.</div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Watt {

	private int wattage;

	public Watt(int wattage) {
		this.wattage = wattage;
	}

	public int getWattage() {
		return wattage;
	}

	public void setWattage(int wattage) {
		this.wattage = wattage;
	}
}
