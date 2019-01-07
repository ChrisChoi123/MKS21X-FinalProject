public class Cube{
  private char[][] data;
  /**Constructs the main cube used for the simulation, in a solved state.
    *Creates a 2d array of characters representing the first letter of the colors
    *of the standard Rubik's Cube, White, Green, Red, Blue, Orange, Yellow.
    *Order of the chars are mostly abitrary.
    */
  public Cube(){
    data = new char[6][9];
    reset();
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

  /**It manually determines the values that lie along an orbit of the cube.
    *There are 3 orbits on a Rubik's Cube, one for each direction along the
    *the x,y,and z axis.
    *
    *@param orbit is the orbit that the method is determining the sides for.
    *@return the 4 sides that lie along a given direction. Returns them starting from the lowest
    *index, and goes in clockwise order relative to the respective side determined by
    *standard cubing conventions
    *
    */
  private static int[] getSidesAlongOrbit(int orbit) {
    if (orbit == 0) {
      return new int[] {0,1,5,3};
    }
    else if (orbit == 1) {
      return new int[] {1,2,3,4};
    }
    else {
      return new int[] {0,2,5,4};
    }
  }

  /**It manually determines the indices of the values that lie along an orbit of the cube.
    *It is like tracing along an equator of the cube.
    *
    *@param orbit is the orbit that the method is determining the values for.
    *@return the 4 sides that lie along a given direction. Returns them starting from the lowest
    *index of the lowest index side, and goes in clockwise order relative to the respective side determined by
    *standard cubing conventions
    *
    */
  private static int[] getValuesAlongOrbit(int orbit) {
    if (orbit == 0) {
      return new int[] {1,4,7,1,4,7,7,4,1,1,4,7};
    }
    else if (orbit == 1) {
      return new int[] {3,4,5,7,4,1,5,4,3,1,4,7};
    }
    else {
      return new int[] {3,4,5,3,4,5,3,4,5,3,4,5};
    }
  }

  /**Performs a 90 degree slice turn by cycling the chars of the stickers
    *that run along an orbit of the puzzle.
    *The rotation of the chars is done by storing the last 3 values in variables, then
    *looping down the list of values.
    *The shifting is done in sets of 3, one for each sticker on a side.
    *
    *@param orbit is the orbit that runs around one of the 3 axes of the puzzle..
    *
    */
  public void slice(int orbit) {
    int[] valsOrb = getValuesAlongOrbit(orbit);
    int[] cycledValsOrb = cycleArray(valsOrb);
    int[] sidesOrb = getSidesAlongOrbit(orbit);
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

  /** Perform the normal move.
  Each move does a 90-degree clockwise rotation.
  *Clockwise face rotation: U(up), F(front), R(right), B(back), L(left), D(down)
  *Slice turns: M(Middle layer), E(Equatorial layer), S(Standing layer)
  *Double layer turns: u, f, r, b, l, d
  *Whole cube rotation: x(rotate the cube on the X axis), y(Y axis), z(Z axis)
  *@param move is the Rubik's Cube Notation that the method is performing the
  * move accordingly.
  */
  public void normalMove(String move){
    if (move.equals("U")){ rotate(0);}
    else if (move.equals("F")){ rotate(1);}
    else if (move.equals("R")){ rotate(2);}
    else if (move.equals("B")){ rotate(3);}
    else if (move.equals("L")){ rotate(4);}
    else if (move.equals("D")){ rotate(5);}
    //slice turns
    else if (move.equals("M")){ slice(0);}
    else if (move.equals("E")){ slice(1);}
    else if (move.equals("S")){ slice(2);}
    //double layer turns
    else if (move.equals("u")){ rotate(0); slice(1); slice(1); slice(1);}
    else if  (move.equals("f")){ rotate(1); slice(2);}
    else if (move.equals("r")){ rotate(2); slice(0); slice(0); slice(0);}
    else if (move.equals("b")){ rotate(3); slice(2); slice(2); slice(2);}
    else if (move.equals("l")){ rotate(4); slice(0);}
    else if  (move.equals("d")){ rotate(5); slice(1);}
    //rotations
    else if (move.equals("x")){ rotate(4); rotate(4); rotate(4); rotate(2); slice(0); slice(0); slice(0);}
    else if (move.equals("y")){ rotate(0); rotate(5); rotate(5); rotate(5); slice(1); slice(1); slice(1);}
    else {rotate(1); rotate(3); rotate(3); rotate(3); slice(2);}
  }

  /**Perform the inverse move.
  *Each move does a 90-degree counterclockwise rotation.
  *It is notated with prime symbol.
  *i.e) U' does a counterclockwise U-rotation.
  */
  public void inverseMove(String move){
    for (int i = 0; i < 3; i++){
      normalMove(move);
    }
  }

  /**Perform the double move.
  *Each move repeats twice in the same direction.
  *i.e) U2 does a U-rotation twice.
  */
  public void doubleMove(String move){
    for (int i = 0; i < 2; i++){
      normalMove(move);
    }
  }

  /** Perform all possible single move of Rubik's Cube
  * by using three helper methods.
  */
  public void performMove(String move){
    if (move.length() == 1){
      normalMove(move);
    }
    else if (move.substring(1,2).equals("2")){
      doubleMove(move.substring(0,1));
    }
    else{
      inverseMove(move.substring(0,1));
    }
  }

  /** Perform a series of moves
  */
  public void performMoveSet(String moves) {
    String[] movesToPerform = moves.split(" ");
    for (int i = 0; i < movesToPerform.length;i++) {
      performMove(movesToPerform[i]);
    }
  }

  /** Reset the cube to the original form.
  */
  public void reset(){
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

  /** Check if the Rubik's Cube is solved.
      Compare the color of the first piece with the rest of pieces on the same side.
  */
  public boolean isSolved(){
    boolean match = true;
    for (int i = 0; i < 6; i++){
      char color = data[i][0];
      for (int g = 0; g < 9; g++){
        if (color != data[i][g]){
          match = false;
      }
    }
  }
  return match;
}

  /**Used for testing the functionality of methods at certain points.
    *
    */
  public static void main(String args[]){
    Cube c1 = new Cube();
    String scramble = "F' L2 D2 R B2 L2 U2 R U2 R F2 R2 B2 D' B2 L F' R2 D L U";
    c1.performMoveSet(scramble);
    c1.performMove("x");
    System.out.println(c1);
    System.out.println(c1.isSolved());

    Cube c2 = new Cube();
    String scramble1 = "M U' M' U2 M U M U M2 U M'";
    c2.performMoveSet(scramble1);
    c2.performMove("x");
    System.out.println(c2);
  }
}
