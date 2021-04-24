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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyEditor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 473-474. This class is a
 * custom editor for the messageText property of the YesNoPanel bean. It is
 * necessary because the default editor for properties of type String does not
 * allow multi-line strings to be entered.
 * <p/>
 * So says the manual, but in fact this version doesn't work either. It still
 * requires the pipe-separated version of the MultiLineLabel (a recent
 * workaround in the updated 2021 codebase).
 */
public class YesNoPanelMessageEditor implements PropertyEditor {
	// The value we will be editing:
	private String value;

	public void setValue(Object object) {
		this.value = (String) object;
	}

	public Object getValue() {
		return this.value;
	}

	public void setAsText(String string) {
		this.value = string;
	}

	public String getAsText() {
		return this.value;
	}

	// Not enumerated; no tags.
	public String[] getTags() {
		return null;
	}

	// Specify that we allow custom editing.
	public boolean supportsCustomEditor() {
		return true;
	}

	/*
	 * Return the custom editor. This just creates and returns a TextArea wrapped in
	 * a JScrollPane to edit the multi-line text. But it also registers a listener
	 * on the text area to update the value as the user types and to fire the
	 * property change events that property editors are required to fire.
	 */
	public Component getCustomEditor() {
		// This will be 5 rows and 30 columns.
		final JTextArea jTextArea = new JTextArea(value, 5, 30);
		jTextArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				update();
			}

			public void update() {
				value = jTextArea.getText();
				listeners.firePropertyChange(null, null, null);
			}
		});

		return new JScrollPane(jTextArea);
	}

	/*
	 * Visual display of the value, for use with the custom editor. Just print some
	 * instructions and hope they fit in the in the box. This could be more
	 * sophisticated.
	 */
	public boolean isPaintable() {
		return true;
	}

	public void paintValue(Graphics graphics, Rectangle rectangle) {
		graphics.setClip(rectangle);
		graphics.drawString("Click to edit...", rectangle.x + 5, rectangle.y + 15);
	}

	/*
	 * Important method for code generators. Note that it really ought to escape any
	 * quotes or backslashes in value before returning the string.
	 */
	public String getJavaInitializationString() {
		return "\"" + value + "\"";
	}

	/*
	 * This code uses the PropertyChangeSupport class to maintain a list of
	 * listeners interested in the edits we make to the value.
	 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		listeners.addPropertyChangeListener(propertyChangeListener);
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		listeners.removePropertyChangeListener(propertyChangeListener);
	}

	public static class Demo {
		public static void main(String[] args) {
			YesNoPanel.Demo.main(args);
			System.out.print("Demo for YesNoPanelMessageEditor in the YesNoPanel.");
			System.out.print("Click on \"Properties\" and then \"messageText\".");
		}
	}
}
