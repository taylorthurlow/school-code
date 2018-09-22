import java.util.*;

public class Puzzle {
    int[] values;
    int generatedStates = 1;
    PuzzleState currentState;
    PriorityQueue<PuzzleState> frontier;
    ArrayList<PuzzleState> explored;

    public Puzzle() {
        this(generateRandomPuzzle());
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
        int[] list = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

        Random rand = new Random();
        for (int i = 0; i < list.length; i++) {
            int randomPosition = rand.nextInt(list.length);
            int temp = list[i];
            list[i] = list[randomPosition];
            list[randomPosition] = temp;
        }

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

    public ArrayList<PuzzleState> solveH1() {
        System.out.println("Solving puzzle with Heuristic 1:");
        System.out.println();
        currentState.print();

        ArrayList<PuzzleState> solution = null;

        while (!frontier.isEmpty()) {
            // Find previous move direction to make sure we don't generate that state again
//            boolean up, left, right, down;
//            up = left = right = down = false;
//            if (currentState.parent != null) {
//                if ((currentState.getEmptyTileIndex() - currentState.parent.getEmptyTileIndex()) * -1 == -3) up = true;
//                if ((currentState.getEmptyTileIndex() - currentState.parent.getEmptyTileIndex()) * -1 == -1) left = true;
//                if ((currentState.getEmptyTileIndex() - currentState.parent.getEmptyTileIndex()) * -1 == +1) right = true;
//                if ((currentState.getEmptyTileIndex() - currentState.parent.getEmptyTileIndex()) * -1 == +3) down = true;
//            }

            // Find the lowest cost node currently in our frontier
            currentState = frontier.remove();
            explored.add(currentState);

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
            if (row > 0) exploreState(-3, emptyLocation); // up
            if (row < 2) exploreState(+3, emptyLocation); // down
            if (col > 0) exploreState(-1, emptyLocation); // left
            if (col < 2) exploreState(+1, emptyLocation); // right
        }

        System.out.println("Generated: " + generatedStates);

        int largestCost = 0;
        for (PuzzleState solutionState : solution) {
            if (solutionState.costToReach > largestCost)
                largestCost = solutionState.costToReach;
        }

        System.out.println("Solution cost: " + currentState.costToReach);
        System.out.println("Largest cost in explored: " + largestCost);
        System.out.println("Smallest cost in frontier: " + frontier.peek().costToReach);
        if (largestCost > frontier.peek().costToReach) System.out.println("BROKEN!");
        return solution;
    }

    private void exploreState(int offset, int emptyLocation) {
        int[] tempValues = currentState.values.clone();
        tempValues[emptyLocation] = tempValues[emptyLocation + offset];
        tempValues[emptyLocation + offset] = 0;
        PuzzleState newState = new PuzzleState(tempValues, currentState);
        newState.setCostToReach(currentState.pathToState().size() + 1 + numberMisplacedTiles(newState.values));

        // Check goal condition
        if (newState.isGoal())
            System.out.println("FOUND SOLUTION AT STEPS: " + generatedStates);

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
