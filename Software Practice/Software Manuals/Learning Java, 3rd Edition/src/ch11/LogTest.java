package ch11;

import java.util.logging.*;

// From p. 383.
public class LogTest {
	public static void main(String argv[]) {
		Logger logger = Logger.getLogger("com.oreilly.LogTest");

		logger.severe("Power lost - running on backup!");
		logger.warning("Database connection lost, retrying...");
		logger.info("Startup complete.");
		logger.config("Server configuration: standalone, JVM version 1.4");
		logger.fine("Loading graphing package.");
		logger.finer("Doing pie chart");
		logger.finest("Starting bubble sort: value =" + 42);
	}
}
