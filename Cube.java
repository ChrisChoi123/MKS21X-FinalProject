public class Cube{
  private char[][] data;

  /**Constructs the main cube used for the simulation, in a solved state.
    *Creates a 2d array of characters representing the first letter of the colors
    *of the standard Rubik's Cube, White, Green, Red, Blue, Orange, Yellow.
    *Order of the chars are mostly abitrary.
    */
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

  /**Converts the data to String, so troubleshooting code is possible.
    */
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
  /**Rotates the chars of a particular side by 90 degrees.
    *It is one part of visualising the rotation of a layer.
    *
    *@param face is the index of the face being rotated.
    */
  private void rotateSide(int face){
    /*The 90 degree rotation of a side of a cube moves the pieces around
     *irregularily. so we resort to a manual method of moving the colors around.
     */
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
    /*The color at index 4 is ommited from this method because it is located at
     *the center and does not move
     */
  }

  /**It manually determines the faces that are adjacent to a given side.
    *There are 6 sides on a Rubik's Cube, so there are 6 if statements, 1 for each case.
    *
    *@param index is the index of the face that the method is determining the adjacent sides for.
    *@return the 4 adjacent sides of a given side. Returns them starting from the top one, relative
    *        to the side, and goes in clockwise order.
    */
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

  /**It manually determines the indices that surround a given side.
    *Each side of a Rubik's Cube is adjacent to 4 different sides, and each of those
    *sides have 3 stickers that border the edge of the original side. Since our way of
    *storing the data of each side isn't the same orientation for each side, the order
    *of the indices of the stickers is unique for each side.
    *
    *@param index is the index of the face that the method is determining the surrounding indices for.
    *@return the 12 indices that surround the side, starting from the top left, relatively, and
    *        going in a clockwise fashion around the given side.
    */
  private static int[] getValuesSurrounding(int index) {
    if (index == 0) {
      return new int[] {6,7,8,0,3,6,2,1,0,8,5,2};
    }
    else if (index == 1) {
      return new int[] {6,7,8,6,7,8,6,7,8,6,7,8};
    }
    else if (index == 2) {
      return new int[] {8,5,2,0,3,6,8,5,2,8,5,2};
    }
    else if (index == 3) {
      return new int[] {2,1,0,2,1,0,2,1,0,2,1,0};
    }
    else if (index == 4) {
      return new int[] {0,3,6,0,3,6,0,3,6,8,5,2};
    }
    else {
      return new int[] {2,1,0,0,3,6,6,7,8,8,5,2};
    }
  }

  /**Shifts a given array by 3 indices to the right. The last 3 elements of the array
    * go to the front. This method is used in the rotateAround() method to cycle the
    *array returned by getValuesSurrounding() to make them have the same index when
    *being called.
    *
    *@param ary is the int[] array that the method will shift.
    *@return the shifted int[] array
    */
  private static int[] cycleArray(int[] ary) {
    int[] output = new int[ary.length];
    output[0] = ary[9];
    output[1] = ary[10];
    output[2] = ary[11];
    for (int i = 0;i < 9;i++) {
      output[i+3] = ary[i];
    }
    return output;
  }

  /**Rotates the chars of the stickers directly bordering the edge of a given side.
    *The rotation of the chars is done by storing the last 3 values in variables, then
    *looping down the list of values.
    *The shifting is done in sets of 3, one for each sticker of a bordering side.
    *
    *@param index is the index of the side this method will cycle through.
    *
    */
  private void rotateAround(int index) {
    int[] valsSur = getValuesSurrounding(index);
    int[] cycledValsSur = cycleArray(valsSur);
    int[] sidesAdj = getSidesAdjacent(index);
    char storage0 = data[sidesAdj[3]][valsSur[9]];
    char storage1 = data[sidesAdj[3]][valsSur[10]];
    char storage2 = data[sidesAdj[3]][valsSur[11]];
    int position = 11;
    for (int i = 3; i > 0;i--) {
      for (int j = 0; j < 3;j++) {
        data[sidesAdj[i]][valsSur[position]] = data[sidesAdj[i-1]][cycledValsSur[position]];
        position--;
      }
    }
    data[sidesAdj[0]][valsSur[0]] = storage0;
    data[sidesAdj[0]][valsSur[1]] = storage1;
    data[sidesAdj[0]][valsSur[2]] = storage2;
  }

  /**Performs the full 90 degrees clockwise rotation of a layer, by using the two helper methods.
    *The first one rotates just the stickers on the side while the other method rotates the stickers
    *around the side. The order of the two methods is irrelevent, since they are independent of each other.
    *
    *@param index is the index of the side being rotated.
    *
    */
  public void rotate(int index) {
    rotateSide(index);
    rotateAround(index);
  }

  private int[] getSidesAlongOrbit(int index) {
    if (index == 0) {
      return new int[] {0,1,5,3};
    }
    else if (index == 1) {
      return new int[] {1,2,3,4};
    }
    else {
      return new int[] {0,2,5,4};
    }
  }

  private int[] getValuesAlongOrbit(int index) {
    if (index == 0) {
      return new int[] {1,4,7,1,4,7,7,4,1,1,4,7};
    }
    else if (index == 1) {
      return new int[] {3,4,5,7,4,1,5,4,3,1,4,7};
    }
    else {
      return new int[] {3,4,5,3,4,5,3,4,5,3,4,5};
    }
  }

  public void slice(int index) {
    int[] valsOrb = getValuesAlongOrbit(index);
    int[] cycledValsOrb = cycleArray(valsOrb);
    int[] sidesOrb = getSidesAlongOrbit(index);
    char storage0 = data[sidesOrb[3]][valsOrb[9]];
    char storage1 = data[sidesOrb[3]][valsOrb[10]];
    char storage2 = data[sidesOrb[3]][valsOrb[11]];
    int position = 11;
    for (int i = 3; i > 0;i--) {
      for (int j = 0; j < 3;j++) {
        data[sidesOrb[i]][valsOrb[position]] = data[sidesOrb[i-1]][cycledValsOrb[position]];
        position--;
      }
    }
    data[sidesOrb[0]][valsOrb[0]] = storage0;
    data[sidesOrb[0]][valsOrb[1]] = storage1;
    data[sidesOrb[0]][valsOrb[2]] = storage2;
  }

  /**Used for testing the functionality of methods at certain points.
    *
    */
  public static void main(String args[]){
    Cube c1 = new Cube();
    System.out.println(c1);
    c1.slice(1);
    System.out.println(c1);
  }
}
