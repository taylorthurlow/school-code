package edu.tthurlow;

import java.util.ArrayList;
import java.util.Random;

public class Puzzle {
    int[] values;
    int generatedStates = 1;
    PuzzleState currentState;
    ArrayList<PuzzleState> frontier;
    ArrayList<PuzzleState> explored;

    public Puzzle() {
        this(generateRandomPuzzle());
    }

    public Puzzle(int[] values) {
        this.values = values;
        currentState = new PuzzleState(values);
        currentState.setCostToReach(numberMisplacedTiles(values));

        frontier = new ArrayList<>();
        explored = new ArrayList<>();

        frontier.add(currentState);
    }

    private static int[] generateRandomPuzzle() {
        int[] list = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        Random rand = new Random();
        for (int i = 0; i < list.length; i++) {
            int randomPosition = rand.nextInt(list.length);
            int temp = list[i];
            list[i] = list[randomPosition];
            list[randomPosition] = temp;
        }

//        return list;
        return new int[] {1, 4, 2, 6, 7, 5, 8, 3, 0};
    }

    private static boolean checkSolvable(int[] puzzle) {
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
            PuzzleState lowest = frontier.get(0);
            for (PuzzleState state : frontier) {
                if (state.costToReach < lowest.costToReach)
                    lowest = state;
            }
            currentState = lowest;

            // Check goal condition
            if (currentState.isGoal()) {
                solution = currentState.pathToState();
                break;
            }

            frontier.remove(currentState);
            explored.add(currentState);

            // Expand the state to other possible subsequent states
            // Can "move empty tile" up, left, right, or down depending on location
            int emptyLocation = currentState.getEmptyTileIndex();

            // Check each adjacent tile
            if (emptyLocation - 3 >= 0) {
                exploreState(-3, emptyLocation);
                generatedStates++;
            }

            if (emptyLocation + 3 <= 8) {
                exploreState(+3, emptyLocation);
                generatedStates++;
            }

            if (emptyLocation - 1 >= 0) {
                exploreState(-1, emptyLocation);
                generatedStates++;
            }

            if (emptyLocation + 1 <= 8) {
                exploreState(+1, emptyLocation);
                generatedStates++;
            }
        }

        System.out.println("generated: " + generatedStates);
        return solution;
    }

//    public ArrayList<PuzzleState> solveH2() {
//
//    }

    private void exploreState(int offset, int emptyLocation) {
        int[] tempValues = currentState.values.clone();
        tempValues[emptyLocation] = tempValues[emptyLocation + offset];
        tempValues[emptyLocation + offset] = 0;
        PuzzleState newState = new PuzzleState(tempValues, currentState);

        if (explored.indexOf(newState) == -1) {
            newState.setCostToReach(currentState.costToReach + 1 + numberMisplacedTiles(newState.values));
            frontier.add(newState);
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
