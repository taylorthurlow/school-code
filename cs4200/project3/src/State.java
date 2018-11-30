import java.util.*;

public class State {
  private static final int SCORE_EMPTY = 1;
  private static final int SCORE_1 = 2;
  private static final int SCORE_2 = 4;
  private static final int SCORE_3 = 8;
  int[][] board;

  public State() {
    board = new int[8][8];
  }

  public State(int[][] board) {
    this.board = board;
  }

  public State(State copyState) {
    this.board = Arrays.stream(copyState.board)
                       .map(r -> r.clone())
                       .toArray(int[][]::new);
  }

  public State(State copyState, int row, int col, int marker) {
    this.board = Arrays.stream(copyState.board)
                       .map(r -> r.clone())
                       .toArray(int[][]::new);

    this.board[row][col] = marker;
  }

  public int utility(boolean player) {
    int points = 0;

    int marker;
    if (player)
      marker = 1;
    else
      marker = 2;

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        int current = board[row][col];

        if (current == marker) {
          // Check upwards
          int numEmptyMarkers = 0;
          int numOwnMarkers = 0;
          int blockedDistance = -1;

          for (int i = 1; i < 4; i++) { // Get next 3 spaces in direction
            if (row - i >= 0) { // In bounds?
              int next = board[row - i][col];
              if (next == 0) { // no marker
                numEmptyMarkers++;
              } else if (next != marker) { // opponent marker
                blockedDistance = i; // How many spaces until blocked?
                break;
              } else { // own marker
                numOwnMarkers++;
              }
            }
          }

          points += calcPoints(blockedDistance, numEmptyMarkers, numOwnMarkers);

          // Check downwards
          numEmptyMarkers = 0;
          numOwnMarkers = 0;
          blockedDistance = -1;

          for (int i = 1; i < 4; i++) { // Get next 3 spaces in direction
            if (row + i < 8) { // In bounds?
              int next = board[row + i][col];
              if (next == 0) { // no marker
                numEmptyMarkers++;
              } else if (next != marker) { // opponent marker
                blockedDistance = i; // How many spaces until blocked?
                break;
              } else { // own marker
                numOwnMarkers++;
              }
            }
          }

          points += calcPoints(blockedDistance, numEmptyMarkers, numOwnMarkers);

          // Check leftwards
          numEmptyMarkers = 0;
          numOwnMarkers = 0;
          blockedDistance = -1;

          for (int i = 1; i < 4; i++) { // Get next 3 spaces in direction
            if (col - i >= 0) { // In bounds?
              int next = board[row][col - i];
              if (next == 0) { // no marker
                numEmptyMarkers++;
              } else if (next != marker) { // opponent marker
                blockedDistance = i; // How many spaces until blocked?
                break;
              } else { // own marker
                numOwnMarkers++;
              }
            }
          }

          points += calcPoints(blockedDistance, numEmptyMarkers, numOwnMarkers);

          // Check rightwards
          numEmptyMarkers = 0;
          numOwnMarkers = 0;
          blockedDistance = -1;

          for (int i = 1; i < 4; i++) { // Get next 3 spaces in direction
            if (col + i < 8) { // In bounds?
              int next = board[row][col + i];

              if (next == 0) { // no marker
                numEmptyMarkers++;
              } else if (next != marker) { // opponent marker
                blockedDistance = i; // How many spaces until blocked?
                break;
              } else { // own marker
                numOwnMarkers++;
              }
            }
          }

          points += calcPoints(blockedDistance, numEmptyMarkers, numOwnMarkers);
        }
      }
    }

    return points;
  }

  private static int calcPoints(int blockedDistance, int empty, int marker) {
    int points = 0;
    if (blockedDistance < 0) { // not blocked
      if (marker == 1) points += SCORE_1;
      else if (marker == 2) points += SCORE_2;
      else if (marker == 3) points += SCORE_3;
    }

    points += empty * SCORE_EMPTY;

    return points;
  }

  public boolean win() {
    boolean win = false;

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        int current = board[row][col];

        if (current != 0) {
          // Vertical win
          if (row <= 8 - 4 &&
              board[row + 1][col] == current &&
              board[row + 2][col] == current &&
              board[row + 3][col] == current) {
            win = true;
          }

          // Horizontal win
          if (col <= 8 - 4 &&
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

  public ArrayList<State> successors() {
    ArrayList<State> results = new ArrayList<>();

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        int current = board[row][col];

        if (current == 0) {
          int[][] xMove = Arrays.stream(board)
                                .map(r -> r.clone())
                                .toArray(int[][]::new);
          int[][] oMove = Arrays.stream(board)
                                .map(r -> r.clone())
                                .toArray(int[][]::new);

          xMove[row][col] = 1;
          oMove[row][col] = 2;

          results.add(new State(xMove));
          results.add(new State(oMove));
        }
      }
    }

    return results;
  }
}
