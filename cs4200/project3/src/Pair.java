public class Pair {
  int value;
  State state;

  public Pair(int value, State state) {
    this.value = value;
    this.state = state;
  }

  public Pair(Pair copyPair) {
    this.value = copyPair.value;
    this.state = new State(copyPair.state);
  }
}
