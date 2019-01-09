# MKS21X-FinalProject
Developmental Log:
1/3 Created a constructor that creates a char[][] filled with the respective colors for the 6 sides. Also a toString() that returns the contents in the char[][].

1/4 Created a rotateSide() method that rotate the stickers of a single side when a clockwise turn is applied. This is the first out of 2 private methods that will be used for a complete rotate layer method.

1/5 Successfully created the rotate method that performs a clockwise rotation of a layer of the puzzle. It required 4 private methods, getSidesAdjacent() and getValuesSurrounding and cycleArray() which were helper functions for rotateAround(). Finally, the rotate() method used 2 helpers, the rotateSide() from yesterday and rotateAround(). Together, these two methods perform the necessary arrangement of the data that correctly visualizes a 90 degree turn.

1/6 Created a slice method that performs one of 3 middle layer moves. It used 2 helper functions. We implemented the rotate and slice methods into a normalMove function that performs all of the single move turns (there are 12 different moves). We used this method for the inverseMove and doubleMove functions. We also created a function that takes a scramble, converts it to an array, and then performs the necesary moves.

1/7 Created a reset method and isSolved method. We will be spending some time figuring out how to visualize the cube using laterna.jar.

1/8 Created lanterna.jar and Display.java to be used for displaying the puzzle. We tried using lanterna 2 for a while, but it was glitching on our windows terminals and gitbash, so we decided to use lanterna 3, and with the help of E. Morgan in google groups, we successfully got the lanterna to work properly on the windows. 2 new methods, getColor and getSticker were created in Cube.java to return a TextCharacter with a correct background color to add to the screen. 
