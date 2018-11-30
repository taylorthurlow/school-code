import java.util.*;

class Main {
  public static void main(String[] args) {
    Scanner sc1 = new Scanner(System.in);
    System.out.print("Enter turn time limit in seconds: ");
    int maxSeconds = sc1.nextInt();

    System.out.println();

    Scanner sc2 = new Scanner(System.in);
    System.out.print("Is the opponent going first? (y/N): ");
    String opponentFirstInput = sc2.nextLine();

    boolean opponentFirst = false;
    if (opponentFirstInput == "y")
      opponentFirst = true;

    Game game = new Game(maxSeconds, !opponentFirst);
    game.start();
  }
}
