import java.util.Scanner;

public class LocalMode extends Mode {

  public LocalMode(Scanner console) {
    super(console);
  }
  
  @Override
  protected void gameLoop() {
    Boolean winner = null;
    boolean nextMark = true;

    for (int i = 0; i < 9; i++) {
      renderField();
      winner = setMarkCoordinate(getPlayerInput(), nextMark);
      if (winner != null) {
        break;
      }
      nextMark = !nextMark;
    }
    renderField();

    if (winner == null) {
      System.out.println("Stalemate");
    } else if (winner){
      System.out.println("Player 1 won!");
    } else {
      System.out.println("Player 2 won!");
    }
  }
}
