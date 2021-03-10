package hackerrank;

/**
 * HackerRank provides many sample test questions on its site; this class is the
 * author's solution to the <a href=
 * "https://www.hackerrank.com/challenges/2d-array/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays">"2D
 * Array - DS"</a> problem. One way to test the solution is to mouse all the
 * code inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class MaxHourglassSumFinder {

	private static String DEBUG = "false";

	private static final int MATRIX_HEIGHT = 6;
	private static final int MATRIX_WIDTH = MATRIX_HEIGHT;

	/**
	 * This function is the solution to the practice problem "2D Array - DS." The
	 * complete problem is posted on the HackerRank website (linked to below) and
	 * this solution can be tested there. Given a specified 6x6 matrix, this
	 * function scans inside it for the "hourglass pattern" whose elements total to
	 * the highest sum. A sample hourglass pattern:
	 * 
	 * <pre>
	 *    2 4 4
	 *      2 
	 *    1 2 4
	 * </pre>
	 * 
	 * In a 6x6 matrix there are sixteen possible hourglass patterns. Each hourglass
	 * requires six mathematical operations. So this solution requires 96
	 * mathematical operations, which is described as O(1) or constant time.
	 * 
	 * @param currentMatrix The matrix being scanned for high-summing hourglass
	 *                      patterns.
	 * @return The highest value found when summing up all the elements in each of
	 *         all hourglass patterns.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/2d-array/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays">Original
	 *      HackerRank Problem</a>
	 */
	public static int hourglassSum(int[][] currentMatrix) {
		String message = String.format("Bad input: current matrices must be %d x %d", MATRIX_HEIGHT, MATRIX_WIDTH);
		if (currentMatrix.length != MATRIX_HEIGHT) {
			System.out.println(message);
			return (-1);
		}
		for (int i = 0; i < MATRIX_HEIGHT; i++) {
			if (currentMatrix[i].length != MATRIX_WIDTH) {
				System.out.println(message);
				return (-1);
			}
		}
		if (Boolean.valueOf(DEBUG) == true) {
			for (int x = 0; x < MATRIX_WIDTH - 2; x++) {
				for (int y = 0; y < MATRIX_WIDTH - 2; y++) {
					printHourglass(x, y, currentMatrix);
					System.out.println("");
				}
			}
		}

		int maxSumX = 0;
		int maxSumY = 0;
		int maxSumValue = Integer.MIN_VALUE;

		for (int x = 0; x < MATRIX_WIDTH - 2; x++) {
			for (int y = 0; y < MATRIX_WIDTH - 2; y++) {
				int currentMax = sumHourglass(x, y, currentMatrix);
				if (currentMax > maxSumValue) {
					maxSumX = x;
					maxSumY = y;
					maxSumValue = currentMax;
				}
			}
		}
		System.out.println("Hourglass with hightest total: ");
		printHourglass(maxSumX, maxSumY, currentMatrix);
		return (maxSumValue);
	}

	public static void printHourglass(int x0, int y0, int[][] matrix) {
		if (x0 < 0 || x0 > MATRIX_WIDTH - 2) {
			System.out.printf("Bad data: X coordinate %d is out of range");
			return;
		}
		if (y0 < 0 || y0 > MATRIX_HEIGHT - 2) {
			System.out.printf("Bad data: X coordinate %d is out of range");
			return;
		}
		System.out.printf(" %d %d %d\n", matrix[x0][y0], matrix[x0][y0 + 1], matrix[x0][y0 + 2]);
		System.out.printf("   %d \n", matrix[x0 + 1][y0 + 1]);
		System.out.printf(" %d %d %d\n", matrix[x0 + 2][y0], matrix[x0 + 2][y0 + 1], matrix[x0 + 2][y0 + 2]);
	}

	public static int sumHourglass(int x0, int y0, int[][] matrix) {
		if (x0 < 0 || x0 > MATRIX_WIDTH - 2) {
			System.out.printf("Bad data: X coordinate %d is out of range");
			return (-1);
		}
		if (y0 < 0 || y0 > MATRIX_HEIGHT - 2) {
			System.out.printf("Bad data: X coordinate %d is out of range");
			return (-1);
		}
		int sum00 = matrix[x0][y0] + matrix[x0][y0 + 1] + matrix[x0][y0 + 2];
		int sum01 = matrix[x0 + 1][y0 + 1];
		int sum02 = matrix[x0 + 2][y0] + matrix[x0 + 2][y0 + 1] + matrix[x0 + 2][y0 + 2];
		int maxSumValue = sum00 + sum01 + sum02;
		return (maxSumValue);
	}
}
