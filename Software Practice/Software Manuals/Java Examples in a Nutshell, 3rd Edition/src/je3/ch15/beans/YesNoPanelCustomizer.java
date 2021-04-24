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
package je3.ch15.beans;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 475-477. This class is a
 * customizer for the YesNoPanel bean. It displays a JTextArea and three
 * JTextFields where the user can enter the main message and the labels for each
 * of the three buttons. It does not allow the alignment property to be set.
 * <p/>
 * This class can only be used in NetBeans or similar GUI tools, with the
 * drag-and-drop version of the YesNoPanel. Testers can bring it up by
 * right-clicking on the displayed version of the widget, and entering
 * "Customize." The Customizer works to a degree, but still requires the
 * pipe-separated version of the Multi-Line Label (a workaround originated in
 * this installation).
 */
public class YesNoPanelCustomizer extends JComponent implements Customizer, DocumentListener {

	private static final long serialVersionUID = 5680913380431233312L;

	// The bean being customized:
	private YesNoPanel yesNoPanel;
	// For entering the message:
	private JTextArea message;
	// For entering button text:
	private JTextField textFields[];

	/*
	 * The bean box calls this method to tell us what object to customize. This
	 * method will always be called before the customizer is displayed, so it is
	 * safe to create the customizer GUI here.
	 */
	@Override
	public void setObject(Object object) {
		// Save the object we're customizing:
		yesNoPanel = (YesNoPanel) object;

		// Put a label at the top of the panel:
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Enter the message to appear in the panel:"), BorderLayout.NORTH);

		// ...and, a big text area below it, for entering the message.
		message = new JTextArea(yesNoPanel.getMessageText(), 5, 35);
		message.getDocument().addDocumentListener(this);
		this.add(new JScrollPane(message), "Center");

		/* Then add a row of text fields for entering the button labels. */
		// The row container:
		JPanel buttonbox = new JPanel();
		// Equally spaced:
		buttonbox.setLayout(new GridLayout(1, 0, 25, 10));
		// Put row on bottom:
		this.add(buttonbox, BorderLayout.SOUTH);

		/*
		 * Now go create three JTextFields to put in this row. But actually position a
		 * JLabel above each, so create an container for each JTextField+JLabel
		 * combination.
		 */
		// Array of TextFields:
		textFields = new JTextField[3];
		// Labels for each:
		String[] labels = new String[] { "Yes Button Label", "No Button Label", "Cancel Button Label" };
		// Initial values of each:
		String[] values = new String[] { yesNoPanel.getYesLabel(), yesNoPanel.getNoLabel(),
				yesNoPanel.getCancelLabel() };
		for (int i = 0; i < 3; i++) {
			// Create a container:
			JPanel jPanel = new JPanel();
			// Give it a BorderLayout:
			jPanel.setLayout(new BorderLayout());
			// Put a label on the top:
			jPanel.add(new JLabel(labels[i]), "North");
			// Create the text field:
			textFields[i] = new JTextField(values[i]);
			// Put it below the label:
			jPanel.add(textFields[i], "Center");
			// Add container to row:
			buttonbox.add(jPanel);
			// Register listener for the JTextField:
			textFields[i].getDocument().addDocumentListener(this);
		}
	}

	// Give ourselves some space around the outside of the panel.
	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}

	/*
	 * These are the method defined by the DocumentListener interface. Whenever the
	 * user types a character in the JTextArea or JTextFields, they will be called.
	 * They all just call the internal method update() below.
	 */
	@Override
	public void changedUpdate(DocumentEvent documentEvent) {
		update(documentEvent);
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) {
		update(documentEvent);
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) {
		update(documentEvent);
	}

	/*
	 * This updates the appropriate property of the bean, and fires a property
	 * changed event, as all customizers are required to do. Note that we are not
	 * required to fire an event for every keystroke.
	 */
	void update(DocumentEvent documentEvent) {
		// What document was updated?
		Document document = documentEvent.getDocument();
		if (document == message.getDocument())
			yesNoPanel.setMessageText(message.getText());
		else if (document == textFields[0].getDocument()) {
			yesNoPanel.setYesLabel(textFields[0].getText());
		} else if (document == textFields[1].getDocument()) {
			yesNoPanel.setNoLabel(textFields[1].getText());
		} else if (document == textFields[2].getDocument()) {
			yesNoPanel.setCancelLabel(textFields[2].getText());
		}
		listeners.firePropertyChange(null, null, null);
	}

	/*
	 * This code uses the PropertyChangeSupport class to maintain a list of
	 * listeners interested in the edits we make to the bean.
	 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		listeners.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		listeners.removePropertyChangeListener(propertyChangeListener);
	}
}
