package hackerrank;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * "https://www.hackerrank.com/challenges/repeated-string/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen">"Repeated String"</a> problem. One way to test the solution is to mouse all the
 * code inside the class definition into HackerRank.
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
	 * @param coreString  The original finite string fragment, which in the problem
	 *                    is theoretically repeated an infinite number of times.
	 * @param numberChars The number of characters to examine in the infinite
	 *                    string.
	 * @return The number of times the character 'a' appears in the examined portion
	 *         of the infinite string.
	 * @see
	 * 
	 *      <a href=
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
