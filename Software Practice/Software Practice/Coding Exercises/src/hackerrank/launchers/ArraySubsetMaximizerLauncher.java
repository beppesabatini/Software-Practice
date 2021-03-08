package hackerrank.launchers;

import java.util.Arrays;

import hackerrank.ArraySubsetSumMaximizer;

/**
 * This class launches the author's solution to the HackerRank "Max Array Sum"
 * problem. Test data, monitoring code, logging code, debug output, and similar
 * support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class ArraySubsetMaximizerLauncher {

	@SuppressWarnings("unused")
	private static int[] testArray00 = { 3, 7, 4, 6, 5 };

	@SuppressWarnings("unused")
	private static int[] testArray01 = { 2, 1, 5, 8, 4 };

	@SuppressWarnings("unused")
	private static int[] testArray02 = { 3, 5, -7, 8, 10 };

	private static int[] testArray03 = { -2, 1, 3, -4, 5 };

	@SuppressWarnings("unused")
	private static int[] testArray04 = { 1, -999999, -999999 };

	// Change this to one of the above for more testing.
	private static final int[] testArray = testArray03;

	public void maximizeArraySubsetSum() {

		System.out.println("--- Problem: Max Array Sum ---");
		System.out.println("Test Array: " + Arrays.toString(testArray));

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		int maximumSum = ArraySubsetSumMaximizer.maxSubsetSum(testArray);
		long endTime = System.currentTimeMillis();

		System.out.println("Maximum Array Subset Sum: " + maximumSum);
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
