package hackerrank.launchers;

import hackerrank.FibonacciCalculator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class launches the author's solution to the HackerRank "Recursion:
 * Fibonacci Numbers" problem. Test data, monitoring code, logging code, debug
 * output, and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class FibonacciCalculatorLauncher {

	private static Logger logger = Logger.getLogger(FibonacciCalculatorLauncher.class.getName());

	@SuppressWarnings("unused")
	private static int sequenceNumber00 = -1; // throws exception
	@SuppressWarnings("unused")
	private static int sequenceNumber01 = 0; // returns 0
	@SuppressWarnings("unused")
	private static int sequenceNumber02 = 3; // returns 2
	@SuppressWarnings("unused")
	private static int sequenceNumber03 = 6; // returns 8

	private static int sequenceNumber04 = 30; // returns 832040
	@SuppressWarnings("unused")
	private static int sequenceNumber05 = 31; // throws exception

	private static int sequenceNumber = sequenceNumber04;

	private enum CalculationMethod {
		RECURSIVE, ITERATIVE
	};

	public void calculateFibonacciNumbers() {

		System.out.println("--- Recursion: Fibonacci Numbers ---");
		System.out.println("Sequence number: " + sequenceNumber);

		int numberRepeatTests = 1000000;
		int recursiveNumber = fibonacciTest(sequenceNumber, numberRepeatTests, CalculationMethod.RECURSIVE);
		int iterativeNumber = fibonacciTest(sequenceNumber, numberRepeatTests, CalculationMethod.ITERATIVE);
		if (recursiveNumber != iterativeNumber) {
			String errorMessage = "";
			errorMessage += "Recursive result " + recursiveNumber;
			errorMessage += " is not equal to iterative result " + iterativeNumber;
			logger.log(Level.SEVERE, errorMessage);
			System.exit(1);
		}
		System.out.println("Fibonacci number: " + recursiveNumber);

	}

	private int fibonacciTest(int sequenceNumber, int numberRepeatTests, CalculationMethod calculationMethod) {
		int fibonacciNumber = 0;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < numberRepeatTests; i++) {
			if (calculationMethod == CalculationMethod.RECURSIVE) {
				fibonacciNumber = FibonacciCalculator.fibonacci(sequenceNumber);
			} else if (calculationMethod == CalculationMethod.ITERATIVE) {
				fibonacciNumber = FibonacciCalculator.fibonacciIterative(sequenceNumber);
			}
		}
		long endTime = System.currentTimeMillis();

		String executionTimeMessage = "";
		executionTimeMessage += "Execution time for " + numberRepeatTests + " " + calculationMethod.name().toLowerCase() + " tests: ";
		executionTimeMessage += (endTime - startTime) + " ms";
		System.out.println(executionTimeMessage);

		return (fibonacciNumber);
	}
}
