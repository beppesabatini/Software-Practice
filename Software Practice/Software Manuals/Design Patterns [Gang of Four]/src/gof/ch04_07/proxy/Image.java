package gof.ch04_07.proxy;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import gof.ch02_02.structure.Point;
import gof.ch04_07.proxy.GraphicSupport.MouseEvent;
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
public class Image implements Graphic {

	public Image(final String fileName) {

	}

	@Override
	public void draw(Point originPoint) {
		// Stub
	}

	@Override
	public void handleMouseEvent(MouseEvent mouseEvent) {
		// Stub
	}

	@Override
	public Point getExtent() {
		// Stub
		return null;
	}

	@Override
	public void load(FileInputStream inputStream) {
		// Stub
	}

	@Override
	public void save(FileOutputStream outputStream) {
		// Stub
	}

	@Override
	public void move(Point newOriginPoint) {
		// Stub
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation.
	}
}
