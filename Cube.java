public class Cube{
  private char[][] data;

  public Cube(){
    for (int i = 0;i < 6;i++) {
      for (int j = 0;j < 9;j++) {
        if (i == 0) {
          data[i][j] = 'W';
        }
        else if (i == 1) {
          data[i][j] = 'G';
        }
        else if (i == 2) {
          data[i][j] = 'R';
        }
        else if (i == 3) {
          data[i][j] = 'B';
        }
        else if (i == 4) {
          data[i][j] = 'O';
        }
        else{
          data[i][j] = 'Y';
        }
      }
    }
  }

  public String toString(){
    String output = "[";
    for (int i = 0;i < 6;i++) {
      output += "[";
      for (int j = 0;j < 9;j++) {
        output += data[i][j];
        if (j < 8) {
          output += ", " ;
        }
      }
      output += "]";
      if (i < 5) {
        output += "], ";
      }
    }
    return output;
  }
}
