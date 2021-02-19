package partII.sudoku;

/**
 * At start up-time, all the elements of the Sudoku matrix are mapped out here,
 * for a fast and clean way to traverse the puzzle.
 */
public class SudokuStructure {

	private final static int boxEdge = SudokuSolver.BOX_EDGE;
	private final static  int boxSize = SudokuSolver.BOX_SIZE;
	private final static  int numberBoxes = SudokuSolver.NUMBER_BOXES;
	private final static  int numberCells = SudokuSolver.NUMBER_CELLS;
	private final static  int numberCols = SudokuSolver.NUMBER_COLS;
	private final static  int numberRows = SudokuSolver.NUMBER_ROWS;
	private final static  int tierSize = SudokuSolver.TIER_SIZE;

	public class Cell {
		public byte row;
		public byte column;
		public byte box;
	}

	public Cell[] cells;

	public byte[][] rows;
	public byte[][] columns;
	public byte[][] boxes;

	/** "Container" is a generalized term for either a row, a column, or a box. **/
	byte[][][] containers;

	public SudokuStructure() {
		// System.out.println("Initializing SudokuStructure");
		this.cells = new Cell[numberCells];

		this.rows = new byte[numberRows][numberCols];
		this.columns = new byte[numberCols][numberRows];
		this.boxes = new byte[numberBoxes][boxSize];

		// Cross-reference everything
		for (byte i = 0; i < numberCells; i++) {
			this.cells[i] = new Cell();

			byte row = (byte) (i / numberRows);
			byte column = (byte) (i % numberRows);
			this.cells[i].row = row;
			this.cells[i].column = column;

			this.rows[row][column] = i;
			this.columns[column][row] = i;
		}
		// Walk through tiers (3 x 9 rectangles)
		for (byte tier = 0, boxNumber = 0; tier < numberCells; tier += tierSize) {
			// Walk through boxes (3 x 3 squares)
			for (byte box = tier; box < tier + numberCols; box += boxEdge, boxNumber++) {
				// Walk through box-rows
				for (byte row = box, boxIndex = 0; row < box + tierSize; row += numberCols) {
					// Walk through box-columns
					for (byte cell = row; cell < row + boxEdge; cell++, boxIndex++) {
						this.cells[cell].box = boxNumber;
						this.boxes[boxNumber][boxIndex] = cell;
					}
				}
			}
		}
		this.containers = new byte[3][][];
		this.containers[0] = this.rows;
		this.containers[1] = this.columns;
		this.containers[2] = this.boxes;
	}
}
