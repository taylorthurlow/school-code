import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Genetic {
    boolean validSolution = false;
    int cost = 0;
    int[] mostFit;

    public Genetic(Board board) {
        double maxSteps = 5000;
        double mutateProbability = 0.8;
        int maxPopulationSize = 1000;
        PriorityQueue<Board> population = new PriorityQueue<>(Comparator.comparingInt(a -> a.attackingQueenPairs()));
        Random rand = new Random();

        // Randomly generate our start states
        for (int i = 0; i < maxPopulationSize; i++)
            population.add(new Board(board.boardSize));

        while (maxSteps > cost && !validSolution) {
            Board[] mostFitPopulation = new Board[maxPopulationSize];
            for (int j = 0; j < maxPopulationSize; j++)
                mostFitPopulation[j] = population.poll();

            population.clear();

            // Add them all back
            for (int j = 0; j < mostFitPopulation.length; j++)
                population.add(mostFitPopulation[j]);

            PriorityQueue<Board> newPopulation = new PriorityQueue<>(Comparator.comparingInt(a -> a.attackingQueenPairs()));
            for (int i = 0; i < mostFitPopulation.length; i++) {
                int[] x = mostFitPopulation[rand.nextInt(mostFitPopulation.length)].positions;
                int[] y = mostFitPopulation[rand.nextInt(mostFitPopulation.length)].positions;

                while (x == y)
                    y = mostFitPopulation[rand.nextInt(mostFitPopulation.length)].positions;

                int[] child = reproduce(x, y, rand);

                if (rand.nextDouble() <= mutateProbability)
                    mutate(child, rand, board.boardSize);

                newPopulation.add(new Board(child));
            }

            population.addAll(newPopulation);

            mostFit = population.peek().positions;
            if (population.peek().nonAttackingQueenPairs() == Board.totalQueenPairs(board.boardSize))
                validSolution = true;

            cost++;
        }
    }

    private int[] reproduce(int[] x, int[] y, Random rand) {
        int cross = rand.nextInt(x.length) + 1;
        int[] child = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            if (i <= cross)
                child[i] = x[i];
            else
                child[i] = y[i];
        }

        return child;
    }

    private void mutate(int[] toMutate, Random rand, int maxValue) {
        toMutate[rand.nextInt(toMutate.length)] = rand.nextInt(maxValue);
    }
}
