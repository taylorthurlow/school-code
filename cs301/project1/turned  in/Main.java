/**
 * Taylor Thurlow
 * California Polytechnic University Pomona
 * 1 May 2017
 * Raheja CS301 Spring 2017
 *
 * Solves a system of linear equations using matrices and the method of
 * Gaussian elimination with scaled partial pivoting. Asks the user for
 * either a data file containing space separated values, or manually
 * inputted data. Solves the system of equations, providing the matrix
 * after each pivot step. Finally, prints the answer.
 */

package com.tthurlow.cs301project1;

import java.io.*;

public class Main {

    public static void main(String[] args) {
		double[][] matrix;
		int matrixSize = 0;

		// ask user about size of matrix
		System.out.print("Size of matrix (<=10): ");
		BufferedReader sizebr = new BufferedReader(new InputStreamReader(System.in));
		try {
			// get size and initialize matrix properly
			String[] is = sizebr.readLine().split(" ");
			matrixSize = Integer.parseInt(is[0]);
			matrix = new double[matrixSize][matrixSize+1];
		} catch (IOException e) {
			matrix = new double[0][0]; // this is here to avoid "not initialized" problems...
			e.printStackTrace();
		}

		// ask user if they would like to provide a file with matrix data
		System.out.print("Provide a filename with matrix data, or 'n' to skip: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			String[] is = br.readLine().split(" ");
			if (!is[0].equals("n")) {
				// user has file data, read it
				String path = "./" + is[0];
				try (BufferedReader fbr = new BufferedReader(new FileReader(new File(path)))) {
					int currentRow = 0;
					for (String line; (line = fbr.readLine()) != null; ) {
						if (line.trim().length() > 0) {
							// parse line
							String[] stringNumbers = line.trim().split(" ");
							int[] numbers = new int[stringNumbers.length];

							// convert array from string objects to ints
							for (int i = 0; i < stringNumbers.length; i++) {
								if (i < matrixSize + 1) // this handles data files with too many cols
									matrix[currentRow][i] = Double.parseDouble(stringNumbers[i]);
							}

							currentRow++;
						}
					}
				}
			} else {
				// user doesn't have file data, prompt for matrix size and data
				System.out.println("Manual data entry mode selected. Enter the augmented matrix");
				System.out.println("data left to right, top to bottom.");
				for (int row = 0; row < matrixSize; row++) {
					for (int col = 0; col < matrixSize + 1; col++) {
						System.out.print("(" + row + ", " + col + "): ");
						BufferedReader numbr = new BufferedReader(new InputStreamReader(System.in));
						double value = 0;
						try {
							String[] numis = numbr.readLine().split(" ");
							value = Double.parseDouble(numis[0]);
						} catch (IOException e) {
							e.printStackTrace();
						}

						matrix[row][col] = value;
					}
				}
				System.out.println("\nData entry complete. Moving to solution.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// now we have the matrix filled out and ready to be operated on

		printMatrix(matrix, matrixSize);

		// forward eliminate
		for (int row = 0; row < matrixSize; row++) {

			// find pivot row
			int max = row;
			for (int i = row + 1; i < matrixSize; i++) {
				if (Math.abs(matrix[max][row]) < Math.abs(matrix[i][row]))
					max = i;
			}

			// perform swap
			double[] swapTemp = matrix[row]; // copy current row to temporary array
			matrix[row] = matrix[max];       // overwrite current row with max row
			matrix[max] = swapTemp;          // set the old max row to the removed current row

			// perform pivot
			for (int i = row + 1; i < matrixSize; i++) {
				// calculate scale ratio
				double ratio = matrix[i][row] / matrix[row][row];
				// perform scale
				for (int j = row; j <= matrixSize; j++)
					matrix[i][j] -= matrix[row][j] * ratio;
			}

			printMatrix(matrix, matrixSize);
		}

		// back substitute
		double[] result = new double[matrixSize];
		for (int i = matrixSize - 1; i >= 0; i--) {
			double total = 0;
			for (int j = i + 1; j < matrixSize; j++)
				total += result[j] * matrix[i][j];
			result[i] = (matrix[i][matrixSize] - total) / matrix[i][i];
		}

		for (int i = 0; i < matrixSize; i++)
			System.out.println("x" + (i+1) + ": " + result[i]);
    }

	private static void printMatrix(double[][] matrix, int size) {
		System.out.println("");
    	for (int row = 0; row < size; row++) {
			System.out.print("| ");
			for (int col = 0; col < size + 1; col++) {
				double value = (double) Math.round(matrix[row][col] * 1000d) / 1000d; // round to 3 decimal places
				String numPrint = String.format("%1$-" + 10 + "s", Double.toString(value)); // pad with spaces
				System.out.print(numPrint);
			}
			System.out.println(" |");
		}
		System.out.println("");
	}
}
