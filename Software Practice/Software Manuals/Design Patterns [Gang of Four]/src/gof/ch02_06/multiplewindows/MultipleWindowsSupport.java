package gof.ch02_06.multiplewindows;

import gof.ch04_02.bridge.Window;
import gof.ch04_02.bridge.WindowImpl;

/**
 * <div class="javadoc-text">More non-functional stubs. Most of these are only
 * mentioned in passing in the manual. These are included here primarily to get
 * other stubs to compile.</div>
 * 
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MultipleWindowsSupport {

	public class ColorImpl {
		// Colors used by some particular Windowing system.
	};

	public class FontImpl {
		// Fonts used by some particular Windowing system.
	}

	/**
	 * <div class="javadoc-text">With the classes in this package we can now define
	 * a concrete factory for each Window system. These are
	 * {@linkplain PMWindowSystemFactory} and {@linkplain XWindowSystemFactory}.
	 * With these defined, a client can build a Window of the desired type like so.
	 * This is our equivalent of the code fragment from p. 58.</div>
	 * <link rel="stylesheet" href="../../styles/gof.css">
	 */
	public static final WindowImpl platformSpecificWindow = initPlatformSpecificWindow();

	private static WindowImpl initPlatformSpecificWindow() {
		WindowSystemFactory windowSystemFactory = Window.getWindowSystemFactory();
		WindowImpl window = windowSystemFactory.createWindowImpl();
		return (window);
	}
}
