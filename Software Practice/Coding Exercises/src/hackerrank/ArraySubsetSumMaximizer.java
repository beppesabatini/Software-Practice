package hackerrank;

import java.util.Arrays;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a
 * href=https://www.hackerrank.com/challenges/max-array-sum/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dynamic-programming">Max
 * Array Sum</a> problem. One way to test the solution is to mouse all the code
 * inside the outermost class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class ArraySubsetSumMaximizer {

	private static final String DEBUG = "false";

	/**
	 * This function is the solution to the practice problem "Max Array Sum." The
	 * complete problem is posted on the HackerRank website (linked to below) and
	 * this solution can be tested there. This function makes one pass through the
	 * specified data array and makes two comparisons for each element, so it's
	 * linear and runs on O(2N) time.
	 * 
	 * @see <a href=
	 *      "href=https://www.hackerrank.com/challenges/max-array-sum/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dynamic-programming">Original
	 *      HackerRank Problem</a>
	 */
	public static int maxSubsetSum(int[] testArray) {

		int[] maximums = new int[testArray.length];
		Arrays.fill(maximums, Integer.MIN_VALUE);

		// Induction - Base Case
		maximums[0] = testArray[0];
		maximums[1] = Math.max(maximums[0], testArray[1]);
		int maxValueSoFar = Math.max(testArray[0], testArray[1]);

		// Induction - General Case
		for (int i = 2; i < testArray.length; i++) {
			int max01 = Math.max(testArray[i], maxValueSoFar);
			int max02 = Math.max(max01, maximums[i - 2] + testArray[i]);
			maximums[i] = max02;
			if (max02 > maxValueSoFar) {
				maxValueSoFar = max02;
			}
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println("Max so far: " + maxValueSoFar);
				System.out.println(Arrays.toString(maximums));
			}
		}
		return (maxValueSoFar);
	}
}
