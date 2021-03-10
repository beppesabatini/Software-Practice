package hackerrank;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a
 * https://www.hackerrank.com/challenges/ctci-linked-list-cycle/problem">Linked
 * Lists: Detect a Cycle</a> problem. One way to test the solution is to mouse
 * all the code inside the outermost class definition into HackerRank.
 * <p/>
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class LinkedListCycleDetector {

	private static final String DEBUG = "false";

	/**
	 * This function is the solution to the practice problem "Linked Lists: Detect a
	 * Cycle" The complete problem is posted on the HackerRank website (linked to
	 * below) and this solution can be tested there.
	 * <p>
	 * One of the standard linked lists questions, the goal is to determine if the
	 * specified linked list links back into itself at some point, forming a cycle.
	 * Two pointers travel through the list, one going twice as fast as the other.
	 * If the hare, the faster pointer, catches up to the tortoise, the slower
	 * pointer, the hare has traveled in a loop and the method returns true.
	 * <p>
	 * The hare will travel two loops while the tortoise travels one, so for a loop
	 * of size N the runtime is O(3N).
	 * 
	 * @param head The starting node of the list being inspected.
	 * @return boolean "true" if the list has a cycle, "false" if it does not.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-linked-list-cycle/problem">Original HackerRank Problem</a>
	 */
	public boolean hasCycle(Node head) {
		if (head == null) {
			return (false);
		}
		Node tortoise = head;
		Node hare = head;

		while (tortoise.next != null && hare.next != null && hare.next.next != null) {
			tortoise = tortoise.next;
			hare = hare.next.next;
			if (tortoise == hare) {
				if (Boolean.valueOf(DEBUG) == true) {
					System.out.println("Tortoise node: " + tortoise.data);
					System.out.println("Hare node: " + hare.data);
				}
				return (true);
			}
		}
		return (false);
	}

	public class Node {
		public int data;
		public Node next;

		public String toString(int maxListSize) {
			String nodeString = "";
			nodeString += this.data;

			Node nodeWalker = this;
			if (nodeWalker.next == null) {
				nodeString += " --> null";
				return (nodeString);
			}
			int i = 0;
			do {
				nodeString += " --> ";
				nodeString += nodeWalker.next.data;
				nodeWalker = nodeWalker.next;
				if (i++ > maxListSize) {
					nodeString += "... <interruputed>";
					return (nodeString);
				}
			} while (nodeWalker.next != null);

			nodeString += " --> null";
			return (nodeString);
		}
	}
}
