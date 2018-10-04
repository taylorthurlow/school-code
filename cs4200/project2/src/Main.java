public class Main {
    public static void main(String[] args) {
	    Board board = new Board(5);

	    board.print();

	    System.out.println("Attacking pairs: " + board.attackingQueenPairs());
    }
}
