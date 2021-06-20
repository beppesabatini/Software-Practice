package gof.ch05_05.mediator;

import java.util.Arrays;
import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 279-280. Part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Mediator Mediator} design pattern.
 * <p/>
 * A Mediator is like the moderator in a chat room. In the example here, the
 * FontDialogDirector is the Mediator, acting like Control Central, to
 * coordinate different widgets in a Font Select dialog.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_05/mediator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class FontDialogDirector extends DialogDirector implements Mediator {

	private Button okButton;
	private Button cancelButton;
	private ListBox fontList;
	private EntryField fontName;
	private String[] allFonts = { "Arial", "Courier", "Times New Roman" };

	public FontDialogDirector() {
		System.out.println(" -- Initializing the FontDialogDirector");
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public ListBox getFontList() {
		return fontList;
	}

	public EntryField getFontName() {
		return fontName;
	}

	@Override
	protected void createWidgets() {
		okButton = new Button(this);
		cancelButton = new Button(this);
		fontList = new ListBox(this);
		fontName = new EntryField(this);

		okButton.setText("OK");
		cancelButton.setText("Cancel");
		fontList.setList(Arrays.asList(allFonts));
		fontName.setText("Enter font name here");

		// Assemble the widgets in the Font Select dialog
	}

	@Override
	public void widgetChanged(Widget theChangedWidget) {
		if (theChangedWidget == this.fontList) {
			fontName.setText(fontList.getSelection());
		} else if (theChangedWidget == this.okButton) {
			System.out.println(" -- Mediator applying the font change");
			System.out.println(" -- Mediator dismissing the font dialog");
		} else if (theChangedWidget == this.cancelButton) {
			System.out.println(" -- Mediator dismissing the font dialog");
		}
	}

	public void finalize() {
		System.out.println(" -- In finalize(), nulling out the font list");
		this.fontList = null;
	}
}
