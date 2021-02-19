package hackerrank.launchers;

import hackerrank.*;

/**
 * This class launches the author's solution to the HackerRank "Sales by Match"
 * problem. Test data, monitoring code, logging code, debug output, and similar
 * support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class SockPairCounterLauncher {

	public int salesByMatch() {
		System.out.println("--- Problem: Sales By Match ---");

		int[] sockColors = { 10, 20, 20, 10, 10, 30, 50, 10, 20 };
		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		final int numberOfPairs = new SockPairCounter().sockMerchant(sockColors.length, sockColors);
		long endTime = System.currentTimeMillis();

		System.out.println("Execution Time: " + (endTime - startTime) + " ms");
		System.out.println("Number of Sock Pairs: " + numberOfPairs);
		System.out.println("");
		return (numberOfPairs);
	}

}
