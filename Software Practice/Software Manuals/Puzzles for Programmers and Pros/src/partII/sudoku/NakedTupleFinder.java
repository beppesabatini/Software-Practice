package partII.sudoku;

import java.util.HashMap;
import java.util.Map;

/**
 * If the same pair of two prospects appears in exactly two places, then those
 * two prospect values can be eliminated from every other cell in the current
 * space. Likewise for three values in three places, and four values in four
 * places. "Tuple" here means either a pair or a triple or a quadruple.
 */
public class NakedTupleFinder implements SudokuStrategy {

	private SudokuStructure sudokuStructure;
	private short[] prospects;

	NakedTupleFinder(SudokuStructure sudokuStructure, short[] prospects) {
		this.sudokuStructure = sudokuStructure;
		this.prospects = prospects;
	}

	public void tryStrategy() {
		Map<Short, Integer> prospectCount = new HashMap<Short, Integer>();

		// Walk through rows, columns, boxes
		for (int i = 0; i < sudokuStructure.containers.length; i++) {
			// Walk through all instances of the row/column/box
			for (int j = 0; j < SudokuSolver.BOX_SIZE; j++) {
				byte[] container = sudokuStructure.containers[i][j];
				prospectCount.clear();
				// Walk through each element in the row/column/box
				for (byte k = 0; k < SudokuSolver.BOX_SIZE; k++) {
					short prospect = prospects[container[k]];
					byte numBits = SudokuSolver.bitsPerInteger[prospect];
					if (numBits < 2 || numBits > 4) {
						continue;
					}
					Object value = prospectCount.get(prospect);
					int newValue = (value == null ? 1 : (Integer) value + 1);
					prospectCount.put(prospect, newValue);
				}
				for (Short prospect : prospectCount.keySet()) {
					int numberProspects = prospectCount.get(prospect);
					if (numberProspects < 2 || numberProspects > 4) {
						continue;
					}
					for (int m = 2; m <= 4; m++) {
						if (numberProspects == m && SudokuSolver.bitsPerInteger[prospect] == m) {
							// We found a naked tuple. Find the bit masks that
							// represent the values stored in the tuple.
							for (int n = 1; n < 10; n++) {
								if ((prospect & ~SudokuSolver.masks[n]) == 0) {
									continue;
								}
								// Now we know "n" is one of the tuple values
								for (int p = 0; p < SudokuSolver.BOX_SIZE; p++) {
									// Skip the actual tuples we found.
									if (prospects[container[p]] == prospect) {
										continue;
									}
									// Zero out the tuple value from all non-tuple prospects.
									prospects[container[p]] &= SudokuSolver.masks[n];
								}
							}
						}
					}
				}
			}
		}
	}
}
