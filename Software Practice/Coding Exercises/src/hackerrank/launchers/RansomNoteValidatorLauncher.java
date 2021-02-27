package hackerrank.launchers;

import java.util.Arrays;

import hackerrank.RansomNoteValidator;

/**
 * This class launches the author's solution to the HackerRank "Hash Tables:
 * Ransom Note" problem. Test data, monitoring code, logging code, debug output,
 * and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class RansomNoteValidatorLauncher {

	@SuppressWarnings("unused")
	private final String[] testMagazine00 = { "give", "me", "one", "grand", "today", "night" };
	@SuppressWarnings("unused")
	private final String[] testNote00 = { "give", "one", "grand", "today" };

	private final String[] testMagazine01 = { "two", "times", "three", "is", "not", "four" };
	private final String[] testNote01 = { "two", "times", "two", "is", "four" };

	@SuppressWarnings("unused")
	private final String[] testMagazine02 = { "ive", "got", "a", "lovely", "bunch", "of", "coconuts" };
	@SuppressWarnings("unused")
	private final String[] testNote02 = { "ive", "got", "some", "coconuts" };

	// Change this for more test cases.
	private final String[] testMagazine = testMagazine01;
	private final String[] testNote = testNote01;

	public boolean validateNoteAgainstMagazine() {
		System.out.println("--- Problem: Hash Tables-Ransom Note ---");
		System.out.println("Test Data: ");
		System.out.println("    Test Magazine: " + Arrays.toString(testMagazine));
		System.out.println("    Test Note:\t   " + Arrays.toString(testNote));

		long startTime = System.currentTimeMillis();

		// This function is the solution to the HackerRank problem.
		RansomNoteValidator.checkMagazine(testMagazine, testNote);
		boolean isNoteValid = RansomNoteValidator.isNoteValid();

		long endTime = System.currentTimeMillis();

		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("Note is valid: " + isNoteValid);
		System.out.println("");

		return (isNoteValid);
	}
}
