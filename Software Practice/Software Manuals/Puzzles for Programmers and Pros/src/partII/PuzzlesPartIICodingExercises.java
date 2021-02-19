package partII;

import partII.launchers.*;

/**
 * Solutions for problems from the manual "Puzzles for Programmers and Pros."
 * This is the only "main()" function in the partII package. It will run all the
 * solutions for this manual which the author has posted to date, and output
 * short status messages for each one.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class PuzzlesPartIICodingExercises {

	public static void main(String[] args) {
		new SudokuSolverLauncher().solveSudokuPuzzles();
	}
}
