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
            int puzzleCount = 2000;
            System.out.println("DEPTH, H1 COST, H1 RUNTIME (ns), H2 COST, H2 RUNTIME (ns)");
            System.out.println();
            for (int i = 0; i < puzzleCount; i++) {
                Puzzle puzzleH1 = new Puzzle();
                Puzzle puzzleH2 = new Puzzle(puzzleH1); // Duplicate puzzle for Manhattan
                puzzleH1.allowLargeSolutions = false;
                puzzleH2.allowLargeSolutions = false;

                long H1startTime = System.nanoTime();
                ArrayList<PuzzleState> solutionH1 = puzzleH1.solve(false);
                long H1endTime = System.nanoTime();
                long H1totalTime = H1endTime - H1startTime;

                if (solutionH1 == null) { // Solution was deeper than 24, skip
                    i--; // Decrement our loop so we don't count this in our total puzzle count
                    continue;
                }

                long H2startTime = System.nanoTime();
                ArrayList<PuzzleState> solutionH2 = puzzleH2.solve(true);
                long H2endTime = System.nanoTime();
                long H2totalTime = H2endTime - H2startTime;

                System.out.println((solutionH1.size() - 1) + ", "
                        + puzzleH1.generatedStates + ", " + H1totalTime + ", "
                        + puzzleH2.generatedStates + ", " + H2totalTime);
            }

        } else {
            System.out.println("Invalid selection.");
        }
    }
}
