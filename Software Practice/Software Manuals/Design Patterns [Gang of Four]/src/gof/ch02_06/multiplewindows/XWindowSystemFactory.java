package gof.ch02_06.multiplewindows;

import gof.ch04_02.bridge.XWindowImpl;
import gof.ch02_06.multiplewindows.MultipleWindowsSupport.ColorImpl;
import gof.ch02_06.multiplewindows.MultipleWindowsSupport.FontImpl;
import gof.ch04_02.bridge.WindowImpl;
import gof.designpatterns.AbstractFactory;
import gof.designpatterns.Bridge;
import gof.designpatterns.Singleton;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 57, 159.
 * Part of an early introduction to the {@linkplain Bridge} design pattern. The
 * pattern and the sample code to illustrate Bridge are dealt with at greater
 * length later, in the Pattern Catalog, on pp. 151-161. Included in this
 * package are elements of an {@linkplain AbstractFactory} which will be used to
 * help construct the Bridge. In this class, XWindow refers an old Linux widget
 * library.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class XWindowSystemFactory extends WindowSystemFactory implements AbstractFactory, Singleton {

	private static XWindowSystemFactory instance;

	// Make this private so that this will remain a Singleton.
	private XWindowSystemFactory() {
	};

	public static XWindowSystemFactory getInstance() {
		if (instance == null) {
			instance = new XWindowSystemFactory();
		}
		return (instance);
	}

	@Override
	public WindowImpl createWindowImpl() {
		// From Section 4.2:
		return new XWindowImpl();
	}

	@Override
	public ColorImpl createColorImpl() {
		// Stub
		return (new MultipleWindowsSupport().new ColorImpl());
	}

	@Override
	public FontImpl createFontImpl() {
		// Stub
		return (new MultipleWindowsSupport().new FontImpl());
	}
}
