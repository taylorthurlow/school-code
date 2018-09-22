package edu.tthurlow;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("== A* Analysis of the 8-Puzzle ==");
        System.out.println("1: Generate a random puzzle and solve it");
        System.out.println("2: Provide a specific puzzle configuration");

        Scanner sc = new Scanner(System.in);
        int selection = sc.nextInt();

        if (selection == 1) {
            Puzzle puzzle = new Puzzle(new int[] {4,3,2,6,1,5,7,8,0});
            ArrayList<PuzzleState> solution = puzzle.solveH1();
            System.out.println("Solution depth: " + (solution.size() - 1));
        } else if (selection == 2) {
//            specific_solve();
        } else {
            System.out.println("Invalid selection.");
        }
    }
}
