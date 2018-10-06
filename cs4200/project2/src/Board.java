import java.util.Random;

public class Board {
    public int boardSize;
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
        positions = copyBoard.positions.clone();
    }

    public void print() {
        print(positions);
    }

    public int attackingQueenPairs() {
        return attackingQueenPairs(positions);
    }

    // STATIC METHODS

    public static void print(int[] positions) {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions.length; j++) {
                if (positions[j] == i)
                    System.out.print("[Q]");
                else
                    System.out.print("[ ]");
            }
            System.out.println();
        }
        System.out.println("Attacking pairs: " + attackingQueenPairs(positions));
        System.out.println();
    }

    public static int attackingQueenPairs(int[] positions) {
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

    private static int[] randomPositions(int boardSize) {
        int[] positions = new int[boardSize];
        Random random = new Random();
        for (int i = 0; i < boardSize; i++)
            positions[i] = random.nextInt(boardSize);

        return positions;
    }
}
