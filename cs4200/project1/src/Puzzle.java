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

        Comparator<PuzzleState> stateComparator = new Comparator<>() {
            @Override
            public int compare(PuzzleState ps1, PuzzleState ps2) {
                return ps1.costToReach - ps2.costToReach;
            }
        };

        frontier = new PriorityQueue<>(stateComparator);
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
            // Find the lowest cost node currently in our frontier
            currentState = frontier.remove();

            // Check goal condition
            if (currentState.isGoal()) {
                solution = currentState.pathToState();
                break;
            }

            explored.add(currentState);

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
        return solution;
    }

    private void exploreState(int offset, int emptyLocation) {
        int[] tempValues = currentState.values.clone();
        tempValues[emptyLocation] = tempValues[emptyLocation + offset];
        tempValues[emptyLocation + offset] = 0;
        PuzzleState newState = new PuzzleState(tempValues, currentState);
        newState.setCostToReach(currentState.costToReach + numberMisplacedTiles(newState.values));
        generatedStates++;

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
        }

        // ====

//        if (explored.indexOf(newState) == -1) {     // not in explored set
//
//
//            // See if we can find this state in the queue already
//            Iterator value = frontier.iterator();
//            PuzzleState duplicateState = null;
//            while (value.hasNext()) {
//                PuzzleState testState = (PuzzleState) value.next();
//                if (testState.equals(newState))
//                    duplicateState = testState;
//            }
//
//            if (!frontier.contains(newState) || (duplicateState != null && newState.costToReach < duplicateState.costToReach)) { // not in explored set but IS in frontier set
//                frontier.add(newState);
//            } else {                                // not in explored set but IS in frontier set
//                PuzzleState actualState = frontier.get(frontier.indexOf(newState));
//                if ((newState.costToReach - numberMisplacedTiles(newState.values)) <
//                        (actualState.costToReach - numberMisplacedTiles(actualState.values))) {
//                    actualState.parent = newState.parent;
//                    actualState.costToReach = newState.costToReach;
//                }
//            }
//        }
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
