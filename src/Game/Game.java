package Game;

import java.util.Scanner;

import Chessboard.Chessboard;
import Chessboard.vect2D;

public class Game {
	
	private Chessboard board;
	private boolean isWhitePlaying = true;
	
	
	public static Boolean whiteLoosed = false;
	public static Boolean blackLoosed = false;
	
	public void start() {
		System.out.println(beginningTxt());
		while (!whiteLoosed && !blackLoosed) {
			turnManager();
		}
		System.out.println(looserTxt());
	}
	
	public Game(Chessboard chessboard) {
		this.board = chessboard;
	}
	
	// utile pour les tests unitaires
	public String board_toString() {
		return board.toString();
	}
	
	public void turnManager() {
		System.out.println(board_toString());
		
		boolean showErrorPrompt = false;
		isWhitePlaying = true;
		int count = 0;
		while(count < 2) {
			System.out.println(turnTxt(isWhitePlaying) + "\n");
			System.out.print((showErrorPrompt) ? "#> ":"> ");
			
			
			
			Scanner sc = new Scanner(System.in);
			String s = sc.next();

			try {
				play(s.substring(0, 2), s.substring(2, 4), isWhitePlaying);
				isWhitePlaying = false;
				count++;
				showErrorPrompt = false;
			} catch (BadMoveException e) {
				showErrorPrompt = true;
				
			}
			System.out.println(board.toString());
			
			sc.close();
		}
	}
	

	public void play(String originCoord, String newCoord, Boolean isWhite) throws BadMoveException {
		// décodage de l'entrée
		vect2D originCoordConv = vect2D.createFromChessCoord(originCoord);
		vect2D newCoordConv = vect2D.createFromChessCoord(newCoord);
		if (   originCoordConv.equals(vect2D.INVALID_VECT)
			|| newCoordConv.equals(vect2D.INVALID_VECT)) {
			throw new BadMoveException("Coordonnées invalides");
		}
		
		Ipiece toPlay = this.board.getPiece(originCoordConv);
		if (toPlay.isWhite()) {
			BadMoveException up = new BadMoveException("Vous êtes en train de déplacer la pièce de l'adversaire. How dare you ?");
			throw up; // ha ha
		}
		
		toPlay.play(this.board, originCoordConv, newCoordConv, isWhite);
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
