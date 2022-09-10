import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Mode implements GameMode {
  private Boolean[][] field = new Boolean[3][3];
  private Scanner console;

  public Mode(Scanner console) {
    this.console = console;
    gameLoop();
  }

  // Overridden (implemented) function from interface (1st of 2 types of polymorphism)
  @Override
  public void renderField() {
    for (int i = 0; i < field.length; i++) {
      if (i > 0) {
        System.out.println("-----------");
      }

      String line = " ";
      for (int j = 0; j < field[i].length; j++) {
        if (field[i][j] == null) {
          line += " ";
        } else if (field[i][j]) {
          line += "X";
        } else {
          line += "O";
        }

        if (j < field[i].length - 1) {
          line += " | ";
        }
      }
      System.out.println(line);
    }
  }

  protected HashMap<String, Integer> getPlayerInput() {
    System.out.println("Input the location of the mark");

    while (true) {
      String input = this.console.nextLine();

      if (input.length() != 2) {
        System.out.println("Your input is invalid!");
        continue;
      }

      Integer x = Character.getNumericValue(input.charAt(0)) - 1;
      Integer y = Character.getNumericValue(input.charAt(1)) - 1;

      if (x + y < 0 || x + y > 4) {
        System.out.println("Your input is outside of play field!");
        continue;
      } else if (field[x][y] != null) {
        System.out.println("Your input is already occupied!");
        continue;
      }

      HashMap<String, Integer> coordinates = new HashMap<String, Integer>();
      coordinates.put("x", x);
      coordinates.put("y", y);
      return coordinates;
    }
  }

  protected Boolean setMarkCoordinate(HashMap<String, Integer> coordinates, boolean mark) {
    field[coordinates.get("x")][coordinates.get("y")] = mark;

    return checkWinCondition();
  }

  protected Boolean checkWinCondition(Boolean[][] field) {
    if(checkHorizontal(field) != null) {
      return checkHorizontal(field);
    } else if (checkVertical(field) != null) {
      return checkVertical(field);
    } else if (checkDiagonal(field) != null) {
      return checkDiagonal(field);
    }

    return null;
  }

  // Overloaded function (2nd of 2 types of polymorphism)
  protected Boolean checkWinCondition() {
    return checkWinCondition(this.field);
  }

  private Boolean checkHorizontal(Boolean[][] field) {
    Boolean winner = null;

    for (Boolean[] row : field) {
      winner = allValuesSame(row);
      if (winner != null) {
        break;
      }
    }

    return winner;
  }

  private Boolean checkVertical(Boolean[][] field) {
    Boolean[][] fieldCopy = deepCopy(field);
    rotateMatrix(fieldCopy);

    Boolean winner = null;

    for (Boolean[] row : fieldCopy) {
      winner = allValuesSame(row);
      if (winner != null) {
        break;
      }
    }

    return winner;
  }

  private Boolean checkDiagonal(Boolean[][] field) {
    int n = field.length;
    // top left to bottom right
    Boolean[] principal = new Boolean[3];
    // top right to bottom left
    Boolean[] secondary = new Boolean[3];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
          if (i == j) {
            principal[i] = field[i][j];
          } else if ((i + j) == (n - 1)) {
            secondary[i] = field[i][j];
        }
      }
    }

    if (allValuesSame(principal) != null) {
      return allValuesSame(principal);
    } else if (allValuesSame(secondary) != null) {
      return allValuesSame(secondary);
    }

    return null;
  }

  private Boolean allValuesSame(Boolean[] list) {
    Boolean prevValue = list[0];

    if (prevValue == null) {
      return null;
    }

    for (Boolean value : list) {
      if (value != prevValue) {
        return null;
      }
    }

    return prevValue;
  }

  // copy pasta section
  private void rotateMatrix(Boolean[][] matrix) {
    for (int row = 0; row < matrix.length / 2; ++row) {
      int last = matrix.length - 1 - row;
      // better name for i
      for(int i = row; i < last; ++i) {
          int offset = i - row;
          Boolean buffer = matrix[row][i]; // save top
          // left -> top
          matrix[row][i] = matrix[last - offset][row];          
          // bottom -> left
          matrix[last-offset][row] = matrix[last][last - offset]; 
          // right -> bottom
          matrix[last][last - offset] = matrix[i][last]; 
          // top -> right
          matrix[i][last] = buffer; // right <- saved top
      }
    }
  }

  public static Boolean[][] deepCopy(Boolean[][] original) {
    final Boolean[][] result = new Boolean[original.length][];
    for (int i = 0; i < original.length; i++) {
        result[i] = Arrays.copyOf(original[i], original[i].length);
    }
    return result;
  }

  // abstract methods for child classes to implement
  protected abstract void gameLoop();
}
