package hackerrank;

import java.util.ArrayList;
import java.util.List;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * "https://www.hackerrank.com/challenges/jumping-on-the-clouds/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen&h_r=next-challenge&h_v=zen">"Jumping
 * on the Clouds"</a> problem. One way to test the solution is to mouse all the
 * code inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class CloudJumper {

	private final static int CUMULUS = 0;
	private final static int THUNDERHEAD = 1;

	private class CloudJump {
		public int jumpStart;
		public int jumpEnd;

		CloudJump(int jumpStart, int jumpEnd) {
			this.jumpStart = jumpStart;
			this.jumpEnd = jumpEnd;
		}

		public String toString() {
			String newString = "";
			newString += "{";
			newString += this.jumpStart;
			newString += ", ";
			newString += this.jumpEnd;
			newString += "}";
			return (newString);
		}
	}

	/**
	 * This function is the solution to the practice problem "Jumping on the
	 * Clouds." The complete problem is posted on the HackerRank website (linked to
	 * below) and this solution can be tested there. While jumping from one cloud to
	 * another, the goal is to find the shortest path of jumps from the start-point
	 * cloud to the end-point cloud, and then return the number of jumps in that
	 * path.
	 * 
	 * @param int[] clouds An array of zeroes and ones, in which zeroes represent a
	 *              cumulus cloud--safe to jump to--and a 1 represents a
	 *              thunderhead--not allowed to jump to.
	 * @return int Returns the number of jumps in the shortest path from the first
	 *         cloud to the last cloud.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/jumping-on-the-clouds/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen&h_r=next-challenge&h_v=zen">Original
	 *      HackerRank Problem</a>
	 */
	public static int jumpingOnClouds(int[] clouds) {
		List<CloudJump> cloudJumps = buildCloudJumpPath(clouds, 0, null);
		String badDataMessage = "Bad data: the game can not be won.";
		if (cloudJumps == null || cloudJumps.size() <= 0) {
			System.out.println(badDataMessage);
			System.out.println("No cloud jumps were found");
			return (0);
		}

		for (CloudJump cloudJump : cloudJumps) {
			if (clouds[cloudJump.jumpStart] == THUNDERHEAD || clouds[cloudJump.jumpEnd] == THUNDERHEAD) {
				System.out.println("Application logic is broken - thunderhead found in path: " + cloudJump);
				System.out.println("Full path: " + cloudJumps);
				return (0);
			}
		}

		String allClouds = cloudArrayToString(clouds);
		System.out.println("All clouds: " + allClouds);
		System.out.println("Cloud jumps: " + cloudJumps);
		int numberOfJumps = cloudJumps.size();
		int lastJump = numberOfJumps - 1;
		int endPoint = cloudJumps.get(lastJump).jumpEnd;
		int lastCloud = clouds.length - 1;

		if (endPoint != lastCloud) {
			System.out.println(badDataMessage);
			System.out.println("End point: " + endPoint + ", Last cloud: " + lastCloud);
			return (0);
		}

		if (numberOfJumps <= 0) {
			System.out.println(badDataMessage);
			System.out.println("Number of Jumps: " + numberOfJumps);
			return (numberOfJumps);
		}

		return (numberOfJumps);
	}

	/**
	 * A recursive function which finds the longest legal jump from a starting point
	 * and then calls itself--recurses--with the remainder of clouds to traverse.
	 * 
	 * @param clouds     An integer array of zeros (safe clouds) and ones
	 *                   (disallowed dangerous clouds).
	 * @param startIndex
	 * @param cloudJumps The list which accumulates the jumps found up to this
	 *                   point.
	 * @return The list of cloud jumps which grows as the function calls itself.
	 */
	private static List<CloudJump> buildCloudJumpPath(int[] clouds, int startIndex, List<CloudJump> cloudJumps) {
		if (cloudJumps == null) {
			cloudJumps = new ArrayList<CloudJump>();
		}
		if (clouds == null || clouds.length <= 1) {
			System.out.println("Bad data: no clouds found");
			return (cloudJumps);
		}
		int lastCloud = clouds.length - 1;
		int nextToLast = lastCloud - 1;
		int secondToLast = nextToLast - 1;

		if (startIndex < 0 || startIndex > nextToLast) {
			return (cloudJumps);
		}

		/*
		 * The next two tests are "stop cases" which prevent the recursive function from
		 * going into an infinite loop. When one of these two tests returns true the
		 * calculations are complete and the list of jumps can be returned.
		 */
		if (startIndex == secondToLast) {
			if (clouds[secondToLast] == CUMULUS && clouds[lastCloud] == CUMULUS) {
				CloudJump cloudJump = new CloudJumper().new CloudJump(secondToLast, lastCloud);
				cloudJumps.add(cloudJump);
				return (cloudJumps);
			}
		} else if (startIndex == nextToLast) {
			if (clouds[nextToLast] == CUMULUS && clouds[lastCloud] == CUMULUS) {
				CloudJump cloudJump = new CloudJumper().new CloudJump(nextToLast, lastCloud);
				cloudJumps.add(cloudJump);
				return (cloudJumps);
			}
		}

		int shortJump = startIndex + 1;
		int longJump = startIndex + 2;

		if (clouds[startIndex] == CUMULUS && clouds[longJump] == CUMULUS) {
			cloudJumps.add(new CloudJumper().new CloudJump(startIndex, longJump));
			int newStartIndex = longJump; // For readability
			buildCloudJumpPath(clouds, newStartIndex, cloudJumps); // Recursive call
		} else if (clouds[startIndex] == CUMULUS && clouds[shortJump] == CUMULUS) {
			cloudJumps.add(new CloudJumper().new CloudJump(startIndex, shortJump));
			int newStartIndex = shortJump; // For readability
			buildCloudJumpPath(clouds, newStartIndex, cloudJumps); // Recursive call
		}
		return (cloudJumps);
	}

	private static String cloudArrayToString(int[] clouds) {
		String cloudString = "";
		cloudString += "{";
		for (int i = 0; i < clouds.length; i++) {
			String currentCloud = Cloud.fromValue(clouds[i]).toString();
			cloudString += currentCloud;
			if (i < clouds.length - 1) {
				cloudString += ", ";
			}
		}
		cloudString += "}";
		return (cloudString);
	}
}
