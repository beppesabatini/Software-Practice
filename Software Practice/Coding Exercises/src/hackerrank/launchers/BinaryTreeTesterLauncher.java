package hackerrank.launchers;

import hackerrank.BinaryTreeTester;
import hackerrank.BinaryTreeTester.Node;

/**
 * This class launches the author's solution to the HackerRank "Trees: Is This a
 * Binary Search Tree?" problem. Test data, monitoring code, logging code, debug
 * output, and similar support code can be included here.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class BinaryTreeTesterLauncher {

	@SuppressWarnings("unused")
	private static Node testTree00 = initTestTree00();

	private static Node initTestTree00() {
		// The only test Hacker applies before submitting. Correct.
		Node node02 = new Node(2, new Node(1, null, null), new Node(3, null, null));
		Node node06 = new Node(6, new Node(5, null, null), new Node(7, null, null));
		Node node04 = new Node(4, node02, node06);
		return (node04);
	}

	@SuppressWarnings("unused")
	private static Node testTree01 = initTestTree01();

	private static Node initTestTree01() {
		// The first example on the HackerRank site; posted as an example of a
		// correct tree, but in fact it's wrong. The 5 is to the left of the 4, and 5
		// is not less than 4.
		Node node02 = new Node(2, new Node(1, null, null), null);
		Node node04 = new Node(4, new Node(5, null, null), new Node(6, null, null));
		Node node03 = new Node(3, node02, node04);
		return (node03);
	}

	@SuppressWarnings("unused")
	private static Node testTree02 = initTestTree02();

	private static Node initTestTree02() {
		// A (deliberately) incorrect tree posted on the HackerRank site.
		Node node02 = new Node(2, new Node(1, null, null), null);
		Node node05 = new Node(5, new Node(6, null, null), new Node(1, null, null));
		// Switch 6 and 1
		// Node node05 = new Node(5, new Node(1, null, null), new Node(6, null, null) );
		Node node03 = new Node(3, node02, node05);
		return (node03);
	}

	@SuppressWarnings("unused")
	private static Node testTree03 = initTestTree03();

	private static Node initTestTree03() {
		// A (deliberately) incorrect tree.
		Node node09 = new Node(9, null, new Node(10, new Node(11, null, null), null));
		Node node07 = new Node(7, null, new Node(8, node09, null));
		Node node02 = new Node(2, new Node(1, null, null), null);
		Node node04 = new Node(4, new Node(5, null, null), new Node(6, node07, null));
		Node node03 = new Node(3, node02, node04);
		return (node03);
	}

	private static Node testTree04 = initTestTree04();

	private static Node initTestTree04() {
		// From the Wikipedia page on binary trees, extended. A correct tree.
		Node node10 = new Node(10, null, new Node(14, new Node(13, null, null), null));
		Node node06 = new Node(6, new Node(4, null, new Node(5, null, null)), new Node(7, null, null));
		Node node03 = new Node(3, new Node(1, null, null), node06);
		Node node08 = new Node(8, node03, node10);
		return (node08);
	}

	// Change this to whichever tree you want to validate
	private static final Node testTree = testTree04;

	public void validateBinaryTree() {

		System.out.println("--- Trees: Is This a Binary Tree? ---");
		String testTreeString = testTree.toString(testTree, 0);
		System.out.println("Test tree: \n" + testTreeString);

		long startTime = System.currentTimeMillis();
		// This function is the solution to the HackerRank problem.
		boolean isBinaryTree = new BinaryTreeTester().checkBST(testTree);
		long endTime = System.currentTimeMillis();

		System.out.println("Tree is Binary: " + isBinaryTree);
		System.out.println("Execution time: " + (endTime - startTime) + " ms");
		System.out.println("");
	}
}
