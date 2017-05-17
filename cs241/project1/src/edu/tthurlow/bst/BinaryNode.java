package edu.tthurlow.bst;

public class BinaryNode<T> {

	/* Data members */

	private T data;
	private BinaryNode<T> left;
	private BinaryNode<T> right;


    /* Constructors */

	public BinaryNode() {
		this(null);
	}

	public BinaryNode(T newData) {
		data = newData;
	}

	public BinaryNode(T newData, BinaryNode<T> leftChild, BinaryNode<T> rightChild) {
		data = newData;
		left = leftChild;
		right = rightChild;
	}


	/* Getters and Setters */

	public T getData() {
		return data;
	}

	public void setData(T newData) {
		data = newData;
	}

	public BinaryNode<T> getLeftChild() {
		return left;
	}

	public void setLeftChild(BinaryNode<T> newLeftNode) {
		left = newLeftNode;
	}

	public BinaryNode<T> getRightChild() {
		return right;
	}

	public void setRightChild(BinaryNode<T> newRightNode) {
		right = newRightNode;
	}


    /* Evaluations */

	public boolean hasLeftChild() {
		return left != null;
	}

	public boolean hasRightChild() {
		return right != null;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

    /* Other methods */

	/**
	 * Makes a copy of a given BinaryNode
	 * @return  The new, copied node
	 */
	public BinaryNode<T> copy() {
		BinaryNode<T> newRoot = new BinaryNode<T>(data);

		if (left != null)
			newRoot.left = left.copy();
		if (right != null)
			newRoot.right = right.copy();

		return newRoot;
	}

	/**
	 * Gets the height of the subtree rooted at the specified node
	 * @param node  The root node of the subtree to be queried
	 * @return  The integer height of the tree, in number of nodes
	 */
	public int getHeight(BinaryNode<T> node) {
		int height = 0;

		if (node != null)
			height = 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));

		return height;
	}

	/**
	 * Gets the total number of nodes in the subtree rooted at the specified node
	 * @param node  The root node of the subtree to be queried
	 * @return  The integer number of nodes in the subtree, including the root
	 */
	public int getNumberOfNodes(BinaryNode<T> node) {
		int numberOfNodes = 0;

		if (node != null)
			numberOfNodes = 1 + getNumberOfNodes(node.getLeftChild())
							  + getNumberOfNodes(node.getRightChild());

		return numberOfNodes;
	}
}
