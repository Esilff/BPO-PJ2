package Game;

import java.util.Scanner;

import Chessboard.Chessboard;

public class Game {
	
	public static void turnManagement(Chessboard chessboard) {
		System.out.println(chessboard.toString());
		while(true) {
			Scanner sc = new Scanner(System.in);
			String s = sc.next();
			chessboard.getPiece(s.substring(0, 2)).play(chessboard,s.substring(0, 2), s.substring(2, 4));
			System.out.println(chessboard.toString());
		}
		
	}
}
