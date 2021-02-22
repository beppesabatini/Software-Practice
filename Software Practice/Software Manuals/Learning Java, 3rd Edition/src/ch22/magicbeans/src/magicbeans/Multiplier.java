package ch22.magicbeans.src.magicbeans;

/**
 * From Learning Java, 3rd Edition, p. 772-773.
 */
import java.beans.*;

public class Multiplier implements java.io.Serializable {

	private static final long serialVersionUID = -7446751977035215340L;

	private double a, b, c;

	synchronized public void setA(double val) {
		a = val;
		multiply();
	}

	synchronized public double getA() {
		return a;
	}

	synchronized public void setB(double val) {
		b = val;
		multiply();
	}

	synchronized public double getB() {
		return b;
	}

	synchronized public double getC() {
		return c;
	}

	synchronized public void setC(double val) {
		multiply();
	}

	private void multiply() {
		double oldC = c;
		c = a * b;
		propChanges.firePropertyChange("C", oldC, c);
	}

	private PropertyChangeSupport propChanges = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChanges.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChanges.removePropertyChangeListener(listener);
	}
}
