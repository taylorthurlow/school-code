import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    boolean validSolution = false;
    int cost = 0;
    int[] solutionPositions;

    public SimulatedAnnealing(Board board) {
        ArrayList<Board> boardList = new ArrayList<>();
        double temperature = 100;
        double threshold = 1.0E-200;
        int[] childPositions;

        while (temperature > threshold) {
            childPositions = board.positions.clone();
            Random rand = new Random();
            int col = rand.nextInt(board.boardSize);
            int row = board.positions[col];
            childPositions[col] = row;

            while (childPositions[col] == row)
                childPositions[col] = rand.nextInt(board.boardSize);

            Board child = new Board(childPositions);
            cost++;

            if (!boardList.contains(child)) {
                boardList.add(child);

                int scoreDelta = board.attackingQueenPairs() - child.attackingQueenPairs();
                if (scoreDelta > 0) {
                    board = child;
                    boardList.clear();

                    if (board.attackingQueenPairs() == 0) {
                        validSolution = true;
                        solutionPositions = board.positions.clone();
                        break;
                    }
                } else if (Math.exp(scoreDelta / temperature) > rand.nextDouble()) {
                    board = child;
                    boardList.clear();
                }
            }

            temperature *= 0.995;
        }
    }
}
