package gof.ch04_05.facade;

import java.io.ByteArrayInputStream;
import gof.ch04_05.facade.FacadeSupport.Token;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 189. Sample
 * code for an illustration of the {@linkplain gof.designpatterns.Facade Facade}
 * design pattern. See the {@linkplain Compiler} for the actual Facade.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_05/facade/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Scanner {

	private ByteArrayInputStream inputStream;

	public Scanner(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
		System.out.println("Initialized input stream to: " + this.inputStream);
	}

	public Token scan() {
		// Stub
		return (new Token());
	}

	public void finalize() {
		// Clean-up before deallocation.
	}
}
