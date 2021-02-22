package ch22.magicbeans.src.magicbeans;

/**
 * Not in the Learning Java manual.
 */
public class DialEvent extends java.util.EventObject {

	private static final long serialVersionUID = -5862923920758090399L;

	int value;

	DialEvent(Dial source, int value) {
		super(source);
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
