import java.util.Scanner;

import Chessboard.Chessboard;
import Game.Game;

public class Application {

	public static void main(String[] args) {
		Chessboard chessboard = new Chessboard();
		Game.gameManagement(chessboard);
	
	}

}
