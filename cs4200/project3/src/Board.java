import java.util.Arrays;

public class Board {
  int BOARD_SIZE = 8;
  int[][] board;

  public Board() {
    board = new int[BOARD_SIZE][BOARD_SIZE];
  }

  public Board(Board copyBoard) {
    this.board = Arrays.copyOf(copyBoard.board, copyBoard.board.length);
  }

  public void print() {
    System.out.print("  ");
    for(int i = 1; i <= BOARD_SIZE; i++)
      System.out.print(i + " ");

    System.out.println();

    int letter = 65; // ascii for A
    for(int i = 0; i < BOARD_SIZE; i++) {
      System.out.print((char) letter + " ");

      for(int j = 0; j < BOARD_SIZE; j++) {
        char marker = intToSymbol(board[i][j]);
        System.out.print(marker + " ");
      }

      System.out.println();
      letter++;
    }
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
}
