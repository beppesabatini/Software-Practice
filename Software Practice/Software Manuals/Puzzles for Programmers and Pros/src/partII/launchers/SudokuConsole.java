package partII.launchers;

/**
 * This opens a command line and runs a different class in the same jar
 * @author Brandon Barajas
 */
import java.io.*;
import java.awt.GraphicsEnvironment;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class SudokuConsole {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Console console = System.console();
		if (console == null && !GraphicsEnvironment.isHeadless()) {
			String filename;
			if (args != null && args.length > 0) {
				filename = args[0];
			} else {
				CodeSource codeSource = SudokuConsole.class.getProtectionDomain().getCodeSource();
				filename = codeSource.getLocation().toString().substring(6);
			}

			filename = filename.replace("%20", " ");
			String[] commandLine = new String[] { "cmd", "/c", "start", "cmd", "/k", "java -jar \"" + filename + "\"" };
			Runtime.getRuntime().exec(commandLine);
		} else {
			new SudokuSolverLauncher().solveSudokuPuzzles();
			System.out.println("Program has ended, please type 'exit' to close the console.");
		}
	}
}