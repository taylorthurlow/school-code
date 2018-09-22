package edu.tthurlow;

import java.util.ArrayList;

public class PuzzleState {
    public PuzzleState parent = null;
    public int[] values;
    public int costToReach;

    public PuzzleState(int[] values) {
        this.values = values;
    }

    public PuzzleState(int[] values, PuzzleState parent) {
        this.values = values;
        this.parent = parent;
    }

    public int getEmptyTileIndex() {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0)
                return i;
        }

        return -1;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isGoal() {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != i)
                return false;
        }

        return true;
    }

    public ArrayList<PuzzleState> pathToState() {
        if (isRoot()) {
            ArrayList<PuzzleState> path = new ArrayList<>();
            path.add(this);
            return path;
        } else {
            ArrayList<PuzzleState> path = parent.pathToState();
            path.add(this);
            return path;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PuzzleState) {
            PuzzleState puzzleState = (PuzzleState) obj;
            for (int i = 0; i < this.values.length; i++) {
                if (this.values[i] != puzzleState.values[i])
                    return false;
            }

            return true;
        }

        return false;
    }

    public void print() {
        for (int i = 0; i < 9; i++) {
            System.out.print(values[i] + " ");
            if (i == 2 || i == 5 || i == 8)
                System.out.println();
        }
    }

    public int getCostToReach() {
        return costToReach;
    }

    public void setCostToReach(int costToReach) {
        this.costToReach = costToReach;
    }
}
