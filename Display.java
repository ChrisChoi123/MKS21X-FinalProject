import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.screen.*;
import java.io.IOException;
import java.awt.Color;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class Display {
	/**Places a given string on the screen.
    *
    *@param x is the x value of the coordinate the string starts from.
		*@param y is the y value of the coordinate the string starts from.
		*@param screen is the screen the method adds the string to.
		*@param str is the string that the method places.
    */
	public static void putString(int x, int y, Screen screen, String str) {
		for (int i = 0; i < str.length(); ++i) {
			screen.setCharacter(x+i, y, new TextCharacter(str.charAt(i)));
		}
	}

	/**Displays a single sticker, given the dimensions of the sticker to resemble
		*a square on the screen. .
    *
		*@param side is the index of the side of the Rubik's Cube the sticker is on
		*@param index is the index of the specific sticker the method is displaying
    *@param x is the x value of the sticker's upperleftmost coordinate.
		*@param y is the y value of the sticker's upperleftmost coordinate.
		*@param size is the int[] that contains the dimensions of the length and width
		*of the sticker necesary to resemble square
		*@param screen is the screen the method displays the sticker on.
		*@param cube is the Cube that the method is displaying a sticker of
		*
    */
	private static void drawSticker(int side, int index, int x, int y, int[] size, Screen screen,Cube cube) {
		int storeY = y;
		int length = size[0];
		int height = size[1];
		for (int i = 0; i < height;i++) {
			for (int j = 0; j < length;j++) {
				screen.setCharacter(x+j,storeY,cube.drawColor(side,index));
			}
			storeY++;
		}
	}

	/**Displays an entire side of the Rubik's Cube, made up of a 3x3 grid of
		*stickers and their corresponding colors.
    *
		*@param side is the index of the side of the Rubik's Cube the method is displaying
    *@param startX is the x value of the upperleftmost coordinate.
		*@param startY is the y value of the upperleftmost coordinate.
		*@param size is the int[] that contains the dimensions of the length and width
		*of each sticker necessary to resemble square
		*@param screen is the screen the method displays the side on.
		*@param cube is the Cube that the method is displaying a side of
		*
    */
	private static void drawSide(int side,int startX, int startY,int[] size, Screen screen,Cube cube) {
		int storeStartY = startY;
		int currentSticker = 0;
		int gapX = size[0];
		int gapY = size[1];
		for (int i = 0;i < 3;i++) {
			for (int j = 0;j < 3;j++) {
				drawSticker(side,currentSticker,startX+(gapX*j),storeStartY,size, screen, cube);
				currentSticker++;
			}
			storeStartY += gapY;
		}
	}

	/**Displays the entire Rubik's Cube, made up of 6 sides and laid out like
		*a sideways cross.
		*
		*@param startX is the x value of the upperleftmost coordinate of the side
		*on the top row, with an index 3.
		*@param startY is the y value of the upperleftmost coordinate of the side
		*on the top row, with an index 3.
		*@param screen is the screen the method displays the Rubik's Cube on
		*@param cube is the Cube that the method is displaying
		*
		*/
	public static void drawCube(int[] size,int[] startingPositions,Screen screen, Cube cube){
		int length = size[0];
		int height = size[1];
		int startX = startingPositions[0];
		int startY = startingPositions[1];
		drawSide(3,startX,startY, size,screen,cube);
		drawSide(4,startX-3*length,startY+3*height,size,screen,cube);
		drawSide(0,startX,startY+3*height,size,screen,cube);
		drawSide(2,startX+3*length,startY+3*height,size,screen,cube);
		drawSide(5,startX+6*length,startY+3*height,size,screen,cube);
		drawSide(1,startX,startY+6*height,size,screen,cube);
	}

	/**Given a screen, the method calculates its dimensions, and, depending on the
		*range of the screen's width, returns the appropriate dimensions of a
		*sticker that produces a cube that fits on the screen. Since each cell of the
		*screen has a width of .18 cm and .34 cm, the values for the width and length
		*can be calculated by the equation y = (17/9)x. the height can be any integer value,
		*while the length must be rounded to the closest integer
		*
		*@param screen is the Screen class that the method is taking the dimensions of
		*@return the dimensions of the sticker. First value is the width, and the 2nd
		*second value is the height.
		*/
	private static int[] getSize(Screen screen) {
		TerminalSize dimensions = screen.getTerminalSize();
		int row = dimensions.getRows();
		int col = dimensions.getColumns();
		int[] sizes = new int[] {1,1};
		if (col < 36) {
			sizes[0] = 1;
			sizes[1] = 1;
 		}
		else if (col >= 36 && col < 72) {
			sizes[0] = 2;
			sizes[1] = 1;
		}
		else if (col >= 72 && col < 108) {
			sizes[0] = 4;
			sizes[1] = 2;
		}
		else if (col >= 108 && col < 144) {
			sizes[0] = 6;
			sizes[1] = 3;
		}
		else if (col >= 144 && col < 162) {
			sizes[0] = 8;
			sizes[1] = 4;
		}
		else if (col >= 162 && col < 198) {
			sizes[0] = 9;
			sizes[1] = 5;
		}
		else if (col >= 198 && col < 234) {
			sizes[0] = 11;
			sizes[1] = 6;
		}
		else if (col >= 234) {
			sizes[0] = 13;
			sizes[1] = 7;
		}
		return sizes;
	}

	/**Gives the x and y values of the starting cell, or the upperleftmost cell of
		*of the first side drawCube draws: side 3. It centers the cube so that the
		*spaces between the topmost and bottommost sides from the edges of the screen are the same, and
		*the spaces between the leftmost and rightmost from the edges of the screen are the same.
		*
		*@param screen is the Screen class that the method is taking the dimensions of
		*@param sizes is storing the dimensions of the stickers
		*@return the x and y coordinate of the starting cell
		*
		*/
	public static int[] getStartingPositions(Screen screen, int[] sizes){
		TerminalSize dimensions = screen.getTerminalSize();
		int row = dimensions.getRows();
		int col = dimensions.getColumns();
		int[] positions = new int[2];
		int sizeOfRLSpace = (col - (3*sizes[0]*4)) / 2;
		int sizeOfUDSpace = (row - (3*sizes[1]*3)) / 2;
		positions[0] = 3*sizes[0] + sizeOfRLSpace;
		positions[1] = sizeOfUDSpace;
		return positions;
	}

	public static String convertMove(String move) {
		String result ="";
		switch (move){
			case "q":
				result = "D"; break;
			case "w":
				result = "E"; break;
			case "e":
				result = "U"; break;
			case "a":
				result = "B"; break;
			case "s":
					result = "S"; break;
			case "d":
					result = "F"; break;
			case "z":
					result = "U"; break;
			case "x":
					result = "E"; break;
			case "c":
					result = "D"; break;
			case "r":
					result = "D"; break;
			case "f":
					result = "E"; break;
			case "v":
					result = "U"; break;
			case "t":
					result = "L"; break;
			case "g":
					result = "M"; break;
			case "b":
					result = "R"; break;
			case "y":
					result = "U"; break;
			case "h":
					result = "E"; break;
			case "n":
					result = "D"; break;
			case "u":
					result = "R"; break;
			case "j":
					result = "M"; break;
			case "m":
					result = "L"; break;
			}
			return result;
		}


	/**Creates an interactive Screen that allows the user to interact with a
		*simulation of a Rubik's Cube. The user may turn different sides,
		*scramble the cube, reset the cube, customize the puzzle to their liking, and time their
		*attempts to solve it.
		*
		*@param args is the array of the user's inputs, but this program doesn't
		*actually take any user input this way.
		*
		*
		*/
	public static void main(String[] args) throws IOException {
		Screen screen = new DefaultTerminalFactory().createScreen();
		screen.startScreen();
		/**Starts a timer of the time the program has gone on for
		  */
		long tStart = System.currentTimeMillis();
		long lastSecond = 0;
		/**Creates a cube to be simulated, and draws it on the screen
		  */
		Cube cube = new Cube();
		drawCube(getSize(screen), getStartingPositions((screen),getSize(screen)),screen,cube);
		String scramble = "";

		TerminalSize originalSize = screen.getTerminalSize();
		ArrayList<String> userMoves = new ArrayList<String>();

		long timer = 0;
		long lastTime = 0;
		long currentTime = 0;
		boolean firstMove = false;
		int mode = 0;

		/**while program is running. stops running when escape is pressed
		  */
		while (true) {
			KeyStroke key = screen.pollInput();
			String keyString = "" + key;
			TerminalSize currentSize = screen.getTerminalSize();
			if (currentSize != originalSize) {
				screen.clear();
				drawCube(getSize(screen), getStartingPositions((screen),getSize(screen)),screen,cube);
				originalSize = currentSize;
				putString(0,0,screen,"Scramble: "+scramble);
				putString(1,5,screen,"Caps lock is on: "+Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)+"   ");
				putString(1,7,screen,"Mode: "+mode);
			}


			if (key != null) {

				if (key.getKeyType() == KeyType.Escape) break;
				else if (key.getKeyType() == KeyType.Character) {
					// stores the String of the key that was pressed
					String letterPressed = ""+keyString.charAt(keyString.length()-3);
					// stores the all the valid moves the user can make
					String[] validMoves0 = new String[] {"F","B","U","D","R","L","f","b","u","d","r","l","M","S","E","x","y","z"};
					String[] validMoves1 = new String[] {"q","a","z","w","s","x","e","d","c","r","f","v","t","g","b","y","h","n","u","j","m"};
					boolean letterIsValid = false;
					if (letterPressed.equals("0")) {
						mode = 0;
						putString(1,5,screen,"Mode: "+mode);
					}
					if (letterPressed.equals("1")) {
						mode = 1;
						putString(1,5,screen,"Mode: "+mode);
					}
					//checks to see if the move being made is a valid move
					if (mode == 0) {
						for (int i = 0;i < validMoves0.length;i++) {
							if (letterPressed.equals(validMoves1[i])) {
								letterIsValid = true;
							}
						}
					}
					if (mode == 1) {
						for (int i = 0;i < validMoves1.length;i++) {
							if (letterPressed.equals(validMoves1[i])) {
								letterIsValid = true;
							}
						}
					}
					//checks to see if it is the first time a move is made from a scrambled or reset position
					// . if the move being made is valid, and if the size of the amount of moves the
					// user made is 0
					if (letterIsValid && userMoves.size() == 0) {
						firstMove = true;
						lastTime = System.currentTimeMillis() / 1000;
					}
					//adds the user move to an array, and then performs the move
					if (mode == 0) {
						for (int i = 0;i<validMoves0.length;i++) {
							if (letterPressed.equals(validMoves0[i])) {
								userMoves.add(letterPressed);
								cube.performMove(letterPressed);
							}
						}
					}
					if (mode == 1)	{
						for (int i = 0;i<validMoves1.length;i++) {
							if (letterPressed.equals(validMoves1[i])) {
								userMoves.add(convertMove(letterPressed));
								cube.performMove(convertMove(letterPressed));
							}
						}
					}

				}

				else if (key.getKeyType() == KeyType.Backspace) {
					int tempSize = userMoves.size();
					//performs the last move made by the user 3 times (which is essentially performing the inverse)
					// and then removes the move from the array once it has been performed
					if (tempSize > 0) {
						for (int i = 0; i < 3;i++) {
							cube.performMove(userMoves.get(userMoves.size()-1));
						}
						userMoves.remove(userMoves.size()-1);
					}
				}
				else if (key.getKeyType() == KeyType.Enter) {
					userMoves.clear();
					cube.reset();
					screen.clear();
					putString(0,0,screen,"Scramble: ");
					firstMove = false;
				}
				else if (key.getKeyType() == KeyType.Tab) {
					scramble = cube.generateScramble(2);
					cube.reset();
					cube.performMoveSet(scramble);
					userMoves.clear();
					screen.clear();
					putString(0,0,screen,"Scramble: "+scramble);
					firstMove = false;
				}
				drawCube(getSize(screen), getStartingPositions((screen),getSize(screen)),screen,cube);
			}
			putString(1,5,screen,"Caps lock is on: "+Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)+"   ");
			putString(1,7,screen,"Mode: "+mode);

			if (!firstMove) {
				timer = 0;
			}
			//when the first valid move, after the cube has been reset or scrambled,
			// has been performed, this continuously updates the timer with the
			//difference between the time when the first move was made, and the
			//current time in seconds.
			if (firstMove && !cube.isSolved()){
				currentTime = System.currentTimeMillis() / 1000;
        timer = (currentTime - lastTime);//add the amount of time since the last frame.
        putString(1,3,screen, "Timer: "+ timer);
			}

			long tEnd = System.currentTimeMillis();
			long millis = tEnd - tStart;
			if (millis / 1000 > lastSecond) {
				lastSecond = millis / 1000;
			}
			screen.doResizeIfNecessary();
			screen.refresh();
	 	}
		screen.stopScreen();
	}
}
