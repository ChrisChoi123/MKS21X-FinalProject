public class Cube{
  private char[][] data;


  public Cube(){
    data = new char[6][9];
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
        output += ", ";
      }
    }
    return output+="]";
  }

  public static void main(String args[]){
    Cube c1 = new Cube();
    System.out.print(c1);
  }

  private void rotate(int face){
      char color0 = data[face][0];
      char color1 = data[face][1];
      char color2 = data[face][2];
      char color3 = data[face][3];
      char color4 = data[face][4];
      char color5 = data[face][6];
      char color6 = data[face][7];
      char color7 = data[face][8];

      data[face][0] = color5;
      data[face][1] = color3;
      data[face][2] = color0;
      data[face][3] = color6;
      data[face][5] = color6;
      data[face][6] = color1;
      data[face][7] = color7;
      data[face][8] = color2;
  }


}
