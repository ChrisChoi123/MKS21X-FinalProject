# MKS21X-FinalProject
Developmental Log:
1/3 Created a constructor that creates a char[][] filled with the respective colors for the 6 sides. Also a toString() that returns the contents in the char[][].

1/4 Created a rotateSide() method that rotate the stickers of a single side when a clockwise turn is applied. This is the first out of 2 private methods that will be used for a complete rotate layer method.

1/5 Successfully created the rotate method that performs a clockwise rotation of a layer of the puzzle. It required 4 private methods, getSidesAdjacent() and getValuesSurrounding and cycleArray() which were helper functions for rotateAround(). Finally, the rotate() method used 2 helpers, the rotateSide() from yesterday and rotateAround(). Together, these two methods perform the necessary arrangement of the data that correctly visualizes a 90 degree turn.

1/6 Created a slice method that performs one of 3 middle layer moves. It used 2 helper functions. We implemented the rotate and slice methods into a normalMove function that performs all of the single move turns (there are 12 different moves). We used this method for the inverseMove and doubleMove functions. We also created a function that takes a scramble, converts it to an array, and then performs the necessary moves.

1/7 Created a reset method and isSolved method. We will be spending some time figuring out how to visualize the cube using laterna.jar.

1/8 Created lanterna.jar and Display.java to be used for displaying the puzzle. We tried using lanterna 2 at first, but it was glitching on our windows gitbash, so we decided to use lanterna 3, and thanks to Ethan Morgan in APCS google groups, we successfully got lanterna to work properly on gitbash. 2 new methods, getColor and getSticker were created in Cube.java to return a TextCharacter with a correct background color to add to the screen.

1/9 With the help of 3 helper functions that displayed an individual sticker, displayed a side, and displayed the entire cube, we were able to successfully visually represent the Rubik's Cube on the screen. We created the generateScramble() method that generates a random sequence of moves, with a specified length, was created and implemented successfully. The screen also displays the scramble when it is generated.

1/10 We created a method changeCube to receive a KeyStroke and perform the move on the cube. We were having trouble extracting the move from the KeyStroke, but we later figured out that it was because the keyStroke toString method converted it to a very long string with unnecessary information. Also, since any key press registers as a keystroke, we had to figure out a way to prevent any invalid keypress from turning the cube.
