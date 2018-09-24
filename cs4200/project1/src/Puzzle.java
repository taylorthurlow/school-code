import java.util.*;

public class Puzzle {
    int[] values;
    int generatedStates = 1;
    boolean allowLargeSolutions = true;
    PuzzleState currentState;
    PriorityQueue<PuzzleState> frontier;
    ArrayList<PuzzleState> explored;

    public Puzzle() {
        this(generateRandomPuzzle());
    }

    public Puzzle(Puzzle copyPuzzle) {
        this(copyPuzzle.values);
    }

    public Puzzle(int[] values) {
        this.values = values;
        currentState = new PuzzleState(values);
        currentState.setCostToReach(0);

        frontier = new PriorityQueue<>(Comparator.comparingInt(a -> a.costToReach));
        explored = new ArrayList<>();

        frontier.add(currentState);
    }

    private static int[] generateRandomPuzzle() {
        int[] list;
        do {
            list = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

            Random rand = new Random();
            for (int i = 0; i < list.length; i++) {
                int randomPosition = rand.nextInt(list.length);
                int temp = list[i];
                list[i] = list[randomPosition];
                list[randomPosition] = temp;
            }
        } while (!checkSolvable(list));


        return list;
    }

    public static boolean checkSolvable(int[] puzzle) {
        int inversions = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[j] != 0 && puzzle[i] != 0 && puzzle[j] < puzzle[i])
                    inversions++;
            }
        }

        return inversions % 2 == 0;
    }

    public ArrayList<PuzzleState> solve(boolean useManhattan) {
        ArrayList<PuzzleState> solution = null;

        while (!frontier.isEmpty()) {
            // Find the lowest cost node currently in our frontier
            currentState = frontier.remove();
            explored.add(currentState);

            // Cancel the entire thing if we've exceeded depth 22
            if (!allowLargeSolutions && currentState.pathToState().size() - 1 > 22) {
                return null;
            }


            // Check goal condition
            if (currentState.isGoal()) {
                solution = currentState.pathToState();
                break;
            }

            // Expand the state to other possible subsequent states
            // Can "move empty tile" up, left, right, or down depending on location
            int emptyLocation = currentState.getEmptyTileIndex();
            int row = emptyLocation / 3;
            int col = emptyLocation % 3;

            // Check each adjacent tile
            if (row > 0) exploreState(-3, emptyLocation, useManhattan); // up
            if (row < 2) exploreState(+3, emptyLocation, useManhattan); // down
            if (col > 0) exploreState(-1, emptyLocation, useManhattan); // left
            if (col < 2) exploreState(+1, emptyLocation, useManhattan); // right
        }

        int largestCost = 0;
        for (PuzzleState solutionState : solution) {
            if (solutionState.costToReach > largestCost)
                largestCost = solutionState.costToReach;
        }

        return solution;
    }

    private void exploreState(int offset, int emptyLocation, boolean useManhattan) {
        int[] tempValues = currentState.values.clone();
        tempValues[emptyLocation] = tempValues[emptyLocation + offset];
        tempValues[emptyLocation + offset] = 0;
        PuzzleState newState = new PuzzleState(tempValues, currentState);
        if (useManhattan)
            newState.setCostToReach(currentState.pathToState().size() + 1 + manhattanDistance(newState.values));
        else
            newState.setCostToReach(currentState.pathToState().size() + 1 + numberMisplacedTiles(newState.values));

        boolean continueExploring = true;

        Iterator it = frontier.iterator();
        while (it.hasNext()) {
            PuzzleState test = (PuzzleState) it.next();
            if (newState == test && newState.costToReach > test.costToReach) {
                continueExploring = false;
                break;
            }
        }

        if (explored.indexOf(newState) != -1) {
            PuzzleState test = explored.get(explored.indexOf(newState));
            if (newState.costToReach > test.costToReach) {
                continueExploring = false;
            }
        }

        if (continueExploring) {
            frontier.add(newState);
            generatedStates++;
        }
    }

    private static int numberMisplacedTiles(int[] puzzle) {
        int misplaced = 0;

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != 0 && puzzle[i] != i)
                misplaced++;
        }

        return misplaced;
    }

    private static int manhattanDistance(int[] puzzle) {
        int manhattan = 0;

        for (int i = 0; i < puzzle.length; i++) {
            int longDistance = Math.abs(i - puzzle[i]);
            manhattan += (longDistance % 3) + (longDistance / 3);
        }

        return manhattan;
    }
}
