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

  private void rotateSide(int face){
    // The 90 degree rotation of a side of a cube moves the pieces around
    // irregularily. so we resort to a manual method of moving the colors around
    char color0 = data[face][0];
    char color1 = data[face][1];
    char color2 = data[face][2];
    char color3 = data[face][3];
    char color4 = data[face][5];
    char color5 = data[face][6];
    char color6 = data[face][7];
    char color7 = data[face][8];
    data[face][0] = color5;
    data[face][1] = color3;
    data[face][2] = color0;
    data[face][3] = color6;
    data[face][5] = color1;
    data[face][6] = color7;
    data[face][7] = color4;
    data[face][8] = color2;
    //The color at index 4 is ommited from this method because it is located at
    //the center and does not move
  }

  private static int[] getSidesAdjacent(int index){
    if (index == 0) {
      return new int[] {3,2,1,4};
    }
    else if (index == 1) {
      return new int[] {0,2,5,4};
    }
    else if (index == 2) {
      return new int[] {3,5,1,0};
    }
    else if (index == 3) {
      return new int[] {5,2,0,4};
    }
    else if (index == 4) {
      return new int[] {3,0,1,5};
    }
    else {
      return new int[] {3,4,1,2};
    }
  }
  public static int[] cycleValues(int[] ary) {
    int[] output = new int[ary.length];
    output[0] = ary[9];
    output[1] = ary[10];
    output[2] = ary[11];
    for (int i = 0;i < 9;i++) {
      output[i+3] = ary[i];
    }
    return output;

  }

  private void rotateAround(int index) {
    if (index == 0) {
      int[] valuesToRotate = new int[] {3,5,1,0};
    }
    else if (index == 0) {
      int[] valuesToRotate = new int[] {3,5,1,0};
    }
  }

  public static void main(String args[]){
    Cube c1 = new Cube();
    System.out.println(c1);
    c1.rotateSide(1);
    System.out.println(c1);
    int[] output = new int[] {0,1,2,3,4,5,6,7,8,9,10,11};
    cycleValues(output);
    for (int i = 0;i < cycleValues(output).length;i++) {
      System.out.println(cycleValues(output)[i]);
    }
  }


}
