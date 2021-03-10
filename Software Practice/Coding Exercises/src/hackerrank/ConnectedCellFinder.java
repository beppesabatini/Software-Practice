package hackerrank;

import java.util.Arrays;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a
 * https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=graphs">DFS:
 * Connected Cell in a Grid</a> problem. One way to test the solution is to
 * mouse all the code inside the outermost class definition into HackerRank.
 * <p/>
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class ConnectedCellFinder {

	private static final String DEBUG = "false";

	/**
	 * This function is the solution to the practice problem "DFS: Connected Cell in
	 * a Grid." The complete problem is posted on the HackerRank website (linked to
	 * below) and this solution can be tested there.
	 * <p/>
	 * The solution employs depth first search and treats the matrix like an octal
	 * (base 8) tree. The program recurses in eight different directions to find the
	 * largest contiguous region of ones in the test matrix.
	 * <p/>
	 * Each octal search takes on average log-base-8 operations to find the largest
	 * region of ones. In a matrix size A x B, the algorithm will consider each
	 * element as a possible starting point, and will launch A x B searches, and
	 * remember the size of largest region. So in the worst case this will take A x
	 * B octal searches, and thus A x B log-base-8 searches makes this algorithm
	 * O(NlogN). This is where N is equal to A times B, the total number of elements
	 * in the matrix. Realistically, the implementation below has been optimized to
	 * the point where even a 70 x 70 matrix finishes in less than a millisecond.
	 * 
	 * <pre>
	 *  | 7 | 6 | 5 |
	 *  -------------
	 *  | 0 | * | 4 |
	 *  -------------
	 *  | 1 | 2 | 3 |
	 *  
	 *  0: x-1, y
	 *  1: x-1, y+1
	 *  2:   x, y+1
	 *  3: x+1, y+1
	 *  4: x+1, y
	 *  5: x+1, y-1
	 *  6:   x, y-1
	 *  7: x-1, y-1
	 * 
	 * </pre>
	 * 
	 * @param grid An M x N two-dimensional matrix of integers, to be analyzed for
	 *             the largest contiguous region of ones.
	 * @return The number of cells in the largest region found.
	 * 
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=graphs">Original
	 *      HackerRank Problem</a>
	 */
	public static int maxRegion(int[][] grid) {

		if (Boolean.valueOf(DEBUG) == true) {
			new VisitTracker(4, 8).testVisitTracker();
		}

		int totalFilledCells = countTotalFilledGridCells(grid);

		int height = grid.length;
		int width = grid[0].length;

		int maximumRegionFound = Integer.MIN_VALUE;
		for (int yValue = 0; yValue < height; yValue++) {
			for (int xValue = 0; xValue < width; xValue++) {
				Accumulator accumulator = new Accumulator();
				VisitTracker visitTracker = new VisitTracker(height, width);
				depthFirstSearch(grid, xValue, yValue, accumulator, visitTracker);
				if (accumulator.total > maximumRegionFound) {
					maximumRegionFound = accumulator.total;
				}
				if (maximumRegionFound >= totalFilledCells) {
					return (maximumRegionFound);
				}
			}
		}
		return (maximumRegionFound);
	}

	private static class Accumulator {
		public int total;

		public Accumulator() {
			this.total = 0;
		}
	}

	private static void depthFirstSearch(int[][] grid, int xValue, int yValue, Accumulator accumulator,
			VisitTracker visitTracker) {
		if (grid == null) {
			return;
		}
		int height = grid.length;
		int width = grid[0].length;

		if (height <= 0 || width <= 0) {
			return;
		}
		if (xValue < 0 || yValue < 0) {
			return;
		}
		if (xValue >= width || yValue >= height) {
			return;
		}
		if (visitTracker == null) {
			return;
		}
		if (visitTracker.getVisited(xValue, yValue) == true) {
			return;
		}

		// Note that the X and Y coordinates are in reverse order here.
		// It's due to way that Java constructs int[][], a two-dimensional
		// array, perhaps counterintuitive.
		if (grid[yValue][xValue] == 0) {
			// Dead end
			visitTracker.setVisited(xValue, yValue, true);
			return;
		}
		if (grid[yValue][xValue] == 1) {
			// Keep the contiguous cells region growing
			accumulator.total++;
		}
		visitTracker.setVisited(xValue, yValue, true);

		// 00
		depthFirstSearch(grid, xValue - 1, yValue, accumulator, visitTracker);
		// 01
		depthFirstSearch(grid, xValue - 1, yValue + 1, accumulator, visitTracker);
		// 02
		depthFirstSearch(grid, xValue, yValue + 1, accumulator, visitTracker);
		// 03
		depthFirstSearch(grid, xValue + 1, yValue + 1, accumulator, visitTracker);
		// 04
		depthFirstSearch(grid, xValue + 1, yValue, accumulator, visitTracker);
		// 05
		depthFirstSearch(grid, xValue + 1, yValue - 1, accumulator, visitTracker);
		// 06
		depthFirstSearch(grid, xValue, yValue - 1, accumulator, visitTracker);
		// 07
		depthFirstSearch(grid, xValue - 1, yValue - 1, accumulator, visitTracker);
	}

	private static int countTotalFilledGridCells(int[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		if (height <= 0 || width <= 0) {
			throw new IllegalArgumentException("Illegal values for height or width");
		}
		int totalFilledCells = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (grid[y][x] == 1) {
					totalFilledCells++;
				}
			}
		}
		return (totalFilledCells);
	}

	final static public class VisitTracker {
		private boolean[][] visited;
		private int height;
		private int width;

		VisitTracker(int height, int width) {
			this.height = height;
			this.width = width;
			this.visited = new boolean[height][width];
		}

		public int getHeight() {
			return (this.height);
		}

		public int getWidth() {
			return (this.width);
		}

		public void setVisited(int Xvalue, int Yvalue, boolean state) {
			checkXYCoordinates(Xvalue, Yvalue);
			visited[Yvalue][Xvalue] = state;
		}

		public boolean isVisited(int Xvalue, int Yvalue) {
			checkXYCoordinates(Xvalue, Yvalue);
			return (visited[Yvalue][Xvalue]);
		}

		public boolean getVisited(int Xvalue, int Yvalue) {
			checkXYCoordinates(Xvalue, Yvalue);
			return (isVisited(Xvalue, Yvalue));
		}

		private void checkXYCoordinates(int Xvalue, int Yvalue) {
			if (Xvalue > this.width - 1) {
				String message = "X value " + Xvalue + " is greater than grid width " + this.width;
				throw new IllegalArgumentException(message);
			}
			if (Yvalue > this.height - 1) {
				String message = "Y value " + Yvalue + " is greater than grid height " + this.height;
				throw new IllegalArgumentException(message);
			}
		}

		public void clear() {
			this.visited = new boolean[this.height][this.width];
		}

		public void outputGrid() {
			for (int i = 0; i < height; i++) {
				System.out.println(Arrays.toString(visited[i]));
			}
		}

		private void testVisitTracker() {
			VisitTracker testTracker = new VisitTracker(4, 8);
			testTracker.outputGrid();
			System.out.println();

			testTracker.setVisited(7, 3, true);
			testTracker.outputGrid();
			System.out.println("");

			System.out.println(testTracker.getVisited(7, 3));
			System.out.println("");

			System.out.println(testTracker.isVisited(7, 3));
			System.out.println("");

			testTracker.setVisited(7, 3, false);
			System.out.println(testTracker.isVisited(7, 3));
			System.out.println("");

			for (int i = 0; i < 3; i++) {
				testTracker.setVisited(i, i, true);
			}
			testTracker.outputGrid();
			System.out.println("");

			testTracker.clear();
			testTracker.outputGrid();
			System.out.println();
		}
	}
}
