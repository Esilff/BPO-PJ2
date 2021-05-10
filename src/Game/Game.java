package Game;

import java.util.Scanner;

import Chessboard.Chessboard;
import Piece.BadMoveException;

public class Game {
	
	public static Boolean whiteLoosed = false;
	public static Boolean blackLoosed = false;
	
	public static void gameManagement (Chessboard chessboard) {
		System.out.println(beginningTxt());
		while (!whiteLoosed && !blackLoosed) {
			turnManagement(chessboard);
		}
		System.out.println(looserTxt());
	}
	
	public static void turnManagement(Chessboard chessboard) {
		System.out.println(chessboard.toString());
		/*System.out.println(turnTxt(choosenOne));*/
		boolean showErrorPrompt = false;
		boolean isWhite = true;
		int count = 0;
		while(count < 2) {
			System.out.println(turnTxt(isWhite) + "\n");
			System.out.print((showErrorPrompt)? "#> ":"> ");
			Scanner sc = new Scanner(System.in);
			String s = sc.next();

			try {
				chessboard.play(s.substring(0, 2), s.substring(2, 4), isWhite);
				isWhite = false;
				count++;
				showErrorPrompt = false;
			} catch (BadMoveException e) {
				showErrorPrompt = true;
				
			}
			System.out.println(chessboard.toString());
		}
	}
	
	private static String beginningTxt() {
		String s = "The match has begun ";
		return s;
	}
	
	private static String turnTxt(Boolean isWhite) {
		String s ="";
		if (isWhite) s += "White turn has started";
		else s += "Black turn has started";
		return s;
	}

	private static String looserTxt() {
		String s ="";
		if (whiteLoosed) s+= "The White side loosed";
		else s+= "The black side loosed";
		return s;
	}
	
}
