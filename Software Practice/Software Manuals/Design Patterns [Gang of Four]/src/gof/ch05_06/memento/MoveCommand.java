package gof.ch05_06.memento;

import gof.ch02_02.structure.Point;
import gof.ch04_07.proxy.Graphic;
import gof.designpatterns.Command;
import gof.designpatterns.Memento;

/**
 * <table class="javadoc-text">
 * <tr>
 * <td>Adapted from Design Patterns [Gang of Four], p. 288. Part of the sample
 * code illustrating the {@linkplain Memento} design pattern.
 * <p/>
 * The goal for MoveCommand is to relocate a Graphic in a manner which can be
 * undone and then redone. We want to do this by saving the state and the
 * context of the Graphic in Memento objects, which must be secure, and which
 * must not violate encapsulation.
 * <p/>
 * These days many desktop programs maintain a two-ended queue of about twenty
 * states, that is, twenty checkpoints, and allow the user to travel back and
 * forth across the queue of states. On Windows, by convention, the shortcut for
 * Undo is Control-Z, and the shortcut for Redo is Control-Y. The user should be
 * able to Control-Z and Control-Y back and forth between states indefinitely.
 * Before the initial edit, the system pops the original state onto the front
 * end of the queue. When the length of the queue goes over twenty, it pops off
 * and discards states from the most remote end. If the size of the saved states
 * becomes very large, the length of the queue may be shortened. (None of this
 * is mentioned in the manual.)
 * <p/>
 * For further research, as of this writing, a nice alternate version of the
 * Memento pattern (with more implementation details) can be found on the
 * <a href="https://refactoring.guru/design-patterns/memento">internet</a>.</td>
 * </tr>
 * <tr>
 * <td><img src="UML Diagram.jpg" /></td>
 * </tr>
 * </table>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MoveCommand implements Command, Memento {

	// This should probably be a two-headed queue of Memento objects:
	private ConstraintSolver.Memento state;
	// Called "delta" in the manual:
	private Point movingVector;
	// Called "target" in the manual:
	private Graphic graphicToMove;

	/**
	 * Implement a MoveCommand which can be undone and redone. Save its various
	 * states (its checkpoints), in Memento objects, which should be secure and
	 * which must not violate encapsulation.
	 * 
	 * @param movingVector  The entire Graphic should be relocated by this vector
	 * @param graphicToMove The target Graphic being moved
	 */
	public MoveCommand(final Point movingVector, Graphic graphicToMove) {
		this.movingVector = movingVector;
		this.graphicToMove = graphicToMove;
	}

	public ConstraintSolver.Memento getMemento() {
		return this.state;
	}

	public void setMemento(ConstraintSolver.Memento memento) {
		this.state = memento;
	}

	/*
	 * Save the current state before executing the MoveCommand. It's not exactly the
	 * state of the Graphic that's being moved, it's the state of all the side
	 * effects and calculations that go along with the move.
	 */
	public void execute() {
		ConstraintSolver constraintSolver = ConstraintSolver.getInstance();
		this.state = constraintSolver.new Memento();
		this.graphicToMove.move(movingVector);
		constraintSolver.solve();
	}

	/* Undo the most recent MoveCommand. */
	public void unexecute() {
		float deltaX = movingVector.getPointX();
		float deltaY = movingVector.getPointY();
		Point undoMoveVector = new Point(-deltaX, -deltaY);
		this.graphicToMove.move(undoMoveVector);
		ConstraintSolver constraintSolver = ConstraintSolver.getInstance();
		constraintSolver.solve();
	}
}
