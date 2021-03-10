package hackerrank;

import java.util.List;
import java.util.ArrayList;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a href=
 * https://www.hackerrank.com/challenges/alternating-characters/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=strings">Alternating
 * Characters</a> problem. One way to test the solution is to mouse all the code
 * inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class AlternatingCharacters {

	static final String DEBUG = "false";

	/**
	 * This function is the solution to the practice problem "Alternating
	 * Characters." The complete problem is posted on the HackerRank website (linked
	 * to below) and this solution can be tested there.
	 * <p>
	 * The goal is to reduce a string of any number of As and Bs to a string of
	 * alternating As and Bs, and return how many had to be deleted to reduce it
	 * that way. The code makes one pass through the data for validation. It makes a
	 * second pass to identify changes, and a third pass to redraw the string in
	 * accordance with the changes. So this method runs in O(3N), which is linear.
	 * 
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/alternating-characters/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=strings">Original
	 *      HackerRank Problem</a>
	 * 
	 * @param sourceString A string of any number of capital As and Bs
	 * @return the number of deletions employed to change the source string to
	 *         alternating As and Bs
	 */
	public static int alternatingCharacters(String sourceString) {
		if (sourceString == null || "".equals(sourceString)) {
			throw new IllegalArgumentException("sourceString is empty");
		}
		for (int i = 0; i < sourceString.length(); i++) {
			char charCurrent = Character.toChars(sourceString.codePointAt(i))[0];
			if ('A' == charCurrent || 'B' == charCurrent) {
				continue;
			} else {
				throw new IllegalArgumentException("All characters must be 'A' or 'B'");
			}
		}
		if (sourceString.length() == 1) {
			correctedStrings.add(sourceString);
			return (0);
		}
		List<Integer> changes = trackChanges(sourceString);

		StringBuffer alternatingString = new StringBuffer(sourceString.length());
		char charFirst = Character.toChars(sourceString.codePointAt(0))[0];
		alternatingString.append(charFirst);
		char currentChar = charFirst;
		int allCharactersDeleted = 0;
		for (int i = 1; i < changes.size(); i++) {
			if (i < changes.size() - 1) {
				char newChar = (currentChar == 'A' ? 'B' : 'A');
				alternatingString.append(newChar);
				currentChar = newChar;
			}
			int currentChange = changes.get(i);
			int previousChange = changes.get(i - 1);
			int charactersDeleted = currentChange - previousChange - 1;
			allCharactersDeleted += charactersDeleted;
		}
		correctedStrings.add(alternatingString.toString());
		return (allCharactersDeleted);
	}

	private static List<Integer> trackChanges(String sourceString) {
		List<Integer> changes = new ArrayList<Integer>();
		char charPrevious = Character.toChars(sourceString.codePointAt(0))[0];
		charPrevious = (charPrevious == 'A' ? 'B' : 'A');
		for (int i = 0; i < sourceString.length(); i++) {
			char charCurrent = Character.toChars(sourceString.codePointAt(i))[0];
			if (charPrevious != charCurrent) {
				changes.add(i);
				charPrevious = charCurrent;
			}
		}
		changes.add(sourceString.length()); // end-padding
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Changes: " + changes);
			System.out.println("Changes length: " + changes.size());
		}
		return (changes);
	}

	private static List<String> correctedStrings = new ArrayList<String>();

	public static void setCorrectedStrings(List<String> correctedStringsList) {
		correctedStrings = correctedStringsList;
	}

	public static List<String> getCorrectedStrings() {
		return (correctedStrings);
	}
}
