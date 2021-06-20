package gof.ch02_06.multiplewindows;

import gof.ch02_06.multiplewindows.MultipleWindowsSupport.*;
import gof.ch04_02.bridge.WindowImpl;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 57. One
 * class in the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Bridge Bridge} design pattern. The pattern and
 * the sample code to illustrate the Bridge pattern are dealt with at greater
 * length later, in the Pattern Catalog, on pp. 151-161. Included in Section 2.6
 * are elements of an {@linkplain gof.designpatterns.AbstractFactory
 * AbstractFactory} which will be used to construct the Bridge.
 * <p/>
 * The manual asks, when do the implementation classes get initialized, and who
 * is aware of what Window system is in use? The manual authors decide on the
 * AbstractFactory design pattern. In our current Java implementation, the
 * Window system in use is specified by a user-defined property (see
 * {@linkplain gof.ch04_02.bridge.Window Window} and
 * getWindowSystemFactory()).</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_06/multiplewindows/UML%20Diagram.jpg"
 * /> </div>
 * 
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
