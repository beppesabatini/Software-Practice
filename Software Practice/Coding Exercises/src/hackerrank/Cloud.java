package hackerrank;

/**
 * Typical enum, used to format output for the CloudJumper exercise. A good
 * simple example of common enum syntax.
 * 
 * @author Beppe Sabatini
 *
 */
public enum Cloud {
	CUMULUS(0), THUNDERHEAD(1);

	private int value;

	public int getValue() {
		return (this.value);
	}

	static public Enum<Cloud> fromValue(String value) {
		return (valueOf(value));
	}

	static public Enum<Cloud> fromValue(int value) {
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
