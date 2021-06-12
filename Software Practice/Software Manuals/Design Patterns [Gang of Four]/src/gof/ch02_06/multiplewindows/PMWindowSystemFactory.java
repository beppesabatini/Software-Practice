package gof.ch02_06.multiplewindows;

import gof.ch02_06.multiplewindows.MultipleWindowsSupport.*;
import gof.ch04_02.bridge.PMWindowImpl;
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
 * help construct the Bridge. In this class, PM refers to Presentation Manager,
 * an old IBM/Microsoft GUI library.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PMWindowSystemFactory extends WindowSystemFactory implements AbstractFactory, Singleton {

	private static PMWindowSystemFactory instance;

	// Make this private so that this will remain a Singleton.
	private PMWindowSystemFactory() {
	};

	public static PMWindowSystemFactory getInstance() {
		if (instance == null) {
			instance = new PMWindowSystemFactory();
		}
		return (instance);
	}

	@Override
	public WindowImpl createWindowImpl() {
		// From Section 4.2:
		return new PMWindowImpl();
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
