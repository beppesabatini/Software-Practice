package gof.ch04_02.bridge;

import java.util.ResourceBundle;
import gof.ch02_02.structure.Point;
import gof.ch02_06.multiplewindows.WindowSystemFactory;
import gof.ch02_06.multiplewindows.PMWindowSystemFactory;
import gof.ch02_06.multiplewindows.XWindowSystemFactory;
import gof.ch04_01.adapter.text.Coordinate;
import gof.designpatterns.AbstractFactory;
import gof.designpatterns.Bridge;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 156-159.
 * An illustration of the {@linkplain Bridge} design pattern. The Bridge pattern
 * can be useful in situations such as servicing third-party requests by
 * invoking a fourth-party service.
 * <p/>
 * In the example code, an abstract hierarchy of UI widgets and commands is very
 * loosely coupled to a second abstract UI hierarchy, and the second version
 * generalizes two or three different GUI libraries.
 * <p/>
 * Imagine some complex, useful GUI unit tester is hard-coded to send requests
 * to one specific GUI interface standard. But, stakeholders want to run the
 * same unit tests against two or three or four alternative, platform-specific
 * GUIs. This is the kind of situation which this example code addresses. The
 * second, third, and fourth GUIs are generalized, perhaps with an
 * {@linkplain AbstractFactory}. Our Bridge design pattern maps requests from
 * the original GUI tester, onto the platform-generalized hierarchy of GUI
 * widgets and functions. This scenario may seem contrived, or an exercise in
 * adding empty complication, but may be more real-world practical when, for
 * example, mapping functionality between out-of-house microservices.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Window implements Bridge {

	private View view; // Window contents
	protected WindowImpl windowImpl;

	// From p. 58.
	public Window() {
		this.windowImpl = getWindowImpl();
	}

	public Window(View view) {
		this.view = view;
	}

	public View getView() {
		return (this.view);
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setWindowContents(View windowContents) {
		this.view = windowContents;
	}

	/**
	 * The manual (p.159) assumes there is an {@link AbstractFactory} which returns
	 * the correct WindowImpl for the current platform. These were actually noted
	 * earlier in passing on p. 57. For the current example they are
	 * {@linkplain PMWindowSystemFactory} and {@linkplain XWindowSystemFactory}.
	 */
	protected WindowImpl getWindowImpl() {
		if (this.windowImpl != null) {
			return (this.windowImpl);
		}

		WindowSystemFactory windowSystemFactory = getWindowSystemFactory();
		this.windowImpl = windowSystemFactory.createWindowImpl();

		return this.windowImpl;
	}

	/**
	 * <div style="width: 580px; ">The manual doesn't specify how the
	 * AbstractFactories know which Windowing system is in use. We specify it here
	 * with a Java feature, a properties bundle.<div>
	 */
	public static WindowSystemFactory getWindowSystemFactory() {

		ResourceBundle properties = ResourceBundle.getBundle("properties");
		String currentWindowSystem = properties.getString("window.system");

		WindowSystemFactory windowSystemFactory = null;
		if (currentWindowSystem.equals("PresentationManager")) {
			windowSystemFactory = PMWindowSystemFactory.getInstance();
		} else if (currentWindowSystem.equals("XWindow")) {
			windowSystemFactory = XWindowSystemFactory.getInstance();
		}
		return (windowSystemFactory);
	}

	public void setWindowImpl(WindowImpl windowImpl) {
		this.windowImpl = windowImpl;
	}

	// Requests handled by concrete subclasses of Window:
	abstract public void drawContents();

	/**
	 * <pre>
	 * abstract public void openWindow(){
	 * // Open 'er up
	 * };
	 * 
	 * abstract public void closeWindow(){
	 * // Shut 'er down
	 * };
	 * 
	 * abstract public void iconifyWindow(){
	 * // Shrink shrink
	 * };
	 * 
	 * abstract public void deiconifyWindow(){
	 * // Grow grow
	 * };
	 * </pre>
	 */

	// Requests forwarded to the implementation, WindowImpl:

	/**
	 * <pre>
	 * public void setOrigin(Point lowerLeftPoint);
	 * 
	 * public void setExtent(Point upperRightPoint);
	 * 
	 * public void raiseWindow();
	 * 
	 * public void lowerWindow();
	 * 
	 * public void drawLine(final Point startPoint, final Point endPoint);
	 * </pre>
	 **/

	// This corresponds to the second code fragment on p. 158.
	public void drawRectangle(final Point lowerLeftPoint, final Point upperRightPoint) {
		WindowImpl windowImpl = getWindowImpl();
		Coordinate lowerLeftX = new Coordinate(lowerLeftPoint.getPointX());
		Coordinate lowerLeftY = new Coordinate(lowerLeftPoint.getPointY());
		Coordinate upperRightX = new Coordinate(upperRightPoint.getPointX());
		Coordinate upperRightY = new Coordinate(upperRightPoint.getPointY());
		// Here "device" means draw with the appropriate device-specific function.
		windowImpl.deviceRectangle(lowerLeftX, lowerLeftY, upperRightX, upperRightY);
	}

	/**
	 * <pre>
	 * public void drawPolygon(final Point[] vertices, int numberVertices);
	 * 
	 * public void drawText(final String textString, final Point startPoint);
	 * </pre>
	 */

	public static class Demo {
		public static void main(String[] args) {
			// No dynamic demo yet.
		}
	}
}
