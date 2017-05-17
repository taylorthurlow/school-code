package edu.tthurlow.bst;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BinarySearchTree<T extends Comparable<? super T>> {

	/* Data members */

	private BinaryNode<T> root;


	/* Constructors */

	public BinarySearchTree(BinaryNode newRoot) {
		this.root = newRoot;
	}


	/* Getters and Setters */

	public BinaryNode getRoot() {
		return root;
	}

	public void setRoot(BinaryNode newRoot) {
		this.root = newRoot;
	}


	/* Other methods */

	/**
	 * Searches for an entry in the entire tree
	 * @param rootNode  The root node representing the (sub)tree to be searched
	 * @param entry  The data to find
	 * @return  The data that was found, otherwise null if the data could not be found
	 */
	public T getEntry(BinaryNode<T> rootNode, T entry) {
		T result = null;

		if (rootNode != null) {
			T rootEntry = rootNode.getData();

			// The root matches the query
			if (entry.equals(rootEntry))
				result = rootEntry;
			// The query is less than the root, search left
			else if (entry.compareTo(rootEntry) < 0)
				result = getEntry(rootNode.getLeftChild(), entry);
			// The query is more than the root, search right
			else
				result = getEntry(rootNode.getRightChild(), entry);
		}

		return result;
	}

	/**
	 * Searches for entry in the entire tree
	 * @param entry  The data to find
	 * @return  True if the object was found
	 */
	public boolean contains(T entry) {
		return getEntry(root, entry) != null;
	}

	/**
	 * Adds an entry to the tree
	 * @param newEntry  The entry to add
	 * @return  True if the addition was successful, false if the addition failed due to
	 * the entry already being in the tree
	 */
	public boolean add(BinaryNode<T> rootNode, T newEntry) {
		boolean result = false;
		if (rootNode != null) {
			int comparison = newEntry.compareTo(rootNode.getData());

			// The new entry matches the root, set anyways
			if (comparison == 0)
				rootNode.setData(newEntry);
			// The new entry is smaller than the root
			else if (comparison < 0) {
				if (rootNode.hasLeftChild())
					// A child exists on the left side, need to traverse further
					result = add(rootNode.getLeftChild(), newEntry);
				else
					// No child, we have found a place for the new node
					rootNode.setLeftChild(new BinaryNode<T>(newEntry));
			} else {
				if (rootNode.hasRightChild())
					// A child exists on the left side, need to traverse further
					result = add(rootNode.getRightChild(), newEntry);
				else
					// No child, we have found a place for the new node
					rootNode.setRightChild(new BinaryNode<T>(newEntry));
			}
		}

		return result;
	}

	/**
	 * Removes an existing entry from the tree
	 * @param entry  The entry to remove
	 * @return  The root node of the resulting tree
	 */
	public BinaryNode<T> remove(BinaryNode<T> rootNode, T entry) {
		if (rootNode != null) {
			T rootEntry = rootNode.getData();
			int comparison = entry.compareTo(rootEntry);

			// Found a match
			if (comparison == 0)
				rootNode = removeFromRoot(rootNode);
			// Node is too big, traverse left and keep searching
			else if (comparison < 0) {
				BinaryNode<T> leftChild = rootNode.getLeftChild();
				BinaryNode<T> subtreeRoot = remove(leftChild, entry);
				rootNode.setLeftChild(subtreeRoot);
			// Node is too small, traverse right and keep searching
			} else {
				BinaryNode<T> rightChild = rootNode.getRightChild();
				BinaryNode<T> subtreeRoot = remove(rightChild, entry);
				rootNode.setRightChild(subtreeRoot);
			}
		}

		setRoot(rootNode);

		return rootNode;
	}

	/**
	 * Removes the given root node of a subtree
	 * @param rootNode  The root node of the subtree
	 * @return  The root node of the resulting subtree
	 */
	public BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		// Case 1: Two children
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Find rightmost in left subtree
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> rightmostChild = getRightmost(leftSubtreeRoot);
			// Perform the swap with the root (technically the parent node) and repeat
			rootNode.setData(rightmostChild.getData());
			rootNode.setLeftChild(removeRightmost(leftSubtreeRoot));
		}

		// Case 2: At most one child
		else if (rootNode.hasRightChild())
			rootNode = rootNode.getRightChild();
		else
			rootNode = rootNode.getLeftChild();

		return rootNode;
	}

	/**
	 * Gets the rightmost node of the subtree rooted at the given root node. This is
	 * effectively the largest value in the subtree.
	 * @param rootNode  The root of the subtree to find the rightmost node of
	 * @return  The rightmost (largest) node in the subtree
	 */
	public BinaryNode<T> getRightmost(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild())
			rootNode = getRightmost(rootNode.getRightChild());

		return rootNode;
	}

	/**
	 * Removes the rightmost node of the subtree rooted at the given node. This is
	 * effectively the largest value in the subtree.
	 * @param rootNode  The root of the subtree to remove the rightmost node from
	 * @return  The root node of the resulting subtree
	 */
	public BinaryNode<T> removeRightmost(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			BinaryNode<T> root = removeRightmost(rightChild);
			rootNode.setRightChild(root);
		} else {
			rootNode = rootNode.getLeftChild();
		}

		return rootNode;
	}

	/**
	 * Determine the entry that comes before the given entry in the in-order traversal
	 * @param entry  The entry to find the predecessor of
	 * @return  The predecessor value, if it exists, null otherwise
	 */
	public T getPredecessor(T entry) {
		T result = null;
		ArrayList<T> list = new ArrayList<>();
		inOrder(root, list);
		int find = list.indexOf(entry);
		if (find != -1 && (find - 1) >= 0)
			// Entry exists in ArrayList and a predecessor exists
			result = list.get(find - 1);

		return result;
	}

	/**
	 * Determine the entry that comes after the given entry in the in-order traversal
	 * @param entry  The entry to find the successor of
	 * @return  The successor value, if it exists, null otherwise
	 */
	public T getSuccessor(T entry) {
		T result = null;
		ArrayList<T> list = new ArrayList<>();
		inOrder(root, list);
		int find = list.indexOf(entry);
		if (find != -1 && (find + 1) <= (list.size() - 1))
			// Entry exists in ArrayList and a successor exists
			result = list.get(find + 1);

		return result;
	}

	/**
	 * Prints the elements in the tree in "root, left, right" order
	 * @param rootNode  The node representing the subtree to be printed
	 */
	public void preOrder(BinaryNode<T> rootNode) {
		System.out.print(rootNode.getData() + " ");

		if (rootNode.getLeftChild() != null)
			preOrder(rootNode.getLeftChild());

		if (rootNode.getRightChild() != null)
			preOrder(rootNode.getRightChild());
	}

	/**
	 * Stores the elements in the tree in "root, left, right" order
	 * @param rootNode  The node representing the subtree to be stored
	 * @param target  The target ArrayList for storage
	 * @return  The ArrayList the data was stored in
	 */
	public ArrayList<T> preOrder(BinaryNode<T> rootNode, ArrayList<T> target) {
		target.add(rootNode.getData());
		if (rootNode.getLeftChild() != null)
			preOrder(rootNode.getLeftChild(), target);
		if (rootNode.getRightChild() != null)
			preOrder(rootNode.getRightChild(), target);
		return target;
	}

	/**
	 * Prints the elements in the tree in "left, right, root" order
	 * @param rootNode  The node representing the subtree to be printed
	 */
	public void postOrder(BinaryNode<T> rootNode) {
		if (rootNode.getLeftChild() != null)
			postOrder(rootNode.getLeftChild());

		if (rootNode.getRightChild() != null)
			postOrder(rootNode.getRightChild());

		System.out.print(rootNode.getData() + " ");
	}

	/**
	 * Stores the elements in the tree in "left, right, root" order
	 * @param rootNode  The node representing the subtree to be stored
	 * @param target  The target ArrayList for storage
	 * @return  The ArrayList the data was stored in
	 */
	public ArrayList<T> postOrder(BinaryNode<T> rootNode, ArrayList<T> target) {
		if (rootNode.getLeftChild() != null)
			postOrder(rootNode.getLeftChild(), target);
		if (rootNode.getRightChild() != null)
			postOrder(rootNode.getRightChild(), target);
		target.add(rootNode.getData());
		return target;
	}

	/**
	 * Prints the elements in the tree in "left, root, right" order
	 * @param rootNode  The node representing the subtree to be printed
	 */
	public void inOrder(BinaryNode<T> rootNode) {
		if (rootNode.getLeftChild() != null)
			inOrder(rootNode.getLeftChild());

		System.out.print(rootNode.getData() + " ");

		if (rootNode.getRightChild() != null)
			inOrder(rootNode.getRightChild());
	}

	/**
	 * Stores the elements in the tree in "left, root, right" order
	 * @param rootNode  The node representing the subtree to be stored
	 * @param target  The target ArrayList for storage
	 * @return  The ArrayList the data was stored in
	 */
	public ArrayList<T> inOrder(BinaryNode<T> rootNode, ArrayList<T> target) {
		if (rootNode.getLeftChild() != null)
			inOrder(rootNode.getLeftChild(), target);
		target.add(rootNode.getData());
		if (rootNode.getRightChild() != null)
			inOrder(rootNode.getRightChild(), target);
		return target;
	}

	/**
	 * Constructs a string from a given traversal ArrayList for easier printing
	 * @param list  The ArrayList traversal to print
	 * @return  The printable string
	 */
	public String listToString(ArrayList<T> list) {
		String result = "";
		for(T entry : list)
			result = result + entry + " ";
		return result;
	}
}
