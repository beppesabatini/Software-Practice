package hackerrank.launchers;

import java.util.Arrays;

import hackerrank.ConnectedCellFinder;

/**
 * This class launches the author's solution to the HackerRank "DFS: Connected
 * Cell in a Grid" problem. Test data, monitoring code, logging code, debug
 * output, and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class ConnectedCellFinderLauncher {

	private static int[] testRow00 = { 1, 1, 0, 0 };
	private static int[] testRow01 = { 0, 1, 1, 0 };
	private static int[] testRow02 = { 0, 0, 1, 0 };
	private static int[] testRow03 = { 1, 0, 0, 0 };
	private static int[][] testGrid00 = { testRow00, testRow01, testRow02, testRow03 };

// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1
// 1 1 1 1 1 1 1 1 1 1

	@SuppressWarnings("unused")
	private static final int[][] testGrid01 = initFullGrid(10, 10, 0);
	@SuppressWarnings("unused")
	private static final int[][] testGrid02 = initFullGrid(2, 10, 0);

//	[0, 0, 0, 0, 0, 0, 0, 0]
//	[0, 0, 0, 0, 0, 0, 0, 0]
//	[1, 1, 1, 1, 1, 1, 1, 1]
//	[0, 0, 0, 0, 0, 0, 0, 0]
//	[0, 0, 0, 0, 0, 0, 0, 0]
//	[1, 1, 1, 1, 1, 1, 1, 1]
//	[0, 0, 0, 0, 0, 0, 0, 0]
//	[0, 0, 0, 0, 0, 0, 0, 0]
//	[1, 1, 1, 1, 1, 1, 1, 1]

	@SuppressWarnings("unused")
	private static final int[][] testGrid03 = initFullGrid(9, 8, 2);

	@SuppressWarnings("unused")
	private static final int[][] testGrid04 = initFullGrid(3, 12, 2);

	@SuppressWarnings("unused")
	private static final int[][] testGrid05 = initFullGrid(15, 4, 2);

	private static int[][] initFullGrid(int height, int width, int blankStripes) {
		int[][] fullGrid = new int[height][width];
		for (int i = 0; i < height; i++) {
			int cellValue = 1;
			if (blankStripes > 0) {
				cellValue = (i + 1) % (blankStripes + 1) == 0 ? 1 : 0;
			}
			for (int j = 0; j < width; j++) {
				fullGrid[i][j] = cellValue;
			}
		}
		return (fullGrid);
	}

	private static void gridToString(int[][] testGrid) {
		for (int i = 0; i < testGrid.length; i++) {
			System.out.println(Arrays.toString(testGrid[i]));
		}
	}

	// Change this to whichever grid you want to evaluate
	private static final int[][] testGrid = testGrid00;

	public void findMaximumConnectedCellRegionSize() {

		System.out.println("--- Problem: DFS: Connected Cell in a Grid ---");
		System.out.println("Grid Height: " + testGrid.length);
		System.out.println("Grid Width:  " + testGrid[0].length);
		System.out.println("Grid Data: ");
		gridToString(testGrid);
		System.out.println("");

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		int maximumRegionSize = ConnectedCellFinder.maxRegion(testGrid);
		long endTime = System.currentTimeMillis();

		System.out.println("Maximum Connected Cell Region Size: " + maximumRegionSize);
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
