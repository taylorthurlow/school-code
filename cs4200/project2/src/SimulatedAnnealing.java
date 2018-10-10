import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    boolean validSolution = false;
    int cost = 0;
    int[] solutionPositions;

    public SimulatedAnnealing(Board board) {
        ArrayList<Board> boardList = new ArrayList<>();
        double temperature = Math.pow(board.boardSize, 2);
        double maxSteps = 100_000;
        int[] childPositions;

        while (maxSteps >= cost) {
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
                    boardList.clear();
                    board = child;

                    if (board.attackingQueenPairs() == 0) {
                        validSolution = true;
                        solutionPositions = board.positions.clone();
                        break;
                    }
                } else if (Math.exp(scoreDelta / temperature) > rand.nextDouble()) {
                    boardList.clear();
                    board = child;
                }
            }

            double newTemp = temperature * 0.95;
            temperature = (newTemp > 0.01 ? newTemp : 0.01);
//            temperature *= 0.95;
        }
    }
}
