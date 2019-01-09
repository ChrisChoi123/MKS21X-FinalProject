import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.screen.*;
import java.io.IOException;
import java.awt.Color;

/*  Mr. K's TerminalDemo edited for lanterna 3 by Ethan
*/

public class Display {

	public static void putString(int x, int y, Screen screen, String str) {
		for (int i = 0; i < str.length(); ++i) {
			screen.setCharacter(x+i, y, new TextCharacter(str.charAt(i)));
		}
	}
	private static void drawSticker(int side, int index, int x, int y,Screen screen,Cube cube) {
		int storeY = y;
		for (int i = 0;i < 2;i++) {
			for (int j = 0; j < 3;j++) {
				screen.setCharacter(x+j,storeY,cube.drawColor(side,index));
			}
			storeY++;
		}

	}
	private static void drawSide(int side,int startX, int startY,Screen screen,Cube cube) {
		int storeStartY = startY;
		int currentSticker = 0;
		for (int i = 0;i < 3;i++) {
			for (int j = 0;j < 3;j++) {
				drawSticker(side,currentSticker,startX+(3*j),storeStartY,screen,cube);
				currentSticker++;
			}
			storeStartY += 2;
		}
	}

	public static void drawCube(int startX, int startY,Screen screen,Cube cube){
		drawSide(3,startX,startY,screen,cube);
		drawSide(4,startX-9,startY+6,screen,cube);
		drawSide(0,startX,startY+6,screen,cube);
		drawSide(2,startX+9,startY+6,screen,cube);
		drawSide(5,startX+18,startY+6,screen,cube);
		drawSide(1,startX,startY+12,screen,cube);
	}

	public static void main(String[] args) throws IOException {

		int x = 10;
		int y = 10;

		Screen screen = new DefaultTerminalFactory().createScreen();
		screen.startScreen();

		long tStart = System.currentTimeMillis();
		long lastSecond = 0;
		Cube cube = new Cube();
		drawCube(20,5,screen,cube);

		while (true) {

			TextCharacter chr = new TextCharacter(
				'\u263B',
				new TextColor.RGB((int)(255*Math.random()), (int)(255*Math.random()), (int)(255*Math.random())),
				TextColor.ANSI.DEFAULT
			);
			screen.setCharacter(x, y, chr);

			KeyStroke key = screen.pollInput();

			if (key != null) {
				screen.setCharacter(x, y, new TextCharacter(' '));

				if      (key.getKeyType() == KeyType.Escape)     break;
				else if (key.getKeyType() == KeyType.ArrowLeft)  x--;
				else if (key.getKeyType() == KeyType.ArrowRight) x++;
				else if (key.getKeyType() == KeyType.ArrowUp)    y--;
				else if (key.getKeyType() == KeyType.ArrowDown)  y++;

				putString(1, 1, screen, key+"                 ");
			}
			long tEnd = System.currentTimeMillis();
			long millis = tEnd - tStart;
			putString(1, 2, screen, "Milliseconds since start of program: "+millis);
			if (millis / 1000 > lastSecond) {
				lastSecond = millis / 1000;
				putString(1, 3, screen, "Seconds since start of program: "+millis/1000);
			}
			screen.doResizeIfNecessary();
			screen.refresh();
		}
		screen.stopScreen();
	}
}
