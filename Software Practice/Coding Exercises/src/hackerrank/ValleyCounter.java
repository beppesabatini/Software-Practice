package hackerrank;

import java.util.ArrayList;
import java.util.List;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * "https://www.hackerrank.com/challenges/counting-valleys/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen">"Counting
 * Valleys"</a> problem. One way to test the solution is to mouse all the code
 * inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class ValleyCounter {

	/**
	 * This function is the solution to the practice problem "Counting Valleys." The
	 * complete problem is posted on the HackerRank website (linked to below) and
	 * this solution can be tested there. The problem is to calculate how many
	 * valleys a hiker encounters when hiking up and hiking down a certain path.
	 * <p>
	 * The algorithm makes one pass through the input string, making a list of every
	 * rise and fall in the hiking trail. It then makes a pass through the list of
	 * rises and falls, which theoretically could be as long as the original string
	 * of hiking steps. So if N is the size of the original input string, this will
	 * require 2N operations, which can be described as O(2N), or a linear runtime.
	 * 
	 * @param steps Ignored, but HackerRank expects it.
	 * @param path  The trail taken by a hiker, represented as steps up and steps
	 *              down.
	 * @return An integer value for the number of valleys found.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/counting-valleys/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen">Original
	 *      HackerRank Problem</a>
	 */
	public int countingValleys(int steps, String path) {
		if (path == null || path.length() <= 0) {
			return (0);
		}
		int lengthOfHike = path.length();

		int[] altitudes = new int[lengthOfHike + 2];
		altitudes[0] = 0;
		altitudes[lengthOfHike + 1] = 0;

		for (int i = 1; i <= lengthOfHike; i++) {
			char currentStep = path.charAt(i - 1);
			int previousAltitude = altitudes[i - 1];

			// System.out.println(currentStep);
			switch (currentStep) {
			case 'U':
				altitudes[i] = previousAltitude + 1;
				break;
			case 'D':
				altitudes[i] = previousAltitude - 1;
				break;
			default:
				// Bad data
				System.out.println("Value '" + currentStep + "' is not allowed in path " + path);
				return (0);
			}
		}

		List<TrailChange> trailChanges = getTrailChanges(altitudes);

		int numberOfHills = 0;
		int numberOfValleys = 0;
		for (TrailChange trailChange : trailChanges) {
			if (trailChange.type == TrailChange.HillOrValley.HILL) {
				numberOfHills++;
			} else if (trailChange.type == TrailChange.HillOrValley.VALLEY) {
				numberOfValleys++;
			}
		}
		System.out.println("Hiking path: " + path);
		System.out.println("Number of Hills: " + numberOfHills);
		System.out.println("Number of Valleys: " + numberOfValleys);
		return (numberOfValleys);
	}

	private List<TrailChange> getTrailChanges(int[] altitudes) {
		List<TrailChange> trailChanges = new ArrayList<TrailChange>();
		if (altitudes.length <= 2) {
			return (trailChanges);
		}

		int currentStartPoint = -1;
		int currentEndPoint = -1;

		for (int i = 0; i < altitudes.length; i++) {

			if (altitudes[i] == 0) {
				if (currentStartPoint == -1) {
					currentStartPoint = i;
					continue;
				} else if (currentEndPoint == -1) {
					currentEndPoint = i;
				}
			} else
				continue;

			if (currentStartPoint + 1 == currentEndPoint) {
				continue;
			}
			if (currentEndPoint == -1) {
				return (trailChanges);
			}
			if (currentStartPoint >= 0 && currentEndPoint >= 0) {
				TrailChange currentTrailChange = new TrailChange();
				currentTrailChange.startPoint = currentStartPoint;
				currentTrailChange.endPoint = currentEndPoint;
				if (altitudes[currentStartPoint] < altitudes[currentStartPoint + 1]) {
					currentTrailChange.type = TrailChange.HillOrValley.HILL;
				} else if (altitudes[currentStartPoint] > altitudes[currentStartPoint + 1]) {
					currentTrailChange.type = TrailChange.HillOrValley.VALLEY;
				}
				trailChanges.add(currentTrailChange);
				currentStartPoint = currentEndPoint;
				currentEndPoint = -1;
			}
		}
		return (trailChanges);
	}

	private static class TrailChange {
		public enum HillOrValley {
			HILL, VALLEY
		}

		@SuppressWarnings("unused")
		public Integer startPoint;
		@SuppressWarnings("unused")
		public Integer endPoint;
		public HillOrValley type;

		public TrailChange() {
			this.startPoint = null;
			this.endPoint = null;
			this.type = null;
		}
	}

}
