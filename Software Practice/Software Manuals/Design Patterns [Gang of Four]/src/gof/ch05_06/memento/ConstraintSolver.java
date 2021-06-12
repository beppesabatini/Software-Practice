package gof.ch05_06.memento;

import gof.ch04_07.proxy.Graphic;
import gof.designpatterns.Memento;
import gof.designpatterns.Singleton;

/**
 * <table class="javadoc-text">
 * <tr>
 * <td>Adapted from Design Patterns [Gang of Four], pp. 288-289. Part of the
 * sample code illustrating the {@linkplain gof.designpatterns.Memento Memento}
 * design pattern.
 * <p/>
 * Here "Constraint" means the side-effects or complications that arise from a
 * Graphics edit, and the rules that must be followed. Connecting lines between
 * elements need to be redrawn. Colors and transparencies and stacking order may
 * change depending on the context.
 * <p/>
 * A complication in Graphics editing is that decisions on what to display may
 * depend on the order in which elements and edits are added. An "un-do" may not
 * deliver the expected effects. Though the manual does not specifically state
 * so, we may need to change our data model so that all decisions and
 * calculations are captured and stored in a form which can be restored out of
 * context.
 * <p/>
 * As of this writing, Vlissides's original article explaining a
 * ConstraintSolver, which he calls a csolver, is <a href=
 * "http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.95.6572&rep=rep1&type=pdf">on
 * line</a>.</td>
 * </tr>
 * <tr>
 * <td align="center"><img src="UML Diagram.jpg" /></td>
 * </tr>
 * </table>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ConstraintSolver implements Singleton, Memento {
	private static ConstraintSolver instance = null;

	private ConstraintSolver() {
		// This is a Singleton and so should only be instantiated once.
	}

	public static ConstraintSolver getInstance() {
		if (instance == null) {
			instance = new ConstraintSolver();
		}
		return (instance);
	}

	public void solve() {
		/**
		 * Solves the registered constraints. In the case of a Graphics editor, this
		 * means calculate and display the side effects of an edit. Ensure the specified
		 * rules are obeyed. The manual gives the example of drawing connecting lines
		 * between two Graphic elements.
		 */
	}

	public void addConstraint(Graphic startConnection, Graphic endConnection) {
		/**
		 * To use the example in the manual, this means specify that there is a
		 * connecting line between one Graphic and another. In real life you would
		 * probably need more arguments and more detailed rules about drawing the lines.
		 */
	}

	public void removeConstraint(Graphic startConnection, Graphic endConnection) {
		/**
		 * In the example, specify that in the future we don't want to keep drawing that
		 * connecting line.
		 */
	}

	public ConstraintSolver.Memento createMemento() {
		return new ConstraintSolver.Memento();
	}

	public void setMemento(MoveCommand moveCommand, ConstraintSolver.Memento memento) {
		moveCommand.setMemento(memento);
	}

	public class Memento {

		public Memento() {
			/*
			 * Capture the current state of the Constraint Solver, perhaps with a Clone. The
			 * key point is don't reveal any internal details of the ConstraintSolver, don't
			 * violate encapsulation. Nothing should be able to use the Memento except a
			 * ConstraintSolver instance.
			 */
		}

		public void finalize() {
			// Clean-up before deallocation.
		}
	}
}
