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
 * provide some gatekeeping and bookkeeping functionality.
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ImageProxy implements Graphic, Proxy {

	private String fileName;
	private Point extent;
	private Image image;

	public ImageProxy(final String fileName) {
		this.fileName = new String(fileName);
	}

	@Override
	public void draw(Point originPoint) {
		getImage().draw(originPoint);
	}

	@Override
	public void handleMouseEvent(MouseEvent mouseEvent) {
		// Stub
	}

	@Override
	public Point getExtent() {
		if (this.extent == null) {
			this.extent = getImage().getExtent();
		}
		return this.extent;
	}

	@Override
	public void load(FileInputStream inputStream) {
		// Stub
	}

	@Override
	public void save(FileOutputStream outputStream) {
		// Stub
	}

	protected Image getImage() {
		if (this.image == null) {
			this.image = new Image(this.fileName);
		}
		return (this.image);
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
