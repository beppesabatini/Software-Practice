package gof.ch05_02.command;

import gof.ch05_02.command.CommandSupport.Application;
import gof.ch05_02.command.CommandSupport.Document;
import gof.ch05_02.command.CommandSupport.Menu;
import gof.ch05_02.command.CommandSupport.Spreadsheet;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 239-241. This class does not appear in the manual. This tests the sample code
 * which illustrates the {@linkplain gof.designpatterns.Command Command} design
 * pattern.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {
	public static void main(String[] args) {

		System.out.println("--- Using dedicated commands: ---");
		Menu demoMenu = new Menu();
		Application demoApplication = new Application();
		demoMenu.put(WordProcessingFunction.OPEN, new OpenCommand(demoApplication));
		demoMenu.execute(WordProcessingFunction.OPEN);

		Document demoDocument = demoApplication.getDocument();
		demoMenu.put(WordProcessingFunction.PASTE, new PasteCommand(demoDocument));
		demoMenu.execute(WordProcessingFunction.PASTE);
		System.out.println();

		System.out.println("--- Using Simple commands: ---");
		for (WordProcessingFunction function : WordProcessingFunction.values()) {
			SimpleCommand simpleCommand = new SimpleCommand(demoDocument, function);
			demoMenu.put(function, simpleCommand);
			demoMenu.execute(function);
		}
		System.out.println();

		System.out.println("--- Testing with a Spreadsheet: ---");
		Spreadsheet demoSpreadsheet = new Spreadsheet("demoSpreadsheet");
		for (WordProcessingFunction function : WordProcessingFunction.values()) {
			SimpleCommand simpleCommand = new SimpleCommand(demoSpreadsheet, function);
			demoMenu.put(function, simpleCommand);
			demoMenu.execute(function);
		}
		System.out.println();

		System.out.println("--- Testing with a MacroCommand: ---");
		MacroCommand macroCommand = new MacroCommand();
		for (WordProcessingFunction function : WordProcessingFunction.values()) {
			macroCommand.add(new SimpleCommand(demoDocument, function));
		}
		macroCommand.execute();
	}
}
