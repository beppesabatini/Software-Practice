package hackerrank.launchers;

import java.util.Arrays;
import java.util.List;

import hackerrank.AlternatingCharacters;

/**
 * This class launches the author's solution to the HackerRank "Sorting: Bubble
 * Sort" problem. Test data, monitoring code, logging code, debug output, and
 * similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class AlternatingCharactersLauncher {

	private final String testString00 = "AAAA";
	private final String testString01 = "BBBBB";
	private final String testString02 = "ABABABAB";
	private final String testString03 = "BABABA";
	private final String testString04 = "AAABBB";
	private final String testString05 = "AAABBBABBBBBAAAAAABAAAAAAAABB";
	private final String testString06 = "A";
	private final String testString07 = "B";
	

	private final String[] testArray01 = {testString00, testString01, testString02, testString03, testString04};
	@SuppressWarnings("unused")
	private final String[] testArray02 = {testString04, testString05, testString06, testString07};
	
	// Change this for more test cases.
	private final String[] testArray = testArray01;

	public void enforceAlternatingCharacters() {
		System.out.println("--- Problem: Alternating Characters ---");
		System.out.println("Test Array:   " + Arrays.toString(testArray));

		long startTime = System.currentTimeMillis();

		Integer[] charactersRemovedArray = new Integer[testArray.length];
		// This function is the solution to the HackerRank problem.
		for (int i = 0; i < testArray.length; i++) {
			charactersRemovedArray[i] = AlternatingCharacters.alternatingCharacters(testArray[i]);
		}
		long endTime = System.currentTimeMillis();

		List<String> correctedStrings = AlternatingCharacters.getCorrectedStrings();
		System.out.println("Corrected Strings: " + correctedStrings);
		System.out.println("Number Characters Removed: " + Arrays.toString(charactersRemovedArray));
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
