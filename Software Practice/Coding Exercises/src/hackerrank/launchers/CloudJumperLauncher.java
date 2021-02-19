package hackerrank.launchers;

import hackerrank.CloudJumper;

/**
 * This class launches the author's solution to the HackerRank "Jumping on the
 * Clouds" problem. Test data, monitoring code, logging code, debug output, and
 * similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class CloudJumperLauncher {
	static int[] clouds01 = { 0, 0, 1, 0, 0, 1, 0 };
	static int[] clouds02 = { 0, 0, 0, 0, 1, 0 };
	// This next test can't be won, but the class handles it correctly.
	// This exceeds specs, which guarantee that every game can be won.
	static int[] clouds03 = { 0, 1, 0, 1, 1 };

	public int jumpingOnTheClouds() {
		System.out.println("--- Problem: Jumping on the Clouds ---");
		int[] testData = clouds01;

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		int numberOfJumps = CloudJumper.jumpingOnClouds(testData);
		long endTime = System.currentTimeMillis();

		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("Number of Jumps: " + numberOfJumps);
		System.out.println("");
		return (numberOfJumps);
	}
}
