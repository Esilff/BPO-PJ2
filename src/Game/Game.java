package Game;

import java.util.Scanner;

import Chessboard.Chessboard;
import Chessboard.vect2D;
import Piece.EmptyPiece;

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
				play(s.substring(0, 2), s.substring(2, 4));
				count++;
				showErrorPrompt = false;
			} catch (BadMoveException e) {
				showErrorPrompt = true;
				System.out.println(e.getMessage());
			}
			System.out.println(board.toString());
			
		}
	}
	

	public void play(String originCoord, String newCoord) throws BadMoveException {
		// décodage de l'entrée
		vect2D originCoordConv = vect2D.createFromChessCoord(originCoord);
		vect2D newCoordConv = vect2D.createFromChessCoord(newCoord);
		if (   originCoordConv.equals(vect2D.INVALID_VECT)
			|| newCoordConv.equals(vect2D.INVALID_VECT)) {
			throw new BadMoveException("Coordonnées invalides");
		}
		
		// Vérification si la pièce est manipulable
		Ipiece toPlay = this.board.getPiece(originCoordConv);
		Ipiece target = this.board.getPiece(newCoordConv);
		if (toPlay.isWhite() != isWhitePlaying) {
			throw new BadMoveException("Vous êtes en train de déplacer la pièce de l'adversaire. How dare you ?");
		} else if (toPlay instanceof EmptyPiece) {
			throw new BadMoveException("Vous êtes en train de déplacer du vide");
		}
		if (! (target instanceof EmptyPiece) && target.isWhite() == isWhitePlaying) {
			BadMoveException up = new BadMoveException("Cannibalisme interdit");
			throw up; // ha ha
		}
		
		toPlay.play(this, originCoordConv, newCoordConv);
		
		board.setPiece(originCoord, new EmptyPiece());
		board.setPiece(newCoord, toPlay);
		this.isWhitePlaying = ! this.isWhitePlaying;
	}
	

	/**
	 * Un algorithme qui permet de vérifier s'il n'y a aucun obstacle lors d'un déplacement horizontal, vertical ou diagonal.
	 * Assume qu'une vérification de corresponsance de déplacement a été effectuée au préalable !!! 
	 * @param originCoord
	 * @param newCoord
	 * @throws BadMoveException
	 */
	public void checkNoObstaclesInTheWay(vect2D originCoord, vect2D newCoord) throws BadMoveException {
		vect2D relativeMove = newCoord.minus(originCoord);
		vect2D step = relativeMove.generate_signum();
		vect2D i = originCoord.clone();
		while (true) {
			i.addAndApply(step);
			if (newCoord.equals(i) ) {
				break;
			}
			if(!(this.board.getPiece(i) instanceof EmptyPiece)) {
				System.out.println("Le chemin est bloqué " + i.toString());
				throw new BadMoveException("Le chemin est bloque");
			}
		}
	}
	
	/**
	 * N'est utilisé que par pion. Comme quoi on aurait pas dû le coder...
	 * @return le chessboard courant
	 */
	public Ipiece getCloneOfPiece(int line, int column) {
		return this.board.getPiece(line, column); // getPiece retourne déjà un clone...
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
