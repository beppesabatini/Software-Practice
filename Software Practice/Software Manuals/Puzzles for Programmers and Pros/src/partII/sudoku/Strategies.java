package partII.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Inner class managing the strategy objects. Calls to this will return
 * strategies which are progressively more intelligent and more expensive.
 */
public class Strategies {

	private List<SudokuStrategy> activeStrategies = new ArrayList<SudokuStrategy>();
	private int active = 0;

	private SudokuStrategy[] strategies = new SudokuStrategy[3];
	
	public Strategies(byte[] puzzle, SudokuStructure sudokuStructure, short[] prospects) {
		this.strategies[0] = new Eliminator(puzzle, sudokuStructure, prospects);
		this.strategies[1] = new HiddenSingleFinder(puzzle, sudokuStructure, prospects);
		this.strategies[2] = new NakedTupleFinder(sudokuStructure, prospects);

		activeStrategies.add(strategies[0]);
		active = 1;
	}

	public boolean addStrategy() {
		if (active >= strategies.length) {
			return (false);
		}
		active++;
		return (true);
	}

	public boolean removeStrategy() {
		if (active < 1) {
			return (false);
		}
		active--;
		return (true);
	}

	public void runStrategies() {
		for (int i = active - 1; i >= 0; i--) {
			strategies[i].tryStrategy();
		}
	}
}
