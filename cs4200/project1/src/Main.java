import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("== A* Analysis of the 8-Puzzle ==");
        System.out.println("1: Generate a random puzzle and solve it");
        System.out.println("2: Provide a specific puzzle configuration");

        Scanner sc = new Scanner(System.in);
        int selection = sc.nextInt();

        if (selection == 1) {
            File file = new File("src/depth10.txt");
            Scanner in = new Scanner(file);
            int totalGenerated = 0;
            while(in.hasNextLine()) {
                int[] values = PuzzleState.parseInputPuzzle(in.nextLine());
                Puzzle puzzle = new Puzzle(values);
                if (Puzzle.checkSolvable(values)) {
                    ArrayList<PuzzleState> solution = puzzle.solveH1();
                    System.out.println("Solution depth: " + (solution.size() - 1));
                    totalGenerated += puzzle.generatedStates;

                    System.out.println("Solution steps:");
                    for (PuzzleState solutionState : solution) {
                        solutionState.print(solutionState.parent);
                        System.out.println();
                    }

                    System.out.println();
                    System.out.println("-----");
                    System.out.println();
                }

                System.out.println("Average generated: " + totalGenerated / 100);
            }
            in.close();
        } else if (selection == 2) {
            System.out.print("Input the puzzle using space delimiters: ");
            Scanner sc2 = new Scanner(System.in);
            int[] values = PuzzleState.parseInputPuzzle(sc2.nextLine());
            Puzzle puzzle = new Puzzle(values);

            if (Puzzle.checkSolvable(values)) {
                ArrayList<PuzzleState> solution = puzzle.solveH1();
                System.out.println("Solution depth: " + (solution.size() - 1));
                System.out.println("States generated: " + puzzle.generatedStates);

                System.out.println("Solution steps:");
                for (PuzzleState solutionState : solution) {
                    solutionState.print(solutionState.parent);
                    System.out.println();
                }
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }
}
