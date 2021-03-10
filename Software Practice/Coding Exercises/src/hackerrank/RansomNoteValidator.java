package hackerrank;

import java.util.Map;
import java.util.HashMap;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * https://www.hackerrank.com/challenges/ctci-ransom-note/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps">Hash
 * Tables: Ransom Note</a> problem. One way to test the solution is to mouse all
 * the code inside the class definition into HackerRank. The premise is to
 * determine if a given note can be represented by cutting and pasting all its
 * words from a given magazine.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class RansomNoteValidator {

	/**
	 * This function is the solution to the practice problem "Hash Tables: Ransom
	 * Note." The complete problem is posted on the HackerRank website (linked to
	 * below) and this solution can be tested there. The goal is to determine if,
	 * for each occurrence of every word in a ransom note, enough instances can be
	 * found in one specified magazine to cut up the magazine and paste up the note.
	 * Of course, one scrap of paper can not be pasted down in two places at the
	 * same time. Furthermore, the problem doesn't state so, but it's implicitly
	 * assumed none of the words in the magazine are printed back-to-back.
	 * <p>
	 * Input is in the form of two string arrays, which represent a magazine and a
	 * ransom note. Call the sizes of these arrays M and R. The solution inserts all
	 * the input data into two String key-value maps. Assume the maps are optimized
	 * and call this M + R insertions. The "ransomNote" map is the smaller input
	 * array, and for each of its elements there will be one look-up in the larger
	 * "magazine" map, another R operations. So this will require M + R + R
	 * operations, which can be described as linear runtime, or O(N).
	 * 
	 * @param magazine    A dictionary of all the words appearing in some given
	 *                    magazine.
	 * @param ransomeNote A dictionary of all the words the user might use in a
	 *                    ransom note.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-ransom-note/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps">Original
	 *      HackerRank Problem</a>
	 */
	public static void checkMagazine(String[] magazine, String[] ransomNote) {
		isNoteValid = false;

		Map<String, Integer> magazineCompendium = initializeCompendium(magazine);
		Map<String, Integer> noteCompendium = initializeCompendium(ransomNote);

		for (String key : noteCompendium.keySet()) {
			Integer magazineCount = magazineCompendium.get(key);
			if (magazineCount == null || magazineCount <= 0) {
				isNoteValid = false;
				System.out.println("No");
				return;
			}
			Integer wordCount = noteCompendium.get(key);
			if (wordCount == null || wordCount <= 0) {
				throw new IllegalArgumentException("Corrupt noteCompendium in checkMagazine");
			}
			if (wordCount > magazineCount) {
				isNoteValid = false;
				System.out.println("No");
				return;
			}
		}
		isNoteValid = true;
		System.out.println("Yes"); // Writing to the console is the output expected from HackerRank
	}

	private static Map<String, Integer> initializeCompendium(String[] dictionary) {
		Map<String, Integer> compendium = new HashMap<String, Integer>();

		if (dictionary == null || dictionary.length <= 0) {
			return (compendium);
		}

		for (String word : dictionary) {
			Integer wordCount = 0;
			if ((wordCount = compendium.get(word)) == null) {
				compendium.put(word, 1);
				continue;
			}
			compendium.put(word, wordCount + 1);
		}
		return (compendium);
	}

	private static boolean isNoteValid;

	public static boolean isNoteValid() {
		return (isNoteValid);
	}

	public static boolean getNoteValid() {
		return (isNoteValid);
	}

}