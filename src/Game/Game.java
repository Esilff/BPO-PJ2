package Game;

import java.util.Scanner;

import Chessboard.Chessboard;
import Piece.BadMoveException;

public class Game {
	
	
	public static void gameManagement (Player one, Player two, Chessboard chessboard) {
		System.out.println(beginningTxt(one,two));
		while (one.getHasLoosed() == false && two.getHasLoosed() == false) {
			turnManagement(chessboard, one);
			if (one.getHasLoosed() == false && two.getHasLoosed() == false) break; 
			turnManagement(chessboard,two);
		}
		System.out.println((one.getHasLoosed())? loosingTxt(one):loosingTxt(two));
	}
	
	public static void turnManagement(Chessboard chessboard, Player choosenOne) {
		System.out.println(chessboard.toString());
		System.out.println(turnTxt(choosenOne));
		boolean showErrorPrompt = false;
		boolean hasPlayed = false;
		while(!hasPlayed) {
			System.out.print((showErrorPrompt)? "#> ":"> ");
			Scanner sc = new Scanner(System.in);
			String s = sc.next();

			try {
				chessboard.play(s.substring(0, 2), s.substring(2, 4)); //ToDo, placer en parametre la couleur du joueur et en faire une exception lors du play
				showErrorPrompt = false;
				hasPlayed = true;
			} catch (BadMoveException e) {
				showErrorPrompt = true;
				System.out.println(chessboard.toString());
			}
			
		}
	}
	
	private static String beginningTxt(Player one, Player two) {
		String s = "The match between ";
		s += one.getName() + " and " + two.getName() + " has started !"; 
		return s;
	}
	
	private static String turnTxt(Player choosenOne) {
		String s = choosenOne.getName() + " turn has started !"; return s;
	}
	
	private static String loosingTxt(Player looser) {
		String s = looser.getName() + " has loosed ! "; return s;
	}
}
