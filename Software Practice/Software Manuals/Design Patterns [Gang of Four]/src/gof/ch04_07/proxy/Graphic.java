package gof.ch04_07.proxy;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import gof.ch02_02.structure.Point;
import gof.ch04_07.proxy.GraphicSupport.*;
import gof.designpatterns.Proxy;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 214. Sample
 * code to illustrate the {@linkplain Proxy} design pattern. In this example, a
 * Proxy serves as a stand-in for an image file until it is needed. It can also
 * provide some gatekeeping and bookkeeping functionality.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Graphic {

	public void draw(final Point originPoint);

	public void handleMouseEvent(MouseEvent mouseEvent);

	public Point getExtent();

	public void load(FileInputStream inputStream);

	public void save(FileOutputStream outputStream);

	public void move(Point newOriginPoint);

	public void finalize();
	// Clean-up before deallocation.
}
