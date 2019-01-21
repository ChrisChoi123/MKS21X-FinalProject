# MKS21X-FinalProject

**Instructions to run the cube simulator:**

1.javac -cp lanterna.jar:. Display.java
2.java -cp lanterna.jar:. Display
3.You may change the dimensions of the screen to the optimal size: 170x47, by manually resizing the SwingTerminalFrame screen that pops up when the program is run. (you can see the current dimensions by referring to numbers following the word "Dimensions:"). However, you may use the program with smaller or larger dimensions, as our program tries its best to resize the cube and center it when the screen is manually resized.
4.If you want the program to accept user friendly moves, press "1" to change the mode to mode 1. (The default mode, 0, accepts the WCA standard notation to which you can refer to this website to use: https://ruwix.com/the-rubiks-cube/notation/advanced/ ). The following instructions will be written for mode 1.
5.After you press the "1" key, letters should pop up surrounding the cube. These letters tell you which key you should press to move the corresponding row/column towards the direction the letter is, in relation to the side.
6.Turning on caps lock and then pressing one of the keys will move the corresponding row/column in the opposite direction from side the letter is located in.
7.Pressing on the backspace key undos the most recent move. Clicking it in succession continues to undo the preceding moves, all the way until you reach the orignal state of the puzzle.
8.You can press on the enter key to reset the cube to its solved state.
9.You can press on the tab key to apply a randomly generated scramble to the puzzle. The scramble will appear on the top of the screen. This notation is strictly in the official WCA notation.
10.When you click on the tab key or enter key, the timer will start as soon as the first valid move (a move that manipulates the puzzle) is pressed, and will continue counting until the cube is solved.
11.When you have explored the functionalities of the simulator to your heart's content, or have successfully solved the puzzle, you may exit the program by pressing the escape key.


**Developmental Log**
1/3 Created a constructor that creates a char[][] filled with the respective colors for the 6 sides. Also a toString() that returns the contents in the char[][].

1/4 Created a rotateSide() method that rotate the stickers of a single side when a clockwise turn is applied. This is the first out of 2 private methods that will be used for a complete rotate layer method.

1/5 Successfully created the rotate method that performs a clockwise rotation of a layer of the puzzle. It required 4 private methods, getSidesAdjacent() and getValuesSurrounding and cycleArray() which were helper functions for rotateAround(). Finally, the rotate() method used 2 helpers, the rotateSide() from yesterday and rotateAround(). Together, these two methods perform the necessary arrangement of the data that correctly visualizes a 90 degree turn.

1/6 Created a slice method that performs one of 3 middle layer moves. It used 2 helper functions. We implemented the rotate and slice methods into a normalMove function that performs all of the single move turns (there are 12 different moves). We used this method for the inverseMove and doubleMove functions. We also created a function that takes a scramble, converts it to an array, and then performs the necessary moves.

1/7 Created a reset method and isSolved method. We will be spending some time figuring out how to visualize the cube using laterna.jar.

1/8 Created lanterna.jar and Display.java to be used for displaying the puzzle. We tried using lanterna 2 at first, but it was glitching on our windows gitbash, so we decided to use lanterna 3, and thanks to Ethan Morgan in APCS google groups, we successfully got lanterna to work properly on gitbash. 2 new methods, getColor and getSticker were created in Cube.java to return a TextCharacter with a correct background color to add to the screen.

1/9 With the help of 3 helper functions that displayed an individual sticker, displayed a side, and displayed the entire cube, we were able to successfully visually represent the Rubik's Cube on the screen. We created the generateScramble() method that generates a random sequence of moves, with a specified length, was created and implemented successfully. The screen also displays the scramble when it is generated.

1/10 We created a method changeCube to receive a KeyStroke and perform the move on the cube. We were having trouble extracting the move from the KeyStroke, but we later figured out that it was because the keyStroke toString method converted it to a very long string with unnecessary information. Also, since any key press registers as a keystroke, we had to figure out a way to prevent any invalid keypress from turning the cube.

1/11 We found a way to get the size of the terminal by using TerminalSize class. To find the appropriate size of the simulation of the cube, the ratio between the length and width of each cell had to be determined. Using a centimeter ruler, we figured out that the ratio between length and width of the spaces in the screen was 17 to 9. From this, we determined what the length should be for each integer value of the height (since width is shorter than the height, the dimensions are dependent on the height). Using this information, a giveSize() method was created that received the TerminalSize and returned the dimensions each sticker of the cube should be so that the sides resembled a square as closely as possible.

1/12 We created giveStartingPosition() that gave the x and y coordinates that the cube's upper-leftmost coordinate should be at, depending on its size and the dimensions of the screen. We used this method and giveSize() to update the drawSticker, drawSide, and drawCube methods to try any cube given its sizes and starting positions. We used these updated draw methods in the static void main method to draw the cube at an appropriate size when the user changes the size of the screen. We also allowed the user to click enter to reset the cube, and click the tab key to scramble the cube with a randomly generated scramble. Also, we allowed the user to undo a move by using an array userMoves to store the moves the user inputs after the cube is either reset or scrambled.

1/13 We used this day to prepare for the presentation of the demo. We did not want to mess with the code, so we made sure to make all of our final changes to the master branch (which is just our normal branch since we didn't use separate branches).

1/14 We discussed on how we were going to implement the timer to our program. We decided that the timer would start when the user performs the a move for the first time from a scrambled position after pressing on tab. Also, the timer would reset when the cube is scrambled again, by clicking on tab, or reset by clicking on the enter key. We weren't sure on how to display the timer, whether in seconds or centiseconds, so we thought that there should be a way to customize that.

1/15 We created 3 new long variables in the static void main to store the time starting from when the first move is made and continuously updates. 2 boolean variables were made to keep track of when the cube was being scrambled or reset for the first time, as we want the continuous updating of the timer to happen only when the first move has been pressed. When we first tried to keep track of this, we only used 1 variable, firstMove, that becomes true when the first move is pressed, but we found that when moves are performed and then subsequently all undone, the timer resets back to 0 when the next move is made (we want the timer to keep going until the cube is solved or its reset/scrambled again). We had to create another boolean, firstReset that turns to true whenever any move was made to fix this problem.

1/16 We displayed whether the capslocks is on or off on the screen. After the demo, we had a feedback regarding the feasibility of interface. We will add different keys so that the user can solve easily cube without necessarily knowing Rubik's Cube notation.

1/18: We created a convertMove method that converts the standard notation to user friendly key.

1/19 We introduced the modes 0 and 1 that accepts a different set of keys to manipulate the cube. Mode 0 accepts the standard WCA notation moves (like F,B,U,D,R,L, and so on) while mode 1 accepts user friendly keys (starting from "q" and all the way to "m") that correspond to the row or column they are moving.

1/21 We displayed the letters next to the row of the cube that they correspond to in mode 1. This allows the user to see which keys move which sides. Also, we fixed the problem with the keys of the same row/column going in different directions. This is because the middle layer moves follows the directions of one of the sides, so the other side will necesarily be going in the opposite direction as the other two. So we added an apostrophe (representing the inverse move) to the one going in the different direction to the other two.  Then we cleaned the main method by removing redundent code and we added comments to many of the variables, if statements, and loops to explain what they do.
