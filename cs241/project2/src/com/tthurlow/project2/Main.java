package com.tthurlow.project2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

    	final int NUM_SETS = 20;

		// Obtain initial data set from user input
		System.out.println("Please select a mode:");
		System.out.println("1) 20 sets of 100 randomly generated integers");
		System.out.println("2) Fixed integer values 1 to 100");
		System.out.print("Enter choice: ");

		Scanner readIn = new Scanner(System.in);
		int choice = readIn.nextInt();

		System.out.println("");
		System.out.println(" ===== ");
		System.out.println("");

		if (choice == 1) {
			// 20 sets of 100 randomly generated integers

			int[] smartSwaps = new int[NUM_SETS];
			int[] dumbSwaps = new int[NUM_SETS];

			for (int sets = 0; sets < NUM_SETS; sets++) {

				Integer[] entries = new Integer[100];

				for (int i = 0; i < 100; i++) {
					Integer newRand = ThreadLocalRandom.current().nextInt(0, 1000 + 1);

					// Avoid duplicates
					while (Arrays.asList(entries).contains(newRand))
						newRand = ThreadLocalRandom.current().nextInt(0, 1000 + 1);

					entries[i] = newRand;
				}

				// Set up heap with smart method
				MaxHeap<Integer> smartHeap = new MaxHeap<>(entries);
				smartSwaps[sets] = smartHeap.getNumSwaps();

				MaxHeap<Integer> dumbHeap = new MaxHeap<>();
				for (int i = 0; i < entries.length; i++)
					dumbHeap.add(entries[i]);
				dumbSwaps[sets] = dumbHeap.getNumSwaps();

			}

			int totalSmartSwaps = 0, totalDumbSwaps = 0;
			for(int i = 0; i < smartSwaps.length; i++)
				totalSmartSwaps += smartSwaps[i];
			int avgSmartSwaps = totalSmartSwaps / NUM_SETS;

			for(int i = 0; i < dumbSwaps.length; i++)
				totalDumbSwaps += dumbSwaps[i];
			int avgDumbSwaps = totalDumbSwaps / NUM_SETS;

			System.out.println("Average number of smart (fewer reheaps) swaps: " + avgSmartSwaps);
			System.out.println("Average number of dumb (series of insertions) swaps: " + avgDumbSwaps);


		} else if (choice == 2) {
			// Fixed integer values 1 to 100

			// Smart

			Integer[] entries = new Integer[100];
			for (int i = 1; i <= 100; i++)
				entries[i - 1] = i;

			MaxHeap<Integer> smartHeap = new MaxHeap<>(entries);
			int smartSwaps = smartHeap.getNumSwaps();

			System.out.println("Number of smart (fewer reheaps) swaps: " + smartSwaps + "\n");

			System.out.print("First 10 entries: ");
			for (int i = 1; i <= 10; i++) {
				System.out.print(smartHeap.getValueAt(i) + " ");
			}

			System.out.println("");

			for (int i = 0; i < 10; i++) {
				smartHeap.removeMax();
			}

			System.out.print("After 10 removals: ");
			for (int i = 1; i <= 10; i++) {
				System.out.print(smartHeap.getValueAt(i) + " ");
			}

			System.out.println("\n\n ===== \n");

			// Dumb

			MaxHeap<Integer> dumbHeap = new MaxHeap<>();

			for (int i = 0; i < entries.length; i++) {
				dumbHeap.add(entries[i]);
			}

			int dumbSwaps = dumbHeap.getNumSwaps();

			System.out.println("Number of dumb (series of insertions) swaps: " + dumbSwaps + "\n");

			System.out.print("First 10 entries: ");
			for (int i = 1; i <= 10; i++) {
				System.out.print(dumbHeap.getValueAt(i) + " ");
			}

			System.out.println("");

			for (int i = 0; i < 10; i++) {
				dumbHeap.removeMax();
			}

			System.out.print("After 10 removals: ");
			for (int i = 1; i <= 10; i++) {
				System.out.print(dumbHeap.getValueAt(i) + " ");
			}

		} else {
			System.out.println("Invalid choice.");
		}
    }
}
