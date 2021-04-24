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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.border.LineBorder;

import je3.ch11.gui.ShowBean;
import je3.ch15.beans.Bean;

import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.TransferHandler;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 441-443. A custom Swing
 * component that displays a simple digital clock. This demonstrates how to add
 * copy and drag support to a Swing component with TransferHandler.
 * <p>
 * In the current installation, a user can launch two instances of the
 * DigitalClock application by running its run configuration twice. Click on the
 * text field tab in one of the two, and drag the time value from one instance
 * to the the text field of the second one. Control-C (copy) and Control-V
 * (paste) should work also.
 */
public class DigitalClock extends JLabel {
	private static final long serialVersionUID = -2613674050651463183L;

	// How to display the time in string form:
	DateFormat format;
	// How often to update the time (in milliseconds):
	int updateFrequency;
	// Triggers repeated updates to the clock:
	Timer timer;

	public DigitalClock() {

		// Set default values for our properties.
		setFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM, getLocale()));
		// Update once a second:
		setUpdateFrequency(1000);

		/*
		 * Specify a Swing TransferHandler object to do the dirty work of copy-and-paste
		 * and drag-and-drop for us. This one will transfer the value of the "time"
		 * property. Since this property is read-only it will allow drags but not drops.
		 */
		setTransferHandler(new TransferHandler("time"));

		/*
		 * // Since JLabel does not normally support drag-and-drop, we need an // event
		 * handler to detect a drag and start the transfer.
		 */
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				getTransferHandler().exportAsDrag(DigitalClock.this, e, TransferHandler.COPY);
			}
		});

		/*
		 * Before we can have a keyboard binding for a Copy command, the component needs
		 * to be able to accept keyboard focus.
		 */
		setFocusable(true);

		// Request focus when we're clicked on.
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocus();
			}
		});
		// Use a LineBorder to indicate when we've got the keyboard focus.
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				setBorder(LineBorder.createBlackLineBorder());
			}

			@Override
			public void focusLost(FocusEvent e) {
				setBorder(null);
			}
		});

		// Now bind the Ctrl-C keystroke to a "Copy" command.
		InputMap inputMap = new InputMap();
		inputMap.setParent(getInputMap(WHEN_FOCUSED));
		@SuppressWarnings("deprecation")
		int controlMask = InputEvent.CTRL_MASK;
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, controlMask), "Copy");
		setInputMap(WHEN_FOCUSED, inputMap);

		/*
		 * ...and, bind the "Copy" command to a pre-defined Action that performs a copy
		 * using the TransferHandler we've installed.
		 */
		ActionMap actionMap = new ActionMap();
		actionMap.setParent(getActionMap());
		actionMap.put("Copy", TransferHandler.getCopyAction());
		setActionMap(actionMap);

		/*
		 * Create a javax.swing.Timer object that will generate ActionEvents to tell us
		 * when to update the displayed time. Every updateFrequency milliseconds, this
		 * timer will cause the actionPerformed() method to be invoked. (For non-GUI
		 * applications, see java.util.Timer.)
		 */
		timer = new Timer(updateFrequency, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set label to current time string:
				setText(getTime());
			}
		});
		// Do the first update immediately:
		timer.setInitialDelay(0);
		// Start timing now!
		timer.start();
	}

	/*
	 * Return the current time as a String. This is the property accessor method
	 * used by the TransferHandler. Since there is a getter, but no setter, the
	 * TransferHandler will reject any attempts to drop data on us.
	 */
	public String getTime() {
		// Use the DateFormat object to convert current time to a string.
		return format.format(new Date());
	}

	// Here are two related property setter methods.
	public void setFormat(DateFormat format) {
		this.format = format;
	}

	public void setUpdateFrequency(int ms) {
		this.updateFrequency = ms;
	}

	public static class Demo {
		public static void main(String[] args) {
			System.out.println("Demo for DigitalClock.");
			String instructions = "";
			instructions += "Launch two Demos, and drag-and-drop the Time from one ";
			instructions += "into the Text Field of the other.";
			System.out.println(instructions);

			List<Bean> beansToShow = new ArrayList<Bean>();
			try {
				Bean clockBean = Bean.forClassName("je3.ch14.datatransfer.DigitalClock", false);
				beansToShow.add(clockBean);
				Bean textFieldBean = Bean.forClassName("javax.swing.JTextField", false);
				beansToShow.add(textFieldBean);
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
			ShowBean showBean = new ShowBean(beansToShow);
			showBean.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Dimension dimension = new Dimension(300, 200);
			showBean.setPreferredSize(dimension);
			showBean.pack();
			showBean.setVisible(true);
		}
	}
}
