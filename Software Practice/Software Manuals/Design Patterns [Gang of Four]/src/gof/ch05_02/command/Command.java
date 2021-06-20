package gof.ch05_02.command;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 239. One
 * class from the sample code which illustrates the design pattern
 * {@linkplain gof.designpatterns.Command Command}, an executable algorithm
 * encapsulated in an object.
 * <p/>
 * Seen in the Command class is that the constructor and the deconstructor
 * (finalize()) can be defined with reasonable default bodies, which can be
 * overridden later if necessary. There is no meaningful default for the
 * execute() method (which is in the interface), so the compiler will enforce
 * defining the interface version, in any concrete subclasses. The UML Diagram
 * is below.
 * <p/>
 * Note that Command has both an {@linkplain gof.designpatterns.Command
 * interface} (for the mouse-over definition) and an abstract class (for the
 * sample code). Don't get these two confused. This is the abstract version.
 * </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_02/command/UML%20Diagram.jpg"
 * /> </div>
 * 
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
