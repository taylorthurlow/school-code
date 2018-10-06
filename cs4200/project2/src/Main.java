import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("== Analysis of the N-Queen Problem ==");
        System.out.println("1: Generate a random puzzle and solve it");
        System.out.println("2: Generate and solve 500 random puzzles");

        Scanner sc = new Scanner(System.in);
        int selection = sc.nextInt();

        if (selection == 1) {
            Board board = new Board(21);
            board.print();

            System.out.print("Attempting solution...");
            SimulatedAnnealing sa = new SimulatedAnnealing(board);
            System.out.println("done.");

            System.out.println("Valid Solution: " + sa.validSolution);
            System.out.println("Search Cost: " + sa.cost);

            if (sa.validSolution)
                Board.print(sa.solutionPositions);
        } else if (selection == 2) {
            int successes = 0;
            int failures = 0;
            long cumulativeTime = 0;

            for (int i = 0; i < 500; i++) {
                long startTime = System.nanoTime();
                SimulatedAnnealing sa = new SimulatedAnnealing(new Board(21));
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                cumulativeTime += totalTime;

                if (sa.validSolution) {
                    System.out.println("SUCCESS  " + totalTime);
                    successes++;
                } else {
                    System.out.println("FAILURE  " + totalTime);
                    failures++;
                }
            }

            System.out.println();
            System.out.println("Total successes: " + successes);
            System.out.println("Total failures: " + failures);
            System.out.println("Success rate: " + ((double) successes / (successes + failures)) * 100 + "%");
            System.out.println("Average time: " + cumulativeTime / 500D / 1000000D + " ms");
        } else {
            System.out.println("Invalid selection.");
        }
    }
}
