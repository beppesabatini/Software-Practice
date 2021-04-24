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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import je3.ch11.gui.JPanelDemoable;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 463-467. This JavaBean
 * displays a multi-line message and up to three buttons. It fires an
 * AnswerEvent when the user clicks on one of the buttons. Test it with the Run
 * Configuration YesNoPanelEditable.
 * <p>
 * The YesNoPanel can also be tested by experienced NetBeans users. Generate a
 * jar file using the build.je3.ch15.beans.jar.xml file at the root of the
 * current project. Inside NetBeans, import YesNoPanel from the jar, into a
 * NetBeans project Palette, where the YesNoPanel can act as a reusable
 * drag-and-drop widget.
 */
public class YesNoPanel extends JPanelDemoable {

	private static final long serialVersionUID = 3012401647154213188L;

	/* Properties of the bean. */
	// The message to display:
	private String messageText;
	// The alignment of the message:
	private Alignment alignment;
	// Text for the yes, no, and cancel buttons:
	private String yesLabel;
	private String noLabel;
	private String cancelLabel;

	// Internal components of the panel:
	private MultiLineLabel message;
	private JPanel buttonbox;
	private JButton yes, no, cancel;

	/** The no-argument bean constructor, with default property values */
	public YesNoPanel() {
		this("Your|Message|Here");
	}

	public YesNoPanel(String messageText) {
		this(messageText, Alignment.LEFT, "Yes", "No", "Cancel");
	}

	/** A constructor for programmers using this class "by hand." */
	public YesNoPanel(String messageText, Alignment alignment, String yesLabel, String noLabel, String cancelLabel) {
		// Create the components for this panel:
		setLayout(new BorderLayout(15, 15));

		// Put the message label in the middle of the window.
		message = new MultiLineLabel(messageText, 20, 20, alignment);
		// Allow background color to show through:
		message.setOpaque(false);
		add(message, BorderLayout.CENTER);

		/*
		 * Create a panel for the Panel buttons and put it at the bottom of the Panel.
		 * Specify a FlowLayout layout manager for it.
		 */
		buttonbox = new JPanel();
		buttonbox.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		// Allow background color to show through:
		buttonbox.setOpaque(false);
		add(buttonbox, BorderLayout.SOUTH);

		/*
		 * Create each specified button, specifying the action listener and action
		 * command for each, and adding them to the button box.
		 */
		// Create buttons:
		yes = new JButton();
		no = new JButton();
		cancel = new JButton();
		// Add the buttons to the button box:
		buttonbox.add(yes);
		buttonbox.add(no);
		buttonbox.add(cancel);

		// Register listeners for each button:
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				fireEvent(new AnswerEvent(YesNoPanel.this, AnswerEvent.YES));
			}
		});

		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				fireEvent(new AnswerEvent(YesNoPanel.this, AnswerEvent.NO));
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				fireEvent(new AnswerEvent(YesNoPanel.this, AnswerEvent.CANCEL));
			}
		});

		/*
		 * Now call property setter methods to set the message and button components to
		 * contain the right text.
		 */
		setMessageText(messageText);
		setAlignment(alignment);
		setYesLabel(yesLabel);
		setNoLabel(noLabel);
		setCancelLabel(cancelLabel);
	}

	// Methods to query all of the bean properties.
	public String getMessageText() {
		return messageText;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public String getYesLabel() {
		return yesLabel;
	}

	public String getNoLabel() {
		return noLabel;
	}

	public String getCancelLabel() {
		return cancelLabel;
	}

	public Font getMessageFont() {
		return message.getFont();
	}

	public Color getMessageColor() {
		return message.getForeground();
	}

	public Font getButtonFont() {
		return yes.getFont();
	}

	// Methods to set all of the bean properties.
	public void setMessageText(String messageText) {
		this.messageText = messageText;
		message.setLabel(messageText);
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
		message.setAlignment(alignment);
	}

	public void setYesLabel(String yesLabel) {
		this.yesLabel = yesLabel;
		yes.setText(yesLabel);
		yes.setVisible((yesLabel != null) && (yesLabel.length() > 0));
	}

	public void setNoLabel(String noLabel) {
		this.noLabel = noLabel;
		no.setText(noLabel);
		no.setVisible((noLabel != null) && (noLabel.length() > 0));
	}

	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
		cancel.setText(cancelLabel);
		cancel.setVisible((cancelLabel != null) && (cancelLabel.length() > 0));
	}

	public void setMessageFont(Font font) {
		message.setFont(font);
	}

	public void setMessageColor(Color color) {
		message.setForeground(color);
	}

	public void setButtonFont(Font font) {
		yes.setFont(font);
		no.setFont(font);
		cancel.setFont(font);
	}

	/** This field holds a list of registered ActionListeners. */
	protected List<AnswerListener> listeners = new ArrayList<AnswerListener>();

	/** Register an ActionListener to be notified when a button is pressed */
	public void addAnswerListener(AnswerListener answerListener) {
		listeners.add(answerListener);
	}

	/** Remove an AnswerListener from our list of interested listeners */
	public void removeAnswerListener(AnswerListener answerListener) {
		listeners.remove(answerListener);
	}

	/** Send an event to all registered listeners */
	public void fireEvent(AnswerEvent answerEvent) {
		/*
		 * Make a copy of the list and fire the events using that copy. This means that
		 * listeners can be added or removed from the original list in response to this
		 * event.
		 */
		Object[] copy = listeners.toArray();
		for (int i = 0; i < copy.length; i++) {
			AnswerListener listener = (AnswerListener) copy[i];
			switch (answerEvent.getID()) {
				case AnswerEvent.YES:
					listener.yes(answerEvent);
					break;
				case AnswerEvent.NO:
					listener.no(answerEvent);
					break;
				case AnswerEvent.CANCEL:
					listener.cancel(answerEvent);
					break;
			}
		}
	}

	/** A main method that demonstrates the YesNoPanel class. */
	public static void originalMain(String[] args) {
		// Create an instance of YesNoPanel, with its title and message specified:
		YesNoPanel yesNoPanel = new YesNoPanel("Do you really want to quit?");

		/*
		 * Register an action listener for the Panel. This one just prints the results
		 * out to the console.
		 */
		yesNoPanel.addAnswerListener(new AnswerListener() {
			@Override
			public void yes(AnswerEvent answerEvent) {
				System.out.println("Yes");
			}

			@Override
			public void no(AnswerEvent answerEvent) {
				System.out.println("No");
			}

			@Override
			public void cancel(AnswerEvent answerEvent) {
				System.out.println("Cancel");
			}
		});

		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.getContentPane().add(yesNoPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch15.beans.YesNoPanel", 400, 240);
			System.out.println("Demo for YesNoPanel. Edit it through the Properties Menu.");
			System.out.println("The buttons are non-functional.");
		}
	}
}
