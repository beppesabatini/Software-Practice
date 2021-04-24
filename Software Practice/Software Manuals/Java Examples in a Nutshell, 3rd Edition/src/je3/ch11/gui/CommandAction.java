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
package je3.ch11.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import je3.ch09.reflect.Command;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 296-297. This example is
 * used in the ActionParser and the WebBrowser in this chapter, so it can be at
 * least partly tested by testing the WebBrowser.
 */
public class CommandAction extends AbstractAction {

	private static final long serialVersionUID = -6934051524519206898L;

	// The command to execute in response to an ActionEvent.
	Command command;

	/**
	 * Create an Action object that has the various specified attributes, and
	 * invokes the specified Command object in response to ActionEvents.
	 */
	public CommandAction(Command command, String label, Icon icon, String tooltip, KeyStroke accelerator, int mnemonic,
			boolean enabled) {
		// Remember the command to invoke:
		this.command = command;

		// Set the various action attributes with putValue().
		if (label != null) {
			putValue(NAME, label);
		}
		if (icon != null) {
			putValue(SMALL_ICON, icon);
		}
		if (tooltip != null) {
			putValue(SHORT_DESCRIPTION, tooltip);
		}
		if (accelerator != null) {
			putValue(ACCELERATOR_KEY, accelerator);
		}
		if (mnemonic != KeyEvent.VK_UNDEFINED) {
			putValue(MNEMONIC_KEY, mnemonic);
		}

		// Tell the action whether it is currently enabled or not.
		setEnabled(enabled);
	}

	/**
	 * This method implements ActionListener, which is a super-interface of Action.
	 * When a component generates an ActionEvent, it is passed to this method. This
	 * method simply passes it on to the Command object which is also an
	 * ActionListener object
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		command.actionPerformed(actionEvent);
	}

	// These constants are defined by Action in Java 1.3.
	// For compatibility with Java 1.2, we re-define them here.
	public static final String ACCELERATOR_KEY = "AcceleratorKey";
	public static final String MNEMONIC_KEY = "MnemonicKey";
}
