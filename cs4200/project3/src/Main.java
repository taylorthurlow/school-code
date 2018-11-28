class Main {
  public static void main(String[] args) {
    Board theBoard = new Board();

    // Need to ask who moves first here, player or opponent

    theBoard.addMove(new Move(1, 1, 4));
    theBoard.addMove(new Move(2, 2, 4));
    theBoard.addMove(new Move(1, 3, 4));
    theBoard.addMove(new Move(2, 4, 4));
    theBoard.print();

    System.out.println("Win?: " + theBoard.win());
  }
}
