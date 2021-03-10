package hackerrank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * "https://www.hackerrank.com/challenges/sock-merchant/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup">"Sales
 * by Match"</a> problem. One way to test the solution is to mouse all the code
 * inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class SockPairCounter {

	/**
	 * This function is the solution to the practice problem "Sales by Match." The
	 * complete problem is posted on the HackerRank website (linked to below) and
	 * this solution can be tested there. The problem takes a large pile of socks
	 * and determines how many matching pairs can be found there.
	 * <p>
	 * The solution sorts some number of socks, call this S, into some number of
	 * buckets, sorting by color. Theoretically, if every sock is a different color,
	 * there could be S buckets. The logic then examines each bucket, and counts the
	 * number of matching pairs of socks found in each bucket. Sorting will take S
	 * operations, and counting might take S operations as well. An algorithm
	 * requiring two S number of operations can be described as O(N), or a linear
	 * runtime.
	 * 
	 * @param n          Ignored, but HackerRank expects it.
	 * @param sockColors An array of integers in which each integer represents one
	 *                   sock of a certain color.
	 * @return The number of matching pairs of socks found.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/sock-merchant/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup">Original
	 *      HackerRank Problem</a>
	 */
	public int sockMerchant(int n, int[] sockColors) {
		// Map<Integer> color, List<Integer> socksOfThatColor
		Map<Integer, List<Integer>> sockMatcher = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < sockColors.length; i++) {
			int currentColor = sockColors[i];
			List<Integer> socksOfThatColor = sockMatcher.get(currentColor);
			if (socksOfThatColor == null) {
				socksOfThatColor = new ArrayList<Integer>();
				sockMatcher.put(currentColor, socksOfThatColor);
			}
			socksOfThatColor.add(i);
		}

		System.out.println(sockMatcher);

		int numberOfPairs = 0;
		Set<Integer> colorsFound = sockMatcher.keySet();
		for (Integer color : colorsFound) {
			List<Integer> socksWithColor = sockMatcher.get(color);
			Integer numberCurrentPairs = socksWithColor.size() / 2;
			numberOfPairs += numberCurrentPairs;
		}

		return (numberOfPairs);
	}
}
