package gof.ch05_02.command;

import gof.ch05_02.command.CommandSupport.WordProcessingDocument;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 239-241. This class does not appear in the manual. This is used by the sample
 * code to illustrate the {@linkplain gof.designpatterns.Command Command} design
 * pattern. The manual uses C++ and invokes a pointer to a function; here
 * instead, we define a lambda (λ) that invokes the desired function, and store
 * the lambda in the corresponding enum. This enum can then be used to
 * initialize up to five different Command objects. </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_02/command/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public enum WordProcessingFunction {

	OPEN((document) -> {
		document.open();
	}), //
	PASTE((document) -> {
		document.paste();
	}), //
	SPELL_CHECKER((document) -> {
		document.spellCheck();
	}), //
	TABLE_CONVERTER((document) -> {
		document.convertToTable();
	}), //
	PAGE_FORMATTER((document) -> {
		document.formatPages();
	});

	private WordProcessingLambda lambda;

	private WordProcessingFunction(WordProcessingLambda lambda) {
		this.lambda = lambda;
	}

	public void execute(WordProcessingDocument document) {
		this.lambda.execute(document);
	}

	public interface WordProcessingLambda {
		public void execute(WordProcessingDocument document);
	}
}
