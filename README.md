# MKS21X-FinalProject
Developmental Log:
1/3 Created a constructor that creates a char[][] filled with the respective colors for the 6 sides. Also a toString() that returns the contents in the char[][].

1/4 Created a rotateSide() method that rotate the stickers of a single side when a clockwise turn is applied. This is the first out of 2 private methods that will be used for a complete rotate layer method.

1/5 Successfully created the rotate method that performs a single clockwise rotation of a side. It required 4 private methods, getSidesAdjacent() and getValuesSurrounding and cycleArray() which were helper functions for rotateAround(). Finally, the rotate() method used 2 helpers, the rotateSide() from yesterday and rotateAround(). Together, these two methods perform the necessary arrangement of the data that correctly visualizes a 90 degree turn.
