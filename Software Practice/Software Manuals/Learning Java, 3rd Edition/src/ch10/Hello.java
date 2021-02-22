package ch10;

import java.util.*;

/**
 * From Learning Java, 3rd Edition, p. 309. If you can't read the Chinese here,
 * set Eclipse to UTF-8 through: 
 * <pre>
 * Window/Preferences/General/Workspace/Text File Encoding
 * </pre>
 */
public class Hello {
	public static void main(String[] args) {
		ResourceBundle bundle;
		bundle = ResourceBundle.getBundle("resources.Message", Locale.US);
		System.out.println(bundle.getString("hello.world"));
		System.out.println(bundle.getString("bye.for.now"));
		bundle = ResourceBundle.getBundle("resources.Message", Locale.ITALY);
		System.out.println(bundle.getString("hello.world"));
		System.out.println(bundle.getString("bye.for.now"));
		bundle = ResourceBundle.getBundle("resources.Message", Locale.CHINA);
		System.out.println(bundle.getString("hello.world"));
		System.out.println(bundle.getString("bye.for.now"));
	}
}
