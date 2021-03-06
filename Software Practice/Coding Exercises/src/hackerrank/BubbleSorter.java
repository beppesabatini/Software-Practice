package hackerrank;

import java.util.Arrays;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a href=
 * https://www.hackerrank.com/challenges/ctci-bubble-sort/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=sorting">Sorting:
 * Bubble Sort</a> problem. One way to test the solution is to mouse all the
 * code inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class BubbleSorter {

	static final String DEBUG = "false";

	/**
	 * This function is the solution to the practice problem "Sorting: Bubble Sort."
	 * The complete problem is posted on the HackerRank website (linked to below)
	 * and this solution can be tested there.
	 * <p/>
	 * The Bubble Sort algorithm as given in the HackerRank problem (as of this
	 * writing) isn't quite right. It's corrected in this implementation; see line
	 * 47 below.
	 * <p/>
	 * The best case runtime for this sorting algorithm is when the data is in order
	 * already. It will require zero swaps, so in big O notation it's O(N) or
	 * linear.
	 * <p/>
	 * The worst case is when the data is in reverse order. Then the number of swaps
	 * is the famous Gaussian series, N(N-1)/2 swaps. Strictly speaking this is
	 * O(N-squared). People often refer to it loosely as O(N-squared/2).
	 * 
	 * @param array The array to be bubble-sorted in place.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-bubble-sort/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=sorting">Original
	 *      HackerRank Problem</a>
	 */
	public static void countSwaps(int[] array) {

		numberOfSwaps = 0;

		for (int indexOuter = 0; indexOuter < array.length; indexOuter++) {
			int innerLoopSwaps = 0;
			for (int indexInner = 0; indexInner < array.length - indexOuter - 1; indexInner++) {
				// Swap adjacent elements if they are in decreasing order
				if (array[indexInner] > array[indexInner + 1]) {
					int lowerValue = array[indexInner + 1];
					array[indexInner + 1] = array[indexInner];
					array[indexInner] = lowerValue;
					innerLoopSwaps++;
				}
			}
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println(Arrays.toString(array));
				System.out.println("Inner Loop Swaps: " + innerLoopSwaps);
				numberOfSwaps += innerLoopSwaps;
			}
		}
		System.out.println("Array is sorted in " + numberOfSwaps + " swaps.");
		System.out.println("First Element: " + array[0]);
		System.out.println("Last Element: " + array[array.length - 1]);
	}

	private static int numberOfSwaps;

	public static int getNumberOfSwaps() {
		return (numberOfSwaps);
	}
}