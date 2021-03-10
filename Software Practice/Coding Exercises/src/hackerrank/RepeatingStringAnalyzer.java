package hackerrank;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * "https://www.hackerrank.com/challenges/repeated-string/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen">"Repeated
 * String"</a> problem. One way to test the solution is to mouse all the code
 * inside the class definition into HackerRank. The HackerRank problem evaluates
 * a finite portion of a (theoretically) infinitely long string, and returns the
 * number of times the character 'a' is found in that portion.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class RepeatingStringAnalyzer {

	/**
	 * This function is the solution to the practice problem "Repeated String." The
	 * complete problem is posted on the HackerRank website (linked to below) and
	 * this solution can be tested there. The function examines a finite portion of
	 * an infinitely long string and returns the number of times the character 'a'
	 * is found in that portion.
	 * 
	 * <pre>
	 * Original word:       aardvark      (3 instances of 'a')
	 * Infinite string:     aardvarkaardvarkaardvarkaardvarkaardvark... [infinite aardvarks]
	 * Portion of Infinite: aardvarkaard  (5 instances of 'a')
	 * </pre>
	 * 
	 * The infinitely long string is assembled from an infinite number of
	 * repetitions of one finite string segment. We are evaluating one portion of
	 * the infinitely long string, and the portion may or may not be an exact
	 * multiple of the repeating finite string.
	 * <p>
	 * The logic makes one pass through one instance of the repeated finite string,
	 * and counts the number of 'a' letters which were found. Call the length of the
	 * one instance L. Then, the logic also counts the number of 'a' letters found
	 * in any leftover or remainder portion, any cut-off finite portion. This
	 * cut-off remainder theoretically could be almost as long as L, so the number
	 * of comparisons in total is about 2L. Thus this algorithm can be called O(N),
	 * or linear runtime.
	 * 
	 * @param coreString  The original finite string fragment, which in the problem
	 *                    is theoretically repeated an infinite number of times.
	 * @param numberChars The number of characters to examine in the infinite
	 *                    string.
	 * @return The number of times the character 'a' appears in the examined portion
	 *         of the infinite string.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/repeated-string/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen">Original
	 *      HackerRank Problem</a>
	 */
	public static long repeatedString(String coreString, long numberChars) {

		int coreStringLength = coreString.length();
		int theAsPerCoreString = 0;
		for (int i = 0; i < coreStringLength; i++) {
			if (coreString.charAt(i) == 'a') {
				theAsPerCoreString++;
			}
		}
		long numberReps = numberChars / coreStringLength;
		long numberAs = numberReps * theAsPerCoreString;

		// The modulo operator ('%') gives the remainder after dividing two numbers.
		Long lengthRemainder = (numberChars % coreStringLength);
		int lengthRemainderInt = lengthRemainder.intValue();
		String remainderString = coreString.substring(0, lengthRemainderInt);

		int theAsInRemainder = 0;
		for (int i = 0; i < remainderString.length(); i++) {
			if (remainderString.charAt(i) == 'a') {
				theAsInRemainder++;
			}
		}
		numberAs += theAsInRemainder;

		return (numberAs);
	}
}
