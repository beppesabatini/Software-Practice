package gof.ch04_05.facade;

import java.io.ByteArrayInputStream;
// import java.io.ByteArrayOutputStream;

import gof.ch04_05.facade.FacadeSupport.Parser;
import gof.ch04_05.facade.FacadeSupport.RISCCodeGenerator;
import gof.designpatterns.Facade;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 191. Sample code to illustrate the {@linkplain gof.designpatterns.Facade
 * Facade} design pattern. The thinking, as explained in the manual, is that
 * most users will not care about internal details of the Compiler, such as the
 * parser and scanner and the code generator. Providing this Facade simplifies
 * use for most users, while still allowing access to system details for those
 * who need it.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_05/facade/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Compiler implements Facade {

	private Scanner scanner;
	private ProgramNodeBuilder programNodeBuilder;
	private Parser parser;
	RISCCodeGenerator codeGenerator;

	public Compiler(ByteArrayInputStream inputStream) {
		this.scanner = new Scanner(inputStream);
		this.programNodeBuilder = new ProgramNodeBuilder();
		this.parser = new Parser();
		this.codeGenerator = new RISCCodeGenerator(inputStream);
	}

	public void compile() {
		parser.parse(scanner, programNodeBuilder);
		ProgramNode parseTree = programNodeBuilder.getRootNode();
		parseTree.traverse(codeGenerator);
	}
}
