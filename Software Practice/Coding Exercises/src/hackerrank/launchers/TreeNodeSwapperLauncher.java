package hackerrank.launchers;

import java.util.Arrays;

import hackerrank.TreeNodeSwapper;

/**
 * This class launches the author's solution to the HackerRank "Swap Nodes
 * [Algo]" problem. Test data, monitoring code, logging code, debug output, and
 * similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class TreeNodeSwapperLauncher {

	@SuppressWarnings("unused")
	private static int[][] testTree00 = { { 2, 3 }, { -1, -1 }, { -1, -1 }, };
	@SuppressWarnings("unused")
	private static int[] swapDepth00 = { 0, 1, 1 };

	@SuppressWarnings("unused")
	private static int[][] testTree01 = { { 2, 3 }, { -1, 4 }, { -1, 5 }, { -1, -1 }, { -1, -1 } };
	@SuppressWarnings("unused")
	private static int[] swapDepth01 = { 0, 2 };

	@SuppressWarnings("unused")
	private static int[][] testTree02 = { { 2, 3 }, { 4, -1 }, { 5, -1 }, { 6, -1 }, { 7, 8 }, { -1, 9 }, { -1, -1 },
			{ 10, 11 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, };
	@SuppressWarnings("unused")
	private static int[] swapDepth02 = { 0, 2, 4 };

	@SuppressWarnings("unused")
	private static int[][] testTree03 = initTestTree03();
	@SuppressWarnings("unused")
	private static int[] swapDepth03 = { 16 };

	private static int[][] testTree04 = { { 2, 3 }, { 4, 5 }, { 6, -1 }, { -1, 7 }, { 8, 9 }, { 10, 11 }, { 12, 13 },
			{ -1, 14 }, { -1, -1 }, { 15, -1 }, { 16, 17 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
			{ -1, -1 }, };
	private static int[] swapDepth04 = { 0, 2, 3 };

	// Change this to one of the above for more testing.
	private static final int[][] testTree = testTree04;
	private static final int[] swapDepth = swapDepth04;

	// Generate a massively unbalanced tree, all right nodes only.
	private static int[][] initTestTree03() {
		// Higher than this gets a stack overflow.
		int numberSubArrays = 2 * 2056;
		int[][] testTree03 = new int[numberSubArrays][2];
		for (int i = 0; i < numberSubArrays - 1; i++) {
			testTree03[i] = new int[2];
			testTree03[i][0] = -1;
			testTree03[i][1] = i + 2;
		}
		// Add a null to finish the last leaf.
		testTree03[numberSubArrays - 1][0] = -1;
		testTree03[numberSubArrays - 1][1] = -1;

		return (testTree03);
	}
	
	public void swapTreeNodes() {

		System.out.println("--- Problem: Swap Nodes [Algo] ---");
		System.out.println("Test Tree:    " + Arrays.deepToString(testTree));
		System.out.println("Swap Depth:   " + Arrays.toString(swapDepth));

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		int[][] swappedTree = TreeNodeSwapper.swapNodes(testTree, swapDepth);
		long endTime = System.currentTimeMillis();

		System.out.println("Depth-First Traversals Before and After Swap(s): ");
		System.out.println(Arrays.deepToString(swappedTree));
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
