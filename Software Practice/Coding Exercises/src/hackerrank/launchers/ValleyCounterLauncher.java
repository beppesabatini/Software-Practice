package hackerrank.launchers;

import hackerrank.*;

/**
 * This class launches the author's solution to the HackerRank "Counting
 * Valleys" problem. Test data, monitoring code, logging code, debug output, and
 * similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class ValleyCounterLauncher {

	public int countingValleysExercise() {
		System.out.println("--- Problem: Counting Valleys ---");

		String path01 = "UDDDUDUU"; // 1 hill, 1 valley
		@SuppressWarnings("unused")
		String path02 = "UUDDDDDDDDUUUU"; // 1 hill, 1 valley
		@SuppressWarnings("unused")
		String path03 = "DDDUUUDDDUUUDDDUUUUUUDDD"; // 1 hill, 3 valleys
		String testData = path01;

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		int numberOfValleys = new ValleyCounter().countingValleys(testData.length(), testData);
		long endTime = System.currentTimeMillis();
		System.out.println("Execution Time: " + (endTime - startTime) + " ms");

		System.out.println("");
		return (numberOfValleys);
	}

}
