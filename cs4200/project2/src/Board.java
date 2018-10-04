import java.util.Random;

public class Board {
    private int boardSize;
    public int[] positions;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.positions = randomPositions(boardSize);
    }

    public Board(int[] positions) {
        this.boardSize = positions.length;
        this.positions = positions;
    }

    public Board(Board copyBoard) {
        positions = copyBoard.positions;
    }

    public void print() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (positions[j] == i)
                    System.out.print("[Q]");
                else
                    System.out.print("[ ]");
            }
            System.out.println();
        }
    }

    // STATIC METHODS

    private static int[] randomPositions(int boardSize) {
        int[] positions = new int[boardSize];
        Random random = new Random();
        for (int i = 0; i < boardSize; i++)
            positions[i] = random.nextInt(boardSize);

        return positions;
    }

    public int attackingQueenPairs() {
        int attackingPairs = 0;

        for (int i = 0; i < positions.length - 1; i++) {
            for (int j = i + 1; j < positions.length; j++) {
                // Find all horizontal attacking pairs
                if (positions[i] == positions[j])
                    attackingPairs++;

                // Find all diagonal attacking pairs
                int columnsApart = j - i;
                // Check above
                if (positions[i] == positions[j] - columnsApart)
                    attackingPairs++;
                // Check below
                if (positions[i] == positions[j] + columnsApart)
                    attackingPairs++;
            }
        }

        return attackingPairs;
    }
}
