import java.io.FileNotFoundException;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		// Read data from input.txt
		String[] array;
		try {
			Scanner scan = new Scanner(new File("input.txt"));
			List<String> lines = new ArrayList<String>();
			while (scan.hasNextLine())
				lines.add(scan.nextLine());
			array = lines.toArray(new String[0]);
		} catch (FileNotFoundException e) {
			array = new String[0]; // Catch failure uninitialized vars
			e.printStackTrace();
		}

		String[] xVars = array[0].split(" ");
		String[] yVars = array[1].split(" ");

		performDividedDifference(xVars, yVars);
	}

	public static void performDividedDifference(String[] xVars, String[] yVars) {
		int tableHeight = xVars.length;
		int numRows = (tableHeight * 2) - 1;
		int numCols = tableHeight + 1;
		String[][] mainList = new String[numRows][numCols];

		// Add x and f[] columns to 2d array for all even row
		int valueIndex = 0;
		for (int i = 0; i < numRows; i++) {
			if (i % 2 == 0) {
				mainList[i][0] = xVars[valueIndex];
				mainList[i][1] = yVars[valueIndex];
				valueIndex++;
			}
		}

		double[] interpPolyCoeffs = new double[tableHeight];
		interpPolyCoeffs[0] = Double.parseDouble(mainList[0][1]);
		int subtractDistance = 2;
		int colIndex = 2;
		int numIterations = tableHeight - 1;

		for (int i = numIterations; i > 0; i--) {
			int numDivisions = (tableHeight + 1) - colIndex;
			int startInsertRowIndex = tableHeight - numDivisions;

			for (int j = 0; j < numDivisions; j++) {
				double y2 = Double.parseDouble(mainList[j * 2 + (colIndex - 2) + 2][colIndex - 1]);
				double y1 = Double.parseDouble(mainList[j * 2 + (colIndex - 2)][colIndex - 1]);
				double x2 = Double.parseDouble(mainList[j * 2 + subtractDistance][0]);
				double x1 = Double.parseDouble(mainList[j * 2][0]);

				int insertRowIndex = startInsertRowIndex + (j * 2);
				double result = (y2 - y1) / (x2 - x1);
				mainList[insertRowIndex][colIndex] = Double.toString(result);
				
				if (j == 0) interpPolyCoeffs[colIndex - 1] = result;

				// double result = (y2 - y1) / (x2 - x1);
				// System.out.println("(" + y2 + " - " + y1 + ") / (" + x2 + " - " + x1 + ") = " + result + "  -  inserted on row index " + insertRowIndex);
			}

			subtractDistance += 2;
			colIndex++;
		}

		// Replace all null values in 2d array with blank string
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (mainList[row][col] == null)
					mainList[row][col] = "";
			}
		}

		printDividedDifferenceTable(mainList, interpPolyCoeffs, tableHeight);
	}

	public static void printDividedDifferenceTable(String[][] mainList, double[] interpPolyCoeffs, int tableHeight) {
		// Header
		System.out.println("x         f[]         f[,]      f[,,]        f[,,,]           ...");
		System.out.println("---------------------------------------------------");

		// Contents
		int numRows = (tableHeight * 2) - 1;
		int numCols = tableHeight + 1;

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (mainList[row][col].equals("")) {
					System.out.print("    ");
				} else {
					double num = Double.parseDouble(mainList[row][col]);
					System.out.print(String.format("%.5s", num));
				}
				
				System.out.print("       ");
			}
			System.out.println("");
		}

		// Intepolating polynomial
		System.out.println("\nInterpolating polynomial:");
		StringBuilder sb = new StringBuilder();
		if (Double.parseDouble(mainList[0][0]) < 0) sb.append("-");
		for (int i = 0; i < tableHeight; i++) {
			// Coefficient
			if (i != 0) {
				if (interpPolyCoeffs[i] >= 0)
					sb.append(" + ");
				if (interpPolyCoeffs[i] < 0)
					sb.append(" - ");
			}

			sb.append(String.format("%.5s", Double.toString(Math.abs(interpPolyCoeffs[i]))));

			// Terms of x
			if (i > 0) {
				for (int j = 0; j < i; j++) {
					double value = Double.parseDouble(mainList[j * 2][0]);
					if (value > 0)
						sb.append("(x - " + Math.abs(value) + ")");
					else if (value < 0)
						sb.append("(x + " + Math.abs(value) + ")");
					else if (value == 0)
						sb.append("(x)");

				}
			}
		}

		System.out.println(sb.toString());
	}
}
