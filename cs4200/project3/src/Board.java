import java.io.*;
import java.util.*;

public class Board {
  private int BOARD_SIZE = 8;
  private ArrayList<Move> moves = new ArrayList<>();

  public void addMove(Move move) {
    moves.add(move);
  }

  public int[][] board() {
    int[][] result = new int[BOARD_SIZE][BOARD_SIZE];

    for (Move move : moves)
      result[move.row - 1][move.col - 1] = move.marker;

    return result;
  }

  public boolean win() {
    int[][] board = board();
    boolean win = false;

    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        int current = board[row][col];

        if (current != 0) {
          // Vertical win
          if (row <= BOARD_SIZE - 4 &&
              board[row + 1][col] == current &&
              board[row + 2][col] == current &&
              board[row + 3][col] == current) {
            win = true;
          }

          // Horizontal win
          if (col <= BOARD_SIZE - 4 &&
              board[row][col + 1] == current &&
              board[row][col + 2] == current &&
              board[row][col + 3] == current) {
            win = true;
          }
        }
      }
    }

    return win;
  }

  public void print() {
    String toPrint = sideBySideString(boardString(), moveList(true));
    System.out.println(toPrint);
  }

  private String boardString() {
    StringBuilder result = new StringBuilder();
    result.append("  ");

    for (int i = 1; i <= BOARD_SIZE; i++)
      result.append(i + " ");

    result.append("\n");

    int letter = 65; // ascii for A
    for (int i = 0; i < BOARD_SIZE; i++) {
      result.append((char) letter + " ");

      for (int j = 0; j < BOARD_SIZE; j++) {
        char marker = intToSymbol(board()[i][j]);
        result.append(marker + " ");
      }

      result.append("\n");
      letter++;
    }

    return result.toString();
  }

  private String moveList(boolean playerFirst) {
    StringBuilder result = new StringBuilder();

    if (playerFirst)
      result.append("Player vs. Opponent");
    else
      result.append("Opponent vs. Player");

    for (int i = 0; i < moves.size(); i++) {
      String moveString = moves.get(i).toString();
      if (i % 2 == 0)
        result.append("\n  " + (i / 2 + 1) + ". " + moveString);
      else
        result.append(" " + moveString);
    }

    return result.toString();
  }

  private static char intToSymbol(int input) {
    char result;
    switch(input) {
      case 0:
        result = '-';
        break;
      case 1:
        result = 'X';
        break;
      case 2:
        result = 'O';
        break;
      default:
        result = '?';
    }

    return result;
  }

  // Modified version of StackOverflow answer:
  // https://stackoverflow.com/a/29322564/1177200
  private String sideBySideString(String s1, String s2) {
    String returnPattern = "\n";
    String between = "   ";

    // split the data into individual parts
    String[] s1a = s1.split(returnPattern);
    String[] s2a = s2.split(returnPattern);

    // find out the longest String in data set one
    int longestString = 0;
    for (String s : s1a) {
      if (longestString < s.length())
        longestString = s.length();
    }

    // loop through parts and build new string
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < s1a.length; i++) {
      b.append(s1a[i]); // string 1

      if (i < s2a.length) // string 2 with padding
        b.append(between).append(s2a[i]);

      b.append(returnPattern); // newline
    }

    return b.toString();
  }
}
