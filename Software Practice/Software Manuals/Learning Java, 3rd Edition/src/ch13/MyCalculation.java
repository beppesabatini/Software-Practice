package ch13;

/**
 * From Learning Java, 3rd Edition, p. 457.
 */
public class MyCalculation extends WorkRequest {

	private static final long serialVersionUID = -7284938593681407535L;

	int n;

	public MyCalculation(int n) {
		this.n = n;
	}

	public Object execute() {
		return ((Integer) (n * n));
	}
}
