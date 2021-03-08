package hackerrank.launchers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ch12.FileEchoer; // inside lib/NiemeyerKnudsen1.0.jar

import hackerrank.DoubleStackQueue;
import hackerrank.DoubleStackQueue.MyQueue;

/**
 * This class launches the author's solution to the HackerRank "Queues: A Tale
 * of Two Stacks" problem. Test data, monitoring code, logging code, debug
 * output, and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class DoubleStackQueueLauncher {

	private static final String inputFile00 = "input/doubleStackQueueQueries00.txt";
	@SuppressWarnings("unused")
	private static final String inputFile01 = "input/doubleStackQueueQueries01.txt";
	@SuppressWarnings("unused")
	private static final String inputFile02 = "input/doubleStackQueueQueries02.txt";
	@SuppressWarnings("unused")
	private static final String inputFile16 = "input/doubleStackQueueQueries16.txt";
	@SuppressWarnings("unused")
	private static final String inputFile17 = "input/doubleStackQueueQueries17.txt";

	// Change this for more test cases. This test file gets used when the
	// HackerRankCodingExercises class is run.
	private static String inputFile = inputFile00;

	public static void testDoubleStackQueue() {

		System.out.println("--- Problem: Queues: A Tale of Two Stacks ---");
		System.out.println("Test Input: ");
		FileEchoer.echoFileToScreen(inputFile);
		System.out.println("");

		long startTime = System.currentTimeMillis();
		invokeDoubleStackQueueLauncher();
		long endTime = System.currentTimeMillis();

		System.out.println("");
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}

	public static void invokeDoubleStackQueueLauncher() {
		String[] args = new String[1];
		args[0] = inputFile;
		DoubleStackQueue.setDebug(true);
		DoubleStackQueueLauncher.main(args);
		DoubleStackQueue.setDebug(false);
		// System.out.println(testInput);
	}

	// If you run this main(), the input file in the Run Configuration is used (not
	// the testFile specified above).
	public static void main(String[] args) {

		// This new MyQueue class is the solution to the HackerRank problem.
		MyQueue<Integer> queue = new DoubleStackQueue().new MyQueue<Integer>();

		File inputFile = new File(args[0]);
		Scanner scan = null;
		try {
			scan = new Scanner(inputFile);
		} catch (FileNotFoundException exception) {
			String message = "Sorry, the system could not find file: " + args[0];
			System.out.println(message);
		}
		// This is how HackerRank sends in the data
		// Scanner scan = new Scanner(System.in);
		int numberOfLines = scan.nextInt();

		for (int i = 0; i < numberOfLines; i++) {
			int operation = scan.nextInt();
			if (operation == 1) { // enqueue
				queue.enqueue(scan.nextInt());
			} else if (operation == 2) { // dequeue
				queue.dequeue();
			} else if (operation == 3) { // print/peek
				System.out.println(queue.peek());
			}
		}
		scan.close();
	}
}
