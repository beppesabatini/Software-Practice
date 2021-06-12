package gof.ch02_06.multiplewindows;

import gof.ch02_06.multiplewindows.MultipleWindowsSupport.*;
import gof.ch04_02.bridge.Window;
import gof.ch04_02.bridge.WindowImpl;
import gof.designpatterns.AbstractFactory;
import gof.designpatterns.Bridge;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 57. Part of
 * an early introduction to the {@linkplain Bridge} design pattern. The pattern
 * and the sample code to illustrate Bridge are dealt with at greater length
 * later, in the Pattern Catalog, on pp. 151-161. Included in the current
 * package are elements of an {@linkplain AbstractFactory} which will be used to
 * construct the Bridge.
 * <p/>
 * The manual asks, when do the implementation classes get initialized, and who
 * is aware of what Window system is in use? The manual authors decide on the
 * AbstractFactory design pattern. In our current Java implementation, the
 * Window system is specified by a properties bundle (See
 * {@linkplain Window#getWindowSystemFactory()}.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class WindowSystemFactory implements AbstractFactory {
	public abstract WindowImpl createWindowImpl();

	public abstract ColorImpl createColorImpl();

	public abstract FontImpl createFontImpl();
	/**
	 * ...and so on. Add "Create" operations such as the ones above for each Window
	 * system requirement.
	 */
}
