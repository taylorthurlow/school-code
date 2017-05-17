package edu.tthurlow.bst;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	// Obtain initial data set from user input
		System.out.println("Please enter the initial sequence of values: ");

		Scanner readIn = new Scanner(System.in);
		String str = readIn.nextLine();
		String[] nums = str.split(" ");
		int[] finalArray = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			finalArray[i] = Integer.parseInt(nums[i]);
		}

		// Construct tree from user data
		BinaryNode<Integer> start = new BinaryNode<>(finalArray[0]);
		BinarySearchTree<Integer> tree = new BinarySearchTree<>(start);

		for (int i = 1; i < nums.length; i++) {
			tree.add(tree.getRoot(), finalArray[i]);
		}

		System.out.println("");

		// Print initial traversals
		ArrayList<Integer> preOrder = new ArrayList<>();
		String preOrderString = tree.listToString(tree.preOrder(tree.getRoot(), preOrder));
		System.out.println("Pre-order: " + preOrderString);

		ArrayList<Integer> inOrder = new ArrayList<>();
		String inOrderString = tree.listToString(tree.inOrder(tree.getRoot(), inOrder));
		System.out.println("In-order: " + inOrderString);

		ArrayList<Integer> postOrder = new ArrayList<>();
		String postOrderString = tree.listToString(tree.postOrder(tree.getRoot(), postOrder));
		System.out.println("Post-order: " + postOrderString);

		System.out.println("");

		// Main command prompt loop
		boolean running = true;
		while (running) {
			System.out.print("Command: ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			char cmd = '?'; // Default to help in case something goes wrong
			int value = -123123; // -123123 makes it easy to tell if something went wrong with input
			try {
				String[] inputStream = bufferedReader.readLine().split(" ");
				cmd = inputStream[0].toCharArray()[0];
				if (inputStream.length == 2) // Command plus value entered
					value = Integer.parseInt(inputStream[1]);
				else if (inputStream.length > 2)
					cmd = '?'; // Force help text if too many arguments
			} catch (IOException e) {
				e.printStackTrace();
			}

			switch (Character.toLowerCase(cmd)) {
				case 'i':
					if (value >= 0) {
						if (!tree.contains(value)) {
							tree.add(tree.getRoot(), value);
							inOrder.clear();
							String newList = tree.listToString(tree.inOrder(tree.getRoot(), inOrder));
							System.out.println("   Result: " + newList);
						} else {
							System.out.println("   " + value + " already exists, ignoring.");
						}
					}
					else if (value == -123123)
						System.out.println("   You need to specify an integer value to add: i <val>");
					else
						System.out.println("   Please enter a positive integer, or zero.");
					break;
				case 'd':
					if (value == -123123) {
						System.out.println("   You need to specify an integer value to remove: d <val>");
					} else {
						if(tree.contains(value)) {
							if (tree.getRoot().getNumberOfNodes(tree.getRoot()) != 1) {
								tree.remove(tree.getRoot(), value);
								inOrder.clear();
								String newList = tree.listToString(tree.inOrder(tree.getRoot(), inOrder));
								System.out.println("   Result: " + newList);
							} else {
								System.out.println("   Can't remove the last node in the BST, ignoring.");
							}

						} else {
							System.out.println("   " + value + " not found in BST, ignoring.");
						}
					}

					break;
				case 'p':
					Integer pResult = tree.getPredecessor(value);
					if (pResult != null) {
						System.out.println("   Predecessor: " + pResult);
					} else {
						if (tree.contains(value))
							System.out.println("   " + value + " has no predecessor.");
						else
							System.out.println("   " + value + " not found in BST, ignoring.");
					}
					break;
				case 's':
					Integer sResult = tree.getSuccessor(value);
					if (sResult != null) {
						System.out.println("   Successor: " + sResult);
					} else {
						if (tree.contains(value))
							System.out.println("   " + value + " has no successor.");
						else
							System.out.println("   " + value + " not found in BST, ignoring.");
					}
					break;
				case 'e':
					running = false;
					break;
				case 'h':
				case '?':
					System.out.println("   i <val> - insert a value");
					System.out.println("   d <val> - delete a value");
					System.out.println("   p <val> - find predecessor");
					System.out.println("   s <val> - find successor");
					System.out.println("         e - exit the program");
					System.out.println("    h or ? - display this message");
					break;
				default:
					System.out.println("   Command not recognized.");
			}
		}
	}
}
