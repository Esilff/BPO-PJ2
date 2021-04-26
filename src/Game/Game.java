package Game;

import java.util.Scanner;

import Chessboard.Chessboard;
import Piece.BadMoveException;

public class Game {
	
	public static void turnManagement(Chessboard chessboard) {
		System.out.println(chessboard.toString());
		boolean showErrorPrompt = false;
		while(true) {
			System.out.print((showErrorPrompt)? "#> ":"> ");
			Scanner sc = new Scanner(System.in);
			String s = sc.next();

			try {
				chessboard.play(s.substring(0, 2), s.substring(2, 4));
				showErrorPrompt = false;
			} catch (BadMoveException e) {
				showErrorPrompt = true;
			}
			System.out.println(chessboard.toString());
		}
		
	}
}
