package hackerrank.launchers;

import java.util.Arrays;
import hackerrank.MaxHourglassSumFinder;

/**
 * This class launches the author's solution to the HackerRank "Problem: 2D
 * Array - DS" problem. Test data, monitoring code, logging code, debug output,
 * and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class MaxHourglassSumFinderLauncher {

// Test Matrix 1:
//	1 1 1 0 0 0
//	0 1 0 0 0 0
//	1 1 1 0 0 0
//	0 0 2 4 4 0
//	0 0 0 2 0 0
//	0 0 1 2 4 0

	private int[] row00 = { 1, 1, 1, 0, 0, 0 };
	private int[] row01 = { 0, 1, 0, 0, 0, 0 };
	private int[] row02 = { 1, 1, 1, 0, 0, 0 };
	private int[] row03 = { 0, 0, 2, 4, 4, 0 };
	private int[] row04 = { 0, 0, 0, 2, 0, 0 };
	private int[] row05 = { 0, 0, 1, 2, 4, 0 };
	private int[][] testMatrix01 = { row00, row01, row02, row03, row04, row05 };

//  Test Matrix 2:
//	-9 -9 -9  1  1  1 
//	 0 -9  0  4  3  2
//	-9 -9 -9  1  2  3
//	 0  0  8  6  6  0
//	 0  0  0 -2  0  0
//	 0  0  1  2  4  0	

	private int[] row10 = { -9, -9, -9, 1, 1, 1 };
	private int[] row11 = { 0, -9, 0, 4, 3, 2 };
	private int[] row12 = { -9, -9, -9, 1, 2, 3 };
	private int[] row13 = { 0, 0, 8, 6, 6, 0 };
	private int[] row14 = { 0, 0, 0, -2, 0, 0 };
	private int[] row15 = { 0, 0, 1, 2, 4, 0 };
	@SuppressWarnings("unused")
	private int[][] testMatrix02 = { row10, row11, row12, row13, row14, row15 };

	private int[] row00new = { 1, 1, 1, 0, 0, 99 };
	@SuppressWarnings("unused")
	private int[][] testMatrix03 = { row00new, row01, row02, row03, row04, row05 };

	// Change this for more test cases.
	private final int[][] testMatrix = testMatrix01;

	public int findMaximumHourglassSum() {
		System.out.println("--- Problem: 2D Array - DS ---");
		System.out.println("Test Matrix: ");

		for (int i = 0; i < testMatrix.length; i++) {
			System.out.println(Arrays.toString(testMatrix[i]));
		}
		System.out.println("");

		int maxHourglassSum = 0;

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		maxHourglassSum = MaxHourglassSumFinder.hourglassSum(testMatrix);
		long endTime = System.currentTimeMillis();

		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("Maximum Hourglass Sum: " + maxHourglassSum);
		System.out.println("");

		return (maxHourglassSum);
	}
}
