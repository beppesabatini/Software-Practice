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

import java.util.EventObject;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 467. The YesNoPanel class
 * fires an event of this type when the user clicks one of its buttons. The id
 * field specifies which button the user pressed. Used by the {@link YesNoPanel} class.
 */
public class AnswerEvent extends EventObject {

	private static final long serialVersionUID = 3956160301617294986L;

	// Button Constants:
	public static final int YES = 0;
	public static final int NO = 1;
	public static final int CANCEL = 2;

	// Which button was pressed?
	protected int id;

	public AnswerEvent(Object source, int id) {
		super(source);
		this.id = id;
	}

	// Return the button ID:
	public int getID() {
		return id;
	}
}
