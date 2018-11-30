import java.util.Scanner;

public class Game {
  private int maxSeconds;
  private boolean playerGoesFirst;

  public Game(int maxSeconds, boolean playerGoesFirst) {
    this.maxSeconds = maxSeconds;
    this.playerGoesFirst = playerGoesFirst;
  }

  public void start() {
    Board theBoard = new Board();

    theBoard.state.board[3][3] = 1;
    theBoard.state.board[4][3] = 1;
    theBoard.state.board[5][3] = 1;
    theBoard.state.board[1][6] = 1;
    theBoard.state.board[2][3] = 2;
    theBoard.state.board[4][4] = 2;
    theBoard.state.board[5][4] = 2;
    theBoard.print();

    System.out.println("Score: " + theBoard.state.utility(true));
  }

  private void search(State startState) {
    int v = maxValue(startState, Integer.MIN_VALUE, Integer.MAX_VALUE);
    // return the action in successors_of(state) with value v
  }

  private int maxValue(State state, int alpha, int beta) {
    if (state.win())
      return state.utility(true);

    int v = Integer.MIN_VALUE;

    for (State successor : state.successors()) {
      v = Math.min(v, minValue(successor, alpha, beta));
      if (v >= beta)
        return v;
      alpha = Math.max(alpha, v);
    }

    return v;
  }

  private int minValue(State state, int alpha, int beta) {
    if (state.win())
      return state.utility(true);

    int v = Integer.MAX_VALUE;

    for (State successor : state.successors()) {
      v = Math.max(v, maxValue(successor, alpha, beta));
      if (v <= alpha)
        return v;
      beta = Math.min(beta, v);
    }

    return v;
  }

  private void takeTurn() {

  }
}
