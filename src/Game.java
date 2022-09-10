import java.util.Scanner;

public class Game {
  private static final char LOCAL_MODE = 'l';
  private static final char AI_MODE = 'a';

  private Scanner console = new Scanner(System.in);

  public void start() {
    System.out.println("Start a game of Tik Tac Toe");
    System.out.println("Select game mode:");
    System.out.println(String.format("- %s for local play", LOCAL_MODE));
    System.out.println(String.format("- %s for ai opponent", AI_MODE));

    selection: while(true) {
      char input = console.nextLine().charAt(0);

      switch(input) {
        case LOCAL_MODE:
          localMode();
          break selection;
        case AI_MODE:
          aiMode();
          break selection;
        default:
          System.out.println("Invalid mode, try again.");
      }
    }
  }

  private void localMode() {
    System.out.println("Starting local play");
    new LocalMode(console);
  }

  private void aiMode() {
    System.out.println("Starting ai opponent");
    new AiMode(console);
  }
}
