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
public class HackerRankCodingExercises {

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

		// Sorting
		// https://www.hackerrank.com/interview/interview-preparation-kit/sorting/challenges
		new BubbleSorterLauncher().bubbleSortArray();

		// String Manipulation
		// https://www.hackerrank.com/interview/interview-preparation-kit/strings/challenges
		new AlternatingCharactersLauncher().enforceAlternatingCharacters();

		// Greedy Algorithms
		// https://www.hackerrank.com/interview/interview-preparation-kit/greedy-algorithms/challenges
		new LuckBalancerLauncher().findLuckiestBalance();

		// Search
		// https://www.hackerrank.com/interview/interview-preparation-kit/search/challenges
		new TreeNodeSwapperLauncher().swapTreeNodes();

		// Dynamic Programming
		// https://www.hackerrank.com/interview/interview-preparation-kit/dynamic-programming/challenges
		new ArraySubsetMaximizerLauncher().maximizeArraySubsetSum();

		// Stacks and Queues
		// https://www.hackerrank.com/interview/interview-preparation-kit/stacks-queues/challenges
		DoubleStackQueueLauncher.testDoubleStackQueue();
		
		// Graphs
		// https://www.hackerrank.com/interview/interview-preparation-kit/graphs/challenges
		new ConnectedCellFinderLauncher().findMaximumConnectedCellRegionSize();
		
		// Trees
		// https://www.hackerrank.com/interview/interview-preparation-kit/trees/challenges
		new BinaryTreeTesterLauncher().validateBinaryTree();
		
	}
}
