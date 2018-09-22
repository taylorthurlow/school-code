import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("== A* Analysis of the 8-Puzzle ==");
        System.out.println("1: Generate a random puzzle and solve it");
        System.out.println("2: Provide a specific puzzle configuration");
        System.out.println("3: Perform a series of tests");

        Scanner sc = new Scanner(System.in);
        int selection = sc.nextInt();

        if (selection == 1) {
            System.out.println("Generating a random solvable puzzle...");
            Puzzle puzzleH1 = new Puzzle();

            System.out.println("Solving the puzzle with h1 (Hamming)...");
            ArrayList<PuzzleState> solutionH1 = puzzleH1.solve(false);

            System.out.println("Solution found with depth " + (solutionH1.size() - 1) + ".");
            System.out.println("Generated " + puzzleH1.generatedStates + " total states.");

            System.out.println();

            System.out.println("Solving the puzzle with h2 (Manhattan)...");
            Puzzle puzzleH2 = new Puzzle(puzzleH1.values);
            ArrayList<PuzzleState> solutionH2 = puzzleH2.solve(true);

            System.out.println("Solution found with depth " + (solutionH2.size() - 1) + ".");
            System.out.println("Generated " + puzzleH2.generatedStates + " total states.");

            System.out.println();
            for (PuzzleState solutionState : solutionH1) {
                solutionState.print(solutionState.parent);
                System.out.println();
            }
        } else if (selection == 2) {
            System.out.print("Input the puzzle using space delimiters, and 0 for the empty space: ");
            Scanner sc2 = new Scanner(System.in);
            int[] values = PuzzleState.parseInputPuzzle(sc2.nextLine());
            Puzzle puzzleH1 = new Puzzle(values);

            if (Puzzle.checkSolvable(values)) {
                System.out.println("Solving the puzzle with h1 (Hamming)...");
                ArrayList<PuzzleState> solution = puzzleH1.solve(false);

                System.out.println("Solution found with depth " + (solution.size() - 1) + ".");
                System.out.println("Generated: " + puzzleH1.generatedStates + " total states.");

                System.out.println();

                System.out.println("Solving the puzzle with h2 (Manhattan)...");
                Puzzle puzzleH2 = new Puzzle(puzzleH1.values);
                ArrayList<PuzzleState> solutionH2 = puzzleH2.solve(true);

                System.out.println("Solution found with depth " + (solutionH2.size() - 1) + ".");
                System.out.println("Generated " + puzzleH2.generatedStates + " total states.");

                System.out.println();
                for (PuzzleState solutionState : solution) {
                    solutionState.print(solutionState.parent);
                    System.out.println();
                }
            } else {
                System.out.println("The puzzle you entered is not solvable.");
            }
        } else if (selection == 3) {
            // Generate random puzzles
            int puzzleCount = 2000;
            System.out.println("Generating " + puzzleCount + " puzzles...");
            Puzzle[] puzzles = new Puzzle[puzzleCount];
            for (int i = 0; i < puzzleCount; i++)
                puzzles[i] = new Puzzle();

            System.out.println("Solving puzzles.");
            System.out.println("DEPTH, H1 COST, H1 RUNTIME (ms), H2 COST, H2 RUNTIME (ms)");
            System.out.println();
            for (Puzzle puzzleH1 : puzzles) {
                Puzzle puzzleH2 = new Puzzle(puzzleH1); // Duplicate puzzle for Manhattan

                long H1startTime = System.nanoTime();
                ArrayList<PuzzleState> solutionH1 = puzzleH1.solve(false);
                long H1endTime = System.nanoTime();
                int H1totalTime = (int) (H1endTime - H1startTime) / 1000000;

                long H2startTime = System.nanoTime();
                ArrayList<PuzzleState> solutionH2 = puzzleH2.solve(true);
                long H2endTime = System.nanoTime();
                int H2totalTime = (int) (H2endTime - H2startTime) / 1000000;

                System.out.println((solutionH1.size() - 1) + ", "
                        + puzzleH1.generatedStates + ", " + H1totalTime + ", "
                        + puzzleH2.generatedStates + ", " + H2totalTime);
            }

        } else {
            System.out.println("Invalid selection.");
        }
    }
}
