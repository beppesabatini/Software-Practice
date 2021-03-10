package hackerrank;

import java.lang.IllegalArgumentException;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a
 * https://www.hackerrank.com/challenges/ctci-is-binary-search-tree/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=trees">Trees:
 * Is This a Binary Search Tree?</a> problem. One way to test the solution is to
 * mouse all the code inside the outermost class definition into HackerRank.
 * <p/>
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class BinaryTreeTester {

	private static final String DEBUG = "false";

	private boolean[] dataValuesUsed = new boolean[1250];

	/**
	 * This function is the solution to the practice problem "Trees: Is This a
	 * Binary Search Tree?" The complete problem is posted on the HackerRank website
	 * (linked to below) and this solution can be tested there. The goal is to
	 * determine if a specified tree is well-formed and follows the rules for a
	 * binary tree.
	 * <p>
	 * Searching through a balanced binary tree is O(log-base2(N)), where N is the
	 * number of nodes in the tree. For the worst, most unbalanced tree, it's O(N).
	 * 
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/ctci-is-binary-search-tree/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=trees">Original
	 *      HackerRank Problem</a>
	 */
	public boolean checkBST(Node root) {
		if (root == null) {
			throw new IllegalArgumentException("The root Node is null");
		}
		boolean isBinarySearchTree = checkBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return (isBinarySearchTree);
	}

	private boolean checkBST(Node root, int minimum, int maximum) {
		if (root == null) {
			throw new IllegalArgumentException("The root Node is null");
		}

		if (dataValuesUsed[root.data] == true) {
			if (Boolean.valueOf(DEBUG) == true) {
				String message = "The value " + root.data + " is duplicated in the tree";
				System.out.println(message);
			}
			return (false);
		}

		dataValuesUsed[root.data] = true;

		if (root.left != null) {
			if (root.left.data < root.data == false) {
				if (Boolean.valueOf(DEBUG) == true) {
					String message = "";
					message += "The left child value " + root.left.data;
					message += " is greater than or equal to its parent value " + root.data;
					System.out.println(message);
				}
				return (false);
			}
			if (root.left.data >= maximum) {
				if (Boolean.valueOf(DEBUG) == true) {
					String message = "";
					message += "The left child value " + root.left.data;
					message += " is greater than or equal to the maximum value " + maximum;
					System.out.println(message);
				}
				return (false);
			}
			boolean isLeftBinary = checkBST(root.left, minimum, root.data);
			if (isLeftBinary == false) {
				return (false);
			}
		}

		if (root.right != null) {
			if (root.right.data > root.data == false) {
				if (Boolean.valueOf(DEBUG) == true) {
					String message = "";
					message += "The right child value " + root.right.data;
					message += " is less than or equal to the parent value" + root.data;
					System.out.println(message);
				}
				return (false);
			}
			if (root.right.data <= minimum) {
				if (Boolean.valueOf(DEBUG) == true) {
					String message = "";
					message += "The right child value " + root.right.data;
					message += " is less than or equal to the minimum value " + minimum;
					System.out.println(message);
				}
				return (false);
			}
			if (root.right != null) {
				boolean isRightBinary = checkBST(root.right, root.data, maximum);
				if (isRightBinary == false) {
					return (false);
				}
			}
		}

		if (root.left == null && root.right == null) {
			return (true);
		}
		return (true);
	}

	private static int integerWidth(int integer) {
		if (integer <= 0) {
			return (0);
		}
		int width = 0;
		while (integer > 0) {
			integer = integer / 10;
			width++;
		}
		return (width);
	}

	public static class Node {
		public int data;
		public Node left;
		public Node right;
		private StringBuilder leftMarginPadding;

		public Node(int data, Node leftNode, Node rightNode) {
			this.data = data;
			this.left = leftNode;
			this.right = rightNode;
			this.leftMarginPadding = new StringBuilder();
			this.leftMarginPadding.append("                ");
		}

		public String toString(Node node, int leftMargin) {
			if (node == null) {
				return ("");
			}

			if (leftMargin > leftMarginPadding.length()) {
				leftMarginPadding.append(leftMarginPadding);
			}
			StringBuilder rightTree = new StringBuilder();
			rightTree.append(leftMarginPadding, 0, leftMargin);

			rightTree.append(node.data);

			if (node.right == null) {
				rightTree.append(" R--> null");
			} else {
				rightTree.append(" R--> " + node.right.data);
			}
			rightTree.append("\n");

			int nodeDataWidth = integerWidth(node.data);

			StringBuilder leftTree = new StringBuilder();
			leftTree.append(leftMarginPadding, 0, leftMargin);
			leftTree.append(leftMarginPadding, 0, nodeDataWidth);

			if (node.left == null) {
				leftTree.append(" L--> null");
			} else {
				leftTree.append(" L--> " + node.left.data);
			}
			leftTree.append("\n");

			StringBuilder returnTree = new StringBuilder().append(rightTree.append(leftTree));

			leftMargin += (nodeDataWidth + 6);

			returnTree.append(toString(node.right, leftMargin));
			returnTree.append(toString(node.left, leftMargin));
			return (returnTree.toString());
		}
	}
}
