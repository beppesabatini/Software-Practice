package partII.sudoku;

import partII.sudoku.SudokuSolver.PuzzleState;

/**
 * This class does the core work of attempting to solve the puzzle through a
 * series of different strategies. Progressively more expensive and difficult
 * strategies will be attempted, until the puzzle is solved or until progress
 * halts.
 */
public class SudokuEngine {

	private final static int boxSize = SudokuSolver.BOX_SIZE;
	private final static int numberBoxes = SudokuSolver.NUMBER_BOXES;
	private final static int numberCells = SudokuSolver.NUMBER_CELLS;
	private final static int numberCols = SudokuSolver.NUMBER_COLS;
	private final static int numberRows = SudokuSolver.NUMBER_ROWS;

	/**
	 * Value used to set all nine bits in the prospect array to "On". Equivalent to
	 * 1111111110
	 */
	static final short ALL_PROSPECTS = 0x3fe;

	/**
	 * At start up-time, all the elements of the Sudoku matrix are mapped out here,
	 * for a fast and clean way to traverse the puzzle.
	 */
	private static final SudokuStructure sudokuStructure = new SudokuStructure();

	/**
	 * For each cell in the puzzle, maintain a bit field (in a "short") that
	 * represents possible values, 1 through 9, which are possible or prospect
	 * values for the cell. Zero out the bit if we can demonstrate that the value is
	 * impossible. The zero-position bit is not used. The 1 bit (second from the
	 * right) represents "one", the 2 bit represents "two", etc.
	 */
	/*
	 * 1111111110 987654321
	 */
	// Master copy -- all bits (prospects) set to one, or "possible," at load time
	private final static short[] masterCopyProspects = initMasterCopyProspects();

	private static short[] initMasterCopyProspects() {
		short[] masterCopyProspects = new short[numberCells];
		shortfill(masterCopyProspects, ALL_PROSPECTS);
		return (masterCopyProspects);
	}

	protected short[] prospects; // Cloned from the master copy at run time
	/**
	 * An array representing the Sudoku puzzle which we are trying to solve. This
	 * will usually be accessed through the "SudokoStructure" mapping.
	 */
	protected byte[] puzzle;

	SudokuEngine(byte[] puzzle) {
		this.puzzle = puzzle;
		// Clone the master copy
		this.prospects = masterCopyProspects.clone();
	}

	/**
	 * Driver loop for the strategies. This runs the cheapest algorithm first, until
	 * it stops making progress. It then add a second strategy, and runs both, until
	 * they stop making progress. It keeps adding strategies until it solves the
	 * puzzle or runs out of strategies.
	 * 
	 * @return PuzzleState
	 */
	protected PuzzleState solveByStrategy() {
		@SuppressWarnings("unused")
		int iterations = 0;
		int numberProspects = countProspects();
		Strategies strategies = new Strategies(puzzle, sudokuStructure, prospects);

		do {
			strategies.runStrategies();

			PuzzleState status = this.updateCells();
			if (status == PuzzleState.CORRUPT) {
				// System.out.printf("Puzzle corrupt");
				return (PuzzleState.CORRUPT);
			}
			iterations++;
			int newNumberProspects = countProspects();
			// System.out.println("New number prospects: " + newNumberProspects);
			// Return if done, or stalled, or too hard
			if (newNumberProspects == numberCells) {
				if (!validate()) {
					return (PuzzleState.CORRUPT);
				}
				return (PuzzleState.SOLVED);
			}
			if (numberProspects == newNumberProspects) {
				boolean moreToTry = strategies.addStrategy();
				if (moreToTry == false) {
					// System.out.printf("Stalled with %s iterations and %s prospects: \n",
					// iterations, numberProspects);
					// System.out.println(this);
					return (PuzzleState.INCOMPLETE);
				}
			}
			numberProspects = newNumberProspects;
		} while (this.puzzle != null);
		// Should never get here
		return (PuzzleState.INCOMPLETE);
	}

	public String toString() {
		String myString = "";
		int j = 1;
		for (int i = 0; i < numberCells; i += numberCols) {
			int k = i;
			myString += "" + puzzle[k++] + puzzle[k++] + puzzle[k++];
			myString += " | ";
			myString += "" + puzzle[k++] + puzzle[k++] + puzzle[k++];
			myString += " | ";
			myString += "" + puzzle[k++] + puzzle[k++] + puzzle[k++] + "\n";
			if (j % 3 == 0 && j < numberRows) {
				myString += "---------------\n";
			}
			j++;
		}
		return (myString);
	}

	/** Update the puzzles array, with solved values from the prospect array. */
	private PuzzleState updateCells() {
		for (int i = 0; i < numberCells; i++) {
			short prospectValue = prospects[i];
			if (prospectValue == 0) {
				// All possible values have been eliminated, the puzzle is corrupt
				// Usually means a wrong guess from the guessing logic
				return (PuzzleState.CORRUPT);
			}
			if (SudokuSolver.oneBitNumbers[prospectValue] > 0) {
				this.puzzle[i] = SudokuSolver.oneBitNumbers[prospectValue];
			}
		}
		return (PuzzleState.SOLVING);
	}

	/**
	 * Count the total number of prospects still remaining. When a puzzle is solved,
	 * this number will be equal to the number of cells in a puzzle.
	 */
	public int countProspects() {
		int numberProspects = 0;
		for (int i = 0; i < this.prospects.length; i++) {
			int currentProspects = SudokuSolver.bitsPerInteger[this.prospects[i]];
			numberProspects += currentProspects;
		}
		return (numberProspects);
	}

	/**
	 * This function is similar to the "memset()" function in C/C++. It sets all the
	 * values of an array (of shorts) to a specified value. The compiler will
	 * in-line this function to save the expense of a function call.
	 * 
	 * @param array The target array of shorts to be initialized
	 * @param value The specified value to be assigned
	 */
	private static void shortfill(short[] array, short value) {
		int len = array.length;
		if (len > 0)
			array[0] = value;
		for (int i = 1; i < len; i += i)
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
	}

	/**
	 * Confirm that we have filled in the puzzle correctly.
	 */
	private boolean validate() {
		int numberValidated = 0;
		// Walk through rows, columns, boxes
		for (int i = 0; i < sudokuStructure.containers.length; i++) {
			// Walk through all instances of the row/column/box
			for (int j = 0; j < boxSize; j++) {
				byte[] container = sudokuStructure.containers[i][j];
				short allProspects = ALL_PROSPECTS;
				// Walk through each element in the row/column/box
				// If the box is filled in correctly, every bit will get zeroed out
				for (byte k = 0; k < boxSize; k++) {
					allProspects &= SudokuSolver.masks[puzzle[container[k]]];
				}
				if (allProspects != 0) {
					return (false);
				}
				numberValidated++;
			}
		}
		// Validate the validator
		if (numberValidated != numberRows + numberCols + numberBoxes) {
			return (false);
		}
		return (true);
	}
}
