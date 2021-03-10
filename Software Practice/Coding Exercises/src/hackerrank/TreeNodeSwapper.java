package hackerrank;

import java.util.ArrayList;
import java.util.List;

/**
 * HackerRank provides many sample test questions on its site. This class is one
 * solution to the <a href=
 * https://www.hackerrank.com/challenges/swap-nodes-algo/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=search">Swap
 * Nodes [Algo]</a> problem. One way to test the solution is to mouse all the
 * code inside the class definition into HackerRank.
 * 
 * @author Beppe Sabatini bsabatini@hotmail.com
 *
 */
public class TreeNodeSwapper {

	private static final String DEBUG = "false";
	private static final int ROOT_NODE = 1;
	private static final int NULL_NODE = -1;
	private static final int UNINITIALIZED = -2;

	static private enum DFSGoal {
		SWAP_NODES, VISIT_NODES
	}

	/**
	 * This function is the solution to the practice problem "Swap Nodes [Algo]."
	 * The complete problem is posted on the HackerRank website (linked to below)
	 * and this solution can be tested there. This implementation parses the input
	 * into a binary tree, and swaps the two child nodes for all parent nodes at
	 * certain specified levels of the tree. There isn't any particular difficulty
	 * in that concept; the problems arise mainly in trying to understand the format
	 * of the input data, and the specifications for the output. The binary tree
	 * should be traversed using the most common form of depth-first search, which
	 * always gives preference to the left child node.
	 * <p/>
	 * The worst run time is when a tree is entirely lopsided, and is made up
	 * entirely of either left node children or right-node children. (There is one
	 * such tree in the test cases.) In that case, execution will be linear, and
	 * will run in O(N) time, where N is the number of tree nodes. For a
	 * well-balanced binary tree, an average search runs in O(log-base2(N)).
	 * 
	 * @param testTree   A binary tree represented as an array of integer arrays.
	 *                   This is the tree which will have its nodes swapped at
	 *                   certain levels.
	 * @param swapDepths These are the levels at which each binary tree node will
	 *                   have its left and right child nodes swapped. Swapping
	 *                   actually takes place at every multiple of this value; so
	 *                   for a value of 3, swapping would take place at levels 3, 6,
	 *                   9, 12, and so on. Note levels start numbering with 1.
	 * @return The binary tree with its nodes swapped as specified.
	 * @see <a href=
	 *      "https://www.hackerrank.com/challenges/swap-nodes-algo/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=search">Original
	 *      HackerRank Problem</a>
	 */
	public static int[][] swapNodes(int[][] testTree, int[] swapDepths) {

		List<List<TreeNode>> treeLevels = new ArrayList<List<TreeNode>>();
		TreeNode rootNode = buildTreeStructure(testTree, 1, 0, treeLevels);
		int numberTreeNodes = countNumberNodes(treeLevels);

		int[][] results = new int[swapDepths.length][];

		for (int i = 0; i < swapDepths.length; i++) {
			swapTree(rootNode, swapDepths[i], numberTreeNodes);

			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println("");
				String swapMessage = "";
				swapMessage += "Swapping - Swap Depth " + swapDepths[i];
				if (swapDepths[i] == 0) {
					swapMessage += " (no swapping)";
				}
				if (swapDepths[i] > 0) {
					swapMessage += " (swapping at levels ";
					swapMessage += +swapDepths[i] + ", 2x" + swapDepths[i] + ", 3x" + swapDepths[i] + " ...):";
				}
				System.out.println(swapMessage);
				String treeString = rootNode.toString(rootNode, 0);
				System.out.println(treeString);
			}

			List<Integer> traverseList = traverseTree(rootNode, numberTreeNodes);

			results[i] = new int[traverseList.size()];
			for (int j = 0; j < traverseList.size(); j++) {
				results[i][j] = traverseList.get(j);
			}
		}
		return (results);
	}

	private static int countNumberNodes(List<List<TreeNode>> treeLevels) {
		int numberNodes = 0;
		for (List<TreeNode> treeLevel : treeLevels) {
			if (treeLevel == null) {
				continue;
			}
			numberNodes += treeLevel.size();
		}
		return (numberNodes);
	}

	private static void swapTree(TreeNode rootNode, int swapDepth, int numberTreeNodes) {
		List<TreeNode> swapList = new ArrayList<TreeNode>();
		if (swapDepth < 1) {
			return;
		}
		VisitTracker visitTracker = new VisitTracker(numberTreeNodes);
		depthFirstSearch(rootNode, 1, DFSGoal.SWAP_NODES, swapDepth, null, swapList, visitTracker);
		for (TreeNode treeNode : swapList) {
			TreeNode oldLeftNode = treeNode.getLeftNode();
			TreeNode oldRightNode = treeNode.getRightNode();
			treeNode.setLeftNode(oldRightNode);
			treeNode.setRightNode(oldLeftNode);
		}
	}

	private static List<Integer> traverseTree(TreeNode rootNode, int numberTreeNodes) {
		List<Integer> traverseList = new ArrayList<Integer>();
		VisitTracker visitTracker = new VisitTracker(numberTreeNodes);
		depthFirstSearch(rootNode, 1, DFSGoal.VISIT_NODES, 0, traverseList, null, visitTracker);
		return (traverseList);
	}

	private static void depthFirstSearch(TreeNode treeNode, int level, DFSGoal dfsGoal, int swapLevel,
			List<Integer> traverseList, List<TreeNode> swapList, VisitTracker visitTracker) {
		if (treeNode == null) {
			return;
		}
		TreeNode leftNode = treeNode.getLeftNode();
		if (leftNode != null) {
			boolean leftIsVisited = visitTracker.getVisited(leftNode.getIndex());
			if (leftIsVisited == false) {
				depthFirstSearch(leftNode, level + 1, dfsGoal, swapLevel, traverseList, swapList, visitTracker);
			}
		}

		int index = treeNode.getIndex();
		if (dfsGoal == DFSGoal.VISIT_NODES) {
			traverseList.add(index);
		}
		if (dfsGoal == DFSGoal.SWAP_NODES) {
			if (level % swapLevel == 0) {
				swapList.add(treeNode);
			}
		}

		visitTracker.setVisited(index, true);
		TreeNode rightNode = treeNode.getRightNode();
		if (rightNode != null) {
			boolean rightIsVisited = visitTracker.getVisited(rightNode.getIndex());
			if (rightIsVisited == false) {
				depthFirstSearch(rightNode, level + 1, dfsGoal, swapLevel, traverseList, swapList, visitTracker);
			}
		}
	}

	static private TreeNode buildTreeStructure(int[][] treeData, int treeLevel, int generation,
			List<List<TreeNode>> treeLevels) {
		TreeNode rootNode = null;
		if (treeLevel == ROOT_NODE) {
			rootNode = initializeRootNode(treeLevels);
			buildNextTreeLevel(treeData, treeLevel + 1, generation, treeLevels);
		}
		return (rootNode);
	}

	private static TreeNode initializeRootNode(List<List<TreeNode>> treeLevels) {
		TreeNode rootNode = initializeTreeNode(ROOT_NODE);
		treeLevels.add(null); // dummy level zero
		List<TreeNode> newLevel = new ArrayList<TreeNode>();
		newLevel.add(rootNode);
		treeLevels.add(newLevel); // level 1 with only the root node
		return (rootNode);
	}

	private static void buildNextTreeLevel(int[][] treeData, int treeLevel, int generation,
			List<List<TreeNode>> treeLevels) {
		List<TreeNode> parentLevel = treeLevels.get(treeLevel - 1);
		if (parentLevel == null) {
			throw new IllegalArgumentException("Corrupt tree level structure");
		}

		List<TreeNode> currentLevel = null;
		if (treeLevels.size() <= treeLevel) {
			currentLevel = new ArrayList<TreeNode>();
			treeLevels.add(currentLevel);
		}

		for (TreeNode treeNode : parentLevel) {
			int[] nextGeneration = treeData[generation];
			initializeLeftNode(treeNode, nextGeneration, currentLevel);
			initializeRightNode(treeNode, nextGeneration, currentLevel);
			generation++;
		}

		if (generation >= treeData.length) {
			return;
		}

		buildNextTreeLevel(treeData, treeLevel + 1, generation, treeLevels);
	}

	private static TreeNode initializeTreeNode(int index) {
		TreeNode treeNode = new TreeNode(index);
		treeNode.setLeftNode(new TreeNode(UNINITIALIZED));
		treeNode.setRightNode(new TreeNode(UNINITIALIZED));
		return (treeNode);
	}

	private static void initializeLeftNode(TreeNode treeNode, int[] nextGeneration, List<TreeNode> currentLevel) {
		if (treeNode.getLeftNode() != null && treeNode.getLeftNode().getIndex() == UNINITIALIZED) {
			int nextNodeIndex = nextGeneration[0];
			if (nextNodeIndex == NULL_NODE) {
				treeNode.setLeftNode(null);
			} else {
				TreeNode leftChild = initializeTreeNode(nextNodeIndex);
				treeNode.setLeftNode(leftChild);
				currentLevel.add(leftChild);
			}
		}
	}

	private static void initializeRightNode(TreeNode treeNode, int[] nextGeneration, List<TreeNode> currentLevel) {
		if (treeNode.getRightNode() != null && treeNode.getRightNode().getIndex() == UNINITIALIZED) {
			int nextNodeIndex = nextGeneration[1];
			if (nextNodeIndex == NULL_NODE) {
				treeNode.setRightNode(null);
			} else {
				TreeNode rightChild = initializeTreeNode(nextNodeIndex);
				treeNode.setRightNode(rightChild);
				currentLevel.add(rightChild);
			}
		}
	}

	final static public class VisitTracker {
		private boolean[] visited;
		private int numberTreeNodes;

		VisitTracker(int numberTreeNodes) {
			this.visited = new boolean[numberTreeNodes + 1];
			this.numberTreeNodes = numberTreeNodes;
		}

		public void setVisited(int index, boolean state) {
			visited[index] = state;
		}

		public boolean isVisited(int index) {
			return (visited[index]);
		}

		public boolean getVisited(int index) {
			return (isVisited(index));
		}

		public void clear() {
			this.visited = new boolean[numberTreeNodes + 1];
		}
	}

	final static public class TreeNode {
		private int index = UNINITIALIZED;
		private int level;
		private TreeNode leftNode;
		private TreeNode rightNode;
		private StringBuilder leftMarginPadding;

		public TreeNode(int index) {
			this.setIndex(index);
			this.leftMarginPadding = new StringBuilder();
			this.leftMarginPadding.append("                ");
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public TreeNode getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(TreeNode leftNode) {
			this.leftNode = leftNode;
		}

		public TreeNode getRightNode() {
			return rightNode;
		}

		public void setRightNode(TreeNode rightNode) {
			this.rightNode = rightNode;
		}

		public String toString(TreeNode node, int leftMargin) {
			if (node == null) {
				return ("");
			}

			if (leftMargin > leftMarginPadding.length()) {
				leftMarginPadding.append(leftMarginPadding);
			}
			StringBuilder rightTree = new StringBuilder();
			rightTree.append(leftMarginPadding, 0, leftMargin);

			rightTree.append(node.index);

			if (node.rightNode == null) {
				rightTree.append(" R--> null");
			} else {
				rightTree.append(" R--> " + node.rightNode.index);
			}
			rightTree.append("\n");

			int nodeDataWidth = integerWidth(node.index);

			StringBuilder leftTree = new StringBuilder();
			leftTree.append(leftMarginPadding, 0, leftMargin);
			leftTree.append(leftMarginPadding, 0, nodeDataWidth);

			if (node.leftNode == null) {
				leftTree.append(" L--> null");
			} else {
				leftTree.append(" L--> " + node.leftNode.index);
			}
			leftTree.append("\n");

			StringBuilder returnTree = new StringBuilder().append(rightTree.append(leftTree));

			leftMargin += (nodeDataWidth + 6);

			returnTree.append(toString(node.rightNode, leftMargin));
			returnTree.append(toString(node.leftNode, leftMargin));
			return (returnTree.toString());
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
	}
}
