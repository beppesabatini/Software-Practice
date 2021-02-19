package partII.sudoku;

/**
 * 
 * A Sudoku puzzle looks like this:
 * 
 * <pre>
  000 | 000 | 007
  704 | 000 | 893
  006 | 802 | 000
  ---------------
  107 | 528 | 600
  080 | 036 | 701
  903 | 400 | 080
  ---------------
  000 | 704 | 900
  600 | 090 | 000
  459 | 003 | 108
 * </pre>
 * 
 * The SudokuSolver class uses a combination of different strategies to solve a
 * Sudoku puzzle--a number puzzle which resembles a crossword puzzle. The digits
 * 1 through 9 should appear once in each row, once in each column, and once in
 * each 3 x 3 box. Zeroes represent blanks to be filled in.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class SudokuSolver {
	/** The four possible states in which a puzzle can be. */
	protected enum PuzzleState {
		CORRUPT, SOLVING, SOLVED, INCOMPLETE
	};

	static public final short BOX_EDGE = 3;
	static public final short BOX_SIZE = BOX_EDGE * BOX_EDGE;
	static public final short NUMBER_ROWS = BOX_SIZE;
	static public final short NUMBER_COLS = BOX_SIZE;
	static public final short NUMBER_BOXES = BOX_SIZE;
	static public final short TIER_SIZE = BOX_SIZE * 3;
	static public final short NUMBER_CELLS = NUMBER_ROWS * NUMBER_COLS;

	/**
	 * Bitmasks used to zero out bits in the one position, the two position, etc.
	 **/
	static public final short masks[] = initMasks();

	private static short[] initMasks() {
		short masks[] = new short[10];
		masks[0] = ~(1 << 0); // 0000000001 // 001 // 1111111110
		masks[1] = ~(1 << 1); // 0000000010 // 002 // 1111111101
		masks[2] = ~(1 << 2); // 0000000100 // 004 // 1111111011
		masks[3] = ~(1 << 3); // 0000001000 // 008 // 1111110111
		masks[4] = ~(1 << 4); // 0000010000 // 016 // 1111101111
		masks[5] = ~(1 << 5); // 0000100000 // 032 // 1111011111
		masks[6] = ~(1 << 6); // 0001000000 // 064 // 1110111111
		masks[7] = ~(1 << 7); // 0010000000 // 128 // 1101111111
		masks[8] = ~(1 << 8); // 0100000000 // 256 // 1011111111
		masks[9] = ~(1 << 9); // 1000000000 // 512 // 0111111111
		return (masks);
	}

	/**
	 * A look-up table which will 1) be non-zero for numbers with only one bit in
	 * them, and 2) map from the bit-shifted value to the integer value. This is a
	 * quick way to work with cells with only one bit set--i.e. one prospective
	 * value--i.e. solved cells.
	 */
	public static byte[] oneBitNumbers;

	private static void initOneBitNumbers() {
		oneBitNumbers = new byte[1024];
		for (byte i = 1; i < 10; i++) {
			oneBitNumbers[1 << i] = i;
		}
	}

	/**
	 * A look up table--given an integer value, reference how many bits it takes to
	 * represent that integer. Since each bit in the prospects array represents a
	 * possible answer for the cell, this table can tell us how many candidate
	 * values a cell has.
	 */
	protected static byte[] bitsPerInteger;

	private static void initBitsPerInteger() {
		bitsPerInteger = new byte[1024];
		for (short i = 1; i < 1024; i++) {
			byte numBits = 0;
			for (int j = 0; j < 10; j++) {
				if ((i & ~masks[j]) != 0) {
					numBits++;
				}
			}
			bitsPerInteger[i] = numBits;
		}
	}

	public SudokuSolver() {

		// Initialize look-up tables of frequently used bitmasks.
		// This is one of many devices used to optimize speed.
		initBitsPerInteger();
		initOneBitNumbers();
	}

	/**
	 * This is the entry point to this file and the program.
	 * 
	 * @param sudokuData An array of sudoku puzzles (currently nine), each
	 *                   represented as an 81 character String.
	 */
	public void solveSudokus(String[] sudokuData) {

		// Start time for solving all puzzles
		long startUpStartTime = System.currentTimeMillis();
		for (int i = 0; i < sudokuData.length; i++) {
			// Start time to solving the current puzzle
			long startTime = System.currentTimeMillis();
			Sudoku sudoku = new Sudoku(sudokuData[i]);
			sudoku.solvePuzzle();
			long endTime = System.currentTimeMillis();
			System.out.printf("%s. Run time - %s milliseconds\n\n", i + 1, (endTime - startTime));
		}
		double totalTime = System.currentTimeMillis() - startUpStartTime;
		String formatString = "Total time: %s milliseconds - Average Time: %.2f milliseconds";
		System.out.printf(formatString, totalTime, totalTime / (double) sudokuData.length);
	}
}
