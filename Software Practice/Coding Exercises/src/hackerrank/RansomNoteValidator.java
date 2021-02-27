package hackerrank;

import java.util.Map;
import java.util.HashMap;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * https://www.hackerrank.com/challenges/ctci-ransom-note/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps">Hash
 * Tables: Ransom Note</a> problem. One way to test the solution is to mouse all
 * the code inside the class definition into HackerRank.
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
	 * The problem doesn't state so, but it's implicitly assumed none of the words
	 * are printed back-to-back.
	 * 
	 * @param magazine A dictionary of all the words appearing in some given
	 *                 magazine.
	 * @param note     A dictionary of all the words the user might use in a ransom
	 *                 note.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-ransom-note/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps">Original
	 *      Hacker Rank Problem</a>
	 */
	public static void checkMagazine(String[] magazine, String[] note) {
		isNoteValid = false;

		Map<String, Integer> magazineCompendium = initializeCompendium(magazine);
		Map<String, Integer> noteCompendium = initializeCompendium(note);
		
		for(String key : noteCompendium.keySet()) {
			Integer magazineCount = magazineCompendium.get(key);
			if(magazineCount == null || magazineCount <= 0) {
				isNoteValid = false;
				System.out.println("No");
				return;
			}
			Integer wordCount = noteCompendium.get(key);
			if(wordCount == null || wordCount <= 0) {
				throw new IllegalArgumentException("Corrupt noteCompendium in checkMagazine");
			}
			if(wordCount > magazineCount) {
				isNoteValid = false;
				System.out.println("No");
				return;
			}
		}
		isNoteValid = true;
		System.out.println("Yes");
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