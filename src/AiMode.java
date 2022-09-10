import java.util.HashMap;
import java.util.Scanner;

public class AiMode extends Mode {

  public AiMode(Scanner console) {
    super(console);
  }
  
  @Override
  protected void gameLoop() {
    Boolean winner = null;
    boolean nextMark = true;

    for (int i = 0; i < 9; i++) {
      renderField();

      HashMap<String, Integer> coordinates;
      if (nextMark) {
        coordinates = getPlayerInput();
      } else {
        coordinates = getAiInput();
      }

      winner = setMarkCoordinate(coordinates, nextMark);
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

  private HashMap<String, Integer> getAiInput() {
    HashMap<String, Integer> coordinates = new HashMap<String, Integer>();

    // TODO calculate best location

    coordinates.put("x", 1);
    coordinates.put("y", 1);
    return coordinates;
  }
}
