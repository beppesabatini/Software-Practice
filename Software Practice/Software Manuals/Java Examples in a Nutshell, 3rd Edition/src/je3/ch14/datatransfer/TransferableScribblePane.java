/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch14.datatransfer;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import je3.ch11.gui.JPanelDemoable;
import je3.ch12.graphics.PolyLine;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 448-455. This rewrite of
 * ScribblePane allows individual PolyLine lines to be selected, cut, copied,
 * pasted, dragged, and dropped.
 */
public class TransferableScribblePane extends JPanelDemoable {

	private static final long serialVersionUID = 443446974161835418L;

	// The PolyLines that comprise this scribble:
	List<PolyLine> polyLines;
	// The PolyLine currently being drawn:
	PolyLine currentPolyLine;
	// The PolyLine that is current selected:
	PolyLine selectedPolyLine;
	// Can we drag an image of the line?
	boolean canDragImage;

	// Lines are 3 pixels wide:
	static Stroke stroke = new BasicStroke(3.0f);

	// The selected Polyline is drawn dashed:
	/**
	 * <pre>
	 * The dashPattern is 3 on, 3 off: ---   ---   ---   --- etc.
	 * </pre>
	 */
	static float[] dashPattern = new float[] { 3f, 3f, };
	// How the corners are drawn:
	static int capButt = BasicStroke.CAP_BUTT;
	static int joinRound = BasicStroke.JOIN_ROUND;
	static Stroke selectedStroke = new BasicStroke(3, capButt, joinRound, 0f, dashPattern, 0f);

	// Different borders indicate receptivity to drops.
	static Border normalBorder = new LineBorder(Color.black, 3);
	static Border canDropBorder = new BevelBorder(BevelBorder.LOWERED);

	/* The constructor method: */
	public TransferableScribblePane() {
		// We need a default size and a border:
		setPreferredSize(new Dimension(450, 200));
		setBorder(normalBorder);
		// Start with an empty list of lines:
		polyLines = new ArrayList<PolyLine>();

		// Register interest in mouse button and mouse motion events.
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);

		/*
		 * Enable drag-and-drop by specifying a listener that will be notified when a
		 * drag begins. The dragGestureListener object is defined farther down in the
		 * class.
		 */
		DragSource dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, dragGestureListener);

		/*
		 * Enable drops on this component by registering a listener to be notified when
		 * something is dragged or dropped over us.
		 */
		this.setDropTarget(new DropTarget(this, dropTargetListener));

		// Check whether the system allows us to drag an image of the line.
		canDragImage = DragSource.isDragImageSupported();
	}

	/** We override this method to draw ourselves. */
	public void paintComponent(Graphics graphics) {
		// Let the superclass do its painting first.
		super.paintComponent(graphics);

		// Make a copy of the Graphics context so we can modify it.
		Graphics2D graphics2D = (Graphics2D) (graphics.create());

		// Our superclass doesn't paint the background, so do this ourselves.
		graphics2D.setColor(getBackground());
		graphics2D.fillRect(0, 0, getWidth(), getHeight());

		// Set the line width and color to use for the foreground.
		graphics2D.setStroke(stroke);
		graphics2D.setColor(this.getForeground());

		// Now loop through the PolyLine shapes and draw them all.
		int numberLines = polyLines.size();
		for (int i = 0; i < numberLines; i++) {
			PolyLine polyLine = (PolyLine) polyLines.get(i);
			// If it is the selected line:
			if (polyLine == selectedPolyLine) {
				// Set the dash pattern:
				graphics2D.setStroke(selectedStroke);
				// Draw the line:
				graphics2D.draw(polyLine);
				// Revert to solid lines:
				graphics2D.setStroke(stroke);
			}
			// Otherwise, just draw the line:
			else {
				graphics2D.draw(polyLine);
			}
		}
	}

	/**
	 * This method is called on mouse button events. It begins a new line or tries
	 * to select an existing line.
	 */
	public void processMouseEvent(MouseEvent mouseEvent) {
		// We are only handling left mouse button events:
		if (mouseEvent.getButton() != MouseEvent.BUTTON1) {
			// The superclass method dispatches to registered event listeners.
			super.processMouseEvent(mouseEvent);
			return;
		}
		// If the left mouse button was pressed down:
		if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
			processLeftMouseButtonDown(mouseEvent);
		}
		// Left mouse button up:
		else if (mouseEvent.getID() == MouseEvent.MOUSE_RELEASED) {
			processLeftMouseButtonUp(mouseEvent);
		}

		// The superclass method dispatches to registered event listeners.
		super.processMouseEvent(mouseEvent);
	}

	private void processLeftMouseButtonDown(MouseEvent mouseEvent) {
		// With the Shift key:
		if (mouseEvent.isShiftDown()) {
			processLeftMouseButtonDownWithShiftKey(mouseEvent);
		}
		// No shift key or control key is being pressed.
		else if (!mouseEvent.isControlDown()) {
			processLeftMouseButtonDownNoShiftKey(mouseEvent);
		}
	}

	private void processLeftMouseButtonDownWithShiftKey(MouseEvent mouseEvent) {
		// If the shift key is down, try to select a line.
		int selectedX = mouseEvent.getX();
		int selectedY = mouseEvent.getY();

		// Loop through the lines, checking to see if we hit one.
		PolyLine selection = null;
		int numberLines = this.polyLines.size();
		for (int i = 0; i < numberLines; i++) {
			PolyLine polyLine = polyLines.get(i);
			if (polyLine.intersects(selectedX - 2, selectedY - 2, 4, 4)) {
				selection = polyLine;
				mouseEvent.consume();
				break;
			}
		}
		// If we found an intersecting line, save it and repaint.
		// If the selection changed:
		if (selection != this.selectedPolyLine) {
			// Remember which is selected:
			this.selectedPolyLine = selection;
			// This will make the selection dashed.
			repaint();
		}
	}

	private void processLeftMouseButtonDownNoShiftKey(MouseEvent mouseEvent) {
		// Start a new line on mouse down, without the shift key or control key.
		this.currentPolyLine = new PolyLine(mouseEvent.getX(), mouseEvent.getY());
		this.polyLines.add(this.currentPolyLine);
		mouseEvent.consume();
	}

	private void processLeftMouseButtonUp(MouseEvent mouseEvent) {
		// End the line on mouse up:
		if (this.currentPolyLine != null) {
			this.currentPolyLine = null;
			mouseEvent.consume();
		}
	}

	/**
	 * This method is called for mouse motion events. We don't have to detect
	 * gestures that initiate a drag in this method. That is the job of the
	 * DragGestureRecognizer we created in the constructor: it will notify the
	 * DragGestureListener defined below.
	 */
	public void processMouseMotionEvent(MouseEvent mouseEvent) {
		// If we're dragging, and a line exists:
		if (mouseEvent.getID() == MouseEvent.MOUSE_DRAGGED && currentPolyLine != null) {
			// Add a line segment:
			currentPolyLine.addSegment(mouseEvent.getX(), mouseEvent.getY());
			// Eat the event:
			mouseEvent.consume();
			// Redisplay all PolyLines:
			repaint();
		}
		// Invoke any listeners:
		super.processMouseMotionEvent(mouseEvent);
	}

	/** Copy the selected line to the clipboard, then delete it */
	public void cut() {
		// The cut() function only works if a line is selected.
		if (selectedPolyLine == null) {
			return;
		}
		// Do a Copy operation...
		copy();
		// ...and then erase the selected line:
		polyLines.remove(selectedPolyLine);
		selectedPolyLine = null;
		// Repaint, because a line was removed:
		repaint();
	}

	/** Copy the selected line to the clipboard */
	public void copy() {
		// The copy function only works if a line is selected:
		if (selectedPolyLine == null) {
			return;
		}
		// Get the system Clipboard object:
		Clipboard clipboard = this.getToolkit().getSystemClipboard();

		/*
		 * Wrap the selected line in a TransferablePolyLine object, and pass it to the
		 * clipboard, with a ClipboardOwner object to receive notifications, when some
		 * other application takes ownership of the clipboard.
		 */
		ClipboardOwner clipboardOwner = new ClipboardOwner() {
			public void lostOwnership(Clipboard clipboard, Transferable transferable) {
				/*
				 * This (currently empty) method is called when something else is copied to the
				 * clipboard. We could use it to deselect the selected line, if we wanted.
				 */
			}
		};

		PolyLine selectedLineClone = (PolyLine) this.selectedPolyLine.clone();
		TransferablePolyLine transferablePolyLine = new TransferablePolyLine(selectedLineClone);
		clipboard.setContents(transferablePolyLine, clipboardOwner);
	}

	/** Get a PolyLine from the clipboard, if one exists, and display it */
	public void paste() {
		// Get the system Clipboard and ask for its Transferable contents.
		Clipboard clipboard = this.getToolkit().getSystemClipboard();
		Transferable transferable = clipboard.getContents(this);

		// See if we can extract a PolyLine from the Transferable object.
		PolyLine line;
		try {
			line = (PolyLine) transferable.getTransferData(TransferablePolyLine.FLAVOR);
		}
		// UnsupportedFlavorException or IOException:
		catch (Exception exception) {
			/*
			 * If we get here, the clipboard doesn't hold a PolyLine we can use. So beep to
			 * indicate the error:
			 */
			getToolkit().beep();
			return;
		}

		// We got a line from the clipboard, so add it to list:
		polyLines.add(line);
		// And repaint to make the line appear:
		repaint();
	}

	/** Erase all lines and repaint. */
	public void clear() {
		polyLines.clear();
		repaint();
	}

	/**
	 * This DragGestureListener is notified when the user initiates a drag. We
	 * passed it to the DragGestureRecognizer we created in the constructor.
	 */
	public DragGestureListener dragGestureListener = new DragGestureListener() {
		public void dragGestureRecognized(DragGestureEvent dragGestureEvent) {
			// Don't start a drag if there isn't a selected line:
			if (selectedPolyLine == null) {
				return;
			}

			// Find out where the drag began:
			MouseEvent trigger = (MouseEvent) dragGestureEvent.getTriggerEvent();
			int triggerX = trigger.getX();
			int triggerY = trigger.getY();

			// Don't do anything if the drag was not near the selected line.
			if (!selectedPolyLine.intersects(triggerX - 4, triggerY - 4, 8, 8)) {
				return;
			}

			/*
			 * Make a copy of the selected line, adjust the copy so that the point under the
			 * mouse is (0, 0), and wrap the copy in a Transferable wrapper.
			 */
			PolyLine polyLineClone = (PolyLine) selectedPolyLine.clone();
			polyLineClone.translate(-triggerX, -triggerY);
			Transferable transferable = new TransferablePolyLine(polyLineClone);

			/*
			 * If the system allows custom images to be dragged, make an image of the line
			 * on a transparent background.
			 */
			Image dragImage = null;
			Point hotspot = null;
			if (canDragImage) {
				Rectangle rectangle = polyLineClone.getBounds();
				dragImage = createImage(rectangle.width, rectangle.height);
				Graphics2D graphics2D = (Graphics2D) dragImage.getGraphics();
				// Transparent background:
				graphics2D.setColor(new Color(0, 0, 0, 0));
				graphics2D.fillRect(0, 0, rectangle.width, rectangle.height);
				graphics2D.setColor(getForeground());
				graphics2D.setStroke(selectedStroke);
				graphics2D.translate(-rectangle.x, -rectangle.y);
				graphics2D.draw(polyLineClone);
				hotspot = new Point(-rectangle.x, -rectangle.y);
			}

			/**
			 * Now begin dragging the line, specifying the listener object to receive
			 * notifications about the progress of the operation. Note: the startDrag()
			 * method is defined by the event object, which is unusual.
			 * 
			 * <pre>
			 * null:               use the default drag-and-drop cursors
			 * dragImage:          use the image, if supported
			 * hotspot:            use the hotspot image, if supported
			 * transferable:       drag this object
			 * dragSourceListener: send notifications here
			 * </pre>
			 * 
			 */
			dragGestureEvent.startDrag(null, dragImage, hotspot, transferable, dragSourceListener);
		}
	};

	/**
	 * If this component is the source of a drag, then this DragSourceListener will
	 * receive notifications about the progress of the drag. The only one we use
	 * here is dragDropEnd() which is called after a drop occurs. We could use the
	 * other methods to change cursors or perform other "drag over effects."
	 */
	public DragSourceListener dragSourceListener = new DragSourceListener() {
		// Invoked when dragging stops:
		@Override
		public void dragDropEnd(DragSourceDropEvent dragSourceDropEvent) {
			// Ignore failed drops:
			if (dragSourceDropEvent.getDropSuccess() == false) {
				return;
			}
			// If the drop was a move, then delete the selected line.
			if (dragSourceDropEvent.getDropAction() == DnDConstants.ACTION_MOVE) {
				polyLines.remove(selectedPolyLine);
				selectedPolyLine = null;
				repaint();
			}
		}

		/*
		 * The following methods are unused here. We could implement them to change
		 * custom cursors or perform other "drag over effects".
		 */
		@Override
		public void dragEnter(DragSourceDragEvent dragSourceDragEvent) {
		}

		@Override
		public void dragExit(DragSourceEvent dragSourceEvent) {
		}

		@Override
		public void dragOver(DragSourceDragEvent dragSourceDragEvent) {
		}

		@Override
		public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent) {
		}
	};

	/**
	 * This DropTargetListener is notified when something is dragged over this
	 * component.
	 */
	public DropTargetListener dropTargetListener = new DropTargetListener() {
		/*
		 * This method is called when something is dragged over us. If we understand
		 * what is being dragged, then tell the system we can accept it, and change our
		 * border to provide extra "under the drag" visual feedback to the user to
		 * indicate our receptivity to a drop.
		 */
		public void dragEnter(DropTargetDragEvent dropTargDragEvent) {
			if (dropTargDragEvent.isDataFlavorSupported(TransferablePolyLine.FLAVOR)) {
				dropTargDragEvent.acceptDrag(dropTargDragEvent.getDropAction());
				setBorder(canDropBorder);
			}
		}

		// Revert to our normal border if the drag moves off us.
		@Override
		public void dragExit(DropTargetEvent dropTargDragEvent) {
			setBorder(normalBorder);
		}

		// This method is called when something is dropped on us:
		@Override
		public void drop(DropTargetDropEvent dropTargetDropEvent) {
			// If a PolyLine is dropped, accept either a COPY or a MOVE.
			if (dropTargetDropEvent.isDataFlavorSupported(TransferablePolyLine.FLAVOR)) {
				dropTargetDropEvent.acceptDrop(dropTargetDropEvent.getDropAction());
			} else {
				// Otherwise, reject the drop and return.
				dropTargetDropEvent.rejectDrop();
				return;
			}

			// Get the dropped object and extract a PolyLine from it
			Transferable transferable = dropTargetDropEvent.getTransferable();
			PolyLine polyLine;
			try {
				polyLine = (PolyLine) transferable.getTransferData(TransferablePolyLine.FLAVOR);
			}
			// UnsupportedFlavor or IOException:
			catch (Exception exception) {
				// Something went wrong, so beep:
				getToolkit().beep();
				// Tell the system we failed:
				dropTargetDropEvent.dropComplete(false);
				return;
			}

			/*
			 * Figure out where the drop occurred, and translate so the point that was
			 * formerly (0,0) is now at that point.
			 */
			Point point = dropTargetDropEvent.getLocation();
			polyLine.translate((float) point.getX(), (float) point.getY());

			// Add the line to our list, and repaint:
			polyLines.add(polyLine);
			repaint();

			/*
			 * Tell the system that we successfully completed the transfer. This means it is
			 * safe for the initiating component to delete its copy of the line.
			 * 
			 */
			dropTargetDropEvent.dropComplete(true);
		}

		// We could provide additional drag under effects with this method.
		@Override
		public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
		}

		// If we used custom cursors, we would update them here.
		@Override
		public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
		}
	};

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch14.datatransfer.TransferableScribblePane", 500, 300);
			System.out.println("Demo for TransferableScribblePane. Launch two copies of the Demo, ");
			System.out.println("Shift-Click, and drag-and-drop your scribbles from one to the other.");
		}
	}
}
