package ch22.magicbeans.src.magicbeans;

/**
 * Not in the Learning Java manual.
 */
public class Trivial implements java.io.Serializable {

	private static final long serialVersionUID = -1793682900836872575L;

	boolean foo = false;

	public void setFoo(boolean b) {
		foo = b;
	}

	public boolean getFoo() {
		return foo;
	}

	boolean bar = false;

	public void setBar(boolean b) {
		bar = b;
	}

	public boolean isBar() {
		return bar;
	}
}
