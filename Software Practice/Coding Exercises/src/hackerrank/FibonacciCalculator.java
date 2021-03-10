package hackerrank;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a
 * https://www.hackerrank.com/challenges/ctci-fibonacci-numbers/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking">Recursion:
 * Fibonacci Numbers</a> problem. One way to test the solution is to mouse all
 * the code inside the outermost class definition into HackerRank.
 * <p/>
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class FibonacciCalculator {

	private static final String DEBUG = "false";

	/**
	 * This function is the solution to the practice problem "Recursion: Fibonacci
	 * Numbers." The complete problem is posted on the HackerRank website (linked to
	 * below) and this solution can be tested there. The Fibonacci series is the
	 * oft-noted sequence in which each number is the sum of the two which precede
	 * it.
	 * <p>
	 * This solution exceeds the original HackerRank problem; it implements both a
	 * recursive and an iterative version of the Fibonacci function. Both versions
	 * perform exactly the same additions, so in that respect both functions are
	 * O(N). The recursive version has the additional expense of a function call for
	 * every addition, so we might say that the recursive version is O(2N) and the
	 * iterative version is O(N). Realistically, though, the difference is
	 * negligible; the test code invokes each method one million times, and while
	 * the recursive version does take about twice as long as the iterative version,
	 * the difference for one million function calls is about 50 milliseconds total.
	 * 
	 * @param sequenceNumber The Nth number in the Fibonacci series
	 * @return The specified Fibonacci number
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-fibonacci-numbers/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking">Original
	 *      HackerRank Problem</a>
	 */
	public static int fibonacci(int sequenceNumber) {
		int[] fibonaccis = new int[32];
		fibonaccis[0] = 0;
		fibonaccis[1] = 1;
		int nextToCalculate = 2;

		fibonacciRecursive(sequenceNumber, fibonaccis, nextToCalculate);
		return (fibonaccis[sequenceNumber]);
	}

	private static void fibonacciRecursive(int sequenceNumber, int[] fibonaccis, int nextToCalculate) {
		if (sequenceNumber == nextToCalculate - 1) {
			return;
		}

		if (sequenceNumber < 0 || sequenceNumber > 30) {
			throw new IllegalArgumentException("Fibonacci sequence number must be between 0 and 30");
		}
		if (sequenceNumber == 0 || sequenceNumber == 1) {
			return;
		}
		fibonaccis[nextToCalculate] = fibonaccis[nextToCalculate - 1] + fibonaccis[nextToCalculate - 2];
		fibonacciRecursive(sequenceNumber, fibonaccis, nextToCalculate + 1);
	}

	public static int fibonacciIterative(int sequenceNumber) {
		int[] fibonaccis = new int[32];
		fibonaccis[0] = 0;
		fibonaccis[1] = 1;

		int i;
		for (i = 2; i <= sequenceNumber; i++) {
			fibonaccis[i] = fibonaccis[i - 1] + fibonaccis[i - 2];
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println("Sequence Number: " + i);
				System.out.println("Fibonacci Number: " + fibonaccis[i]);
			}
		}

		return (fibonaccis[sequenceNumber]);
	}

}
