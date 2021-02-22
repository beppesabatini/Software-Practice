package ch11;

import java.util.*;

/**
 * From Learning Java, 3rd Edition, p. 387. The point of this, if any, is that
 * more than one Observer can be associated with an object at one time.
 */
@SuppressWarnings("deprecation")
public class MessageBoard extends Observable {
	private String message;

	public String getMessage() {
		return message;
	}

	public void changeMessage(String message) {
		this.message = message;
		setChanged();
		notifyObservers(message);
	}

	public static void main(String[] args) {
		MessageBoard board = new MessageBoard();
		Student bob = new Student();
		Student joe = new Student();
		board.addObserver(bob);
		board.addObserver(joe);
		board.changeMessage("More Homework!");
	}
}

@SuppressWarnings("deprecation")
class Student implements Observer {
	public void update(Observable o, Object arg) {
		System.out.println("Message board changed: " + arg);
	}
}
