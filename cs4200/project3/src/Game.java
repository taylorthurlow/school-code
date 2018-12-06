import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.toLowerCase;

public class Game {
  private int maxSeconds;
  private boolean playerFirst;
  private Board theBoard;
  private long startTime;
  private boolean foundWin = false;
  private ArrayList<Pair> pairs = new ArrayList<>();
  private ArrayList<Pair> finalPairs = new ArrayList<>();

  public Game(int maxSeconds, boolean playerFirst) {
    this.maxSeconds = maxSeconds;
    this.playerFirst = playerFirst;
  }

  public void start() {
    theBoard = new Board(playerFirst);
    theBoard.print();

    boolean playerWon = false;
    while (!theBoard.state.win()) {
      if (playerFirst || !theBoard.state.isEmpty()) {
        Move playerMove = playerTurn();
        theBoard.state.board[playerMove.row - 1][playerMove.col - 1] = 1;
        theBoard.addMove(playerMove);

        theBoard.print();
        if (theBoard.state.win(true)) {
          playerWon = true;
          break;
        }
      }

      int[] move = promptMove(theBoard.state.board);
      theBoard.state.board[move[0]][move[1]] = 2;
      theBoard.addMove(new Move(2, move[0] + 1, move[1] + 1));
      theBoard.print();
    }

    if (playerWon)
      System.out.println("The player has won!");
    else
      System.out.println("The opponent has won!");
  }

  private boolean isValidMove(int row, int col, int[][] board) {
    // Check move is in bounds
    if (row < 0 || row >= 8 || col < 0 || col >= 8)
      return false;

    // Check move is in currently empty space
    if (board[row][col] == 1 || board[row][col] == 2) {
      System.out.println("Someone already moved there.");
      return false;
    }

    return true;
  }

  private Move playerTurn() {
    search(theBoard.state);
    return getBestMove();
  }

  private int[] promptMove(int[][] board) {
    int row = -1;
    int col = -1;

    while (!isValidMove(row, col, board)) {
      String moveIn = "";
      while(moveIn.length() != 2) {
        System.out.print("Opponent's move: ");
        moveIn = new Scanner(System.in).nextLine();
      }
      System.out.println();
      row = (int) toLowerCase(moveIn.charAt(0)) - 97;
      col = getNumericValue(moveIn.charAt(1)) - 1;
    }

    return new int[]{row, col};
  }

  private Move getBestMove() {
    State bestState = finalPairs.get(0).state;
    int bestValue = finalPairs.get(0).value;
    for (Pair p : finalPairs) {
      if (p.value > bestValue) {
        bestValue = p.value;
        bestState = p.state;
      } else if (p.value == bestValue && p.state.playerUtility() > bestState.playerUtility()) {
        bestState = p.state;
      }
    }

    Move bestMove = theBoard.state.getMove(bestState);

    // Add some randomness. Given a chosen move, 50% of the time add a row,
    // and 50% of the time add a column. This creates a 2x2 grid of probability
    // such that the odds of the marker being placed in any of the 4 spots is 25%.
    // The top-left spot is the one originally chosen.
    if (theBoard.state.isEmpty()) {
      Random r1 = new Random();
      if (r1.nextDouble() > 0.5)
        bestMove.row += 1;

      Random r2 = new Random();
      if (r2.nextDouble() > 0.5)
        bestMove.col += 1;
    }

    return bestMove;
  }

  private void search(State root) {
    startTime = System.nanoTime();
    for (int i = 1; i <= 999; i++) {
      pairs.clear();

      int result = alphaBeta(root, root, i, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

      if (result == -1) {
        System.out.println("Ran out of time.");
        break; // Returns -1 if out of time, need to stop searching
      }

      finalPairs.clear();
      for (Pair p : pairs)
        finalPairs.add(new Pair(p));

      if (finalPairs.size() > 0)
        System.out.println("Depth " + i + ", best move " + getBestMove().toString() + ", (" + elapsedSeconds() + ")");

      if (foundWin) {
        foundWin = false;
        break;
      }
    }
    System.out.println("Finished search, took " + elapsedSeconds() + " seconds.");
    System.out.println();
  }

  private int alphaBeta(State root, State state, int depth, int alpha, int beta, boolean player) {
//    if (depth == 0 || state.win(!player))
//      return state.playerUtility();

    if (state.win(!player)) {
      foundWin = true;
      if (state.childOf(root, !player))
        pairs.add(new Pair(999999999, state));

      return state.playerUtility();
    }

    if (depth == 0) {
      if (state.childOf(root, !player))
        pairs.add(new Pair(state.playerUtility(), state));

      return state.playerUtility();
    }

    ArrayList<State> successors = state.successors(player);

    int value;
    if (player) {
      value = Integer.MIN_VALUE;
      for (State child : successors) {
        value = Math.max(value, alphaBeta(root, child, depth - 1, alpha, beta, false));
        alpha = Math.max(alpha, value);

        if (outOfTime())
          return -1;

        if (alpha >= beta)
          break;
      }
    } else {
      value = Integer.MAX_VALUE;
      for (State child : successors) {
        value = Math.min(value, alphaBeta(root, child, depth - 1, alpha, beta, true));
        beta = Math.min(beta, value);

        if (outOfTime())
          return -1;

        if (alpha >= beta)
          break;
      }
    }

    if (state.childOf(root, !player))
      pairs.add(new Pair(value, state));

    return value;
  }

  private double elapsedSeconds() {
    return ((double) (System.nanoTime() - startTime)) / 1_000_000_000.0;
  }

  private boolean outOfTime() {
    return elapsedSeconds() >= maxSeconds;
  }
}
