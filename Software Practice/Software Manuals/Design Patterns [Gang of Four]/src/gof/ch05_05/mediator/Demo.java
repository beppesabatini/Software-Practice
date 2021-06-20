package gof.ch05_05.mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 278-281. Part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Mediator Mediator} design pattern. This Demo
 * class does not appear in the manual.
 * <p/>
 * A Mediator is like the moderator in a chat room. In the example here, the
 * Mediator acts like Control Central, to coordinate different widgets in a Font
 * Select dialog.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	public static void main(String[] args) {

		System.out.println(" • Initializing the FontDialogDirector (the Mediator): ");
		FontDialogDirector fontDialogDirector = new FontDialogDirector();
		System.out.println();

		System.out.println(" • Mediator creating widgets: ");
		fontDialogDirector.createWidgets();
		System.out.println();

		System.out.println(" • User selects a font: ");
		ListBox fontList = fontDialogDirector.getFontList();
		fontList.getSelection();
		System.out.println();

		System.out.println(" • User clicks Cancel button: ");
		Button cancelButton = fontDialogDirector.getCancelButton();
		cancelButton.handleMouse(null);
		System.out.println();

		System.out.println(" • User selects a font: ");
		fontList = fontDialogDirector.getFontList();
		fontList.getSelection();
		System.out.println();

		System.out.println(" • User clicks OK button: ");
		Button okButton = fontDialogDirector.getOkButton();
		okButton.handleMouse(null);
		System.out.println();

		System.out.println(" • Deallocating the FontDialogDirector. Note this is NOT called automatically: ");
		fontDialogDirector.finalize();
		fontDialogDirector = null;
	}

}
