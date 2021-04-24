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

import java.beans.PropertyEditorSupport;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 472. This PropertyEditor
 * defines the enumerated values of the alignment property so that a bean box or
 * IDE can present those values to the user for selection.
 * <p/>
 * This Editor seems to be used in NetBeans and nowhere else, perhaps not even
 * there. Whichever the case, the YesOrNo drag-and-drop widget is working
 * correctly in NetBeans.
 * 
 */
public class AlignmentEditor extends PropertyEditorSupport {
	/** Return the list of value names for the enumerated type. */
	public String[] getTags() {
		return new String[] { "Left", "Center", "Right" };
	}

	/** Convert each of those value names into the actual value. */
	public void setAsText(String s) {
		if (s.equals("Left")) {
			setValue(Alignment.LEFT);
		} else if (s.equals("Center")) {
			setValue(Alignment.CENTER);
		} else if (s.equals("Right")) {
			setValue(Alignment.RIGHT);
		} else {
			throw new IllegalArgumentException(s);
		}
	}

	/** This is an important method for code generation. */
	public String getJavaInitializationString() {
		Object object = getValue();
		if (object == Alignment.LEFT) {
			return "je3.ch15.beans.Alignment.LEFT";
		}
		if (object == Alignment.CENTER) {
			return "je3.ch15.beans.Alignment.CENTER";
		}
		if (object == Alignment.RIGHT) {
			return "je3.ch15.beans.Alignment.RIGHT";
		}
		return null;
	}
}
