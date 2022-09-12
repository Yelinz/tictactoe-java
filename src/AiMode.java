import java.util.ArrayList;
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

    // TODO calculate depth tree, cache node and alphabeta generation
    Node origin = new Node(field, true);
    // int depth = getTreeDepth(origin);
    Double val = alphabeta(origin, 8, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
    System.out.println(val);

    coordinates.put("x", 1);
    coordinates.put("y", 1);
    return coordinates;
  }

  // alpha beta pruning algorithm
  private Double alphabeta(Node node, int depth, Double alpha, Double beta, boolean player) {
    if (depth == 0 || node.possibleMoves.size() == 0) {
      Boolean winner = checkWinCondition(node.field);
      if (winner == null) {
        return 0.0;
      } else if (winner){
        return 100.0;
      } else {
        return -100.0;
      }
    }

    if (player) {
      Double value = Double.NEGATIVE_INFINITY;
      for (Node child : node.possibleMoves) {
        value = Math.max(value, alphabeta(child, depth - 1, alpha, beta, false));
        alpha = Math.max(alpha, value);
        if (value >= beta) {
          break;
        }
      }
      return value; 
    } else {
      Double value = Double.POSITIVE_INFINITY;
      for (Node child : node.possibleMoves) {
        value = Math.min(value, alphabeta(child, depth - 1, alpha, beta, true));
        beta = Math.min(beta, value);
        if (value <= alpha) {
          break;
        }
      }
      return value;
    }
  }

  static class Node {
    ArrayList<Node> possibleMoves = new ArrayList<Node>();
    Boolean[][] field;
    boolean lastPlayer;

    public Node(Boolean[][] field, boolean lastPlayer) {
      this.field = field;
      this.lastPlayer = lastPlayer;
      calculatePossibleMoves(getEmptySpots());
    }

    // TODO return the depth of the tree while constructing it
    private void calculatePossibleMoves(ArrayList<HashMap<String, Integer>> emptySpots) {
      for (HashMap<String, Integer> spot : emptySpots) {
        Boolean[][] fieldCopy = deepCopy(field);
        fieldCopy[spot.get("x")][spot.get("y")] = !lastPlayer;
        possibleMoves.add(new Node(fieldCopy, !lastPlayer));
      }
    }

    private ArrayList<HashMap<String, Integer>> getEmptySpots() {
      ArrayList<HashMap<String, Integer>> spots = new ArrayList<HashMap<String, Integer>>();
    
      for (int x = 0; x < field.length; x++ ) {
        for (int y = 0; y < field[x].length; y++) {
          if (field[x][y] == null) {
            HashMap<String, Integer> coordinate = new HashMap<String, Integer>();
            coordinate.put("x", x);
            coordinate.put("y", y);
            spots.add(coordinate);
          }
        }
      }

      return spots;
    }
  }
}
