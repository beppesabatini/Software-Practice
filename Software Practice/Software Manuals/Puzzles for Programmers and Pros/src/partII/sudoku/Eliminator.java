package partII.sudoku;

/**
 * For every blank cell in the puzzle, the system maintains a bit field of nine
 * possible legal values, and zeros out any which are eliminated. This function
 * mechanically walks through every cell whose value is known, and removes those
 * candidates from the "prospects" array which can be eliminated.
 */
public class Eliminator implements SudokuStrategy {
	private byte[] puzzle;
	private SudokuStructure sudokuStructure;
	private short[] prospects;
	
	Eliminator(byte[] puzzle, SudokuStructure sudokuStructure, short[] prospects){
		this.puzzle = puzzle;
		this.sudokuStructure = sudokuStructure;
		this.prospects = prospects;
	}
	public void tryStrategy() {

		for (int i = 0; i < SudokuSolver.NUMBER_CELLS; i++) {
			int value = puzzle[i];
			if (value == 0) {
				continue;
			}
			// Rows
			for (byte j : sudokuStructure.rows[sudokuStructure.cells[i].row]) {
				if (i == j) {
					prospects[j] = (short) (1 << value);
					continue;
				}
				prospects[j] &= SudokuSolver.masks[value];
			}
			// Columns
			for (byte k : sudokuStructure.columns[sudokuStructure.cells[i].column]) {
				if (i == k) {
					continue;
				}
				prospects[k] &= SudokuSolver.masks[value];
			}
			// Boxes
			for (byte m : sudokuStructure.boxes[sudokuStructure.cells[i].box]) {
				if (i == m) {
					continue;
				}
				prospects[m] &= SudokuSolver.masks[value];
			}
		}
	}
}
