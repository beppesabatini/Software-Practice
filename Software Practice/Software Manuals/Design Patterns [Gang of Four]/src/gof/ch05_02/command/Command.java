package gof.ch05_02.command;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 239. Sample
 * code to illustrate the design pattern {@linkplain gof.designpatterns.Command
 * Command}. Note that Command has both an
 * {@linkplain gof.designpatterns.Command interface} (for the pop-up definition)
 * and an abstract class (for the sample code). Don't get these two confused.
 * This is the abstract version.
 * <p/>
 * Seen here is that the constructor and the deconstructor (finalize()) can be
 * defined with reasonable default bodies, which can be overridden later if
 * necessary. There is no meaningful default for the execute() method (which is
 * in the interface), so the compiler will enforce defining the interface
 * version, in any concrete subclasses. The UML Diagram is below.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css"> s
 */
public abstract class Command implements gof.designpatterns.Command {

	protected Command() {
		// Initialization code here.
	}

	public void finalize() {
		// Clean-up before deallocation.
	}
}
