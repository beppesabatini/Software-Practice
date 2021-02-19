package partII.sudoku;

import partII.sudoku.SudokuSolver.PuzzleState;

public class Sudoku {
	/**
	 * Class representing a Sudoku puzzle. This class calls the Sudoku Engine, which
	 * does the real logical or strategic work. If the strategies fail to find an
	 * answer, the Sudoku object resolves the puzzle by guessing.
	 */
	SudokuEngine sudokuEngine;

	Sudoku(String puzzleStr) {
		if (puzzleStr == null || "".equals(puzzleStr) || puzzleStr.length() != SudokuSolver.NUMBER_CELLS) {
			System.out.println("Bad puzzle: " + puzzleStr);
			return;
		}
		// The input value is a String, convert it to an array of bytes
		// for faster processing.
		byte[] puzzle = new byte[SudokuSolver.NUMBER_CELLS];
		for (int i = 0; i < SudokuSolver.NUMBER_CELLS; i++) {
			puzzle[i] = (byte) (Integer.valueOf(puzzleStr.charAt(i)) - '0');
		}
		sudokuEngine = new SudokuEngine(puzzle);
		System.out.println("Before: ");
		System.out.print(sudokuEngine);
	}

	public void solvePuzzle() {

		PuzzleState status = sudokuEngine.solveByStrategy();
		if (status == PuzzleState.SOLVED) {
			System.out.println("\nAfter Strategy: ");
			System.out.println(sudokuEngine);
			return;
		}

		for (int i = 0; i < SudokuSolver.NUMBER_CELLS; i++) {
			if (sudokuEngine.puzzle[i] != 0) {
				continue;
			}
			short prospects = sudokuEngine.prospects[i];
			// Try out each of the possible prospects, one at a time
			for (byte j = 1; j < 10; j++) {
				if ((prospects & ~SudokuSolver.masks[j]) != 0) {
					byte[] newPuzzle = sudokuEngine.puzzle.clone();
					// Guess!
					newPuzzle[i] = j;
					SudokuEngine newGuess = new SudokuEngine(newPuzzle);
					// See if the new guess will resolve to a solution
					status = newGuess.solveByStrategy();
					if (status == PuzzleState.SOLVED) {
						System.out.println("\nSolution: ");
						System.out.println(newGuess);
						return;
					}
					if (status == PuzzleState.CORRUPT) {
						// System.out.println("\nEliminating bad guess ");
						prospects &= SudokuSolver.masks[j];
						sudokuEngine.prospects[i] = prospects;
					}
				}
			}
		}
		System.out.println("Couldn't solve -- corrupt or too hard.");
		System.out.println(sudokuEngine);
	}
}
