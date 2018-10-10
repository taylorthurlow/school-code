import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("== Analysis of the N-Queen Problem ==");
        System.out.println("1: Generate a random puzzle and solve it");
        System.out.println("2: Generate and solve 500 random puzzles");

        Scanner sc = new Scanner(System.in);
        int selection = sc.nextInt();

        if (selection == 1) {
            long startTime, endTime;
            System.out.print("Generating random puzzle...");
            Board board = new Board(21);
            System.out.println("done.");

            System.out.println();

            System.out.print("Attempting annealing solution...");
            startTime = System.nanoTime();
            SimulatedAnnealing sa = new SimulatedAnnealing(board);
            endTime = System.nanoTime();
            System.out.println("done.");
            System.out.println("Valid Solution: " + sa.validSolution);
            if (sa.validSolution) {
                System.out.println("Search Cost: " + sa.cost);
                System.out.println("Time: " + (endTime - startTime) / 500D / 1000000D + " ms");
//                Board.print(sa.solutionPositions);
            }

            System.out.println();

            System.out.print("Attempting genetics solution...");
            startTime = System.nanoTime();
            Genetic g = new Genetic(board);
            endTime = System.nanoTime();
            System.out.println("done.");
            System.out.println("Valid Solution: " + g.validSolution);
            if (g.validSolution) {
                System.out.println("Search Cost: " + g.cost);
                System.out.println("Time: " + (endTime - startTime) / 500D / 1000000D + " ms");
//                Board.print(g.mostFit);
            }

        } else if (selection == 2) {
            System.out.println("SA SOLUTION FOUND, SA RUNTIME (ns), SA COST, G SOLUTION FOUND, G RUNTIME (ns), G COST");
            for (int i = 0; i < 500; i++) {
                Board board = new Board(21);

                // Simulated Annealing
                long saStart = System.nanoTime();
                SimulatedAnnealing sa = new SimulatedAnnealing(board);
                long saEnd = System.nanoTime();

                // Genetics
                long gStart = System.nanoTime();
                Genetic g = new Genetic(board);
                long gEnd = System.nanoTime();

                System.out.println((sa.validSolution ? "Yes" : "No") + ", "
                        + (saEnd - saStart) + ", "
                        + sa.cost + ", "
                        + (g.validSolution ? "Yes" : "No") + ", "
                        + (gEnd - gStart) + ", " + g.cost
                );
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }
}
