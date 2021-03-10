package hackerrank.launchers;

import hackerrank.LinkedListCycleDetector;
import hackerrank.LinkedListCycleDetector.Node;

/**
 * This class launches the author's solution to the HackerRank "Linked Lists:
 * Detect a Cycle" problem. Test data, monitoring code, logging code, debug
 * output, and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class LinkedListCycleDetectorLauncher {

	private static Node testList00 = initTestList(5, 3, null);
	private static Node testList01 = initTestList(10, 1, 0);

	private static Node initTestList(int numberOfNodes, int increment, Integer rejoinPoint) {
		Node head = new LinkedListCycleDetector().new Node();
		head.data = 0;
		Node tail = head;

		int i = 1;
		do {
			tail.next = new LinkedListCycleDetector().new Node();
			tail.next.data = tail.data + increment;
			tail = tail.next;
			i++;
		} while (i < numberOfNodes);

		if(rejoinPoint == null) {
			return(head);
		}
		
		Node listWalker = head;
		do {
			if(listWalker.data == rejoinPoint) {
				tail.next = listWalker;
				return(head);
			}
			listWalker = listWalker.next;
		} while	(listWalker.next != null);
		
		return (head);
	}

	public void searchForCycle() {

		System.out.println("--- Trees: Linked Lists: Detect a Cycle ---");
		int maxListSize = 25; // Set to 100 to match problem constraints
		launchSearch(testList00, maxListSize);
		launchSearch(testList01, maxListSize);
	}
	
	private void launchSearch(Node testList, int maxListSize) {
		String testListString = testList.toString(maxListSize);
		System.out.println("Test list: \n" + testListString);

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		boolean hasCycle = new LinkedListCycleDetector().hasCycle(testList);
		long endTime = System.currentTimeMillis();

		System.out.println("List contains cycle: " + hasCycle);
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
