package hackerrank.launchers;

import hackerrank.*;

/**
 * This class launches the author's solution to the HackerRank "Repeated String"
 * problem. Test data, monitoring code, logging code, debug output, and similar
 * support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class RepeatingStringAnalyzerLauncher {

	static String testString01 = "aba";
	static long numberChars01 = 10L;

	static String testString02 = "a";
	static long numberChars02 = 1000000000000L;

	public long analyzeRepeatedString() {
		System.out.println("--- Problem: RepeatedString ---");

		String testString = testString01;
		long numberChars = numberChars01;

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		long numberOfAs = RepeatingStringAnalyzer.repeatedString(testString, numberChars);
		long endTime = System.currentTimeMillis();

		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		String arguments = "String to Repeat: \"" + testString + "\", Length of Excerpt from Repeated String: " + numberChars;
		System.out.println(arguments);
		System.out.println("Number of A's: " + numberOfAs);
		System.out.println("");
		return (numberOfAs);
	}

}
