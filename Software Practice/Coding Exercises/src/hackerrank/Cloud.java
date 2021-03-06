package hackerrank;

/**
 * Typical enum, used to support the CloudJumper class, which is also in this
 * package. This is a good simple example of common enum syntax.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public enum Cloud {
	CUMULUS(0),
	THUNDERHEAD(1);

	private final int value;

	public int getValue() {
		return (this.value);
	}

	static public Cloud fromValue(String value) {
		return (valueOf(value));
	}

	static public Cloud fromValue(int value) {
		for (Cloud cloud : Cloud.values()) {
			if (cloud.getValue() == value) {
				return (cloud);
			}
		}
		return (null);
	}

	Cloud(int value) {
		this.value = value;
	}
}
