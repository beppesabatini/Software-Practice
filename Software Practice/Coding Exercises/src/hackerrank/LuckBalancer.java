package hackerrank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a href=
 * https://www.hackerrank.com/challenges/luck-balance/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=greedy-algorithms">Luck
 * Balance</a> problem. One way to test the solution is to mouse all the code
 * inside the class definition into HackerRank. The goal is for a user to
 * reverse-jinx herself and get as much good luck as possible by losing as many
 * contests as is allowable.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class LuckBalancer {

	static final String DEBUG = "false";
	static final int ROUTINE = 0;
	static final int SIGNIFICANT = 1;

	/**
	 * This function is the solution to the practice problem "Luck Balance." The
	 * complete problem is posted on the HackerRank website (linked to below) and
	 * this solution can be tested there.
	 * <p/>
	 * The premise of the puzzle is that winning a contest drains the winner's luck
	 * reserve, while losing recharges the reserve. Perhaps the contestants are
	 * something like leprechauns or black cats having a karma-driven jinxing
	 * contest. The user (called Lisa) has a plan to lose all the preliminary
	 * trials, to recharge her luck to the maximum for the main contest. Certain
	 * early contests are considered more significant and the user can only lose a
	 * specified number of them before being eliminated altogether. The requirement
	 * is to figure out the best strategy for the user to use to reverse-jinx
	 * herself and maximize her good luck.
	 * <p>
	 * Take N to be the combined number of elements in the two input arrays. The two
	 * input arrays are sorted using the Java sort function. This function's
	 * behavior varies according to the data it receive, but we can meaningfully
	 * assume that it runs in O(NlogN). The logic then makes one pass through each
	 * of the two sorted arrays, and performs one mathematical operation on each
	 * element. So this solution runs in O(NlogN + N) time.
	 * 
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/luck-balance/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=greedy-algorithms">Original
	 *      HackerRank Problem</a>
	 */
	public static int luckBalance(int numberLoseableContests, int[][] contests) {
		int luckReserve = 0;
		List<Integer> routineContests = new ArrayList<Integer>();
		List<Integer> significantContests = new ArrayList<Integer>();

		for (int[] contest : contests) {
			switch (contest[1]) {
			case ROUTINE:
				routineContests.add(contest[0]);
				break;
			case SIGNIFICANT:
				significantContests.add(contest[0]);
				break;
			default:
				throw new IllegalArgumentException("All contests must be either 0 or 1");
			}
		}
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Routine: " + routineContests);
			System.out.println("Significant: " + significantContests);
		}

		Collections.sort(routineContests);
		Collections.sort(significantContests);

		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("After sorting: ");
			System.out.println("Routine: " + routineContests);
			System.out.println("Significant: " + significantContests);
		}
		for (int i = 0; i < routineContests.size(); i++) {
			luckReserve += routineContests.get(i);
		}

		for (int j = significantContests.size() - 1, k = 0; j >= 0; j--, k++) {
			if (k < numberLoseableContests) {
				luckReserve += significantContests.get(j);
			} else {
				luckReserve -= significantContests.get(j);
			}
		}
		System.out.println(luckReserve);
		return (luckReserve);
	}
}
