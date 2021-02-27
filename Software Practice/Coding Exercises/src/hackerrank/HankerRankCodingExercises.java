package hackerrank;

import hackerrank.launchers.*;

/**
 * This is the only "main()" function in the hackerrank package. It will run all
 * the solutions the author has posted to date, and output short status messages
 * for each one.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class HankerRankCodingExercises {

	public static void main(String[] args) {

		// Warm-up Challenges
		// https://www.hackerrank.com/interview/interview-preparation-kit/warmup/challenges
		new SockPairCounterLauncher().salesByMatch();
		new ValleyCounterLauncher().countingValleysExercise();
		new CloudJumperLauncher().jumpingOnTheClouds();
		new RepeatingStringAnalyzerLauncher().analyzeRepeatedString();

		// Arrays
		// https://www.hackerrank.com/interview/interview-preparation-kit/arrays/challenges
		new MaxHourglassSumFinderLauncher().findMaximumHourglassSum();

		// Dictionaries and Hashmaps
		// https://www.hackerrank.com/interview/interview-preparation-kit/dictionaries-hashmaps/challenges
		new RansomNoteValidatorLauncher().validateNoteAgainstMagazine();
	}
}
