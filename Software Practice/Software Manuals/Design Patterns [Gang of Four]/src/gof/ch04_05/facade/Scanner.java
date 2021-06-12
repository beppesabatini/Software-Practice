package gof.ch04_05.facade;

import java.io.ByteArrayInputStream;

import gof.ch04_05.facade.Subsystem.Token;
import gof.designpatterns.Facade;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 189. Sample
 * code for an illustration of the {@linkplain Facade} design pattern. See the
 * {@linkplain Compiler} for the actual Facade. All the sample code in this
 * illustration is composed of non-functional stubs.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Scanner {

	private ByteArrayInputStream inputStream;

	public Scanner(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
		System.out.println("Initialized input stream to: " + this.inputStream);
	}

	public Token scan() {
		return (new Token());
	}

	public void finalize() {
		// Clean-up before deallocation.
	}
}
