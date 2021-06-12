package gof.ch05_02.command;

import gof.ch05_02.command.CommandSupport.WordProcessingDocument;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 240-241. Sample code to illustrate the {@linkplain gof.designpatterns.Command
 * Command} design pattern. In the manual, this is implemented as a C++ template
 * with a pointer to a function. Below the pointer to a function is implemented
 * with a lambda (λ)! See {@linkplain WordProcessingFunction}.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
