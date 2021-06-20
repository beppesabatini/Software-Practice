package gof.ch04_07.proxy;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import gof.ch02_02.structure.Point;
import gof.ch04_07.proxy.GraphicSupport.MouseEvent;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 214. Sample
 * code to illustrate the {@linkplain gof.designpatterns.Proxy Proxy} design
 * pattern. In this example, a Proxy serves as a stand-in for an image file
 * until it is needed. It can also provide some gatekeeping and bookkeeping
 * functionality.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_07/proxy/UML%20Diagram.jpg"
 * /> </div>
 * 
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
