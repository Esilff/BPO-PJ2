package Appli;

import Game.Game;
import Chessboard.Chessboard;
import static Chessboard.Chessboard.INIT_LAYOUT;

public class Application {

	public static final String ENDL = System.lineSeparator();
	
	public static void main(String[] args) {
		Chessboard thatUses = new Chessboard(INIT_LAYOUT.FINALE_2R1T);
		Game game = new Game(thatUses);
		game.start();
	}

}
