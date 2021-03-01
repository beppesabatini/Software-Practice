package hackerrank.launchers;

import java.util.Arrays;

import hackerrank.BubbleSorter;

/**
 * This class launches the author's solution to the HackerRank "Sorting: Bubble Sort" problem. Test data, monitoring code, logging code, debug output,
 * and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class BubbleSorterLauncher  {

	@SuppressWarnings("unused")
	private final int[] testArray00 = {1, 2, 3};
	@SuppressWarnings("unused")
	private final int[] testArray01 = {3, 2, 1};
	@SuppressWarnings("unused")
	private final int[] testArray02 = {9, 8, 7, 6, 5, 4, 3, 2, 1};

	// Change this for more test cases.
	private final int[] testArray = testArray02;

	public int bubbleSortArray() {
		System.out.println("--- Sorting: Bubble Sort ---");
		System.out.println("Test Array:   " + Arrays.toString(testArray));

		long startTime = System.currentTimeMillis();

		// This function is the solution to the HackerRank problem.
		BubbleSorter.countSwaps(testArray);

		long endTime = System.currentTimeMillis();

		System.out.println("Sorted Array: " + Arrays.toString(testArray));
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");

		int numberOfSwaps = BubbleSorter.getNumberOfSwaps();
		return (numberOfSwaps);
	}
}
