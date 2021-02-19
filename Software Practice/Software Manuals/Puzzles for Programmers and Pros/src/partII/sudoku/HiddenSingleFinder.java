package partII.sudoku;

/**
 * Make a temp copy of the prospect array, and eliminate any values which appear
 * in more than one cell. Any singleton values which remain are "hidden
 * singles". If a prospect appears exclusively in one cell, it can be safely
 * assigned to fill in a cell in the puzzle.
 */
public class HiddenSingleFinder implements SudokuStrategy {

	private byte[] puzzle;
	private SudokuStructure sudokuStructure;
	private short[] prospects;

	public HiddenSingleFinder(byte[] puzzle, SudokuStructure sudokuStructure, short[] prospects) {
		this.puzzle = puzzle;
		this.sudokuStructure = sudokuStructure;
		this.prospects = prospects;
	}

	public void tryStrategy() {

		short[] possibleSingles;
		// Walk through rows, columns, boxes
		for (int i = 0; i < sudokuStructure.containers.length; i++) {
			possibleSingles = prospects.clone();
			// Walk through all instances of the row/column/box
			for (int j = 0; j < SudokuSolver.BOX_SIZE; j++) {
				byte[] container = sudokuStructure.containers[i][j];
				// Do Gauss series on each row/column/box: (n * (n + 1)/2)
				for (byte k = 0; k < SudokuSolver.BOX_SIZE; k++) {
					for (byte m = (byte) (k + 1); m < SudokuSolver.BOX_SIZE; m++) {
						short doubles = (short) (prospects[container[k]] & prospects[container[m]]);
						// Zero out doubles in the temp copy
						possibleSingles[container[k]] &= ~doubles;
						possibleSingles[container[m]] &= ~doubles;
					}
				}
				for (byte n : container) {
					short bitValue = possibleSingles[n];
					byte byteValue = 0;
					if ((byteValue = SudokuSolver.oneBitNumbers[bitValue]) != 0) {
						// A unique bit, we found a hidden single
						puzzle[n] = byteValue;
						prospects[n] = bitValue;
					}
				}
			}
		}
	}
}
