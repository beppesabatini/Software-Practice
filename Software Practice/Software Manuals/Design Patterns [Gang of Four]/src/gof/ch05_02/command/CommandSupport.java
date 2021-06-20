package gof.ch05_02.command;

import java.util.HashMap;
import java.util.Map;

/**
 * <div class="javadoc-text">More non-functional stubs. Most of these classes
 * are only mentioned in passing in the manual. These are included here
 * primarily to get other stubs to compile.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class CommandSupport {

	public static class Application {
		private Document document;

		public void addDocument(Document document) {
			this.document = document;
		}

		public Document getDocument() {
			return (this.document);
		}
	}

	public static class Document implements WordProcessingDocument {

		private String documentName;

		public Document(String documentName) {
			// Stub
			this.documentName = documentName;
			System.out.println("Initialized document: " + this.documentName);
		}

		public void open() {
			System.out.println("Opening document: " + getDocumentName());
		}

		public void paste() {
			System.out.println("Pasting to document: " + getDocumentName());
		}

		public void spellCheck() {
			System.out.println("No spelling errors found");
		}

		public void convertToTable() {
			System.out.println("Document converted to table");
		}

		public void formatPages() {
			System.out.println("Pages have been reformatted");
		}

		public String getDocumentName() {
			return (this.documentName);
		}
	}

	public static class Spreadsheet implements WordProcessingDocument {

		private String spreadsheetName;

		public Spreadsheet(String spreadsheetName) {
			// Stub
			this.spreadsheetName = spreadsheetName;
			System.out.println("Initialized spreadsheet: " + this.spreadsheetName);
		}

		public void open() {
			System.out.println("Opening spreadsheet: " + getSpreadsheetName());
		}

		public void paste() {
			System.out.println("Pasting to spreadsheet: " + getSpreadsheetName());
		}

		public void spellCheck() {
			System.out.println("No spelling errors found in spreadsheet");
		}

		public void convertToTable() {
			System.out.println("Spreadsheet converted to table");
		}

		public void formatPages() {
			System.out.println("Spreadsheet pages have been reformatted");
		}

		public String getSpreadsheetName() {
			return (this.spreadsheetName);
		}
	}

	public interface WordProcessingDocument {
		public void open();

		public void paste();

		public void spellCheck();

		public void convertToTable();

		public void formatPages();
	}

	public static class MenuItem {

		private Command command;

		public MenuItem(Command command) {
			this.command = command;
		}

		public void execute() {
			this.command.execute();
		}
	}

	public static class Menu {
		private Map<WordProcessingFunction, Command> menuItems;

		public Menu() {
			menuItems = new HashMap<WordProcessingFunction, Command>();
		}

		public void put(WordProcessingFunction function, Command command) {
			this.menuItems.put(function, command);
		}

		public void execute(WordProcessingFunction function) {
			Command command = this.menuItems.get(function);
			command.execute();
		}
	}

}
