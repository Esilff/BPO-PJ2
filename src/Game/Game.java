package Game;

import java.util.Scanner;

import Chessboard.Chessboard;
import Piece.EmptyPiece;
import vec2.vec2;

public class Game {
	
	private Chessboard board;
	private boolean isWhitePlaying = true;
	private String error = "";
	public static Boolean whiteLoosed = false;
	public static Boolean blackLoosed = false;
	
	
	private static final String EMPTY_STRING = ""; // Comme si c'était une valeur qui pouvait changer ... :-(
	private static final String REGULAR_PROMPT = "> ";
	private static final String ERROR_PROMPT = "#> ";
	private static final String ENDL = System.lineSeparator();
	private static final String MSG = "Joueur %s, c'est à vous !" + ENDL;
	private static final String WHITE_NAME = "blanc";
	private static final String BLACK_NAME = "noir";
	
	public void start() {
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
		System.out.println(EMPTY_STRING); // saut de ligne
		System.out.println(board_toString());
		
		System.out.println(turnTxt(isWhitePlaying));
		if (this.error.equals(EMPTY_STRING)) {
			System.out.print(REGULAR_PROMPT);
		} else {
			System.out.println(this.error);
			System.out.print(ERROR_PROMPT);
		}
		
		String s = REGULAR_PROMPT;
		try {
			Scanner sc = new Scanner(System.in);
			s = sc.next();
			
			play(s.substring(0, 2), s.substring(2, 4));
			
			error = EMPTY_STRING;
		} catch (IndexOutOfBoundsException e) {
			error = "Merci d'entrée une coordonnée valide d'échecs";
		} catch (BadMoveException e) {
			error = s.substring(0, 2) + " : " + e.getMessage();
		} 
	}
	

	public void play(String originCoord, String newCoord) throws BadMoveException {
		// décodage de l'entrée
		vec2 originCoordConv = Chessboard.createVectFromChessCoord(originCoord);
		vec2 newCoordConv = Chessboard.createVectFromChessCoord(newCoord);
		if (   originCoordConv.equals(vec2.INVALID_VECT)
			|| newCoordConv.equals(vec2.INVALID_VECT)) {
			throw new BadMoveException("Coordonnées invalides");
		}
		
		// Vérification si la pièce est manipulable
		Ipiece toPlay = this.board.getPiece(originCoordConv);
		Ipiece target = this.board.getPiece(newCoordConv);
		if (toPlay instanceof EmptyPiece) {
			throw new BadMoveException("Vous êtes en train de déplacer du vide");
		} else if (toPlay.isWhite() != isWhitePlaying) {
			throw new BadMoveException("Vous êtes en train de déplacer la pièce de l'adversaire. How dare you ?");
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
	public void checkNoObstaclesInTheWay(vec2 originCoord, vec2 newCoord) throws BadMoveException {
		vec2 relativeMove = newCoord.minus(originCoord);
		vec2 step = relativeMove.generate_signum();
		vec2 i = originCoord.clone();
		while (true) {
			i.addAndApply(step);
			if (newCoord.equals(i) ) {
				break;
			}
			if(!(this.board.getPiece(i) instanceof EmptyPiece)) {
				throw new BadMoveException("Quelque chose bloque le chemin à "+ i.toString());
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
	
	private static String turnTxt(Boolean isWhite) {
		return String.format(MSG, (isWhite) ? WHITE_NAME : BLACK_NAME);
	}

	private static String looserTxt() {
		String s ="";
		if (whiteLoosed) s+= "The White side loosed";
		else s+= "The black side loosed";
		return s;
	}
	
}
