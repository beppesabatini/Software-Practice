package hackerrank.launchers;

import java.util.Arrays;

import hackerrank.LuckBalancer;

/**
 * This class launches the author's solution to the HackerRank "Luck Balance"
 * problem. Test data, monitoring code, logging code, debug output, and similar
 * support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class LuckBalancerLauncher {

	// This should come to 29
	private int[][] testContests00 = { { 5, 1 }, { 2, 1 }, { 1, 1 }, { 8, 1 }, { 10, 0 }, { 5, 0 }, };
	private int maxLoseableContests00 = 3;

	// This should come to 42
	@SuppressWarnings("unused")
	private int[][] testContests01 = { { 13, 1 }, { 10, 1 }, { 9, 1 }, { 8, 1 }, { 13, 1 }, { 12, 1 }, { 18, 1 },
			{ 13, 1 } };
	@SuppressWarnings("unused")
	private int maxLoseableContests01 = 5;

	// This should come to 21
	@SuppressWarnings("unused")
	private int[][] testContests02 = { { 5, 1 }, { 4, 0 }, { 6, 1 }, { 2, 1 }, { 8, 0 }, };
	@SuppressWarnings("unused")
	private int maxLoseableContests02 = 2;

	// This should come to 66
	private int[][] testContests03 = new int[12][2];
	@SuppressWarnings("unused")
	private int maxLoseableContests03 = 4;

	// Constructor
	public LuckBalancerLauncher() {
		initTestContests03();
	}

	private void initTestContests03() {
		for (int i = 0; i < 12; i++) {
			testContests03[i][0] = i + 1;
			testContests03[i][1] = i % 2;
		}
	}

	// Change this for more test cases.
	private final int[][] testContests = testContests00;
	private final int maxLoseableContests = maxLoseableContests00;

	public void findLuckiestBalance() {

		String contestsString = "";
		contestsString += "[";
		for (int i = 0; i < testContests.length; i++) {
			contestsString += Arrays.toString(testContests[i]);
			if (i < testContests.length - 1) {
				contestsString += ", ";
			}
		}
		contestsString += "]";

		System.out.println("--- Problem: Luck Balance ---");
		System.out.println("Maximum Loseable Contests: " + maxLoseableContests);
		System.out.println("Test Contests: " + contestsString);

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		int luckiestBalance = LuckBalancer.luckBalance(maxLoseableContests, testContests);
		long endTime = System.currentTimeMillis();

		System.out.println("Luckiest Balance: " + luckiestBalance);
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
