package hackerrank;

import java.util.*;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a
 * https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=stacks-queues">Queues:
 * A Tale of Two Stacks</a> problem. One way to test the solution is to mouse
 * all the code inside the outermost class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class DoubleStackQueue {

	private static String DEBUG = "false";

	public static boolean getDebug() {
		return (Boolean.valueOf(DEBUG));
	}

	public static void setDebug(boolean debugValue) {
		DEBUG = String.valueOf(debugValue);
	}

	private static enum Operation {
		PUSH, POP, PEEK
	};

	/**
	 * This function is the solution to the practice problem "Queues: A Tale of Two
	 * Stacks." The complete problem is posted on the HackerRank website (linked to
	 * below) and this solution can be tested there.
	 * <p/>
	 * The algorithm is easy to understand. Imagine you have two cans of Pringles
	 * potato chips--one can is half-full, and one is empty. So you have two stacks,
	 * in this case two stacks of potato chips. How would you implement a queue with
	 * two such stacks? To push a new chip onto the rear of the queue, drop a chip
	 * onto the top of the half-full can. To pop a chip off the front of the queue,
	 * pour the half-full can into the empty can, and take the top chip from the
	 * second can, that is, take it from top of the the no-longer-empty can, which
	 * is now the front of the queue. To peek at what's going to pop off next, peek
	 * into that same second can while you take that chip out. When you have all the
	 * chips and info you need from the second can, pour the chips back into the
	 * first can, and you're back to the beginning state, and ready for more
	 * queries. 
	 * <p/>
	 * For pushing (dropping one chip in), the runtime is constant, and O(1).
	 * Popping involves pouring all of the chips into the second can, and then
	 * pouring almost all of them back again into the first can, so the runtime is
	 * linear, or O(2N).
	 * 
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=stacks-queues">Original
	 *      HackerRank Problem</a>
	 */
	public class MyQueue<T> {

		private Stack<T> stack01 = new Stack<T>(); // Half-full in the general case
		private Stack<T> stack02 = new Stack<T>(); // Empty in the general case

		public void enqueue(T testInteger) {
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.printf("Enqueing (pushing): %s\n", testInteger);
			}
			stack01.push(testInteger);
		}

		public T dequeue() {
			T returnValue = nextOnQueue(Operation.POP);
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.printf("Dequeing (popping): %s\n", returnValue);
			}
			return (returnValue);
		}

		public T peek() {
			T returnValue = nextOnQueue(Operation.PEEK);
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.printf("Peeking (next on the queue): %s\n", returnValue);
			}
			return (returnValue);
		}

		private T nextOnQueue(Operation operation) {
			while (stack01.isEmpty() == false) {
				stack02.push(stack01.pop());
			}
			T returnValue = null;
			switch (operation) {
			case PUSH:
				throw new IllegalArgumentException();
			case POP:
				returnValue = stack02.pop();
				break;
			case PEEK:
				returnValue = stack02.peek();
				break;
			default:
				break;
			}
			while (stack02.isEmpty() == false) {
				stack01.push(stack02.pop());
			}
			return (returnValue);
		}
	}
}