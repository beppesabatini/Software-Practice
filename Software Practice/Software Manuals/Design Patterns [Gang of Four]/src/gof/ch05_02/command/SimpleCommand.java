package gof.ch05_02.command;

import gof.ch05_02.command.CommandSupport.WordProcessingDocument;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 240-241. Sample code to illustrate the {@linkplain gof.designpatterns.Command
 * Command} design pattern.
 * <p/>
 * Unlike the OpenCommand and the PasteCommand, this SimpleCommand has been
 * parameterized so that it can be used to construct any number of Commands. In
 * this example, the logic for five different Commands is stored in the enum
 * {@linkplain WordProcessingFunction}.
 * <p/>
 * In the manual, this is implemented as a C++ template with a pointer to a
 * function. Below, the pointer to a function is implemented with a lambda (λ),
 * the logic stored in the WordProcessingFunction enum.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_02/command/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class SimpleCommand extends Command implements gof.designpatterns.Command {

	private WordProcessingDocument wordProcessingDocument;
	private WordProcessingFunction wordProcessingFunction;

	public SimpleCommand(WordProcessingDocument document, WordProcessingFunction function) {
		this.wordProcessingDocument = document;
		this.wordProcessingFunction = function;
	}

	@Override
	public void execute() {
		wordProcessingFunction.execute(wordProcessingDocument);
	}

}
